package com.example.presentpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addPersonButton;

    public MainActivity() {
        // this method fires only once per application start.
        // getApplicationContext returns null here

        Log.i("main", "Constructor fired");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve list of people
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = preferences.getString("PersonList", "");

        PersonList personList = (PersonList) getApplication();

        // If the retrieved person list from storage is not empty, then set as the person list
        if (json.length() != 0) {
            // Convert the JSON string back to a list of objects
            Gson gson = new Gson();
            Log.d("yeet", "onCreate: " + json);
            Type type = new TypeToken<ArrayList<Person>>() {}.getType();
            ArrayList<Person> retrievedPersonList = gson.fromJson(json, type);
            personList.setSharedList(retrievedPersonList);
        } else {
            // Otherwise, just create a new list of people
            personList.setSharedList(new ArrayList<Person>());
        }

        addPersonButton = findViewById(R.id.add_person_button);

        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddPersonActivity.class));
            }
        });
    }
}