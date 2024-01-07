package com.example.presentpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
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
    Button editButton;
    Button deleteButton;

    public MainActivity() {
        // this method fires only once per application start.
        // getApplicationContext returns null here

        Log.i("main", "Constructor fired");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PresentPal");


        // Find the ListView
        ListView listView = findViewById(R.id.namelistview);

        // This is the code for going to the edit activity for a specific person
//        Intent intent = new Intent(MainActivity.this, EditPersonActivity.class);
//        intent.putExtra("POS", 1);
//        startActivity(intent);


        // Retrieve list of people
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String personJson = preferences.getString("PersonList", "");
        String eventJson = preferences.getString("EventList", "");

        GlobalVars globalVars = (GlobalVars) getApplication();

        // If the retrieved person list from storage is not empty, then set as the person list
        if (personJson.length() != 0) {
            // Convert the JSON string back to a list of objects
            Gson gson = new Gson();
            Log.d("yeet", "onCreate: " + personJson);
            Type type = new TypeToken<ArrayList<Person>>() {}.getType();
            ArrayList<Person> retrievedPersonList = gson.fromJson(personJson, type);
            globalVars.setPersonList(retrievedPersonList);
        } else {
            // Otherwise, just create a new list of people
            globalVars.setPersonList(new ArrayList<Person>());
        }

        // If the retrieved event list from storage is not empty, then set as the person list
        if (eventJson.length() != 0) {
            // Convert the JSON string back to a list of objects
            Gson gson = new Gson();
            Log.d("yeet", "onCreate: " + eventJson);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> retrievedEventList = gson.fromJson(eventJson, type);
            globalVars.setEventList(retrievedEventList);
        } else {
            // Otherwise, just create a new list of people
            globalVars.setEventList(new ArrayList<String>());
        }

        addPersonButton = findViewById(R.id.add_person_button);

        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddPersonActivity.class));
            }
        });

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, globalVars.getPersonList());
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.remove(globalVars.getPersonList().get(position));
                adapter.notifyDataSetChanged();
                deletePerson(position);
                return false;
            }
        });

        // Handle item clicks
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Person selectedPerson = globalVars.getPersonList().get(position);
//            String selectedName = selectedPerson.getName();
//            ArrayList<Gift> Gifts = selectedPerson.getGifts();
//
//            // Create an Intent to launch add gift and pass the selected name
//            Intent intent = new Intent(MainActivity.this, giftlist.class);
//            Log.d("Yeet", "details: " + selectedName + " " + Gifts);
//            intent.putExtra("NAME", selectedName);
//            intent.putExtra("LIST", Gifts);
//            startActivity(intent);
//
//        });
    }

    void deletePerson(int pos) {
        GlobalVars personListObj = (GlobalVars) getApplication();
        ArrayList<Person> personList = personListObj.getPersonList();

//        personList.remove(pos);
        personListObj.setPersonList(personList);
//
        Gson gson = new Gson();
        String json = gson.toJson(personList);

        Log.d("yeet", "Delete: " + json);
//
//        // Save JSON string to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PersonList", json);
        editor.apply();
    }
}