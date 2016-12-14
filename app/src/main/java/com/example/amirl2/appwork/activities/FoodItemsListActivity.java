package com.example.amirl2.appwork.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amirl2.appwork.Accessories.DBHelper;
import com.example.amirl2.appwork.Accessories.FoodItemObj;
import com.example.amirl2.appwork.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodItemsListActivity extends Activity {
    private ArrayList<FoodItemObj> foodItems, searchedResultList;
    private ArrayAdapter<FoodItemObj> listAdapter;
    public HashMap<Integer, Integer> selectedItems;
    DBHelper dbHelper;
    public static ListView lvFoodItems;
    EditText etSearch;
    ImageButton btnAddItems;
    static int FLAG = 1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items_list);

        lvFoodItems = (ListView) findViewById(R.id.lv_food_items);
        etSearch = (EditText) findViewById(R.id.et_search_food);
        btnAddItems = (ImageButton) findViewById(R.id.btn_add_items_to_logs);

        dbHelper = new DBHelper(this);
        foodItems = dbHelper.getAllFoodItems();
        searchedResultList = new ArrayList<>(foodItems);
        selectedItems = new HashMap<>();

        listAdapter = new FoodItemArrayAdapter(this, searchedResultList);
        lvFoodItems.setAdapter(listAdapter);
        lvFoodItems.setTextFilterEnabled(true);
        lvFoodItems.setOnItemClickListener(listItemClickListener);

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String searchString = etSearch.getText().toString();
                int textLength = searchString.length();

                //clear the initial data set
                searchedResultList.clear();

                for (int i = 0; i < foodItems.size(); i++) {
                    String foodName = foodItems.get(i).name;
                    if (textLength <= foodName.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        if (searchString.equalsIgnoreCase(foodName.substring(0, textLength)))
                            searchedResultList.add(foodItems.get(i));
                    }
                }

                listAdapter.notifyDataSetChanged();
//                lvFoodItems.setAdapter(searchedResultList);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                listAdapter.notifyDataSetChanged();
            }
        });

        btnAddItems.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

              dbHelper.insertFoodsListToDailyLogs(selectedItems);

                Intent mainActivityIntent = new Intent(FoodItemsListActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);

            }
        });



    }

    public AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
            FoodItemObj foodItem = listAdapter.getItem(position);
            FoodItemViewHolder viewHolder = (FoodItemViewHolder) item.getTag();
            foodItem.setSelected(true);
            if (foodItem.isSelected) {
//                foodItem.setSelected(true);
                foodItem.quantity++;
                viewHolder.itemQuantity = foodItem.quantity;
                viewHolder.chbx.setChecked(true);
                selectedItems.put(foodItem.id, viewHolder.itemQuantity);
            } else if (!foodItem.isSelected) {
                foodItem.quantity = 0;
                viewHolder.chbx.setChecked(false);
                viewHolder.itemQuantity = 0;
                selectedItems.remove(foodItem.id);
            }

            viewHolder.tvQuantity.setText("x" + viewHolder.itemQuantity);
            Toast.makeText(getApplicationContext(), "item id is: " + foodItem.id, Toast.LENGTH_LONG).show();

        }
    };


    public static class FoodItemViewHolder {
        int foodId;
        TextView tvFoodName;
        TextView tvServing;
        TextView tvCalories;
        TextView tvQuantity;
        int itemQuantity;
        TextView tvTotal;
        CheckBox chbx;

        public FoodItemViewHolder() {
        }

        public FoodItemViewHolder(TextView tvFoodName, CheckBox chbx, TextView tvServing, TextView tvCalories, TextView tvQuantity, TextView tvTotal) {
            this.foodId = foodId;
            this.chbx = chbx;
            this.tvFoodName = tvFoodName;
            this.tvServing = tvServing;
            this.tvCalories = tvCalories;
            this.tvQuantity = tvQuantity;
            this.itemQuantity = 0;
            this.tvTotal = tvTotal;
        }
    }

    private static class FoodItemArrayAdapter extends ArrayAdapter<FoodItemObj> {

        private LayoutInflater inflater;
        FoodItemViewHolder viewHolder;

        public FoodItemArrayAdapter(Context context, ArrayList<FoodItemObj> foodList) {
            super(context, R.layout.row_list, foodList);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FoodItemObj foodItem = this.getItem(position);

            // The child views in each row.
            TextView tvFoodName;
            TextView tvServing;
            TextView tvCalories;
            TextView tvQuantity;
            TextView tvTotal;
            CheckBox chbx;
            int itemQuantity = 0;

            // Create a new row view
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_list, null);

                // Find the child views.
                tvFoodName = (TextView) convertView.findViewById(R.id.tv_food_name);
                tvServing = (TextView) convertView.findViewById(R.id.tv_serving);
                tvCalories = (TextView) convertView.findViewById(R.id.tv_calories);
                tvQuantity = (TextView) convertView.findViewById(R.id.tv_quantity);
                tvTotal = (TextView) convertView.findViewById(R.id.tv_total);
                chbx = (CheckBox) convertView.findViewById(R.id.chbx_item);

                convertView.setTag(new FoodItemViewHolder(tvFoodName, chbx, tvServing, tvCalories, tvQuantity, tvTotal));

                chbx.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        FoodItemObj foodItem = (FoodItemObj) cb.getTag();
                        foodItem.setSelected(cb.isChecked());
                        if (cb.isChecked()) {
                            foodItem.setSelected(true);
                            foodItem.quantity++;
//                            FLAG = 1;
                        }
                        else if (!cb.isChecked()) {
//                            FLAG = 0;
                            foodItem.setSelected(false);
                            foodItem.quantity = 0;
                        }
//                            lvFoodItems.deferNotifyDataSetChanged();
                        lvFoodItems.setAdapter(FoodItemArrayAdapter.this);
                    }
                });
            }
            // Reuse existing row view
            else {
                viewHolder = (FoodItemViewHolder) convertView.getTag();
                chbx = viewHolder.chbx;
                tvFoodName = viewHolder.tvFoodName;
                tvServing = viewHolder.tvServing;
                tvCalories = viewHolder.tvCalories;
                tvQuantity = viewHolder.tvQuantity;
                tvTotal = viewHolder.tvTotal;
                itemQuantity = viewHolder.itemQuantity;
            }

            itemQuantity = foodItem.quantity;
            chbx.setTag(foodItem);
            chbx.setChecked(foodItem.isSelected());
            tvFoodName.setText(foodItem.getName());
            tvServing.setText(foodItem.getServing());
            tvCalories.setText(foodItem.getCalories() + "");
            tvQuantity.setText("x" + itemQuantity);
            tvTotal.setVisibility(View.GONE);
            return convertView;
        }

    }

}