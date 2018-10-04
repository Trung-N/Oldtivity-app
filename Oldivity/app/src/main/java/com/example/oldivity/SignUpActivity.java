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
    public void signUp(View view) {

        firstName = tFirstName.getText().toString().trim();
        lastName = tLastName.getText().toString().trim();
        email = tEmail.getText().toString().trim();
        password = tPassword.getText().toString().trim();
        number = tNumber.getText().toString().trim();

        if (!validateEntries(email, password, firstName, lastName, number)) {
            return;
        }

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

    public boolean validateEntries(String email, String password, String firstName,
                                    String lastName, String number) {
        boolean valid = true;
        boolean hasAtSign = email.indexOf("@") >=1;

        if (email.length()<1) {
            tEmail.setError("Email is required");
            tEmail.requestFocus();
            valid = false;

        }else if(!hasAtSign){
            tEmail.setError("Valid Email is required");
            tEmail.requestFocus();
            valid = false;
        }

        if (password.length()<1) {
            tPassword.setError("Password is required");
            tPassword.requestFocus();
            valid = false;
        }
        if (firstName.length()<1) {
            tFirstName.setError("First Name is required");
            tFirstName.requestFocus();
            valid = false;
        }

        if (lastName.length()<1) {
            tLastName.setError("Last Name is required");
            tLastName.requestFocus();
            valid = false;
        }
        if (number.length()<1) {
            tNumber.setError("Phone Number is required");
            tNumber.requestFocus();
            valid = false;
        }
        return valid;
    }



}
