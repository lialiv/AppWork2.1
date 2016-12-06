package com.example.amirl2.appwork.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amirl2.appwork.Accessories.DBHelper;
import com.example.amirl2.appwork.Accessories.DailyLogItem;
import com.example.amirl2.appwork.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tvDateDay, tvCalories;
    Button btnAddFood, btnAddFromList;
    EditText etItem;
    ListView lvFood;
    DBHelper dbHelper;
    ArrayList<DailyLogItem> dailyItemsList;
    FoodListAdapter adapter;
    String specDate;
    SimpleDateFormat sdf;
    Calendar calendar;
    int caloriesLeft;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("com.example.amirl2.appwork.shared_preferences", Context.MODE_PRIVATE);

        tvCalories = (TextView) findViewById(R.id.tv_calories);
        btnAddFood = (Button) findViewById(R.id.btn_add);
        btnAddFromList = (Button) findViewById(R.id.btn_food_items_list);
        etItem = (EditText) findViewById(R.id.et_food_item);
        lvFood = (ListView) findViewById(R.id.lv_food);

        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd-MMM-yyyy");
        specDate = sdf.format(calendar.getTime());

//        Date date = new Date();
//        Long dateValue = date.getTime();
//        String dayOfWeek = (String) android.text.format.DateFormat.format("EEEE", date);
//        tvDateDay.setText(dayOfWeek + " " + specDate);

        dbHelper = new DBHelper(this);

        dailyItemsList = dbHelper.getDailyLogItems();
        adapter = new FoodListAdapter(this, dailyItemsList);
        lvFood.setAdapter(adapter);

        int totCaloriesUsed = 0;
        for (DailyLogItem item: dailyItemsList){
            totCaloriesUsed = totCaloriesUsed + (item.calories * item.quantity);
        }

        int caloriesPerDay = sharedPref.getInt("Calories_per_day", 2000);
        tvCalories.setText("" + (caloriesPerDay - totCaloriesUsed));


//        btnAddFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String foodName = etItem.getText().toString();
//                FoodItem newItem = new FoodItem(foodName);
//                dbHelper.insertFoodToDailyLogs(newItem);
//                dailyItemsList.add(newItem);
//                adapter.notifyDataSetChanged();
//                etItem.setText("");
//
//            }
//        });

        btnAddFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FoodItemsListIntent = new Intent(MainActivity.this, FoodItemsListActivity.class);
                startActivity(FoodItemsListIntent );
            }
        });


    }

    public class FoodListAdapter extends ArrayAdapter<DailyLogItem> {
        public FoodListAdapter(Context context, ArrayList<DailyLogItem> dailyItemsList) {
            super(context, 0, dailyItemsList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DailyLogItem dailyLogItem= getItem(position);
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
            tvFood.setText(dailyLogItem.foodName);
            tvServingSize.setText(dailyLogItem.serving);
            tvCalories.setText("" + dailyLogItem.calories);
            tvQuantity.setText("" + dailyLogItem.quantity);
            tvTotal.setText("" + dailyLogItem.calories*dailyLogItem.quantity);

            return convertView;
        }
    }


}
