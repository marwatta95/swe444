package net.marwa.applicationy;


import java.io.Serializable;
import java.util.LinkedList;

public class Photographer implements Serializable {
    String first,last,phone;
    LinkedList<String> dates=new LinkedList<String>();
    double price;
    String gender;

    public Photographer(String first,String last, String phone,double price,String gender) {
        this.first=first;
        this.last=last;
        this.phone = phone;
        this.price=price;
        this.gender=gender;

    }

    public Photographer() {
    }
    public String getFirst(){ return first;}
    public double getPrice()
    {
        return price;
    }
    public String getGender(){return gender;}
    public String getLast() {
        return last;
    }
    public String getName(){return first+"  "+last;}
    public String getPhone() {
        return phone;
    }
}