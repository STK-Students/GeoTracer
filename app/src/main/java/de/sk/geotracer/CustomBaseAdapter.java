package de.sk.geotracer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String listPlatz[];
    int listImages[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String [] platzList, int [] images){
        this.context = ctx;
        this.listPlatz = platzList;
        this.listImages = images;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listPlatz.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_best_list_view, null);
        TextView txtView = (TextView) convertView.findViewById(R.id.TextView);
        ImageView listImg = (ImageView) convertView.findViewById(R.id.imageIcon);
        txtView.setText(listPlatz[position]);
        listImg.setImageResource(listImages[position]);
        return convertView;
    }
}
