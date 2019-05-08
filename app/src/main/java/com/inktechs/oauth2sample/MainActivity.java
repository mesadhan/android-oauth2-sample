package com.inktechs.oauth2sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public OkHttpClient client;
    //public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final MediaType CONTENT_TYPE = MediaType.get("application/x-www-form-urlencoded");
    String apUrl = "www.example.com/oauth/token";       // replace host url through your oauth2 server.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor("USER_CLIENT_APP", "password"))
                .build();

        String username = "user";
        String password = "password";
        String grant_type = "password";
        String requestData = "grant_type=" + grant_type + "&username=" + username + "&password=" + password;

        try {
            loginRequest(apUrl, requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginRequest(String apUrl, String requestData) throws IOException {

        RequestBody body = RequestBody.create(CONTENT_TYPE, requestData);
        Request request = new Request.Builder()
                .url(apUrl)
                .addHeader("Content-Type", " application/x-www-form-urlencoded")
                .post(body)
                .build();


        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("CallBackResponse", "onFailure() Request was: " + call.request());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("CallBackResponse ", "onResponse(): " + response.body().string());
            }

        });
    }


    /*public void getRequest(String url) throws IOException {
        client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("CallBackResponse", "onFailure() Request was: " + call.request());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("CallBackResponse ", "onResponse(): " + response.body().string());
            }

        });
    }*/
}
