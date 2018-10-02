package com.example.oldivity;

public class user {

    public String firstName;
    public String lastName;
    public String email;
    public String number;


    public user(String firstName, String lastName, String email, String number) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email = email;
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getNumber() {return number;}


}