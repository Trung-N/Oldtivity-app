package com.example.oldivity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class user {

    @PrimaryKey
    public String name;
    public String lastName;
    public String email;

    public user(String name, String lastName, String email) {
        this.name = name;
        this.lastName  = lastName;
        this.email = email;
    }
}