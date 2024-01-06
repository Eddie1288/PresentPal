package com.example.presentpal;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String relationship;
    private String photoURL;

    public Person(String name, String relationship, String photoURL) {
        this.name = name;
        this.relationship = relationship;
        this.photoURL = photoURL;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
