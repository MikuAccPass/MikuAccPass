package com.example.mikuaccpass;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class InfoStorage {
    /**
     * 保存账号密码
     * @param context
     * @param appname
     * @param username
     * @param password
     */
    //对数据进行加密
    public static void saveInfo(Context context,String appname, String username, String password,String appkey) {

        //得到key
        SecretKey key = InfoStorage.readKey(InfoStorage.getPath(appkey));
        if (key == null) {
            key = get3DESKey();
            //保存key
            InfoStorage.saveKey(key, InfoStorage.getPath(appkey));
        }
        //加密username和password
        byte[] usernameByte = encrypt3DES(username, key);
        byte[] passwordByte = encrypt3DES(password, key);
        username = Base64.encodeToString(usernameByte, Base64.DEFAULT);
        password = Base64.encodeToString(passwordByte,Base64.DEFAULT);
        System.out.println("文件为"+appname);
        SharedPreferences sharedPreferences = context. getSharedPreferences(appname, Context.MODE_PRIVATE);
        SharedPreferences preferences = context.getSharedPreferences("share",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor2 = preferences.edit();
        int n = preferences.getInt("number",0);//n记载了数据数目的多少
        n=n+1;
        String p =n+"";
        editor2.putInt("number",n);//editor2代表本地全局变量的修改
        editor2.putString(p,appname);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("appkey",appkey);
        editor.apply();
        editor.commit();
        editor2.apply();
        editor2.commit();
    }

    public static void saveInfo2(Context context,String appname, String username, String password,String appkey) {

        //得到key
        SecretKey key = InfoStorage.readKey(InfoStorage.getPath(appkey));
        if (key == null) {
            key = get3DESKey();

            //保存key
            InfoStorage.saveKey(key, InfoStorage.getPath(appkey));
        }
        //加密username和password
        byte[] usernameByte = encrypt3DES(username, key);
        byte[] passwordByte = encrypt3DES(password, key);
        username = Base64.encodeToString(usernameByte, Base64.DEFAULT);
        password = Base64.encodeToString(passwordByte,Base64.DEFAULT);
        System.out.println("文件为"+appname);
        SharedPreferences sharedPreferences = context. getSharedPreferences(appname, Context.MODE_PRIVATE);
        //SharedPreferences preferences = context.getSharedPreferences("share",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // SharedPreferences.Editor editor2 = preferences.edit();
        //int n = preferences.getInt("number",0);//n记载了数据数目的多少
        // n=n+1;
        // String p =n+"";
        //editor2.putInt("number",n);//editor2代表本地全局变量的修改
        //editor2.putString(p,appname);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("appkey",appkey);
        editor.apply();
        editor.commit();
        // editor2.apply();
        // editor2.commit();
    }



    //读取账号密码
    public static String[] readInfo(Context context,String appname,String appkey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(appname, Context.MODE_PRIVATE);
        String str[] = new String[]{sharedPreferences.getString("username", ""), sharedPreferences.getString("password", "")};
        str[0] = decode(str[0],appkey);
        str[1] = decode(str[1],appkey);
        return str;
    }

    private static String decode(String str,String appkey) {
        if (!TextUtils.isEmpty(str)) {
            //对数据进行解密
            SecretKey key = readKey(InfoStorage.getPath(appkey));
            if (key != null)
            {
                str = decoder3DES(Base64.decode(str.getBytes(), Base64.DEFAULT), key);
            }
        }
        return str;
    }
    //读取数量
//    public static int readAmount(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("appamount", Context.MODE_PRIVATE);
//        int str =sharedPreferences.getInt("amount", Integer.parseInt(""));
//        return str;
//    }

    //保存key
    public static boolean saveKey(SecretKey key, String path) {
        try {
            FileOutputStream fileOutputStream1 = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    fileOutputStream1);
            objectOutputStream.writeObject(key);
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            Log.d("测试", e.toString());
        }
        return false;
    }

    //读取key
    public static SecretKey readKey(String path) {
        SecretKey key = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(path));
            key = (SecretKey) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            Log.d("测试:读取key:", e.toString());
        }
        return key;
    }

    //获取路径
    public static String getPath(String FileName) {

        if (TextUtils.isEmpty(FileName)) {

            return null;
        }
        //获得手机外部存储路径?
        File file = new File("/data/data/com.example.mikuaccpass/shared_prefs/appkeys");
        //是否存在是否存在是否为文件夹
        if (!file.exists() || !file.isDirectory()) {
            //如果父文件夹不存在并且最后一级子文件夹不存在，它就自动新建所有路经里写的文件夹；如果父文件夹存在，它就直接在已经存在的父文件夹下新建子文件夹
            file.mkdirs();
        }

        File file1 = new File(file, FileName);
        if (!file1.exists() || !file1.isFile()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                Log.d("测试:文件创建失败：", e.toString());
                return null;
            }
        }
        return file1.getPath();
    }

    /**
     * 数据加解密3DES所需要的Key
     */
    public static SecretKey get3DESKey() {
        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(168);// can 168 or 112/new SecureRandom()
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            // 转化key  密钥工厂用来将密钥（类型 Key 的不透明加密密钥）转换为密钥规范（底层密钥材料的透明表示形式）
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            SecretKey generateSecret = factory.generateSecret(deSedeKeySpec);

            return generateSecret;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("测试", e.toString());
        }
        return null;
    }

    /**
     * 数据加密3DES
     */
    private static byte[] encrypt3DES(String str, SecretKey generateSecret) {
        try {
            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, generateSecret);
            byte[] result = cipher.doFinal(str.getBytes("utf-8"));

            return result;
        } catch (Exception e) {
            System.out.println("加密出错：" + e.getMessage());
        }
        return null;
    }

    /**
     * 数据解密3DES
     */
    private static String decoder3DES(byte[] str, SecretKey generateSecret) {
        try {
            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, generateSecret);
            System.out.println(2);
            byte[] result = cipher.doFinal(str);
            System.out.println(3);

            return new String(result, "utf-8");
        } catch (Exception e) {
            System.out.println("解密出错:" + e.getMessage());
        }
        return null;
    }

}

