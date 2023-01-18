package de.sk.geotracer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    List<String> description;
    List<Integer> images;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, List<String> descriptions, List<Integer> images) {
        this.context = ctx;
        this.description = descriptions;
        this.images = images;
        inflater = LayoutInflater.from(ctx);
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    @Override
    public int getCount() {
        return description.size();
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
        txtView.setText(description.get(position));
        listImg.setImageResource(images.get(position));
        return convertView;
    }
}
