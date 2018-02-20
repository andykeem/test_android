package com.apppartner.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.apppartner.androidtest.BaseActivity;
import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;
import com.apppartner.androidtest.api.ChatLogMessage;
import com.apppartner.androidtest.api.ChatLogMessageModel;
import com.apppartner.androidtest.helper.ChatClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Screen that displays a list of chats from a chat log.
 * <p>
 * Created on Aug 27, 2016
 *
 * @author Thomas Colligan
 */
public class ChatActivity extends BaseActivity
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    protected static final String TAG = ChatActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    protected List<ChatLogMessageModel> mItems = new ArrayList<>();

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.activity_chat_title);
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter(this);

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        List<ChatLogMessageModel> tempList = new ArrayList<>();

        ChatLogMessageModel chatLogMessageModel = new ChatLogMessageModel();
        chatLogMessageModel.message = "This is test data. Please retrieve real data.";

        tempList.add(chatLogMessageModel);
        tempList.add(chatLogMessageModel);
        tempList.add(chatLogMessageModel);
        tempList.add(chatLogMessageModel);
        tempList.add(chatLogMessageModel);
        tempList.add(chatLogMessageModel);
        tempList.add(chatLogMessageModel);
        tempList.add(chatLogMessageModel);

        chatAdapter.setChatLogMessageModelList(tempList);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.

        loadChatLogMessages();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void updateUI() {
        chatAdapter.setChatLogMessageModelList(mItems);
    }

    protected void loadChatLogMessages() {
        ChatClient.ChatService service = new ChatClient().getChatService();
        service.getMessages().enqueue(new Callback<ChatLogMessage>() {
            @Override
            public void onResponse(Call<ChatLogMessage> call, Response<ChatLogMessage> response) {
                if (response.isSuccessful()) {
                    mItems = response.body().data;
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<ChatLogMessage> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}