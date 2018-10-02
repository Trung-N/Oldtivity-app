package com.example.oldivity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.FormBody;

public class ExploreEvents extends AppCompatActivity {

    private TextView testTextView;
    private ArrayList<Event> allEvents;
    private FirebaseAuth evAuth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference Database;
    //private FirebaseFunctions mDatabaseFunctions;

    private static final int COMMAND_DISPLAY_SERVER_RESPONSE = 1;
    private static final String KEY_SERVER_RESPONSE_OBJECT = "KEY_SERVER_RESPONSE_OBJECT";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_events);

        allEvents = new ArrayList<>();
        evAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.EventsList);
        Handler respHandler = new Handler() {
            // When this handler receive message from child thread.
            @Override
            public void handleMessage(Message msg) {

                    // Check what this message want to do.
                    if (msg.what == COMMAND_DISPLAY_SERVER_RESPONSE) {
                        // Get server response text.
                        Bundle bundle = msg.getData();
                        String respText = bundle.getString(KEY_SERVER_RESPONSE_OBJECT);

                        // Process server response
                        try {
                            allEvents = constructEventArray(respText);
                            mAdapter = new EventsAdapter(allEvents, new ClickListener() {
                                @Override
                                public void onPositionClicked(int position) {
                                }
                            });
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };

        // Create okhttp3 form body builder, currently location is hard coded. Need to change to gps location
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("latitude", "50");
        formBodyBuilder.add("longitude", "50");
        FormBody formBody = formBodyBuilder.build();

        PostRequester post = new PostRequester(respHandler, "http://oldtivity.herokuapp.com/eventsearch", formBody);
        post.send();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<Event> constructEventArray(String events) throws JSONException {
        ArrayList<Event> returnEvents = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(events);
        @SuppressWarnings("unchecked")
        Iterator<String> keys =jsonObj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject value = jsonObj.getJSONObject(key);
            returnEvents.add(new Event(key, value.getString("title"),value.getString("location"),value.getString("description"),value.getString("date"),value.getString("host"),value.getString("phoneNumber"), value.getString("distance")));
        }
        return returnEvents;
    }

}
