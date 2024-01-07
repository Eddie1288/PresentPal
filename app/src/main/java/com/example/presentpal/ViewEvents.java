package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ViewEvents extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        GlobalVars eventListObj = (GlobalVars) getApplication();
        ArrayList<String> eventList = eventListObj.getEventList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Handle item clicks
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedEvent = eventList.get(position);
//        });
    }
}