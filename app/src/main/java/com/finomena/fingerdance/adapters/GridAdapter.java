package com.finomena.fingerdance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.finomena.fingerdance.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by adarsh on 20/02/17.
 */

public class GridAdapter  extends BaseAdapter {

    private Context context;
    private final Map<Integer, String> remainingViews;
    private int selectedValue;
    private int screenHeight;
    private int screenWidth;
    private int gameSize;

    public GridAdapter(Context context, Map<Integer, String> remainingViews, int selectedGridValue, int height, int width, int gameSize) {
        this.context = context;
        this.remainingViews = remainingViews;
        this.selectedValue = selectedGridValue;
        this.screenHeight = height;
        this.gameSize = gameSize;
        this.screenWidth = width;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.grid_item, null);
            gridView.setLayoutParams(new GridView.LayoutParams(screenWidth/gameSize, screenHeight/gameSize));
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            List<Integer> intList = new ArrayList<Integer>(remainingViews.keySet());
            int intValue = intList.get(position);

            if (intValue == selectedValue) {
                textView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
            } else {
                textView.setBackgroundColor(context.getResources().getColor(android.R.color.black));
            }

        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }

    @Override
    public int getCount() {
        return remainingViews.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
