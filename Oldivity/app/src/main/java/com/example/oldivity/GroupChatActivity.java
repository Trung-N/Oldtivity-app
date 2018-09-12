package com.example.oldivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {
    private static final String TAG = "messageee";
    private EditText inputText;
    private Toolbar myToolbar;
    private ScrollView myScrollView;
    private TextView displayMessages;

    private FirebaseAuth mAuth;

    private String curGroupname, curUserID, curUserName, curDate, curTime;
    private DatabaseReference userDatabase, groupDatabase, groupMessageKeyDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        curGroupname = getIntent().getExtras().get("groupName").toString();
        Toast.makeText(this, curGroupname, Toast.LENGTH_SHORT).show();
        //Get database & Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        curUserID = mAuth.getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        groupDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(curGroupname);
        groupMessageKeyDatabase = groupDatabase.child("messages");

        myToolbar = findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(curGroupname);
        inputText = findViewById(R.id.input_group_message);
        displayMessages = findViewById(R.id.group_chat_text_display);
        myScrollView = findViewById(R.id.my_scroll_view);
        getUserInfo();

    }
    @Override
    public void onStart() {
        super.onStart();

        groupDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    displayPreviousMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    displayPreviousMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseUser user = mAuth.getCurrentUser();
    }



    public void clickSend(View view){

        saveMessageToDatabase();
        inputText.setText("");

    }

    public void saveMessageToDatabase(){
        String message = inputText.getText().toString();
        String messageKey = groupDatabase.push().getKey();
        if(message.isEmpty()){
            Toast.makeText(this, "Nothing to send...", Toast.LENGTH_SHORT).show();
        }else{
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat curDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            curDate = curDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat curTimeFormat = new SimpleDateFormat("hh:mm a");
            curTime = curTimeFormat.format(calForTime.getTime());

            HashMap<String, Object> groupMessageKey = new HashMap<>();
            groupDatabase.updateChildren(groupMessageKey);

            groupMessageKeyDatabase = groupDatabase.child(messageKey);

            HashMap<String, Object> messageInfo = new HashMap<>();
            messageInfo.put("firstName", curUserName);
            messageInfo.put("message", message);
            messageInfo.put("date", curDate);
            messageInfo.put("time", curTime);
            groupMessageKeyDatabase.updateChildren(messageInfo);

            myScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }



    }


    public void getUserInfo() {
        userDatabase.child(curUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    curUserName = dataSnapshot.child("firstName").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayPreviousMessages(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext()){
            String chatDate = (String) ((DataSnapshot)iterator.next()).getValue();
            String chatName = (String) ((DataSnapshot)iterator.next()).getValue();
            String chatMessage = (String) ((DataSnapshot)iterator.next()).getValue();
            String chatTime = (String) ((DataSnapshot)iterator.next()).getValue();
            displayMessages.append(chatTime + "  " + chatDate + "\n" + chatName + ":\n" + chatMessage + "\n\n\n");

            myScrollView.fullScroll(ScrollView.FOCUS_DOWN);

        }
    }
}
