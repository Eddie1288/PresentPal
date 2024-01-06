package com.example.presentpal;
import java.io.Serializable;
public class Gift implements Serializable{
    private String name;
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
