package com.example.mikuaccpass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AcountAdapter extends ArrayAdapter<Acount> {
    private int resourceId;
    //重写构造函数，把id和数据传进来
    public AcountAdapter(Context context, int textViewResourceId, List<Acount> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Acount fruit = getItem(position);//获取当前项的Fruit实例
      /* View view = LayoutInflater.from
                (getContext()).inflate(resourceId, parent, false);

        */
        //对上一个语句进行优化
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);//false参数表示只让我们在父布局中声明的layout属性生效
        }else {
            view = convertView;
        }
        ImageView fruitImage = view.findViewById(R.id.Acount_image);
        TextView fruitStation = view.findViewById(R.id.Acount_station);
        TextView fruitName = view.findViewById(R.id.Acount_name);

        fruitImage.setImageResource(fruit.getImageId());
        fruitStation.setText("平台名："+fruit.getStation());
        fruitName.setText("用户名："+fruit.getName());
        return view;

    }
}
