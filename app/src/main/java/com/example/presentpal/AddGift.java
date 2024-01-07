package com.example.presentpal;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.gson.Gson;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import android.content.Intent;

public class AddGift extends AppCompatActivity {
    EditText nameTXT;
    EditText dateTXT;
    EditText priceTXT;
    EditText linkTXT;
    ImageView cal;
    Button addGiftButton;
    private int mDate, mMonth, mYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);

        // Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add A Gift");

        nameTXT = findViewById(R.id.add_gift_name);
        priceTXT = findViewById(R.id.add_gift_price);
        linkTXT = findViewById(R.id.add_gift_link);
        dateTXT = findViewById(R.id.date);
        cal = findViewById(R.id.datepicker);
        addGiftButton = findViewById(R.id.add_gift_button);

        Intent intent = new Intent();
//        ArrayList<Gift> giftList = (ArrayList<Gift>) intent.getSerializableExtra("LIST");

//        Log.d("yeet", "Received: " + giftList);
        Log.d("yeet", "String " + intent.getStringExtra("TEST"));

        // Create an Intent to launch add gift and pass the selected name
        ArrayList<Gift> giftList = new ArrayList<>();
        Intent i = getIntent();
        Integer position = i.getIntExtra("POS", 0);;
        //check first
        if(i.hasExtra("LIST")){
            giftList = (ArrayList<Gift>) i.getSerializableExtra("LIST");
            Log.d("yeet", "test?: " + giftList);
        }

        cal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddGift.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTXT.setText(date+"-"+month+"-"+year);
                    }
                },mYear,mMonth, mDate);
                datePickerDialog.show();
            }
        });

        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
            }
        });

        ArrayList<Gift> finalGiftList = giftList;
        addGiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Based on constructor Gift(String name, String date, String link, String event, String price)
                Gift gift = new Gift(nameTXT.getText().toString(), dateTXT.getText().toString(), linkTXT.getText().toString(), null ,priceTXT.getText().toString());
                finalGiftList.add(gift);

                // Get personlist global vars
                GlobalVars globalVars = (GlobalVars) getApplication();

                // Get the person of the gift we wanna add to
                Person person = globalVars.getPersonList().get(position);

                // Set the finalGiftList array to the person's gift list
                person.setGifts(finalGiftList);

                // Update this person in the person list
                Log.d("yeet", "position: " + position);
                globalVars.getPersonList().set(position, person);

                Intent resultIntent = new Intent();

                // Pass this gift to the previous activity to add it to the list view in the previous activity
                resultIntent.putExtra("GIFT", gift);
                setResult(Activity.RESULT_OK, resultIntent);

                // Convert list to a JSON string
                Gson gson = new Gson();
                String json = gson.toJson(globalVars.getPersonList());

                Log.d("yeet", "updatePerson: " + json);

                // Save JSON string to SharedPreferences
                SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("PersonList", json);
                editor.apply();

                finish();
            }
        });
    }
}
