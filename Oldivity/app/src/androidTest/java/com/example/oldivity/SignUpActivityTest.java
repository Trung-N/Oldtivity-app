package com.example.oldivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

//IMPORTANT: DISABLE ANIMATIONS ON TEST DEVICE TO PREVENT CRASHES (Espresso doesn't like animations)
@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class SignUpActivityTest{

    private String firstName, lastName, email, password, number;
    private Task<AuthResult> task;
    @Mock
    public DatabaseReference mockedDatabaseReference;
    @Mock
    public FirebaseAuth mockedFirebaseAuth;
    @Mock
    public FirebaseUser mockedUser;
    @Mock
    public FirebaseDatabase mockedFirebaseDatabase;

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule = new
            ActivityTestRule<>(SignUpActivity.class);


    @Before
    public void setUp() {
        Intents.init();
        MockitoAnnotations.initMocks(this);

        mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);
        //mockedUser = Mockito.mock(FirebaseUser.class);

        //Mockito.when(mockedFirebaseAuth.getCurrentUser()).thenReturn(mockedUser);
        Mockito.when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        Mockito.when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        
        Mockito.when(mockedFirebaseAuth.createUserWithEmailAndPassword(email,password)).thenReturn(task);
    }

    @After
    public void cleanUp(){
        Intents.release();
    }

    //test creating a new user
    @Test
    public void createNewUser(){

        Mockito.when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);

        Mockito.doAnswer(new Answer<Void>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener =
                        (ValueEventListener) invocation.getArguments()[0];
                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                valueEventListener.onDataChange(mockedDataSnapshot);
                return null;
            }

        }).when(mockedDatabaseReference)
                .addListenerForSingleValueEvent(any(ValueEventListener.class));

        changeText();
        fillOutFields();
        Espresso.onView(withId(R.id.buttonOK)).perform(click());
        //Checks correct toast is displayed
        Espresso.onView(withText("Account successfully created!"))//"Authentication failed."
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    //--------------------------------HELPER FUNCTIONS-----------------------------------
    private void changeText(){
        email = "newUserEmail@gmail.com";
        password = "SecretPW123";
        firstName = "Easy";
        lastName = "Peter";
        number = "0419957797";
    }

    //this function can be broken by changing other code. For some reason the Toast.check after
    //button click and mocking a FireBaseUser can destabilise this.
    //UPDATE: scrollTo() seems to have fixed this.
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
}
