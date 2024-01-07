package com.example.presentpal;
import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    private String name;
    private String relationship;
    private String photoURL;
    private ArrayList<Gift> gifts;

    public Person(String name, String relationship, String photoURL, ArrayList<Gift> gifts) {
        this.name = name;
        this.relationship = relationship;
        this.photoURL = photoURL;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
