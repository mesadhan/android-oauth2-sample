# android-oauth2-sample 


# Run and test Application

- [ ] Clone application [https://github.com/mesadhan/android-oauth2-sample.git](https://github.com/mesadhan/android-oauth2-sample.git)
- [ ] Import application in android studio.
- [ ] Change `apUrl` address `http://www.example.com/oauth/token";` to `oauth2 server host url` from `MainActivity.java` file.
- [ ] Run and check logcat


# Implementations Task Checklist:-

- [ ] Create an Android Application.
- [ ] Install library.
- [ ] Need some basic configuration.
- [ ] Coding Implementation.
- [ ] Run and test application

# Prerequisites:-

 - [ ] Need to Install Android Studio with SDK
 - [ ] Need basic knowledge about Java

# Implementations:-

- [X] Create an Android Application.
- [X] Install library: [OkHttp](https://square.github.io/okhttp/)

> in anroid `build.gradle` add okhttp library

```groovy
android {
    //... 

    compileOptions {
        targetCompatibility = "8"
        sourceCompatibility = "8"
    }
}

dependencies {
    //...
    
    implementation 'com.squareup.okhttp3:okhttp:3.14.1'
}
```

- [X] Need some basic configuration.

> open `AndroidManifest.xml` file

```xml

<manifest>
  //...
  <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:usesCleartextTraffic="true">
        
        /...
    </application>
</manifest>
```

`Notes:` to make it simplified use `android:usesCleartextTraffic="true"` for advance customization use `android:networkSecurityConfig="@xml/network_security_config"` file

> create file in `res/xml/network_security_config.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">127.0.0.1</domain>
    </domain-config>
</network-security-config>
```

- [X] Coding Implementation:

- create `BasicAuthInterceptor.java`

```java

import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {
    private String credentials;
    public BasicAuthInterceptor(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}

```

- then add code block into `MainActivity.java`

```java

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
}


```


- [X] Finally Run and test your application and check response in 'Logcat'.


# Thanks For Reading


