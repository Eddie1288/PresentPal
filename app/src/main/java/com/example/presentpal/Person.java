package com.example.presentpal;

import java.util.ArrayList;
public class Person {
    private String name;
    private ArrayList<Gift> gifts;
    public Person (String name, ArrayList<Gift> gifts){
        this.name = name;
        this.gifts = gifts;
    }
    public Person (String name){
        this.name = name;
        this.gifts = new ArrayList<Gift>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Gift> getGifts(){
        return gifts;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGifts(ArrayList<Gift> gifts){
        this.gifts = gifts;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
