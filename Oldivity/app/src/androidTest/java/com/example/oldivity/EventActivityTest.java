package com.example.oldivity;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class EventActivityTest {

    public String eventId ="testId", userId;

    //Override EventActivity to pass in extra data
    @Rule
    public ActivityTestRule<EventActivity> mActivityRule =
            new ActivityTestRule<EventActivity>(EventActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    //overrides intent
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, EventActivity.class);
                    String[] extra = {"TestTitle","TestDescription","23/12/2018","Test User",
                            "22 Milton Street Elwood Victoria","0419957797","12km",eventId};
                    result.putExtra("eventDets", extra);
                    return result;
                }
            };

    @Mock
    public FirebaseDatabase mockDatabase;
    @Mock
    public DatabaseReference mockEvents;
    @Mock
    public DatabaseReference mockUsers;
    @Mock
    public FirebaseAuth mockFirebaseAuth;
    @Mock
    public FirebaseUser mockUser;

    //Setup does not work and so neither does this test. All the mocks seem to be null so it
    //naturally crashes the program
    @Before
    public void setUp(){
        Intents.init();
        MockitoAnnotations.initMocks(mActivityRule);

        //all the mocks are null?
        mockDatabase = Mockito.mock(FirebaseDatabase.class);
        mockUser = Mockito.mock(FirebaseUser.class);
        mockFirebaseAuth = Mockito.mock(FirebaseAuth.class);
        Mockito.when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockUser);
        userId = mockUser.getUid();
        mockEvents = Mockito.mock(DatabaseReference.class);
        mockUsers = Mockito.mock(DatabaseReference.class);
        Mockito.when(mockDatabase.getReference("users").child(userId)
                .child("events")).thenReturn(mockUsers);
        Mockito.when(mockDatabase.getReference("events").child(eventId)
                .child("members")).thenReturn(mockEvents);
    }

    @After
    public void cleanUp(){
        Intents.release();
    }

    @Test
    public void joinLeaveEventTest(){
        Espresso.onView(withId(R.id.joinLeaveButton)).perform(click());
        Espresso.onView(withId(R.id.joinLeaveButton)).check(matches(withText("Leave Event")));
    }

    /*
    @Test
    public void callHostTest(){

    }

    @Test
    public void messageGroupText(){

    }

    @Test
    public void mapTest(){

    }*/


}