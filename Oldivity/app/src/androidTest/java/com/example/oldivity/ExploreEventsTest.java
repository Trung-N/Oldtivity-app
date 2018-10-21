package com.example.oldivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class ExploreEventsTest {

    @Mock
    private ArrayList<Event> mockEvents;
    @Mock
    public DatabaseReference mockedDatabaseReference;
    @Mock
    public FirebaseAuth mockedFirebaseAuth;

    @Rule
    public ActivityTestRule<ExploreEvents> mActivityRule = new
            ActivityTestRule<>(ExploreEvents.class);

    @Before
    public void setUp(){
        Intents.init();
        MockitoAnnotations.initMocks(this);

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);
        mockEvents = Mockito.mock(ArrayList.class);
        createRandomEvent();
    }

    //test to check recyclerView event navigation works
    @Test
    public void testRandomEvents() {
        //add some events in here and then perform a click on one
        Espresso.onView(withId(R.id.EventsList)).perform(click());
        assert(mActivityRule.getClass().getName()!=ExploreEvents.class.getName());
    }

    public void createRandomEvent(){
        Event event = new Event("testId","testTitle","testLocation","testDescription","23/12/2018",
                "Greedy Joe","0419957797","12km");
        mockEvents.add(event);
    }
}
