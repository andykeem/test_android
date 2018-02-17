package com.apppartner.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apppartner.androidtest.BaseActivity;
import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;
import com.apppartner.androidtest.api.ChatLogMessageModel;
import com.apppartner.androidtest.helper.ChatFetchr;

import java.util.ArrayList;
import java.util.List;

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

    protected List<ChatLogMessageModel> mChatLogs = new ArrayList<>();

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

        new ChatTask().execute();

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
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void updateUI() {
        chatAdapter.setChatLogMessageModelList(mChatLogs);
    }

    private class ChatTask extends AsyncTask<Void, Void, List<ChatLogMessageModel>> {
        @Override
        protected List<ChatLogMessageModel> doInBackground(Void... params) {
            List<ChatLogMessageModel> chatLogs = new ChatFetchr().fetchChatLogs();
            return chatLogs;
        }
        @Override
        protected void onPostExecute(List<ChatLogMessageModel> result) {
            mChatLogs = result;
            updateUI();
        }
    }
}