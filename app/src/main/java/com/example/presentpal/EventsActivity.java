package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class EventsActivity extends AppCompatActivity {

    private Map<String, Class<?>> eventActivityMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select an Event");

        initializeEventActivityMap();

        ArrayList<String> eventList = new ArrayList<>();

        eventList.add("Birthday");
        eventList.add("Christmas");
        eventList.add("Wedding");
        eventList.add("Anniversary");
        eventList.add("Baby Shower");
        eventList.add("Mother's Day");
        eventList.add("Father's Day");
        eventList.add("House Warming");
        eventList.add("Date");
        eventList.add("Shopping List");
        eventList.add("Custom Event");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        ListView listView = findViewById(R.id.simpleListView);
        listView.setAdapter(adapter);

        // Handle item clicks
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedEvent = eventList.get(position);

//          // Retrieve the corresponding activity class from the map
            Class<?> selectedActivityClass = eventActivityMap.get(selectedEvent);

            if (selectedActivityClass != null) {
                // Create an Intent to launch ItemsActivity and pass the selected name
                Intent resultIntent = new Intent();
                resultIntent.putExtra("EVENT", selectedEvent);
                Log.d("yeet", "onCreate: " + selectedEvent);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
//                startActivity(intent);
            }
        });
    }

    private void initializeEventActivityMap() {
        // Initialize the mapping between event names and activity classes
        eventActivityMap = new HashMap<>();
        eventActivityMap.put("Birthday", BirthdayActivity.class);
        eventActivityMap.put("Christmas", ChristmasActivity.class);
        eventActivityMap.put("Wedding", WeddingActivity.class);
        eventActivityMap.put("Anniversary", AnniversaryActivity.class);
        eventActivityMap.put("Baby Shower", BabyShowerActivity.class);
        eventActivityMap.put("Mother's Day", MothersDayActivity.class);
        eventActivityMap.put("Father's Day", FathersDayActivity.class);
        eventActivityMap.put("House Warming", HouseWarmingActivity.class);
        eventActivityMap.put("Date", DateActivity.class);
        eventActivityMap.put("Shopping List", ShoppingListActivity.class);
        eventActivityMap.put("Custom Event", CustomEventActivity.class);
    }
}