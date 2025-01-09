package com.example.formpendaftaranmahasiswa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String UserName = "Admin";
    String Password = "111111";
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        addUserToDatabase(UserName, Password);

        EditText textUser = findViewById(R.id.etUserName);
        EditText textPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(v -> {
            String enteredUsername = textUser.getText().toString();
            String enteredPassword = textPassword.getText().toString();
            if (validateLogin(enteredUsername, enteredPassword)) {
                startActivity(new Intent(MainActivity.this, Activity_Home.class));
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Username atau Password Salah", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addUserToDatabase(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, null,
                DatabaseHelper.COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);

        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USERNAME, username);
            values.put(DatabaseHelper.COLUMN_PASSWORD, password);
            db.insert(DatabaseHelper.TABLE_USER, null, values);
        }
        cursor.close();
    }

    private boolean validateLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, null,
                DatabaseHelper.COLUMN_USERNAME + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}