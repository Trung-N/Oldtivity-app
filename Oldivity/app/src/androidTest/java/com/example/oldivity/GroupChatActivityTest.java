package com.example.oldivity;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class GroupChatActivityTest {

    private String message;
    @Mock
    public FirebaseAuth mockedFirebaseAuth;
    @Mock
    public DatabaseReference mockedDatabaseReference;
    @Mock
    public StorageReference mockedStorageReference;

    @Rule
    public ActivityTestRule<GroupChatActivity> mActivityRule =
            new ActivityTestRule<GroupChatActivity>(GroupChatActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, GroupChatActivity.class);
                    result.putExtra("groupName", "myTestGroup");
                    return result;
                }
            };

    @Before
    public void setUp() {
        Intents.init();
        MockitoAnnotations.initMocks(this);

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        mockedFirebaseAuth = Mockito.mock(FirebaseAuth.class);
        mockedStorageReference = Mockito.mock(StorageReference.class);

        Mockito.when(mockedDatabaseReference.child("messages")).thenReturn(mockedDatabaseReference);
        Mockito.when(mockedStorageReference.child("message_images")).thenReturn(mockedStorageReference);
    }

    @After
    public void cleanUp(){
        Intents.release();
    }

    //Test sending a message (PASSES)
    @Test
    public void sendMessageTest(){
        message = "my message";//setMessage();
        Espresso.onView(withId(R.id.chat_message_view)).perform(typeText(message));
        Espresso.onView(withId(R.id.chat_send_btn)).perform(click());
        Espresso.onView(withId(R.id.messages_list)).check(matches(hasDescendant(withText(message))));

    }

    @Test
    public void addImageTest(){
        //HAVE TO VERIFY LOADING AN IMAGE
    }

    @Test
    public void sendImageTest(){
        //HAVE TO VERIFY SENDING THE LOADED IMAGE
    }

    //randomises a string so duplicates don't cause test to fail over multiple iterations
    //UPDATE: duplicates do not cause the check(matches()) to fail
    /*private String setMessage(){
        String result = "g";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int n = alphabet.length();
        java.util.Random rnd = new java.util.Random();
        for(int i=0;i<10;i++){
            result = result + alphabet.charAt(rnd.nextInt(n));
        }
        return result;
    }*/
}
