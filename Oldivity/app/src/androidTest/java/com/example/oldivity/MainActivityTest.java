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
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class MainActivityTest{

    private String password;
    private String email;
    @Mock
    private DatabaseReference mockedDatabaseReference;
    @Mock
    private FirebaseDatabase mockedFirebaseDatabase;
    @Mock
    private FirebaseAuth mockedFirebaseAuth;

    //@Rule
    //public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        Intents.init();
        MockitoAnnotations.initMocks(this);

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);

        Mockito.when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
    }

    @After
    public void after(){
        Intents.release();
    }

    //combining tests
    /*public void TestSignIn(){
        invalidSignInTest();
        clearText();
        validSignInTest();
    }*/

    //test for invalid credentials
    @Test
    public void invalidSignInTest(){
        changeText(false);
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignIn)).perform(ViewActions.click());
        assert(mockedFirebaseAuth.getCurrentUser()==null);
    }

    //test for valid credentials
    @Test
    public void validSignInTest(){
        changeText(true);
        Espresso.onView(ViewMatchers.withId(R.id.buttonSignIn)).perform(ViewActions.click());
        assert(mockedFirebaseAuth.getCurrentUser()!=null);
    }


    /*MOCK TEST TO CHECK PROPER WORKING OF HELPER FUNCTIONS
    @Test
    public void checkText(){
        changeText(true);
        onView(withId(R.id.Email)).check(matches(withText(email)));
        onView(withId(R.id.Password)).check(matches(withText(password)));
    }*/

    //OPTIONAL TEST TO CHECK USE OF SIGN UP BUTTON (PASSES)
    /*@Test
    public void SignUp() {
        onView(withId(R.id.buttonSignUp)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }*/


    //----------------------------------HELPER FUNCTIONS----------------------------------
    private void validStringInit(){
        password = "Shityeah3";
        email = "nicstrashbag@gmail.com";
    }

    private void invalidStringInit(){
        password = "notMyPassword";
        email = "nicstrashbag@gmail.com";
    }

    private void clearText() {
        Espresso.onView(ViewMatchers.withId(R.id.Email)).perform(ViewActions.clearText(),
                ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.Password)).perform(ViewActions.clearText(),
                ViewActions.closeSoftKeyboard());
    }

    private void changeText(Boolean validLogin) {
        if(validLogin){
            validStringInit();
        }else{
            invalidStringInit();
        }
        Espresso.onView(ViewMatchers.withId(R.id.Email)).perform(ViewActions.typeText(email),
                ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.Password)).perform(ViewActions.typeText(password),
                ViewActions.closeSoftKeyboard());
    }

}
