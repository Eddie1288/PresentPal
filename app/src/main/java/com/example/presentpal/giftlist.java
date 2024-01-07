package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import java.util.ArrayList;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import androidx.appcompat.widget.Toolbar;
public class giftlist extends AppCompatActivity {
    static final int ADD_ITEM_REQUEST = 1;
    static final int EDIT_ITEM_REQUEST = 2;
    ListView listView;
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
        listView = findViewById(R.id.giftlistview);

        Intent newIntent = getIntent();
        ArrayList<Gift> giftList = (ArrayList<Gift>) newIntent.getSerializableExtra("LIST");
        Integer giftPos = newIntent.getIntExtra("GIFT_POS", 0);
        Integer personPos = newIntent.getIntExtra("PERSON_POS", 0);

        Log.d("yeet", "giftlist: " + giftList);

        // Create an ArrayAdapter and set it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, giftList);
        listView.setAdapter(adapter);
        Log.d("poop", "onCreate: +" + giftList);
        FloatingActionButton fab = findViewById(R.id.fabaddgift);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder adb=new AlertDialog.Builder(giftlist.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete this gift?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        giftList.remove(position);
                        updateGiftList(giftList, personPos, position);
                        adapter.notifyDataSetChanged();  // Notify the adapter of the data change
                    }});
                adb.show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(giftlist.this, EditGift.class);
                Log.d("yeet", "selected: " + giftList.get(position));
                intent2.putExtra("GIFT", giftList.get(position));
                intent2.putExtra("GIFT_POS", position);
                intent2.putExtra("PERSON_POS", getIntent().getIntExtra("PERSON_POS", 0));
                startActivityForResult(intent2, EDIT_ITEM_REQUEST);
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                GlobalVars globalVars = (GlobalVars) getApplication();
                Intent intent = new Intent(giftlist.this, AddGift.class);

                Log.d("yeet", "giftlist: " + giftList);
                // Create an Intent to launch add gift and pass the selected name

                intent.putExtra("LIST", giftList);
                intent.putExtra("POS", giftPos);
//                intent.putExtra("LIST", giftList);
                startActivityForResult(intent, ADD_ITEM_REQUEST);
            }
        });

    }

    // For the love of god never fucking do this ever again. this is unholy code. absolutely cursed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == Activity.RESULT_OK) {
            // Get the data from the result intent
            Gift newItemData = (Gift) data.getSerializableExtra("GIFT");  // Replace with the actual key

            // Update your ListView or adapter with the new item
            adapter.add(newItemData);
            adapter.notifyDataSetChanged();  // Notify the adapter of the data change
        } else if (requestCode == EDIT_ITEM_REQUEST && resultCode == Activity.RESULT_OK) {
            // Get the data from the result intent
            Gift newItemData = (Gift) data.getSerializableExtra("GIFT");  // Replace with the actual key
            Integer giftPos = data.getIntExtra("GIFT_POS", 0);
            Integer personPos = data.getIntExtra("PERSON_POS", 0);

            GlobalVars globalVars = (GlobalVars) getApplication();
            Log.d("yeet", "List so far: " + globalVars.getPersonList().get(personPos).getGifts());
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, globalVars.getPersonList().get(personPos).getGifts());
            listView.setAdapter(adapter);
            Log.d("yeet", "updated ");

            ArrayList<Gift> newGiftList = globalVars.getPersonList().get(personPos).getGifts();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent2 = new Intent(giftlist.this, EditGift.class);
                    Log.d("yeet", "selected: " + newGiftList);
                    intent2.putExtra("GIFT", newGiftList.get(position));
                    intent2.putExtra("GIFT_POS", position);
                    intent2.putExtra("PERSON_POS", getIntent().getIntExtra("PERSON_POS", 0));
                    startActivityForResult(intent2, EDIT_ITEM_REQUEST);
                }
            });
        }
    }

    void updateGiftList(ArrayList<Gift> newGiftList, Integer personPosition, Integer giftPosition) {
        GlobalVars globalVars = (GlobalVars) getApplication();
        ArrayList<Gift> newPersonGiftList = globalVars.getPersonList().get(personPosition).getGifts();

//        // Update the person list
        globalVars.getPersonList().get(personPosition).setGifts(newGiftList);
//
//        // Convert list to a JSON string
        Gson gson = new Gson();
        String json = gson.toJson(globalVars.getPersonList());
//
        Log.d("yeet", "addPerson: " + json);
//
//        // Save JSON string to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PersonList", json);
        editor.apply();
    }
}