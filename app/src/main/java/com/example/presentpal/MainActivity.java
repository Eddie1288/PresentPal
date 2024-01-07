package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve list of people
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = preferences.getString("EventList", "");

        // Retrieve list of people
        CustomEventList customEventList = (CustomEventList) getApplication();
        // If the retrieved person list from storage is not empty, then set as the person list
        if (json.length() != 0) {
            // Convert the JSON string back to a list of objects
            Gson gson = new Gson();
            Log.d("yeet", "onCreate: " + json);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> retrievedEventList = gson.fromJson(json, type);
            customEventList.setSharedList(retrievedEventList);
        } else {
            // Otherwise, just create a new list of people
            customEventList.setSharedList(new ArrayList<String>());
        }

        // Create an Intent to launch ItemsActivity and pass the selected name
        Intent intent = new Intent(MainActivity.this, EventsActivity.class);
        startActivity(intent);
    }
}