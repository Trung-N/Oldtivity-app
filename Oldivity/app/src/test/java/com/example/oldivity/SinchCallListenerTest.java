package com.example.oldivity;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SinchCallListenerTest {
    @Mock
    Call call = Mockito.mock(Call.class);
    List<PushPair> pushPairs = Mockito.mock(List.class);
    EventActivity.SinchCallListener sinchCallListener = Mockito.mock(EventActivity.SinchCallListener.class);

    @Test
    public void testOnCallEnded() throws Exception {

        sinchCallListener.onCallEnded(call);
    }

    @Test
    public void testOnCallEstablished() throws Exception {
        sinchCallListener.onCallEstablished(call);
    }

    @Test
    public void testOnCallProgressing() throws Exception {
        sinchCallListener.onCallProgressing(call);
    }

    @Test
    public void testOnShouldSendPushNotification() throws Exception {
        sinchCallListener.onShouldSendPushNotification(call, pushPairs);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme