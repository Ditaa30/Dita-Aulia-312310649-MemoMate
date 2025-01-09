package com.example.formpendaftaranmahasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_utama);

        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnShoppingList = findViewById(R.id.btnShoppingList);
        Button btnNotes = findViewById(R.id.btnNotes);
        Button btnTodoList = findViewById(R.id.btnTodoLIst);

        btnBack.setOnClickListener(v -> onBackPressed());

        btnShoppingList.setOnClickListener(v ->
                startActivity(new Intent(Activity_Home.this, ActivityList_1.class)));

        btnNotes.setOnClickListener(v ->
                startActivity(new Intent(Activity_Home.this, ActivityList_3.class)));

        btnTodoList.setOnClickListener(v ->
                startActivity(new Intent(Activity_Home.this, ActivityList_2.class)));
    }
}