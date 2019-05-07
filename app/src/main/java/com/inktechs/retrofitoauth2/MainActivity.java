package com.inktechs.retrofitoauth2;

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

        String url = "http://auth-ngfs-core-framework.apps.115.127.24.184.nip.io/oauth/token";
        String requestData = "grant_type=" + grant_type + "&username=" + username + "&password=" + password;
        try {
            post(url, requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void post(String url, String requestData) throws IOException {

        RequestBody body = RequestBody.create(CONTENT_TYPE, requestData);
        Request request = new Request.Builder()
                .url(url)
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


    public void getRequest(String url) throws IOException {
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
    }
}
