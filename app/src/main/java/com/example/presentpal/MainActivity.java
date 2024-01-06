package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button birthday = findViewById(R.id.birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Birthday.class));
            }
        });

        Button christmas = findViewById(R.id.christmas);
        christmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Christmas.class));
            }
        });

        Button wedding = findViewById(R.id.wedding);
        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Wedding.class));
            }
        });

        Button anniversary = findViewById(R.id.anniversary);
        anniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Anniversary.class));
            }
        });

        Button babyShower = findViewById(R.id.babyShower);
        babyShower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BabyShower.class));
            }
        });

        Button mothersDay = findViewById(R.id.mothersDay);
        mothersDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MothersDay.class));
            }
        });

        Button fathersDay = findViewById(R.id.fathersDay);
        fathersDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FathersDay.class));
            }
        });

        Button houseWarming = findViewById(R.id.houseWarming);
        houseWarming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HouseWarming.class));
            }
        });

        Button date = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Date.class));
            }
        });

        Button shoppingList = findViewById(R.id.shoppingList);
        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShoppingList.class));
            }
        });

        Button customEvent = findViewById(R.id.customEvent);
        customEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomEvent.class));
            }
        });
    }
}