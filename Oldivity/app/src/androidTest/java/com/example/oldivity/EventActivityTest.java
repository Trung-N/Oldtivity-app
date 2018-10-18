package com.example.oldivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
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

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
@LargeTest
public class EventActivityTest {

    @Mock
    private DatabaseReference mockedDatabaseReference;
    @Mock
    private FirebaseDatabase mockedFirebaseDatabase;
    @Mock
    private FirebaseAuth mockedFirebaseAuth;

    @Rule
    public ActivityTestRule<EventActivity> mActivityRule =
            new ActivityTestRule<>(EventActivity.class);

    @Before
    public void setUp() {
        Intents.init();
        //HAVE TO SEND INFO INTO EVENT ACTIVITY SO THAT IT DOES NOT CRASH IN INITIALISATION
        //IT TRIES TO READ FROM A NULL ARRAY WITH THE GET_EXTRA (WE NEED TO SUPPLY THIS). BELOW!!!
        MockitoAnnotations.initMocks(this);

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);

        Mockito.when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
    }

    @After
    public void cleanUp(){
        Intents.release();
    }

    //Basic test to check if clicking join event adds you to event in database
    @Test
    public void joinEventTest() {
        Espresso.onView(ViewMatchers.withId(R.id.joinLeaveButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.joinLeaveButton))
                .check(matches(withText("Leave Event")));
    }

    @Test
    public void messageGroupText(){

    }

    @Test
    public void callHostTest(){

    }

    @Test
    public void mapTest(){

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme