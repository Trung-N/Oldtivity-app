package com.example.oldivity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//code based on https://www.dev2qa.com/android-okhttp3-http-get-post-request-example/
public class PostRequester {

    private static final String TAG_OK_HTTP_ACTIVITY = "OK_HTTP_ACTIVITY";
    private static final int COMMAND_DISPLAY_SERVER_RESPONSE = 1;
    private static final String KEY_SERVER_RESPONSE_OBJECT = "KEY_SERVER_RESPONSE_OBJECT";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler displayRespTextHandler;
    private String url;
    private FormBody formBody;

    //constructor
    public PostRequester(Handler displayRespTextHandler, String url, FormBody formBody){
        this.displayRespTextHandler = displayRespTextHandler;
        this.url = url;
        this.formBody = formBody;
    }


    public void send() {
        Thread okHttpExecuteThread = new Thread() {
            @Override
            public void run() {

                String url = "http://oldtivity.herokuapp.com/eventsearch";

                try {

                    // Create okhttp3.Call object with post http request method.
                    Call call = createHttpPostMethodCall(url);

                    // Execute the request and get the response synchronously.
                    Response response = call.execute();

                    // If request process success.
                    boolean respSuccess = response.isSuccessful();
                    if (respSuccess) {

                        // Parse and get server response text data.
                        String respData = parseResponseText(response);

                        // Notify activity main thread to update UI display text with Handler.
                        sendChildThreadMessageToMainThread(respData);
                    } else {
                        sendChildThreadMessageToMainThread("Ok http post request failed.");
                    }
                } catch(Exception ex)
                {
                    Log.e(TAG_OK_HTTP_ACTIVITY, ex.getMessage(), ex);
                    sendChildThreadMessageToMainThread(ex.getMessage());
                }
            }
        };

        // Start the child thread.
        okHttpExecuteThread.start();
    }

    private Call createHttpPostMethodCall(String url)
    {
        // Create a http request object.
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url);
        builder = builder.post(formBody);
        Request request = builder.build();

        // Create a new Call object with post method.
        Call call = okHttpClient.newCall(request);

        return call;
    }

    private String parseResponseText(Response response)
    {
        // Get body text.
        String respBody = "";
        try {
            respBody = response.body().string();
        }catch(IOException ex)
        {
            Log.e(TAG_OK_HTTP_ACTIVITY, ex.getMessage(), ex);
        }

        return respBody;
    }


    private void sendChildThreadMessageToMainThread(String respData)
    {
        // Create a Message object.
        Message message = new Message();

        // Set message type.
        message.what = COMMAND_DISPLAY_SERVER_RESPONSE;

        // Set server response text data.
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SERVER_RESPONSE_OBJECT, respData);
        message.setData(bundle);

        // Send message to activity Handler.
        displayRespTextHandler.sendMessage(message);
    }
}
