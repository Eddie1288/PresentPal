package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CustomEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_event);

        EditText editText = findViewById(R.id.eventName);
        Button btn = findViewById(R.id.eventAddedButton);

        btn.setOnClickListener(v -> {
            String data = editText.getText().toString();

            GlobalVars globalVars  = (GlobalVars) getApplication();
            ArrayList<String> sharedEventList = globalVars.getEventList();

            // Add this person to this list
            sharedEventList.add(data);

            // Update the person list
            globalVars.setEventList(sharedEventList);

            // Convert list to a JSON string
            Gson gson = new Gson();
            String json = gson.toJson(sharedEventList);

            Log.d("yeet", "addPerson: " + json);

            // Save JSON string to SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("EventList", json);
            editor.apply();
        });

        // Retrieve list of people
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = preferences.getString("EventList", "");

        Log.d("hello1", "onCreate: " + json);
    }
}