package com.example.presentpal;
import java.io.Serializable;
public class Gift implements Serializable{
    private String name;
    private String date;
    private String link;
    private String event;
    public Gift(String name){
        this.name = name;
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
