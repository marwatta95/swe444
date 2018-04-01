package net.marwa.applicationy;


import java.io.Serializable;
import java.util.LinkedList;

public class Band implements Serializable{
    String first,phone;
    int last;
    double price;
    LinkedList<String> dates=new LinkedList<String>();

    public Band(String first,int last, String phone,double price) {
        this.first=first;
        this.last=last;
        this.phone = phone;
        this.price=price;

    }

    public Band() {
    }
    public String getFirst(){ return first;}
    public int getLast() {
        return last;
    }
    public String getPhone(){return phone;}
    public double getPrice()
    {
        return price;
    }
    public String getName(){return first+" "+last;}



}