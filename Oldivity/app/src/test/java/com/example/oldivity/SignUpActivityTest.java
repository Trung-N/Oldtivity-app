package com.example.oldivity;

import android.os.Bundle;
import android.view.View;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class SignUpActivityTest{
    @Mock
    View view = Mockito.mock(View.class);
    Bundle savedInstanceState = Mockito.mock(Bundle.class);

    @InjectMocks
    SignUpActivity signUpActivity =  Mockito.mock(SignUpActivity.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {

        signUpActivity.onCreate(savedInstanceState);
    }

    @Test
    public void testOnStart() throws Exception {
        signUpActivity.onStart();
    }

    @Test
    public void testSignUp() throws Exception {

        signUpActivity.signUp(view);
    }

    @Test
    public void testValidateEntries() throws Exception {
        SignUpActivity SignUpActivity = new SignUpActivity();
        boolean result = SignUpActivity.validateEntries("anita.naseri@gmail.com", "123",
                "anita", "naseri", "123");
        Assert.assertEquals(true, result);

    }
}
