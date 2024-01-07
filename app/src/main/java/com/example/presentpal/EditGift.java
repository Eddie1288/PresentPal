package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class EditGift extends AppCompatActivity {
    EditText giftName;
    EditText dateValue;
    EditText price;
    EditText link;
    EditText occasionSelected;
    TextView occasion;
    Button confirmEdit;
    ImageView cal;
    Button addGiftButton;
    private int mDate, mMonth, mYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gift);
        Intent intent = getIntent();
        Gift gift = new Gift("");


        giftName = findViewById(R.id.add_gift_name);
        dateValue = findViewById(R.id.date);
        price = findViewById(R.id.add_gift_price);
        link = findViewById(R.id.add_gift_link);
        confirmEdit = findViewById(R.id.confirm_gift_button);
        addGiftButton = findViewById(R.id.add_gift_button);
        cal = findViewById(R.id.datepicker);
        addGiftButton = findViewById(R.id.add_gift_button);
        occasion = findViewById(R.id.textView5);
        occasionSelected = findViewById(R.id.editTextText);


        if(intent.hasExtra("GIFT")){
            gift = (Gift) intent.getSerializableExtra("GIFT");
            giftName.setText(gift.getName());
            dateValue.setText(gift.getDate());
            price.setText(gift.getPrice());
            link.setText(gift.getLink());
            occasionSelected.setText(gift.getEvent());

            Log.d("yeet", "test?: " + gift);
        } else {
            Log.d("yeet", "not found");
        }

        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGift();
            }
        });

        cal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditGift.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateValue.setText(date+"-"+(month + 1)+"-"+year);
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
    }

    void editGift() {
        Gift newGift = new Gift(giftName.getText().toString(), dateValue.getText().toString(), link.getText().toString(), occasionSelected.getText().toString(), price.getText().toString());

        GlobalVars globalVars  = (GlobalVars) getApplication();
        ArrayList<Person> sharedPersonList = globalVars.getPersonList();

        Integer personPosition = getIntent().getIntExtra("PERSON_POS", 0);
        Integer giftPosition = getIntent().getIntExtra("GIFT_POS", 0);

        ArrayList<Gift> newPersonGiftList = sharedPersonList.get(personPosition).getGifts();
        newPersonGiftList.set(giftPosition, newGift);

        // Update the person list
        globalVars.getPersonList().get(personPosition).setGifts(newPersonGiftList);

        // Convert list to a JSON string
        Gson gson = new Gson();
        String json = gson.toJson(sharedPersonList);

        Log.d("yeet", "addPerson: " + json);

        // Save JSON string to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PersonList", json);
        editor.apply();

        Intent resultIntent = new Intent();

        // Pass this gift to the previous activity to add it to the list view in the previous activity
        resultIntent.putExtra("GIFT", newGift);
        resultIntent.putExtra("GIFT_POS", giftPosition);
        resultIntent.putExtra("PERSON_POS", personPosition);

        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }
}