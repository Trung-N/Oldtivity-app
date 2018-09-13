package com.example.oldivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
    }


    public void createEvent(View view) {
        Intent intent = new Intent(MyEvents.this, CreateEvent.class);
        startActivity(intent);
    }
    public void groupChat(View view) {
        Intent intent = new Intent(MyEvents.this, GroupChatActivity.class);
        startActivity(intent);
    }

}