package com.example.amirl2.appwork.Accessories;

/**
 * Created by AmirL2 on 11/27/2016.
 */

public class FoodItemObj {
    public int id;
    public String name;
    public String serving;
    public int calories;

    public FoodItemObj(int id, String name, String serving, int calories) {
        this.id = id;
        this.name = name;
        this.serving = serving;
        this.calories = calories;
    }

    public FoodItemObj(String name) {
        this.name = name;
    }

    public FoodItemObj() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

}
