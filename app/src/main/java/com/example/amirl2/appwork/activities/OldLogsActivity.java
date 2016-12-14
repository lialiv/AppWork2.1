package com.example.amirl2.appwork.activities;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amirl2.appwork.Accessories.DBHelper;
import com.example.amirl2.appwork.Accessories.DailyLogObj;
import com.example.amirl2.appwork.Accessories.FoodItemObj;
import com.example.amirl2.appwork.R;

import java.util.ArrayList;
import java.util.Calendar;

public class OldLogsActivity extends Activity {

    TextView tvDateDay;
    ListView lvOldRecords;
    ImageButton btnDayBefore, btnDayAfter;
    ArrayList<DailyLogObj> dailyItemsList;
    ArrayList<FoodItemObj> foodItemsList;
    FoodListAdapter adapter;
    Calendar calendar;
    SimpleDateFormat sdf;
    DBHelper dbHelper;
    String specDate;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_logs);

        tvDateDay = (TextView) findViewById(R.id.tv_date_day);
        lvOldRecords = (ListView) findViewById(R.id.lv_old_records);
        btnDayBefore = (ImageButton) findViewById(R.id.btn_day_before);
        btnDayAfter = (ImageButton) findViewById(R.id.btn_day_after);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        sdf = new SimpleDateFormat("dd-MMM-yyyy");
        specDate = sdf.format(calendar.getTime());
        tvDateDay.setText("Records for " + specDate);

        dbHelper = new DBHelper(this);
        createList();

        View.OnClickListener updateDateAndListItemsListener = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_day_before:
                        calendar.add(Calendar.DATE, -1);
                        break;
                    case R.id.btn_day_after:
                        calendar.add(Calendar.DATE, 1);
                        break;
                }

                specDate = sdf.format(calendar.getTime());
                tvDateDay.setText("Records for " + specDate);
                dailyItemsList.clear();
                createList();
            }
        };

        btnDayBefore.setOnClickListener(updateDateAndListItemsListener);
        btnDayAfter.setOnClickListener(updateDateAndListItemsListener);

    }

    private void createList() {
        dailyItemsList = dbHelper.getDailyLogItems(specDate);
        foodItemsList = dbHelper.getAllFoodItems();
        adapter = new FoodListAdapter(OldLogsActivity.this, dailyItemsList);
        lvOldRecords.setAdapter(adapter);
    }


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
            tvFood.setText(foodItemsList.get(dailyLogObj.foodId).name);
            tvServingSize.setText(foodItemsList.get(dailyLogObj.foodId).serving);
            tvCalories.setText(foodItemsList.get(dailyLogObj.foodId).calories + "x" + dailyLogObj.quantity);
            tvQuantity.setVisibility(View.GONE);

            return convertView;
        }
    }
}