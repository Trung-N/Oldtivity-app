package com.example.oldivity;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
@LargeTest
public class CreateEventTest {

    private String title, location, description;
    private Date date;

    @Rule
    public ActivityTestRule<CreateEvent> mActivityRule =
            new ActivityTestRule<>(CreateEvent.class);

    @Mock
    public DatabaseReference mockedDatabaseReference,userReference,eventReference;
    @Mock
    public FirebaseDatabase mockedFirebaseDatabase;
    @Mock
    public FirebaseAuth mockedFirebaseAuth;
    @Mock
    public FirebaseUser mockedFirebaseUser;
    @Mock
    public DataSnapshot mockedDatabaseSnapshot;

    @Before
    public void setUp(){
        Intents.init();
        MockitoAnnotations.initMocks(this);
        //MIGHT HAVE TO MOCK DIFFERENT REFERENCES FOR USERS AND EVENTS
        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);
        mockedFirebaseUser = Mockito.mock(FirebaseUser.class);
        mockedDatabaseSnapshot = Mockito.mock(DataSnapshot.class);
        userReference = Mockito.mock(DatabaseReference.class);
        eventReference = Mockito.mock(DatabaseReference.class);

        Mockito.when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        Mockito.when(mockedFirebaseAuth.getCurrentUser()).thenReturn(mockedFirebaseUser);
        Mockito.when(mockedDatabaseReference.child("users")).thenReturn(userReference);
        Mockito.when(mockedDatabaseReference.child("event")).thenReturn(eventReference);
        Mockito.when(mockedFirebaseUser.getUid()).thenReturn("myValue");

        //Mockito.when(userReference.child(anyString())).thenReturn(mockedDatabaseSnapshot);
        //Mockito.when(mockedFirebaseUser.getUid()).thenReturn(mockedUid);
        //MOCK USER uId STRING?
        //ADD VALUEEVENTLISTENER AND ONCHANGE AND STUFF???

        /*Mockito.when(userReference.child(anyString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot = mockedDatabaseSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        })).thenReturn()*/

    }

    @After
    public void cleanUp(){
        Intents.release();
    }

    @Test
    public void createEvent() {
        fillText();
        Espresso.onView(withId(R.id.createEventButton)).perform(click());
    }

    public void fillText(){
        assignText();
        Espresso.onView(withId(R.id.eventTitle)).perform(ViewActions.scrollTo(),typeText(title),
                closeSoftKeyboard());
        Espresso.onView(withId(R.id.eventLocation)).perform(ViewActions.scrollTo(),
                typeText(location), closeSoftKeyboard());
        Espresso.onView(withId(R.id.eventDescription)).perform(ViewActions.scrollTo(),
                typeText(description), closeSoftKeyboard());
        Espresso.onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(
                ViewActions.scrollTo(), PickerActions.setDate(date.getYear(),date.getMonth(),date.getDay()));
    }

    public void assignText(){
        date = Calendar.getInstance().getTime();
        title = "Fun in the sun";
        location = "22 Milton Street Elwood Victoria 3184";
        description = "We are going to have some fun!";
    }

}
