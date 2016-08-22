package com.pcastanha.travelguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.pcastanha.travelguide.R;

import java.util.List;

public class GuideAdapter extends ArrayAdapter<LatLng> {

    private ViewHolder viewHolder;

    private static class ViewHolder {
        private TextView titleView;
        private TextView itemView;
    }

    public GuideAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.listview_guide_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.guide_item_location);
            viewHolder.titleView = (TextView) convertView.findViewById(R.id.guide_item_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LatLng item = getItem(position);

        if (item != null) {
            // My layout has only one TextView do whatever you want with your string and long
            viewHolder.titleView.setText(String.format("%s","Item Location Name"));
            viewHolder.itemView.setText(String.format("Latitude: %f - Longitude: %f", item.latitude, item.longitude));
        }

        return convertView;
    }
}