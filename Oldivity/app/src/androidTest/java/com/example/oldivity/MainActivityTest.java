package com.example.oldivity;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest{

    private String password;
    private String email;

    @Rule
    public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(MainActivity.class);
    //@Rule
    //public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    public void validStringInit(){
        password = "Shityeah3";
        email = "nicstrashbag@gmail.com";
    }

    public void invalidStringInit(){
        password = "notMyPassword";
        email = "nicstrashbag@gmail.com";
    }

    public void changeText(Boolean validLogin) {
        if(validLogin){
            validStringInit();
        }else{
            invalidStringInit();
        }
        onView(withId(R.id.Email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.Password)).perform(typeText(password), closeSoftKeyboard());
    }

    /*MOCK TEST TO CHECK PROPER WORKING OF HELPER FUNCTIONS
    @Test
    public void checkText(){
        changeText(true);
        onView(withId(R.id.Email)).check(matches(withText(email)));
        onView(withId(R.id.Password)).check(matches(withText(password)));
    }*/

    @Test
    public void SignIn(){
        changeText(true);
        onView(withId(R.id.buttonSignIn)).perform(click());
        //now check if the intent changes,if not it has failed.
        //we should do this once for valid login and once for invalid login

        //intended(hasComponent(profile.class.getName()));

        //ANY INTENT MATCHES NO INTENTS. SO THE DATABASE CALLING DOESNT WORK
        intended(anyIntent());
    }

    //Sign in

    //Sign up

}
