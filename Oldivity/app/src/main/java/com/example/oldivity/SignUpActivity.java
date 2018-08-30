package com.example.oldivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText tFirstName, tLastName, tEmail, tPassword;
    private Button buttonOK;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        //Views
        tFirstName = (EditText) findViewById(R.id.tFirstName);
        tLastName = (EditText)findViewById(R.id.tLastName);
        tEmail = (EditText) findViewById(R.id.tEmail);
        tPassword = (EditText) findViewById(R.id.tPassword);

        //Button
        buttonOK = findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }

                mAuth.createUserWithEmailAndPassword(tEmail.getText().toString(), tPassword.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(SignUpActivity.this, "Authentication sucess.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));


                                   // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                    //updateUI(null);
                                }

                            }
                        });

            }

        });
    }

    //private  updateUI(FirebaseUser user) {
   // }

//Check that all fields are filled
//
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
