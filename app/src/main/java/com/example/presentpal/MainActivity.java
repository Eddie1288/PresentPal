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
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addPersonButton;
    Button editButton;
    Button deleteButton;
    ArrayAdapter<Person> adapter;
    GlobalVars globalVars;

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
        ListView namelist = findViewById(R.id.namelistview);

        // Retrieve list of people
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String personJson = preferences.getString("PersonList", "");
        String eventJson = preferences.getString("EventList", "");

        globalVars = (GlobalVars) getApplication();

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

        // Create an ArrayAdapter and set it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, globalVars.getPersonList());
        namelist.setAdapter(adapter);

        // Handle item clicks
        namelist.setOnItemClickListener((parent, view, position, id) -> {
            Person selectedPerson = globalVars.getPersonList().get(position);
            String selectedName = selectedPerson.getName();
            ArrayList<Gift> Gifts = selectedPerson.getGifts();

            Intent intent = new Intent(MainActivity.this, giftlist.class);
            intent.putExtra("NAME", selectedName);
            intent.putExtra("PERSON_POS", position);
            intent.putExtra("LIST", Gifts);
            startActivity(intent);
        });

        addPersonButton = findViewById(R.id.add_person_button);

        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddPersonActivity.class));
            }
        });

        registerForContextMenu(namelist);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.edit_delete_people, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        if (item.getItemId() == R.id.edit_option) {
            // Edit Person: Implement the logic for editing a person
            Toast.makeText(this, "Person Edited", Toast.LENGTH_SHORT).show();
            Intent edit = new Intent(MainActivity.this, EditPersonActivity.class);
            edit.putExtra("POS", position);
            startActivity(edit);
            return true;
        } else if (item.getItemId() == R.id.delete_option) {
            // Delete Person: Implement the logic for deleting a person
            Toast.makeText(this, "Person Deleted", Toast.LENGTH_SHORT).show();
            adapter.remove(globalVars.getPersonList().get(position));
            adapter.notifyDataSetChanged();
            deletePerson(position);
            Toast.makeText(this, "Person Deleted at position: " + position, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
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