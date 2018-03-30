package net.marwa.applicationy;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapterChooseCustom extends ArrayAdapter<Custom>{
    Activity activity;
    int resource;
    List<Custom> list;

    public MyAdapterChooseCustom(Activity activity, int resource, List<Custom> list) {
        super(activity, resource,list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);

        ImageView imageView = (ImageView) view.findViewById(R.id.getImages);
        Glide.with(activity).load(list.get(position).getImageUri()).into(imageView);

        TextView type = (TextView) view.findViewById(R.id.getType);
        String type1=list.get(position).getType();
        type.setText( type1 );
        TextView price = (TextView) view.findViewById(R.id.getPrice);
        String price1= Double.toString(list.get(position).getPrice());
        price.setText( price1 );





        return view;
    }
}
