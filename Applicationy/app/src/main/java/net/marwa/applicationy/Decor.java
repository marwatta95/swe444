package net.marwa.applicationy;


import java.io.Serializable;

public class Decor implements Serializable {
    String type;
    double price;
    String imageUri;

    public Decor(String type,double price, String imageUri) {
        this.type=type;
        this.price=price;
        this.imageUri = imageUri;
    }

    public Decor() {
    }
    public String getType(){ return type;}
    public double getPrice()
    {
        return price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}