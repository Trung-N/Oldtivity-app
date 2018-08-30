package com.example.oldivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.oob.SignUp;


public class MainActivity extends AppCompatActivity {
    private EditText etUserEmail;
    private EditText etPassword;
    private String username;
    private String password;
    private String storedPassword;
    Context context=this;
    private LoginDatabaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etUserEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.Password);
        // create the instance of Database
        loginDataBaseAdapter=new LoginDatabaseAdapter(getApplicationContext());
    }
    public void SignIN(View view) {
        try {
            loginDataBaseAdapter = loginDataBaseAdapter.open();
            username = etUserEmail.getText().toString();
            password = etPassword.getText().toString();
            if (username.equals("") || password.equals("")) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("ALERT!");
                alertDialog.setMessage("Fill All Fields");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
            // fetch the Password form database for respective user name
            if (!username.equals("")) {
                storedPassword = loginDataBaseAdapter.getSinlgeEntry(username);
                // check if the Stored password matches with Password entered by user
                if (password.equals(storedPassword)) {
                    Intent intent1 = new Intent(MainActivity.this, profile.class);
                    startActivity(intent1);
                    // finish();
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("ALERT!");
                    alertDialog.setMessage("Incorrect Username OR Password");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", "error login");
        }
    }
    public void SignUP(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void ViewListContents(View view) {
        Intent intent2 = new Intent(MainActivity.this, ViewListContents.class);
        startActivity(intent2);
    }

}
