package com.example.presentpal;
import java.io.Serializable;
public class Gift implements Serializable{
    private String name;
    private String date;
    private String link;
    private String event;
    private String price;
    public Gift(String name){
        this.name = name;
    }

    public Gift(String name, String date, String link, String event, String price) {
        this.name = name;
        this.date = date;
        this.link = link;
        this.event = event;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(){
        this.name = name;
    }
    @Override
    public String toString(){
        return this.name;
    }
}
