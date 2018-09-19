package com.example.oldivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExploreEvents extends AppCompatActivity {

    private TextView testTextView;
    private ArrayList<Event> allEvents;
    private FirebaseAuth evAuth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference Database;
    //private FirebaseFunctions mDatabaseFunctions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_events);

        allEvents = new ArrayList<>();
        evAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference();
        //mD

        recyclerView = findViewById(R.id.EventsList);
        ArrayList<Event> testEvents = testArray();
        mAdapter = new EventsAdapter(testEvents, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {

            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //displayAllEvents();
    }

    private void displayAllEvents(){

        Database.child("events").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //THIS AREA WORKS
                Log.w("loadEvents", "loadEvent:success");
                Toast.makeText(ExploreEvents.this, "Events successfully loaded!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("loadEvents", "loadEvent:failure");
                Toast.makeText(ExploreEvents.this, "Events not successfully loaded!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Event> testArray() {
        ArrayList<Event> returEvents = new ArrayList<>();
        Event e = new Event("walking","here","we are walking","18/12/18","Big Joe","911");
        Event e1 = new Event("sitting","here","we are walking","18/12/18","Big Joe","911");
        Event e2 = new Event("funking","here","we are walking","18/12/18","Big Joe","911");
        Event e3 = new Event("just hanging out","here","we are walking","18/12/18","Big Joe","911");
        returEvents.add(e);
        returEvents.add(e1);
        returEvents.add(e2);
        returEvents.add(e3);
        return returEvents;
    }
}
