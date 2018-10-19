package com.example.oldivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

//IMPORTANT: DISABLE ANIMATIONS ON TEST DEVICE TO PREVENT CRASHES (Espresso doesn't like animations)
@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class SignUpActivityTest{

    private String firstName, lastName, email, password, number;
    @Mock
    public DatabaseReference mockedDatabaseReference;
    @Mock
    public FirebaseAuth mockedFirebaseAuth;
    @Mock
    public FirebaseUser mockedUser;
    @Mock
    public FirebaseDatabase mockedFirebaseDatabase;
    @Mock
    public user mockedRealUser;

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule = new
            ActivityTestRule<>(SignUpActivity.class);


    @Before
    public void setUp() {
        Intents.init();
        MockitoAnnotations.initMocks(this);

        mockedRealUser = Mockito.mock(user.class);
        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);
        mockedUser = Mockito.mock(FirebaseUser.class);

        Mockito.when(mockedFirebaseAuth.getCurrentUser()).thenReturn(mockedUser);
        Mockito.when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        Mockito.when(mockedDatabaseReference.child("users")).thenReturn(mockedDatabaseReference);

        //Mockito.when(mockedFirebaseAuth.createUserWithEmailAndPassword(email,password)).thenReturn();
    }

    @After
    public void cleanUp(){
        Intents.release();
    }

    //test creating a new user
    @Test
    public void createNewUser(){
        changeText(true);
        fillOutFields();
        Espresso.onView(withId(R.id.buttonOK)).perform(click());
        //Checks correct toast is displayed
        Espresso.onView(withText("Account successfully created!"))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    /*
    //@Test
    //Test for creating a new user with an existing email. PASSES
    public void createUserWithExistingEmail(){
        changeText(false);
        fillOutFields();
        Espresso.onView(withId(R.id.buttonOK)).perform(click());
        //Checks correct toast is displayed
        Espresso.onView(withText("Authentication failed."))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }*/

    /*@Test
    public void testEm(){
        createUserWithExistingEmail();
        //createNewUser();
        onlyChangeEmail();
    }

    public void onlyChangeEmail(){
        email = createNewRandomEmail();
        Espresso.onView(withId(R.id.tEmail)).perform(ViewActions.scrollTo(),clearText());
        Espresso.onView(withId(R.id.tEmail)).perform(ViewActions.scrollTo(),typeText(email),
                closeSoftKeyboard());
        Espresso.onView(withId(R.id.buttonOK)).perform(click());
        //Checks correct toast is displayed
        Espresso.onView(withText("Account successfully created!"))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }*/

    //--------------------------------HELPER FUNCTIONS-----------------------------------
    //changes text fields to be typed to either a new or existing email
    private void changeText(Boolean valid){
        if(valid){
            email = createNewRandomEmail();
        }else{
            email = "newUserEmail@gmail.com";
        }
        password = "SecretPW123";
        firstName = "Easy";
        lastName = "Peter";
        number = "0419957797";
    }

    //fills out fields with text
    private void fillOutFields(){
        Espresso.onView(withId(R.id.tFirstName)).perform(ViewActions.scrollTo(),typeText(firstName),
                closeSoftKeyboard());
        Espresso.onView(withId(R.id.tLastName)).perform(ViewActions.scrollTo(),typeText(lastName),
                closeSoftKeyboard());
        Espresso.onView(withId(R.id.tEmail)).perform(ViewActions.scrollTo(),typeText(email),
                closeSoftKeyboard());
        Espresso.onView(withId(R.id.tPassword)).perform(ViewActions.scrollTo(),typeText(password),
                closeSoftKeyboard());
        Espresso.onView(withId(R.id.tNumber)).perform(ViewActions.scrollTo(),typeText(number),
                closeSoftKeyboard());
    }

    //generates a new random email
    private String createNewRandomEmail() {
        String result = "a";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int n = alphabet.length();
        java.util.Random rnd = new java.util.Random();
        for(int i=0;i<6;i++){
            result = result + alphabet.charAt(rnd.nextInt(n));
        }
        result = result + "@gmail.com";
        return result;
    }
}
