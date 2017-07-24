package com.jeep.lolesports.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HTTPService {

    private String body;

    public String getRequestContents(String url) {
        //HTTP client
        OkHttpClient client = new OkHttpClient();
        //Build request
        Request request = new Request.Builder()
                .url(url)
                .build();

        //Make a call to the request
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                body = response.body().string();
            } else {
                System.err.println("HTTP response was not successful");
            }
        } catch (IOException e) {
            System.err.println("Failed to retrieve response from HTTP request");
        }

        return body;
    }
}
