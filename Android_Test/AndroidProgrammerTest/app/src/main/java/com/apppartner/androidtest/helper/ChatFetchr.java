package com.apppartner.androidtest.helper;

import android.net.Uri;
import android.util.Log;

import com.apppartner.androidtest.api.ChatLogMessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foo on 2/12/18.
 */

public class ChatFetchr extends HttpClient {

    protected static final String TAG = ChatFetchr.class.getSimpleName();
    protected static final String HOST = "http://dev3.apppartner.com/";
    protected static final String PATH = "AppPartnerDeveloperTest/scripts/chat_log.php";

    protected Uri getEndpoint() {
        return Uri.parse(HOST).buildUpon()
                .appendEncodedPath(PATH)
                .build();
    }

    public List<ChatLogMessageModel> fetchChatLogs() {
        String endpoint = this.getEndpoint().toString();
        String resp = this.getUrlString(endpoint);
        return this.parseJson(resp);
    }

    protected List<ChatLogMessageModel> parseJson(String json) {
        List<ChatLogMessageModel> items = new ArrayList<>();
        try {
            JSONObject jobj = (JSONObject) new JSONTokener(json).nextValue();
            JSONArray jarr = jobj.getJSONArray("data");
            int numObjs = jarr.length();
            for (int i = 0; i < numObjs; i++) {
                JSONObject item = (JSONObject) jarr.get(i);
                int userId = item.getInt("user_id");
                String userName = item.getString("username");
                String avatarUrl = item.getString("avatar_url");
                String message = item.getString("message");
                ChatLogMessageModel chatLog = new ChatLogMessageModel();
                chatLog.setUserId(userId);
                chatLog.setUsername(userName);
                chatLog.setAvatarUrl(avatarUrl);
                chatLog.setMessage(message);
                items.add(chatLog);
            }
        } catch (JSONException je) {
            Log.e(TAG, je.getMessage(), je);
        }
        return items;
    }
}
