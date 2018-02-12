package com.apppartner.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;
import com.apppartner.androidtest.api.ChatLogMessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen that displays a list of chats from a chat log.
 * <p>
 * Created on Aug 27, 2016
 *
 * @author Thomas Colligan
 */
public class ChatActivity extends AppCompatActivity
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

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

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter();

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
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}