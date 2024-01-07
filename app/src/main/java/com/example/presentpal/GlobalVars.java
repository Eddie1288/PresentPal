package com.example.presentpal;

import android.app.Application;

import java.util.ArrayList;

public class GlobalVars extends Application {
    private ArrayList<Person> personList;
    private ArrayList<String> eventList;
    public ArrayList<Person> getPersonList() {
        return personList;
    }
    public void setPersonList(ArrayList<Person> list) {
        personList = list;
    }
    public ArrayList<String> getEventList() {
        return eventList;
    }
    public void setEventList(ArrayList<String> list) {
        eventList = list;
    }
}
