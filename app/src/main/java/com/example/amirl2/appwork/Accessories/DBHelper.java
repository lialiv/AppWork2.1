package com.example.amirl2.appwork.Accessories;

/**
 * Created by AmirL2 on 11/27/2016.
 */

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WatchYourFood.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DAILY_LOGS_TABLE_NAME = "DailyLogs";
    public static final String DAILY_LOGS_COLUMN_ID = "LogID";
    public static final String DAILY_LOGS_COLUMN_FOOD_ID = "FoodID";
    public static final String DAILY_LOGS_COLUMN_FOOD_QUANTITY = "FoodQuantity";
    public static final String DAILY_LOGS_COLUMN_LOG_DATE = "LogDate";

    public static final String FOOD_ITEMS_TABLE_NAME = "FoodItems";
    public static final String FOOD_ITEMS_COLUMN_FOOD_ID = "foodID";
    public static final String FOOD_ITEMS_COLUMN_FOOD_NAME = "foodName";
    public static final String FOOD_ITEMS_COLUMN_SERVING_SIZE = "ServingSize";
    public static final String FOOD_ITEMS_COLUMN_CALORIES = "Calories";

    public Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        SharedPreferences sharedPref = context.getSharedPreferences("com.example.amirl2.appwork.shared_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Calories_per_day", 1500);
        editor.commit();

        db.execSQL(
                "CREATE TABLE " + DAILY_LOGS_TABLE_NAME +
                        "( " + DAILY_LOGS_COLUMN_ID + " integer primary key, " + DAILY_LOGS_COLUMN_FOOD_ID + " integer, " + DAILY_LOGS_COLUMN_FOOD_QUANTITY + " integer, "
                        + DAILY_LOGS_COLUMN_LOG_DATE + " text)")
        ;

        db.execSQL(
                "CREATE TABLE " + FOOD_ITEMS_TABLE_NAME +
                        "( " + FOOD_ITEMS_COLUMN_FOOD_ID + " integer primary key, " + FOOD_ITEMS_COLUMN_FOOD_NAME + " text, " + FOOD_ITEMS_COLUMN_SERVING_SIZE + " text, "
                        + FOOD_ITEMS_COLUMN_CALORIES + " integer)")
        ;

        initiateFoodItemsList(db);

    }

    private void initiateFoodItemsList(SQLiteDatabase db) {

        FoodItemsData foodItemsData = new FoodItemsData();
        ArrayList<FoodItem> foodItemsDataList = foodItemsData.foodItemsDataList;

        ContentValues contentValues = new ContentValues();
        for (FoodItem item : foodItemsDataList) {

            contentValues.put(FOOD_ITEMS_COLUMN_FOOD_ID, item.getId());
            contentValues.put(FOOD_ITEMS_COLUMN_FOOD_NAME, item.getName());
            contentValues.put(FOOD_ITEMS_COLUMN_SERVING_SIZE, item.getServing());
            contentValues.put(FOOD_ITEMS_COLUMN_CALORIES, item.getCalories());

            db.insert(FOOD_ITEMS_TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_LOGS_TABLE_NAME);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean insertFoodToDailyLogs(FoodItem item) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = sdf.format(calendar.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DAILY_LOGS_COLUMN_LOG_DATE, currentDate);

        db.insert(DAILY_LOGS_TABLE_NAME, null, contentValues);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean insertFoodsListToDailyLogs(ArrayList<Integer> foodItemsIds) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = sdf.format(calendar.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Integer foodId : foodItemsIds) {
            contentValues.put(DAILY_LOGS_COLUMN_LOG_DATE, currentDate);
            contentValues.put(DAILY_LOGS_COLUMN_FOOD_ID, foodId);
            contentValues.put(DAILY_LOGS_COLUMN_FOOD_QUANTITY, 1);
            db.insert(DAILY_LOGS_TABLE_NAME, null, contentValues);
        }

        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + DAILY_LOGS_TABLE_NAME + " + where " + DAILY_LOGS_COLUMN_ID + " = " + id, null);
        return res;
    }

    public int getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + DAILY_LOGS_TABLE_NAME + " where " + DAILY_LOGS_COLUMN_ID + " = " + id, null);
        res.moveToFirst();
        int foodItemId = res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_FOOD_ID));
        return foodItemId;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DAILY_LOGS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);

        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<FoodItem> getAllFoodItems() {
        ArrayList<FoodItem> listFood = new ArrayList<FoodItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_ITEMS_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            FoodItem foodItem = new FoodItem();
            foodItem.setId((res.getInt(res.getColumnIndex(FOOD_ITEMS_COLUMN_FOOD_ID))));
            foodItem.setName((res.getString(res.getColumnIndex(FOOD_ITEMS_COLUMN_FOOD_NAME))));
            foodItem.setServing((res.getString(res.getColumnIndex(FOOD_ITEMS_COLUMN_SERVING_SIZE))));
            foodItem.setCalories((res.getInt(res.getColumnIndex(FOOD_ITEMS_COLUMN_CALORIES))));
            listFood.add(foodItem);
            res.moveToNext();
        }
        return listFood;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<DailyLogItem> getDailyLogItems() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = sdf.format(calendar.getTime());

        ArrayList<DailyLogItem> listFood = new ArrayList<DailyLogItem>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + DAILY_LOGS_TABLE_NAME + " WHERE " + DAILY_LOGS_COLUMN_LOG_DATE + " = '" + currentDate + "' ", null);
        Cursor res = db.rawQuery("SELECT * FROM " + DAILY_LOGS_TABLE_NAME + ", " + FOOD_ITEMS_TABLE_NAME + " WHERE " +
         DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_FOOD_ID + " = " + FOOD_ITEMS_TABLE_NAME + "." + FOOD_ITEMS_COLUMN_FOOD_ID + " AND " + DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_LOG_DATE + " = '" + currentDate + "' ", null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            DailyLogItem dailyLogItem = new DailyLogItem();
            dailyLogItem.setId((res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_ID))));
            dailyLogItem.setFoodName((res.getString(res.getColumnIndex(FOOD_ITEMS_COLUMN_FOOD_NAME))));
            dailyLogItem.setServing((res.getString(res.getColumnIndex(FOOD_ITEMS_COLUMN_SERVING_SIZE))));
            dailyLogItem.setCalories((res.getInt(res.getColumnIndex(FOOD_ITEMS_COLUMN_CALORIES))));
            dailyLogItem.setQuantity((res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_FOOD_QUANTITY))));
            listFood.add(dailyLogItem);
            res.moveToNext();
        }
        return listFood;
    }


}


