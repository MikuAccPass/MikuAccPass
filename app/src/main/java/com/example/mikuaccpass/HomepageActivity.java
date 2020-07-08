package com.example.mikuaccpass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomepageActivity extends Fragment {
    private EditText edtSaveKey, edtSaveValue,edtSaveStation;
    private Button btnSave;
    private SharedPreferences preferences;
    private TextView tvContent;
    private String saveKey;
    private String saveValue;
    private String saveStation;
    private FloatingActionButton btnplus,btnSetting;
    private GlobalApplication global;

    private SearchView searchView;  //搜索框
    private ListView listView ;

    private List<Acount> acountList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_homepage, container, false);

        listView = root.findViewById(R.id.list_view);
        searchView =  root.findViewById(R.id.searchView);

        preferences=getActivity().getSharedPreferences("share",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

       /* initFruits();//初始化水果数据

        AcountAdapter adapter = new AcountAdapter(getActivity(),
                R.layout.listview, acountList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        //  MainActivity.this,android.R.layout.simple_list_item_1,data);
        final ListView listView = root.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
*/
        btnplus = root.findViewById(R.id.btn_plus);

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] passwordarray=initFruits1();
//                for(int i=1;i<=3;i++){
//                    System.out.println("跳转前密码组为:"+passwordarray[i]);
//                }

                Intent intent=new Intent(getActivity(), RecordActivity.class);
                intent.putExtra("passwordarray",passwordarray);
                startActivity(intent);
            }
        });
        //注册监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Acount fruit = acountList.get(position);
                String station=fruit.getStation();
                String name=fruit.getName();
                String password=fruit.getPassword();
                //   String nameid=fruit.getNameid();
                Intent intent1 = new Intent(getActivity(), MessageActivity.class);
                intent1.putExtra("activity1", station);
                intent1.putExtra("activity2", name);
                intent1.putExtra("activity3", password);
                // intent1.putExtra("activity4", nameid);

                // intent1.putExtra("activity4", position);
                startActivity(intent1);
               /*if()
               {

               }*/
               /* Toast.makeText(MainActivity.this,fruit.getName(),
                        Toast.LENGTH_LONG).show();*/
                //if(position==listView.getFooterViewsCount()) {
                // startActivity(new Intent(MainActivity.this, RecordActivity.class));
                //String p=Integer.toString(n);
                // }
                //else {

                // }
            }
        });
        searchView.setSearchWay(new SearchView.SearchWay<Acount>(){

            @Override
            public List<Acount> getData() {
                //返回数据源
                return acountList;
            }

            @Override
            public boolean matchItem(Acount item, String s) {
                //如果串item中包含串s，则匹配
                return (item.getStation().contains(s)||item.getName().contains(s));
            }

            @Override
            public void update(List<Acount> resultList) {
                //更新ListView的数据
                setListViewData(resultList);
            }
        });

        initFruits();

        return root;
    }
    private void initFruits()
    {
        global = (GlobalApplication) getActivity().getApplication();
        SharedPreferences.Editor editor = preferences.edit();
        int n=preferences.getInt("number",0);
        editor.putInt("number",n);
        for (int i=1;i<=n;i++)

        {

            String appname=i+"";
            appname = preferences.getString(appname,"");//获取appname为命名的相关sharepreference文件夹名字
            SharedPreferences pref=this.getActivity().getSharedPreferences(appname, MODE_PRIVATE);
            String appkey = pref.getString("appkey","");
            String origin_appname=pref.getString("origin_appname","");
            if(!origin_appname.equals(""))
            {
                int imageid=getResource(origin_appname);
                if(imageid==0)
                    imageid=getResource("ic_launcher_background");
                InfoStorage infostorage = null;
                String[] content=infostorage.readInfo(getActivity(),appname,appkey);
                Acount x = new Acount(appname,content[0],content[1], imageid);
                acountList.add(x);
            }
        }
        editor.apply();
        setListViewData(acountList);
    }

    private String[] initFruits1()
    {
        global = (GlobalApplication) getActivity().getApplication();
        SharedPreferences.Editor editor = preferences.edit();
        int n=preferences.getInt("number",0);
        editor.putInt("number",n);
        String [ ] passwordarray=new String[n+1];
        for (int i=1;i<=n;i++)

        {

            String appname=i+"";
            appname = preferences.getString(appname,"");//获取appname为命名的相关sharepreference文件夹名字
            SharedPreferences pref=this.getActivity().getSharedPreferences(appname, MODE_PRIVATE);
            String appkey = pref.getString("appkey","");
            String origin_appname=pref.getString("origin_appname","");
            if(!origin_appname.equals(""))
            {
                int imageid=getResource(origin_appname);
                if(imageid==0)
                    imageid=getResource("ic_launcher_background");
                InfoStorage infostorage = null;
                String[] content=infostorage.readInfo(getActivity(),appname,appkey);
                Acount x = new Acount(appname,content[0],content[1], imageid);
                passwordarray[i]=x.getPassword();
            }
        }
        return passwordarray;
    }
    private void setListViewData(List<Acount> list){
        //设置ListView的适配器
        AcountAdapter adapter = new AcountAdapter(getActivity(),
                R.layout.listview, list);
        listView.setAdapter(adapter);
    }
    public int  getResource(String imageName){
        Context ctx=getActivity().getBaseContext();
                             int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

}
