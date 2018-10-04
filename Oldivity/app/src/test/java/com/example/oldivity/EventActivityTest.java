package com.example.oldivity;

import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.v4.app.SupportActivity;
import android.support.v4.util.SimpleArrayMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventActivityTest {
    @Mock
    Bundle savedInstanceState = Mockito.mock(Bundle.class);
    @InjectMocks
    EventActivity eventActivity = Mockito.mock(EventActivity.class);;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
        eventActivity.onCreate(savedInstanceState);
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme