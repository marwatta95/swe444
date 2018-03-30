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

public class MyAdapterChooseClown extends ArrayAdapter<Clown> {

    Activity activity;
    int resource;
    List<Clown> list;

    public MyAdapterChooseClown(Activity activity, int resource, List<Clown> list) {
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
        TextView name = (TextView) view.findViewById(R.id.getName);
        TextView price = (TextView) view.findViewById(R.id.getPrice);

        name.setText(list.get(position).getName());
        String str = Double.toString(list.get(position).getPrice());
        price.setText(str);


        Glide.with(activity).load(list.get(position).getImageUri()).into(imageView);

        return view;
    }
}