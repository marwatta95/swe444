package net.marwa.applicationy;

import java.io.Serializable;
import java.util.LinkedList;



public class Clown implements Serializable {
    String name;
    double price;
    String imageUri;
    LinkedList<String> dates=new LinkedList<String>();


    public Clown(String name,double price, String imageUri) {
        this.name = name;this.price=price;
        this.imageUri = imageUri;
    }

    public Clown() {
    }

    public String getName() {
        return name;
    }
    public double getPrice()
    {
        return price;
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