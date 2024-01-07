package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import java.util.ArrayList;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
public class giftlist extends AppCompatActivity {
    static final int ADD_ITEM_REQUEST = 1;
    ArrayAdapter<Gift> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftlist);       

        // Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wish List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find the ListView
        ListView listView = findViewById(R.id.giftlistview);

        Intent newIntent = getIntent();
        ArrayList<Gift> giftList = (ArrayList<Gift>) newIntent.getSerializableExtra("LIST");
        Log.d("yeet", "giftlist: " + giftList);

        // Create an ArrayAdapter and set it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, giftList);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabaddgift);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(giftlist.this, AddGift.class);

                Log.d("yeet", "giftlist: " + giftList);
                // Create an Intent to launch add gift and pass the selected name

                intent.putExtra("LIST", giftList);
                intent.putExtra("TEST", "HEY!");
//                intent.putExtra("LIST", giftList);
                startActivityForResult(intent, ADD_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == Activity.RESULT_OK) {
            // Get the data from the result intent
            Gift newItemData = (Gift) data.getSerializableExtra("GIFT");  // Replace with the actual key

            // Update your ListView or adapter with the new item
            adapter.add(newItemData);
            adapter.notifyDataSetChanged();  // Notify the adapter of the data change
        }
    }
}