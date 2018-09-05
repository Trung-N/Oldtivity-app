package com.example.oldivity;

import java.util.HashMap;
import java.util.Map;

public class Event {

    public String title;
    public String location;
    public String description;
    public String date;
    public String host;

    public Event(String title, String location, String description, String date, String host) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.host = host;
    }

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
}

