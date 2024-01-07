package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.Calendar;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import android.content.Intent;




public class AddGift extends AppCompatActivity {

    EditText dateTXT;
    ImageView cal;
    private int mDate, mMonth, mYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);

        // Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add A Gift");

        dateTXT = findViewById(R.id.date);
        cal = findViewById(R.id.datepicker);

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

        // Retrieve the list of gifts from the intent
        ArrayList<Gift> giftList = (ArrayList<Gift>) getIntent().getSerializableExtra("LIST");
        Log.d("poop", "onCreate: +" + giftList);
        Button button = (Button) findViewById(R.id.confirmButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Retrieve values from UI components
                String giftName = ((EditText) findViewById(R.id.textInputEditText)).getText().toString();
                String giftDate = ((EditText) findViewById(R.id.date)).getText().toString();
                String giftPrice = ((EditText) findViewById(R.id.textInputEditText3)).getText().toString();
                String giftLink = ((EditText) findViewById(R.id.textInputEditText4)).getText().toString();

                // Create a new Gift object with the gathered details
                Gift newGift = new Gift(giftName);
                newGift.setDate(giftDate);
                newGift.setPrice(giftPrice);
                newGift.setLink(giftLink);

                // Retrieve existing gift list or create a new one
                int position = 0;

                Intent intent = getIntent();
                String selectedName = intent.getStringExtra("NAME");
                position = intent.getIntExtra("POS", -1);


                // Add the new gift to the list
                giftList.add(newGift);
                GlobalVars globalVars = (GlobalVars) getApplication();
                ArrayList<Person> plist = globalVars.getPersonList();
                ArrayList<Gift> glist = globalVars.getGiftList();
                Person person = plist.get(position);
                person.setGifts(giftList);
                plist.set(position, person);

                // Pass the updated list back to the previous activity or store it as needed
                intent = new Intent();
                intent.putExtra("UPDATED_LIST", giftList);
                setResult(RESULT_OK, intent);
                finish();

                Toast.makeText(AddGift.this, "Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
