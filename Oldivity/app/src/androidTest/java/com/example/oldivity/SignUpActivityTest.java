package com.example.oldivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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

//IMPORTANT: DISABLE ANIMATIONS ON TEST DEVICE TO PREVENT CRASHES (Espresso doesn't like animations)
@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class SignUpActivityTest{

    private String firstName, lastName, email, password, number;
    @Mock
    private DatabaseReference mockedDatabaseReference;
    @Mock
    private FirebaseAuth mockedFirebaseAuth;
    /*@Mock
    private FirebaseDatabase mockedFirebaseDatabase;*/

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule = new
            ActivityTestRule<>(SignUpActivity.class);


    @Before
    public void setUp() {
        Intents.init();
        MockitoAnnotations.initMocks(this);

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);
    }

    @After
    public void cleanUp(){
        Intents.release();
    }

    //test creating a new user
    @Test
    public void createNewUser(){
        changeText();
        fillOutFields();
        Espresso.onView(ViewMatchers.withId(R.id.buttonOK)).perform(click());
        //Assert.assertEquals("aaa","aaa");
    }

    /*@Test
    public void testValidateEntries() {
        SignUpActivity SignUpActivity = new SignUpActivity();
        changeText();
        boolean result = SignUpActivity.validateEntries(email, password,
                firstName, lastName, number);
        Assert.assertEquals(true, result);
    }*/

    //--------------------------------HELPER FUNCTIONS-----------------------------------
    private void changeText(){
        email = "newUserEmail@gmail.com";
        password = "superSecretPassword123";
        firstName = "Easy";
        lastName = "Peter";
        number = "0419957797";
    }

    //EMAIL FIELD IS BEING TEMPERAMENTAL. DOESN'T LIKE TO BE CLICKED OR FILLED?
    private void fillOutFields(){
        //Espresso.onView(ViewMatchers.withId(R.id.tFirstName)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tFirstName)).perform(ViewActions
                .typeText(firstName), ViewActions.closeSoftKeyboard());
        //Espresso.onView(ViewMatchers.withId(R.id.tLastName)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tLastName)).perform(ViewActions.typeText(lastName),
                ViewActions.closeSoftKeyboard());
        //Espresso.onView(ViewMatchers.withId(R.id.tEmail)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tEmail)).perform(ViewActions.typeText(email),
                ViewActions.closeSoftKeyboard());
        //Espresso.onView(ViewMatchers.withId(R.id.tPassword)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tPassword)).perform(ViewActions.typeText(password),
                ViewActions.closeSoftKeyboard());
        //Espresso.onView(ViewMatchers.withId(R.id.tNumber)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tNumber)).perform(ViewActions.typeText(number),
                ViewActions.closeSoftKeyboard());
    }
}
