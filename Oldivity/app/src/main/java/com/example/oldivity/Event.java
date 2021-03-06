package com.example.oldivity;
import java.util.Map;

public class Event {

    public String id;
    public String title;
    public String location;
    public String description;
    public String date;
    public String host;
    public String phoneNumber;
    public String distance;
    public String membersCount;
    public Map members;


    public Event(String title, String location, String description, String date, String host, String phoneNumber, Map members) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.host = host;
        this.phoneNumber = phoneNumber;
        this.members = members;

    }

    public Event(String id, String title, String location, String description, String date, String host, String phoneNumber, String distance, String membersCount) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.host = host;
        this.phoneNumber = phoneNumber;
        this.distance = distance;
        this.membersCount = membersCount;
    }

    public String getId() {return id;}

    public String getTitle() {
        return title;
    }

    public String getLocation(){
        return location;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){
        return date;
    }

    public String getHost() { return host;}

    public String getPhoneNumber() { return phoneNumber; }

    public String getDistance() {return distance;}

    public String getMembersCount() {return membersCount;}

    public Map getMembers() {return members;}
}


