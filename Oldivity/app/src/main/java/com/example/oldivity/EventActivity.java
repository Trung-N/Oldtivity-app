package com.example.oldivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private String hostNumber, eventId, userId;
    private DatabaseReference eventDatabase;
    private FirebaseAuth mAuth;


    private static final String APP_KEY = "4baa9405-610f-4a05-9544-93f6ffc51079";
    private static final String APP_SECRET = "Vqbd0fS35UO4RBOjPeYrcw==";
    private static final String ENVIRONMENT = "clientapi.sinch.com";

    private Call call;
    private TextView callState;
    private Button button, joinButton, leaveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        title = findViewById(R.id.displayEventTitle);
        description = findViewById(R.id.displayEventDescription);

        //retrieve passed event information
        Intent intent = getIntent();
        String[] info = intent.getStringArrayExtra("eventDets");

        eventId = info[7];
        hostNumber = info[5];
        title.setText(info[0]);
        String descriptionFormatted = "Description: " + info[1] + "\n" + "Date: " + info[2] + "\n" +
                "Location: " + info[4] + "Distance" +info[6]+ "\n" + "Hosted By: " + info[3];
        description.setText(descriptionFormatted);

        //Auth and database instances & references
        mAuth = FirebaseAuth.getInstance();
        eventDatabase = FirebaseDatabase.getInstance().getReference("events").child(eventId).child("members");
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        joinButton = findViewById(R.id.joinButton);
        leaveButton = findViewById(R.id.leaveButton);

        //Update UI depending on whether user is a member of event or not
        updateUI();


        if (ContextCompat.checkSelfPermission(EventActivity.this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(EventActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EventActivity.this,
                    new String[]{android.Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE},
                    1);
        }

        final SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId("current-user-id")
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT)
                .build();

        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();


        button = findViewById(R.id.callHostButton);
        callState = findViewById(R.id.callState);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                eventDatabase.child(userId).setValue(true);
                joinButton.setVisibility(View.GONE);
                leaveButton.setVisibility(View.VISIBLE);
            }

            });


        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                eventDatabase.child(userId).removeValue();
                leaveButton.setVisibility(View.GONE);
                joinButton.setVisibility(View.VISIBLE);

            }

        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (call == null) {
                    call = sinchClient.getCallClient().callPhoneNumber(hostNumber);
                    call.addCallListener(new SinchCallListener());
                    button.setText("Hang Up");
                }else {
                    call.hangup();
                }

            }
        });



    }

    private void updateUI() {
        eventDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userId)) {
                    joinButton.setVisibility(View.GONE);

                } else{
                    leaveButton.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            call = null;
            button.setText("Call");
            callState.setText("");
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
        }

        @Override
        public void onCallEstablished(Call establishedCall) {
            callState.setText("connected");
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        }

        @Override
        public void onCallProgressing(Call progressingCall) {
            callState.setText("ringing");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
        }
    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            call = incomingCall;
            Toast.makeText(EventActivity.this, "incoming call", Toast.LENGTH_SHORT).show();
            call.answer();
            call.addCallListener(new SinchCallListener());
            button.setText("Hang Up");
        }
    }
}
