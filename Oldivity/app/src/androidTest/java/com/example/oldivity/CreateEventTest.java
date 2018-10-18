package com.example.oldivity;


import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
@LargeTest
public class CreateEventTest {

    private String title, location, description, date;

    @Rule
    public ActivityTestRule<CreateEvent> mActivityRule =
            new ActivityTestRule<>(CreateEvent.class);

    @Mock
    public DatabaseReference mockedDatabaseReference;
    @Mock
    public FirebaseDatabase mockedFirebaseDatabase;
    @Mock
    public FirebaseAuth mockedFirebaseAuth;

    @Before
    public void setUp(){
        Intents.init();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void cleanUp(){
        Intents.release();
    }


}
