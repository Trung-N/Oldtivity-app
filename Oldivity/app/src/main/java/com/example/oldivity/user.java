package com.example.oldivity;

public class user {

    public String firstName;
    public String lastName;
    public String email;


    public user(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getemail(){
        return email;
    }



}