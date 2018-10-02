package com.example.oldivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CreateEvent extends AppCompatActivity {

    private static final String TAG = "EventCreation";

    private EditText evTitle, evLocation, evDescription;
    private String title, loc, desc, date, phone;
    private CalendarView evCal;
    private FirebaseAuth evAuth;
    private DatabaseReference Database, userDatabase;
    public String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        evTitle = findViewById(R.id.eventTitle);
        evLocation = findViewById(R.id.eventLocation);
        evDescription = findViewById(R.id.eventDescription);
        evCal = findViewById(R.id.eventDate);

        //gets database and firebase authenticator instances and uid and phone number of user
        evAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser User = evAuth.getCurrentUser();
        uId = User.getUid();
        getUserInfo();

    }

    //copy and pasted from MainActivity
    @Override
    public void onStart() {
        super.onStart();
        /**unfinished**/
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser User = evAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void createEvent(View view){
        //extract all values from the fields
        title = evTitle.getText().toString().trim();
        loc = evLocation.getText().toString().trim();
        desc = evDescription.getText().toString().trim();
        date = getDate(evCal);

        if (!validateEntries()) {
            return;
        }


        //Push the new event to the branch /events/ in the database.

        Event event = new Event(title, loc, desc, date, uId, phone);

        Database.child("events").push().setValue(event);
        Log.w(TAG, "createEvent:success");
        Toast.makeText(CreateEvent.this, "Event successfully created!",
                Toast.LENGTH_SHORT).show();

        //Moves to 'My Events' page after event successfully created
        startActivity(new Intent(CreateEvent.this, MyEvents.class));
    }


    /**copied and modified for variable names from MainActivity.java
    /*makes sure all fields are filled out. If not, the user is notified
     *which required field is missing.
     */
    private boolean validateEntries() {
        boolean valid = true;

        String title = evTitle.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            evTitle.setError("Title is required");
            evTitle.requestFocus();
            valid = false;

        } else {
            evTitle.setError(null);
        }

        String description = evDescription.getText().toString().trim();

        if (TextUtils.isEmpty(description)) {
            evDescription.setError("Tell us something more about your event!");
            evDescription.requestFocus();
            valid = false;
        } else {
            evDescription.setError(null);
        }

        String date = getDate(evCal);

        if (TextUtils.isEmpty(date)) {
            evCal.requestFocus();
            valid = false;

        }

        String location = evLocation.getText().toString().trim();

        if (TextUtils.isEmpty(location)) {
            evLocation.setError("Where is your event being held?");
            evLocation.requestFocus();
            valid = false;

        } else {
            evLocation.setError(null);
        }
        return valid;
    }

    //Gets user/event creator's phone number to attach to event
    public void getUserInfo(){
        userDatabase.child(uId).child("number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phone = dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //helper function to get string from date
    public String getDate(CalendarView cal){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return(sdf.format(cal.getDate()));
    }
}
