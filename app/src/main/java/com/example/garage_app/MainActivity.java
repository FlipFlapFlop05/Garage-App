package com.example.garage_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText Name, password;
    Button proceed;
    private DatabaseHelper databaseHelper;
    private Users user =  null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new Users();
        databaseHelper = new DatabaseHelper(this);
        Name = findViewById(R.id.Name);
        password = findViewById(R.id.password);
        proceed = findViewById(R.id.proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add code to save data in the DB

                user.setName(Name.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                databaseHelper.addUser(user);
               Intent intent =  new Intent(MainActivity.this, Dashboard.class);
               startActivity(intent);
            }
        });
    }
}