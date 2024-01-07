package com.example.presentpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

public class EditPersonActivity extends AppCompatActivity {
    EditText nameText;
    EditText relationshipText;
    Button BSelectImage;
    Button confirmEditButton;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;
    Integer pos;
    String selectedURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        nameText = findViewById(R.id.edit_name_text);
        relationshipText = findViewById(R.id.edit_relationship_text);
        IVPreviewImage = findViewById(R.id.preview_image_edit);
        BSelectImage = findViewById(R.id.select_photo_button2);
        confirmEditButton = findViewById(R.id.confirm_edit_button);

        GlobalVars personListObj = (GlobalVars) getApplication();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            pos = (Integer) b.get("POS");
            Person person = personListObj.getPersonList().get(pos);
            Log.d("yeet", "URI: " + person.getPhotoURL());

            nameText.setText(person.getName());
            relationshipText.setText(person.getRelationship());

            try {
                File photoFile = new File(person.getPhotoURL());
                if (photoFile.exists()) {
                    IVPreviewImage.setImageURI(Uri.fromFile(photoFile));
                } else {
                    Toast.makeText(personListObj, "Image does not exist", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }


        }

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPerson(pos);
                finish();
            }
        });

    }

    void editPerson(int pos) {
        GlobalVars personListObj = (GlobalVars) getApplication();
        ArrayList<Person> personList = personListObj.getPersonList();

        Person person = personList.get(pos);
        person.setName(nameText.getText().toString());
        person.setRelationship(relationshipText.getText().toString());

        personList.set(pos, person);
        personListObj.setPersonList(personList);

        Gson gson = new Gson();
        String json = gson.toJson(personList);

        Log.d("yeet", "Edit: " + json);

        // Save JSON string to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PersonList", json);
        editor.apply();

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
                    selectedURI = selectedImageUri.toString();
                    Log.d("yeet", "onActivityResult: " + selectedURI);
                }
            }
        }
    }
}