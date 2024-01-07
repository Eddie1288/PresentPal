package com.example.presentpal;
import java.io.Serializable;
public class Gift implements Serializable{
    private String name;
    private String date;
    private String link;
    private String event;
    private String price;

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Gift(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
