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




public class MyAdapter4 extends ArrayAdapter<Dj> {

    Activity activity;
    int resource;
    List<Dj> list;

    public MyAdapter4(Activity activity, int resource, List<Dj> list) {
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

        TextView textView1 = (TextView) view.findViewById(R.id.getFirName);
        TextView textView2 = (TextView) view.findViewById(R.id.getLasName);


        textView1.setText(list.get(position).getFirst());
        textView2.setText(list.get(position).getLast());
        return view;
    }
}
