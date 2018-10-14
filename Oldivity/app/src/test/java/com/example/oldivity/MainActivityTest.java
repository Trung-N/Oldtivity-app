package com.example.oldivity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;

public class MainActivityTest {

    private MainActivity myObjectUnderTest;
    Context mockContext;

    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void emailAndPasswordValidator() {
        //assertThat(myObjectUnderTest.validateEntries("a@test.com", "123"), is(true));

    }

    @Test
    public void testValidLogin() {
        //FIND THE TEXT FIELDS AND FILL THEM WITH VALUES
        //MOCK THE DATABASE PROLLY
        MainActivity test = new MainActivity();
        EditText email = test.findViewById(R.id.Email);
        EditText password = test.findViewById(R.id.Password);
        email.setText("nicstrashbag@gmail.com");
        password.setText("Shityeah3");
        assertThat(test.findViewById(R.id.buttonSignIn).performClick(), is(true));//WHAT SHOULD THIS VALUE BE?
    }

    @Test
    public void testInvalidLogin() {
        MainActivity test = new MainActivity();
        EditText email = test.findViewById(R.id.Email);
        EditText password = test.findViewById(R.id.Password);
        email.setText("nicstrashbag@gmail.com");
        password.setText("NotMyPassword");
        assertThat(test.findViewById(R.id.buttonSignIn).performClick(), is(true));//WHAT SHOULD THIS VALUE BE?
    }
    //SIGN IN ACTIVITY THING HERE. TEST VALID SIGN IN AND INVALID SIGN IN
}
