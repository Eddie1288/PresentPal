package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ListView
        ListView listView = findViewById(R.id.namelistview);

        // Create a list of names
        ArrayList<Person> namesList = new ArrayList<>();
        Person person1 = new Person("Alice");

        ArrayList<Gift> person1Gift = new ArrayList<>();
        person1Gift.add(new Gift("car"));
        person1Gift.add(new Gift("hands"));
        person1.setGifts(person1Gift);
        namesList.add(person1);

        namesList.add(new Person("Bob"));
        namesList.add(new Person("Charlie"));
        namesList.add(new Person("Daniel"));
        namesList.add(new Person("Huey"));
        namesList.add(new Person("Chris"));
        namesList.add(new Person("Bill"));

        // Add more names as needed

        // Create an ArrayAdapter and set it to the ListView
        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesList);
        listView.setAdapter(adapter);

        // Handle item clicks
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Person selectedPerson = namesList.get(position);
            String selectedName = selectedPerson.getName();
            ArrayList<Gift> Gifts = selectedPerson.getGifts();

            // Create an Intent to launch addgift and pass the selected name
            Intent intent = new Intent(MainActivity.this, addgift.class);
            intent.putExtra("NAME", selectedName);
            intent.putExtra("LIST", Gifts);
            startActivity(intent);

        });
    }
}