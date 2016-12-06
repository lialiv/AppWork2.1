package com.example.amirl2.appwork.Accessories;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by AmirL2 on 12/3/2016.
 */

public class FoodItemsData {

    public static final ArrayList<FoodItem> foodItemsDataList = new ArrayList<>();

    public FoodItemsData() {

        setFoodItemsDataList(foodItemsDataList);

    }

    public void setFoodItemsDataList(ArrayList<FoodItem> foodItemsDataList) {

        // @formatter:off

        foodItemsDataList.add(new FoodItem(1, "1000 Island, Salad Drsng,Local",            "1 Tbsp",       25));
        foodItemsDataList.add(new FoodItem(2, "1000 Island, Salad Drsng,Reglr",            "1 Tbsp",       60));
        foodItemsDataList.add(new FoodItem(3, "40% Bran Flakes, Kellogg's",                "1 oz",         90));
        foodItemsDataList.add(new FoodItem(4, "40% Bran Flakes, Post",                     "1 oz",         90));
        foodItemsDataList.add(new FoodItem(5, "Alfalfa Seeds, Sprouted, Raw",              "1 Cup",        10));
        foodItemsDataList.add(new FoodItem(6, "All-Bran Cereal",                           "1 oz",         70));
        foodItemsDataList.add(new FoodItem(7, "Almonds, Slivered",                         "1 Cup",        795));
        foodItemsDataList.add(new FoodItem(8, "Almonds, Whole",                            "1 oz",         165));
        foodItemsDataList.add(new FoodItem(9, "Angelfood Cake, From Mix",                  "1 Cake",       1510));
        foodItemsDataList.add(new FoodItem(10, "Angelfood Cake, From Mix",                  "1 Piece",      125));
        foodItemsDataList.add(new FoodItem(11, "Apple Juice, Canned",                       "1 Cup",        115));
        foodItemsDataList.add(new FoodItem(12, "Apple Pie",                                 "1 Pie",        2420));
        foodItemsDataList.add(new FoodItem(13, "Apple Pie",                                 "1 Piece",      405));
        foodItemsDataList.add(new FoodItem(14, "Apples, Dried, Sulfured",                   "10 Rings",     155));
        foodItemsDataList.add(new FoodItem(15, "Apples, Raw, Peeled, Sliced",               "1 Cup",        65));
        foodItemsDataList.add(new FoodItem(16, "Apples, Raw, Unpeeled,2 Per Lb",            "1 Apple",      125));
        foodItemsDataList.add(new FoodItem(17, "Apples, Raw, Unpeeled,3 Per Lb",            "1 Apple",      80));
        foodItemsDataList.add(new FoodItem(18, "Applesauce, Canned, Sweetened",             "1 Cup",        195));
        foodItemsDataList.add(new FoodItem(19, "Applesauce, Canned,Unsweetened",            "1 Cup",        105));
        foodItemsDataList.add(new FoodItem(20, "Apricot Nectar, No Added Vit C",            "1 Cup",        140));
        foodItemsDataList.add(new FoodItem(21, "Apricot, Canned, Heavy Syrup",              "1 Cup",        215));
        foodItemsDataList.add(new FoodItem(22, "Apricot, Canned, Heavy Syrup",              "3 Halves",     70));
        foodItemsDataList.add(new FoodItem(23, "Apricots, Canned, Juice Pack",              "1 Cup",        120));
        foodItemsDataList.add(new FoodItem(24, "Apricots, Canned, Juice Pack",              "3 Halves",     40));
        foodItemsDataList.add(new FoodItem(25, "Apricots, Dried, Cooked,Unswtn",            "1 Cup",        210));
        foodItemsDataList.add(new FoodItem(26, "Apricots, Dried, Uncooked",                 "1 Cup",        310));
        foodItemsDataList.add(new FoodItem(27, "Apricots, Raw",                             "3 Aprcot",     50));
        foodItemsDataList.add(new FoodItem(28, "Artichokes, Globe, Cooked, Drn",            "1 Artchk",     55));
        foodItemsDataList.add(new FoodItem(29, "Asparagus, Ckd Frm Frz,Dr,Sper",            "4 Spears",     15));
        foodItemsDataList.add(new FoodItem(30, "Asparagus, Ckd Frm Frz,Drn,Cut",            "1 Cup",        50));
        foodItemsDataList.add(new FoodItem(31, "Asparagus, Ckd Frm Raw, Dr,Cut",            "1 Cup",        45));
        foodItemsDataList.add(new FoodItem(32, "Asparagus, Ckd Frm Raw,Dr,Sper",            "4 Spears",     15));
        foodItemsDataList.add(new FoodItem(33, "Asparagus,Canned,Spears,Nosalt",            "4 Spears",     10));
        foodItemsDataList.add(new FoodItem(34, "Asparagus,Canned,Spears,W/Salt",            "4 Spears",     10));
        foodItemsDataList.add(new FoodItem(35, "Avocados, California",                      "1 Avocdo",     305));
        foodItemsDataList.add(new FoodItem(36, "Avocados, Florida",                         "1 Avocdo",     340));


// @formatter:on


    }


}
