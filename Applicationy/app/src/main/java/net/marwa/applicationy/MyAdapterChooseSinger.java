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

public class MyAdapterChooseSinger extends ArrayAdapter<Singer> {

    Activity activity;
    int resource;
    List<Singer> list;

    public MyAdapterChooseSinger (Activity activity, int resource, List<Singer> list) {
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

        TextView first = (TextView) view.findViewById(R.id.getFirst);
        String firstn=list.get(position).getFirst();
        first.setText( firstn );
        TextView last = (TextView) view.findViewById(R.id.getLast);
        String lastn=list.get(position).getLast();
        last.setText( lastn );
        TextView phone = (TextView) view.findViewById(R.id.getPhone);
        String phonen=list.get(position).getPhone();
        phone.setText( phonen );
        TextView price = (TextView) view.findViewById(R.id.getPrice);
        String str = Double.toString(list.get(position).getPrice());
        price.setText(str);



        return view;
    }
}