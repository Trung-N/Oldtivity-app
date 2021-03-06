package com.example.oldivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    private TextView editText;
    private String Name;
    private String uID;
    private static final String TAG = "profile";
    private FirebaseAuth mAuth;

    private DatabaseReference Database;
    private DatabaseReference currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editText = findViewById(R.id.displayName);
        getUserProfile();

    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Database = FirebaseDatabase.getInstance().getReference();

        //Get user's name from user database
        if (user != null) {
            uID = user.getUid();
            currentUser = Database.child("users").child(uID).child("firstName");

            //Display user's name to UI
            currentUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Name = dataSnapshot.getValue(String.class);
                    editText.setText(Name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    throw databaseError.toException();
                }

            });

        }
    }

    public void myEvents(View view) {
        Intent intent = new Intent(profile.this, MyEvents.class);
        startActivity(intent);
    }


    public void exploreEvents(View view) {
        Intent intent = new Intent(profile.this, ExploreEvents.class);
        startActivity(intent);
    }

    public void createEvents(View view) {
        Intent intent = new Intent(profile.this, CreateEvent.class);
        startActivity(intent);
    }
}
