package com.example.amirl2.appwork.Accessories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.amirl2.appwork.R;

import java.util.ArrayList;

/**
 * Created by AmirL2 on 12/6/2016.
 */

    public class FoodListAdapter extends ArrayAdapter<DailyLogObj> {
        public FoodListAdapter(Context context, ArrayList<DailyLogObj> dailyItemsList) {
            super(context, 0, dailyItemsList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DailyLogObj dailyLogObj = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_list, parent, false);
            }
            CheckBox chbx = (CheckBox) convertView.findViewById(R.id.chbx_item);
            TextView tvFood = (TextView) convertView.findViewById(R.id.tv_food_name);
            TextView tvQuantity = (TextView) convertView.findViewById(R.id.tv_quantity);
            TextView tvServingSize = (TextView) convertView.findViewById(R.id.tv_serving);
            TextView tvCalories = (TextView) convertView.findViewById(R.id.tv_calories);
            TextView tvTotal = (TextView) convertView.findViewById(R.id.tv_total);
            chbx.setVisibility(View.GONE);
            tvFood.setText(dailyLogObj.foodName);
            tvServingSize.setText(dailyLogObj.serving);
            tvCalories.setText("" + dailyLogObj.calories);
            tvQuantity.setText("" + dailyLogObj.quantity);
            tvTotal.setText("" + dailyLogObj.calories* dailyLogObj.quantity);

            return convertView;
        }
    }


