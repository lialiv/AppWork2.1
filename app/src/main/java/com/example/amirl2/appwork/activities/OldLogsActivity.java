package com.example.amirl2.appwork.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amirl2.appwork.Accessories.DBHelper;
import com.example.amirl2.appwork.Accessories.DailyLogObj;
import com.example.amirl2.appwork.Accessories.FoodListAdapter;
import com.example.amirl2.appwork.R;

import java.util.ArrayList;
import java.util.Calendar;

public class OldLogsActivity extends Activity {

    TextView tvDateDay;
    ListView lvOldRecords;
    Button btnDayBefore, btnDayAfter;
    ArrayList<DailyLogObj> dailyItemsList;
    FoodListAdapter adapter;
    Calendar calendar;
    SimpleDateFormat sdf;
    DBHelper dbHelper;
    String specDate;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_logs_activity);

        tvDateDay = (TextView) findViewById(R.id.tv_date_day);
        lvOldRecords = (ListView) findViewById(R.id.lv_old_records);
        btnDayBefore = (Button) findViewById(R.id.btn_day_before);
        btnDayAfter = (Button) findViewById(R.id.btn_day_after);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        sdf = new SimpleDateFormat("dd-MMM-yyyy");
        specDate = sdf.format(calendar.getTime());
        tvDateDay.setText("Records for " + specDate);

        dbHelper = new DBHelper(this);
        dailyItemsList = dbHelper.getDailyLogItems(specDate);
        adapter = new FoodListAdapter(this, dailyItemsList);
        lvOldRecords.setAdapter(adapter);

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
                dailyItemsList = dbHelper.getDailyLogItems(specDate);
                adapter.notifyDataSetChanged();
            }
        };

        btnDayBefore.setOnClickListener(updateDateAndListItemsListener);
        btnDayAfter.setOnClickListener(updateDateAndListItemsListener);

    }
}