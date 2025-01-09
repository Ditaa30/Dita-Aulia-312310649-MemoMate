package com.example.formpendaftaranmahasiswa;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ActivityList_1 extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListItemAdapter adapter;
    private ArrayList<String> itemList;
    private ArrayList<Integer> itemIds;
    private EditText editTextItem;
    private static final String LIST_TYPE = "SHOPPING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);

        // Initialize variables
        dbHelper = new DatabaseHelper(this);
        itemList = new ArrayList<>();
        itemIds = new ArrayList<>();

        // Bind views
        ImageView btnBack = findViewById(R.id.btnBack);
        ListView listView = findViewById(R.id.listView);
        editTextItem = findViewById(R.id.editTextItem);
        Button btnAdd = findViewById(R.id.btnAdd);

        // Set up adapter
        adapter = new ListItemAdapter(this, itemList, itemIds, R.layout.activity_list_item);

        listView.setAdapter(adapter);

        // Load existing items
        loadItems();

        // Back button handler
        btnBack.setOnClickListener(v -> finish());

        // Add button handler
        btnAdd.setOnClickListener(v -> addNewItem());
    }

    private void addNewItem() {
        String itemName = editTextItem.getText().toString().trim();
        if (!itemName.isEmpty()) {
            long id = dbHelper.insertItem(itemName, LIST_TYPE);
            if (id != -1) {
                itemList.add(itemName);
                itemIds.add((int) id);
                adapter.notifyDataSetChanged();
                editTextItem.setText("");
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadItems() {
        itemList.clear();
        itemIds.clear();
        Cursor cursor = dbHelper.getItemsByType(LIST_TYPE);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_ID);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_NAME);
                if (idIndex != -1 && nameIndex != -1) {
                    itemIds.add(cursor.getInt(idIndex));
                    itemList.add(cursor.getString(nameIndex));
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }
}