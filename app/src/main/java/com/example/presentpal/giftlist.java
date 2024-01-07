package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import java.util.ArrayList;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
public class giftlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftlist);       

        // Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wish List");

        // Find the ListView
        ListView listView = findViewById(R.id.giftlistview);

        Intent intent = getIntent();
        ArrayList<Gift> giftList = (ArrayList<Gift>) intent.getSerializableExtra("LIST");

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<Gift> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, giftList);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabaddgift);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(giftlist.this, AddGift.class);
                startActivity(intent);
            }
        });
    }
}