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
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText tFirstName, tLastName, tEmail, tPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        //User Inputs
        tFirstName = (EditText) findViewById(R.id.tFirstName);
        tLastName = (EditText) findViewById(R.id.tLastName);
        tEmail = (EditText) findViewById(R.id.tEmail);
        tPassword = (EditText) findViewById(R.id.tPassword);

    }

    public void OK(View view) {
        if (!validateForm()) {
            return;
        }
        //Create user entry in database with authenticator
        mAuth.createUserWithEmailAndPassword(tEmail.getText().toString(), tPassword.getText().toString())
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignUpActivity.this, "Account successfully created!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(tFirstName.getText().toString() + " " + tLastName.getText().toString()).build();
                            user.updateProfile(profileUpdates);
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            // updateUI(user);
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                             //updateUI(null);
                            }
                    }
                 });
    }

//Check that all fields are filled
    private boolean validateForm() {
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
        return valid;
    }


}
