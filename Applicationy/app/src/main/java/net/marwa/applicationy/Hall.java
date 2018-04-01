package net.marwa.applicationy;

import java.io.Serializable;
import java.util.LinkedList;



public class Hall implements Serializable {
    String name,description,address;
    int capacity;double price;
    String imageUri;
    LinkedList<String> dates=new LinkedList<String>();
    String location;

    public Hall(String name, String description,String address,int capacity,double price, String imageUri,String location) {
        this.name = name;this.description = description;this.address=address;this.capacity=capacity;this.price=price;
        this.imageUri = imageUri;this.location=location;

    }

    public Hall() {
    }

    public String getName() {
        return name;
    }
    public String getDescription(){
       return description;
    }
    public String getAddress()
    {
        return address;
    }
    public double getPrice()
    {
        return price;
    }
    public int getCapacity()
    {
        return capacity;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}