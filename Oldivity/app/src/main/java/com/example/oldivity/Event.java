package com.example.oldivity;

import java.util.HashMap;
import java.util.Map;

public class Event {

    public String title;
    public String location;
    public String description;
    public String date;

    public Event(String title, String location, String description, String date) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("date", date);
        result.put("location", location);

        return result;
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
}

