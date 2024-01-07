package com.example.presentpal;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PersonList extends Application {
    private ArrayList<Person> sharedList;

    public ArrayList<Person> getSharedList() {
        return sharedList;
    }

    public void setSharedList(ArrayList<Person> list) {
        sharedList = list;
    }
}
