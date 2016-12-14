package com.example.amirl2.appwork.Accessories;

import java.util.ArrayList;

/**
 * Created by AmirL2 on 12/5/2016.
 */

public class DailyLogObj {
    public String date;
    public int foodId;
    public int quantity;

    public DailyLogObj() {
    }

    public DailyLogObj(int foodId, int quantity) {
        this.foodId = foodId;
        this.quantity = quantity;
    }

    public DailyLogObj(FoodItemObj foodItemObj, int quantity) {
        this.foodId = foodItemObj.id;
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }


    public static ArrayList<DailyLogObj> getDailyListFromFoodList(ArrayList<FoodItemObj> foodItemObjList) {

        ArrayList<DailyLogObj> dailyLogObjsList = new ArrayList<>();
        for (FoodItemObj foodItemObj : foodItemObjList) {
            DailyLogObj dailyLogObj = new DailyLogObj(foodItemObj, 1);
            dailyLogObjsList.add(dailyLogObj);
        }
        return dailyLogObjsList;
    }
}
