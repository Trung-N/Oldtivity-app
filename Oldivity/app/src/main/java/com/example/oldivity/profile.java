package com.example.oldivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class profile extends AppCompatActivity {
    private TextView editText;
    private static final String TAG = "profile";
    private FirebaseAuth mAuth;

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
        if (user != null) {

            editText.setText(user.getEmail());

        }
    }

    public void myEvents(View view) {

    }

    public void friendsList(View view) {

    }

    public void exploreEvents(View view) {

    }

}
