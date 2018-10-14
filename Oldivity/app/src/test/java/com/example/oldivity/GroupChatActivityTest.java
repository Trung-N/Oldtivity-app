package com.example.oldivity;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentController;
import android.support.v4.app.SupportActivity;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class GroupChatActivityTest {
    @Mock
    EditText inputText;
    @Mock
    Toolbar myToolbar;
    @Mock
    ScrollView myScrollView;
    @Mock
    TextView displayMessages;
    @Mock
    FirebaseAuth mAuth;
    @Mock
    DatabaseReference userDatabase;
    @Mock
    DatabaseReference groupDatabase;
    @Mock
    DatabaseReference groupMessageKeyDatabase;
    @Mock
    AppCompatDelegate mDelegate;
    @Mock
    Resources mResources;
    @Mock
    Handler mHandler;
    @Mock
    FragmentController mFragments;
    @Mock
    ViewModelStore mViewModelStore;
    @Mock
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    @Mock
    SimpleArrayMap<Class<? extends SupportActivity.ExtraData>, SupportActivity.ExtraData> mExtraDataMap;
    @Mock
    LifecycleRegistry mLifecycleRegistry;
    @InjectMocks
    GroupChatActivity groupChatActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        groupChatActivity.onCreate(null);
    }

    @Test
    public void testOnStart() throws Exception {
        //groupChatActivity.onStart();
    }

    @Test
    public void testClickSend() throws Exception {
        //groupChatActivity.clickSend(null);
    }

    @Test
    public void testSaveMessageToDatabase() throws Exception {
        //groupChatActivity.saveMessageToDatabase();
    }

    @Test
    public void testGetUserInfo() throws Exception {
        //groupChatActivity.getUserInfo();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme