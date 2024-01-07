package com.example.presentpal;

import android.app.Application;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomEventList extends Application {
    private ArrayList<String> sharedList;
    public ArrayList<String> getSharedList() {
        return sharedList;
    }
    public void setSharedList(ArrayList<String> list) {
        sharedList = list;
    }

}
