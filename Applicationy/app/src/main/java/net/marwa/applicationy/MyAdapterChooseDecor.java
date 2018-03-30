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

public class MyAdapterChooseDecor extends ArrayAdapter<Decor> {

    Activity activity;
    int resource;
    List<Decor> list;

    public MyAdapterChooseDecor(Activity activity, int resource, List<Decor> list) {
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

        TextView price = (TextView) view.findViewById(R.id.getPrice);



        String str = Double.toString(list.get(position).getPrice());
        price.setText(str);


        Glide.with(activity).load(list.get(position).getImageUri()).into(imageView);

        return view;
    }
}