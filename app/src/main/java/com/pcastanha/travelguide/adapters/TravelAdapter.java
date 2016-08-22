package com.pcastanha.travelguide.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by pedro.matos.castanha on 7/7/2016.
 * Adapter class used to bind responses from the Content Provider into the UI.
 */
public class TravelAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_COUNT = 1;
    private static final int VIEW_TYPE_MODERN = 2;
    private static final int VIEW_TYPE_OLD = 3;

    public TravelAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Cache of the children views for a list item.
     */
    /*public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }*/

    // This callback method is used to return the layout of each individual list item element.
    // Consider implementing variations of the layout for each orientation and screen sizes.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        /*int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                layoutId = R.layout.list_item_forecast_today;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                layoutId = R.layout.list_item_forecast;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);*/

        return null;
    }

    // Switches between all available view types at the current layout.
    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_MODERN;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
