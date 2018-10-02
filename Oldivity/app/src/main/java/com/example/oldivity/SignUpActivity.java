package com.example.oldivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText tFirstName, tLastName, tEmail, tPassword, tNumber;
    private String firstName, lastName, email, password, number;
    private FirebaseAuth mAuth;
    private String uID;

    private DatabaseReference Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get database & Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference("users");

        //User Inputs
        tFirstName = findViewById(R.id.tFirstName);
        tLastName = findViewById(R.id.tLastName);
        tEmail = findViewById(R.id.tEmail);
        tPassword = findViewById(R.id.tPassword);
        tNumber = findViewById(R.id.tNumber);

    }

    //When Signup button is pressed
    public void OK(View view) {
        if (!validateEntries()) {
            return;
        }

        firstName = tFirstName.getText().toString().trim();
        lastName = tLastName.getText().toString().trim();
        email = tEmail.getText().toString().trim();
        password = tPassword.getText().toString().trim();
        number = tNumber.getText().toString().trim();

        //Create user authenticator & entry in user database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this,
                        new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Add user information to database with same unique ID as authenticator
                            FirebaseUser user = mAuth.getCurrentUser();
                            uID = user.getUid();
                            user newUser = new user(firstName, lastName, email, number);
                            Database.child(uID).setValue(newUser);

                            // Inform successful sign-up
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignUpActivity.this,
                                    "Account successfully created!", Toast.LENGTH_SHORT).show();

                            //Move to login page to sign in
                            startActivity(new Intent(SignUpActivity.this,
                                    MainActivity.class));

                        } else {

                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /**
     * Called when Sign Up Button is pressed. Should verify that
     * all fields are filled out, if not, notifies user which
     * field is empty and focuses on the input box
     */

    private boolean validateEntries() {
        boolean valid = true;

       String email = tEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            tEmail.setError("Email is required");
            tEmail.requestFocus();
            valid = false;

        } else {
            tEmail.setError(null);
        }

        String password = tPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            tPassword.setError("Password is required");
            tPassword.requestFocus();
            valid = false;
        } else {
            tPassword.setError(null);
        }

        String firstName = tFirstName.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            tFirstName.setError("First Name is required");
            tFirstName.requestFocus();
            valid = false;
        } else {
            tFirstName.setError(null);
        }

        String lastName = tLastName.getText().toString().trim();
        if (TextUtils.isEmpty(lastName)) {
            tLastName.setError("Last Name is required");
            tLastName.requestFocus();
            valid = false;
        } else {
            tLastName.setError(null);
        }

        String number = tNumber.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            tNumber.setError("Phone Number is required");
            tNumber.requestFocus();
            valid = false;
        } else {
            tNumber.setError(null);
        }

        return valid;
    }



}
