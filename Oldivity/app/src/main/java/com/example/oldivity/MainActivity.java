package com.example.oldivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private EditText etUserEmail;
    private EditText etPassword;
    private FirebaseAuth mAuth;
    private String password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etUserEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.Password);
        // create the instance of Database
        mAuth = FirebaseAuth.getInstance();
    }

    //When login button is pressed
    public void SignIN(View view) {
        email = etUserEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (!validateEntries()) {
            return;
        }

        //Check login details with authenticator
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this,
                                    "Login successful!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            /** Move to profile page after successful login (temporary) **/
                            startActivity(new Intent(MainActivity.this, profile.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //When Signup button is pressed, move to signup page
    public void SignUP(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    /**
     * Called when Login Button is pressed. Should verify that
     * all fields are filled out, if not, notifies user which
     * field is empty and focuses on the input box
     */

    public boolean validateEntries() {
        boolean valid = true;

        String email = etUserEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etUserEmail.setError("Email is required");
            etUserEmail.requestFocus();
            valid = false;

        } else {
            etUserEmail.setError(null);
        }

        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            valid = false;
        } else {
            etPassword.setError(null);
        }
        return valid;
    }



}
