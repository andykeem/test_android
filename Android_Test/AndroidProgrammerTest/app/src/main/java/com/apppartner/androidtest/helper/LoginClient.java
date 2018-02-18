package com.apppartner.androidtest.helper;

import com.apppartner.androidtest.login.LoginResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by foo on 2/17/18.
 */

public class LoginClient {

    protected static final String TAG = LoginClient.class.getSimpleName();
    protected static final String BASE_URL = "http://dev3.apppartner.com";

    public interface LoginService {
        @FormUrlEncoded
        @POST("AppPartnerDeveloperTest/scripts/login.php")
        Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
    }

    public LoginService getService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        LoginService service = retrofit.create(LoginService.class);
        return service;
    }
}
