package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import java.util.ArrayList;

public class addgift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgift);

        // Find the ListView
        ListView listView = findViewById(R.id.giftlistview);

        Intent intent = getIntent();
        ArrayList<Gift> giftList = (ArrayList<Gift>) intent.getSerializableExtra("LIST");
        // Create a list of names
//        ArrayList<String> giftList = new ArrayList<>();
//        giftList.add("toy1");
//        giftList.add("toy2");
//        giftList.add("toy3");
//        giftList.add("toy4");
//        giftList.add("toy5");
//        giftList.add("toy6");
        // Add more names as needed

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<Gift> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, giftList);
        listView.setAdapter(adapter);

    }
}