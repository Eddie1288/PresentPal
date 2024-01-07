package com.example.presentpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        // Create a list of names
        ArrayList<Person> namesList = new ArrayList<>();
        Person person1 = new Person("Alice");

        ArrayList<Gift> person1Gift = new ArrayList<>();
        person1Gift.add(new Gift("car"));
        person1Gift.add(new Gift("hands"));
        person1.setGifts(person1Gift);

        namesList.add(person1);
        namesList.add(new Person("Bob"));
        namesList.add(new Person("Charlie"));
        namesList.add(new Person("Daniel"));
        namesList.add(new Person("Huey"));
        namesList.add(new Person("Chris"));
        namesList.add(new Person("Bill"));

        // Add more names as needed

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesList);
        listView.setAdapter(adapter);

        // Handle item clicks
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Person selectedPerson = namesList.get(position);
            String selectedName = selectedPerson.getName();
            ArrayList<Gift> Gifts = selectedPerson.getGifts();

            // Create an Intent to launch addgift and pass the selected name
            Intent intent = new Intent(MainActivity.this, giftlist.class);
            intent.putExtra("NAME", selectedName);
            intent.putExtra("LIST", Gifts);
            startActivity(intent);

        });
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

    void deletePerson(int pos) {
        PersonList personListObj = (PersonList) getApplication();
        ArrayList<Person> personList = personListObj.getSharedList();

        personList.remove(pos);
        personListObj.setSharedList(personList);

        Gson gson = new Gson();
        String json = gson.toJson(personList);

        Log.d("yeet", "Delete: " + json);

        // Save JSON string to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PersonList", json);
        editor.apply();

    }
}