package net.marwa.applicationy;


import java.io.Serializable;
import java.util.LinkedList;

public class Dj implements Serializable{
    String first,last,phone;
    double price;
    LinkedList<String> dates=new LinkedList<String>();

    public Dj(String first,String last, String phone,double price) {
        this.first=first;
        this.last=last;
        this.phone = phone;
        this.price=price;

    }

    public Dj() {
    }
    public String getFirst(){ return first;}
    public double getPrice()
    {
        return price;
    }
public String getPhone(){return phone;}
    public String getLast() {
        return last;
    }
    public String getName(){return first+"  "+last;}

}