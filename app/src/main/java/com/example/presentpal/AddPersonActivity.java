package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/*
    Adding images by Geeksforgeeks:
    https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
*/
public class AddPersonActivity extends AppCompatActivity {
    // One Button
    Button BSelectImage;

    String selectedURI;

    // One Preview Image
    ImageView IVPreviewImage;

    Button BConfirmAdd;

    EditText nameText;
    EditText relationshipText;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        BSelectImage = findViewById(R.id.select_photo_button);
        BConfirmAdd = findViewById((R.id.confirm_add_button));
        IVPreviewImage = findViewById(R.id.preview_image);
        nameText = findViewById(R.id.add_name_text);
        relationshipText = findViewById(R.id.add_relationship_text);

        BConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                addPerson();
            }
        });


        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }
    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);

                    selectedURI = getPathFromURI(selectedImageUri);
                    Log.d("Found url", "onActivityResult: " + selectedURI);
                }
            }
        }
    }

    // Utility method to convert content URI to file path
    private String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }

    void addPerson() {
        // Create a new person object
        Person person = new Person(nameText.getText().toString(), relationshipText.getText().toString(), selectedURI, new ArrayList<Gift>());

        // Retrieve the global person list
        GlobalVars globalVars  = (GlobalVars) getApplication();
        ArrayList<Person> sharedPersonList = globalVars.getPersonList();

        // Add this person to this list
        sharedPersonList.add(person);

        // Update the person list
        globalVars.setPersonList(sharedPersonList);

        // Convert list to a JSON string
        Gson gson = new Gson();
        String json = gson.toJson(sharedPersonList);

        Log.d("yeet", "addPerson: " + json);

        // Save JSON string to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PersonList", json);
        editor.apply();

    }
}