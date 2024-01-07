package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class GiftDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_details);

        // Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wish List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesList);
        listView.setAdapter(adapter);

        // Assuming you have a ListView set up and populated with gifts tied to a person
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected gift object based on the position in the list
            Gift selectedGift = giftList.get(position);

            // Create an Intent to launch GiftDetailsActivity and pass the selected gift details
            Intent intent = new Intent(MainActivity.this, GiftDetailsActivity.class);
            intent.putExtra("SELECTED_GIFT_ID", selectedGift.getId()); // Pass necessary details
            // You can pass more details like name, description, etc., based on your Gift object
            startActivity(intent);
        });
    }
}