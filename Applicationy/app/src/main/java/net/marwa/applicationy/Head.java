package net.marwa.applicationy;


import java.util.LinkedList;

public class Head {
    String first,last,phone;
String location;
    LinkedList<String> dates=new LinkedList<String>();

    public Head(String first,String last, String phone,String location) {
        this.first=first;
        this.last=last;
        this.phone = phone;
        this.location=location;


    }

    public Head() {
    }
    public String getFirst(){ return first;}
    public String getLocation()
    {
        return location;
    }
    public String getPhone(){return phone;}
    public String getLast() {
        return last;
    }

}