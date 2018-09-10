package com.example.oldivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    private static final String TAG = "messageee";
    private EditText inputText;
    private Toolbar myToolbar;
    private ScrollView myScrollView;
    private TextView displayMessage;

    private FirebaseAuth mAuth;

    private String curGroupname, curUserID, curUserName, curDate, curTime;
    private DatabaseReference userDatabase, groupDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        //Get database & Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        curUserID = mAuth.getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        groupDatabase = FirebaseDatabase.getInstance().getReference().child("groups");
        curGroupname = getIntent().getExtras().get("groupName").toString();
        Toast.makeText(this, curGroupname, Toast.LENGTH_SHORT).show();

        myToolbar = findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(curGroupname);
        inputText = findViewById(R.id.input_group_message);
        displayMessage = findViewById(R.id.group_chat_text_display);
        myScrollView = findViewById(R.id.my_scroll_view);

    }
    @Override
    public void onStart() {
        super.onStart();


        FirebaseUser user = mAuth.getCurrentUser();
    }

    public void clickSend(View view){
        String message = inputText.getText().toString();
        if(TextUtils.isEmpty(message)){
            inputText.setError("Write something");
            inputText.requestFocus();
            return;
        }
        saveMessageToDatabase(message);

    }

    public void saveMessageToDatabase(String message){
        FirebaseUser user = mAuth.getCurrentUser();
        String uID = user.getUid();
        String curTime = DateFormat.getDateTimeInstance().format(new Date());
        Message newMessage = new Message(curTime, message, uID);
        groupDatabase.child("messages").setValue(newMessage);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message fetchMessage = dataSnapshot.getValue(Message.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadMessage:onCancelled", databaseError.toException());
                // ...
            }
        };
        System.out.println(newMessage.getMessage());




    }





}
