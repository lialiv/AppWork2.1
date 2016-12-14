package com.example.amirl2.appwork.Accessories;

/**
 * Created by AmirL2 on 11/27/2016.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
                        "( " + DAILY_LOGS_COLUMN_FOOD_ID + " integer, " + DAILY_LOGS_COLUMN_FOOD_QUANTITY + " integer, "
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
        ArrayList<FoodItemObj> foodItemsDataListObj = foodItemsData.FOOD_ITEMS_DATA_LIST_OBJ;

        ContentValues contentValues = new ContentValues();
        for (FoodItemObj item : foodItemsDataListObj) {

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
    public boolean insertFoodToDailyLogs(FoodItemObj item) {

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
    public boolean insertFoodsListToDailyLogs(HashMap<Integer, Integer> selectedItemsIdQuantities) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = sdf.format(calendar.getTime());
        ArrayList<Integer> itemsIds = new ArrayList<Integer>(selectedItemsIdQuantities.keySet());


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (Integer foodId : itemsIds) {
            contentValues.put(DAILY_LOGS_COLUMN_LOG_DATE, currentDate);
            contentValues.put(DAILY_LOGS_COLUMN_FOOD_ID, foodId);
            contentValues.put(DAILY_LOGS_COLUMN_FOOD_QUANTITY, selectedItemsIdQuantities.get(foodId));
            db.insert(DAILY_LOGS_TABLE_NAME, null, contentValues);
        }

        return true;
    }
//
//    public Cursor getData(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from " + DAILY_LOGS_TABLE_NAME + " + where " + DAILY_LOGS_COLUMN_FOOD_ID+ " = " + id, null);
//        return res;
//    }
//
//    public int getItem(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from " + DAILY_LOGS_TABLE_NAME + " where " + DAILY_LOGS_COLUMN_ID + " = " + id, null);
//        res.moveToFirst();
//        int foodItemId = res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_FOOD_ID));
//        return foodItemId;
//    }

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

    public ArrayList<FoodItemObj> getAllFoodItems() {
        ArrayList<FoodItemObj> listFood = new ArrayList<FoodItemObj>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FOOD_ITEMS_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            FoodItemObj foodItemObj = new FoodItemObj();
            foodItemObj.setId((res.getInt(res.getColumnIndex(FOOD_ITEMS_COLUMN_FOOD_ID))));
            foodItemObj.setName((res.getString(res.getColumnIndex(FOOD_ITEMS_COLUMN_FOOD_NAME))));
            foodItemObj.setServing((res.getString(res.getColumnIndex(FOOD_ITEMS_COLUMN_SERVING_SIZE))));
            foodItemObj.setCalories((res.getInt(res.getColumnIndex(FOOD_ITEMS_COLUMN_CALORIES))));
            listFood.add(foodItemObj);
            res.moveToNext();
        }
        return listFood;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<DailyLogObj> getDailyLogItemsForToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = sdf.format(calendar.getTime());

        ArrayList<DailyLogObj> dailyLogsList = new ArrayList<DailyLogObj>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DAILY_LOGS_TABLE_NAME + " WHERE " + DAILY_LOGS_COLUMN_LOG_DATE + " = '" + currentDate + "' ", null);
//        Cursor res = db.rawQuery("SELECT * FROM " + DAILY_LOGS_TABLE_NAME + ", " + FOOD_ITEMS_TABLE_NAME + " WHERE " +
//                DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_FOOD_ID + " = " + FOOD_ITEMS_TABLE_NAME + "." + FOOD_ITEMS_COLUMN_FOOD_ID + " AND " + DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_LOG_DATE + " = '" + currentDate + "' ", null);

        dailyLogsList = insertLogObjData(res, dailyLogsList);
        return dailyLogsList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public HashMap<Integer, Integer> getDailyLogItemsAsMapForToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = sdf.format(calendar.getTime());

        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + DAILY_LOGS_TABLE_NAME + " WHERE " + DAILY_LOGS_COLUMN_LOG_DATE + " = '" + currentDate + "' ", null);
        Cursor res = db.rawQuery("SELECT * FROM " + DAILY_LOGS_TABLE_NAME + ", " + FOOD_ITEMS_TABLE_NAME + " WHERE " +
                DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_FOOD_ID + " = " + FOOD_ITEMS_TABLE_NAME + "." + FOOD_ITEMS_COLUMN_FOOD_ID + " AND " + DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_LOG_DATE + " = '" + currentDate + "' ", null);

        HashMap<Integer, Integer> dailyItemsMap = new HashMap<>();
        while (res.isAfterLast() == false) {
            int itemId = (res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_FOOD_ID)));
            int quantity = (res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_FOOD_QUANTITY)));
            dailyItemsMap.put(itemId, quantity);
            res.moveToNext();
        }

        return dailyItemsMap;
    }

    public ArrayList<DailyLogObj> getDailyLogItems(String date) {

        ArrayList<DailyLogObj> dailyLogsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DAILY_LOGS_TABLE_NAME + ", " + FOOD_ITEMS_TABLE_NAME + " WHERE " +
                DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_FOOD_ID + " = " + FOOD_ITEMS_TABLE_NAME + "." + FOOD_ITEMS_COLUMN_FOOD_ID + " AND " + DAILY_LOGS_TABLE_NAME + "." + DAILY_LOGS_COLUMN_LOG_DATE + " = '" + date + "' ", null);

        dailyLogsList = insertLogObjData(res, dailyLogsList);
        return dailyLogsList;
    }

    public ArrayList<DailyLogObj> insertLogObjData(Cursor res, ArrayList<DailyLogObj> dailyLogsList) {

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            DailyLogObj dailyLogObj = new DailyLogObj();
            dailyLogObj.setFoodId((res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_FOOD_ID))));
            dailyLogObj.setQuantity((res.getInt(res.getColumnIndex(DAILY_LOGS_COLUMN_FOOD_QUANTITY))));
            dailyLogsList.add(dailyLogObj);
            res.moveToNext();
        }
        return dailyLogsList;
    }

}