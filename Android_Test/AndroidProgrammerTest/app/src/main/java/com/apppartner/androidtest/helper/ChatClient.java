package com.apppartner.androidtest.helper;

import com.apppartner.androidtest.api.ChatLogMessage;
import com.apppartner.androidtest.api.ChatLogMessageModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ChatClient {

    protected static final String TAG = ChatClient.class.getSimpleName();
    protected static final String BASE_URL = "http://dev3.apppartner.com";
    protected List<ChatLogMessageModel> mResp = new ArrayList<>();

    public interface ChatService {
        @GET("AppPartnerDeveloperTest/scripts/chat_log.php")
        Call<ChatLogMessage> getMessages();
    }

    public ChatService getChatService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ChatService service = retrofit.create(ChatService.class);
        return service;
    }
}
