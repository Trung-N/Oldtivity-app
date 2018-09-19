package com.example.oldivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private String hostNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        title = findViewById(R.id.displayEventTitle);
        description = findViewById(R.id.displayEventDescription);

        //retrieve passed event information
        Intent intent = getIntent();
        String[] info = intent.getStringArrayExtra("eventDets");

        hostNumber = info[5];
        title.setText(info[0]);
        String descriptionFormatted = "Description: " + info[1] + "\n" + "Date: " + info[2] + "\n" +
                "Location: " + info[4] + "\n" + "Hosted By: " + info[3];
        description.setText(descriptionFormatted);
    }
}
