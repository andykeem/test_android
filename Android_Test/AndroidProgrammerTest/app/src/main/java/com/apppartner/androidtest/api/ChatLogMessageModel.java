package com.apppartner.androidtest.api;

/**
 * A data model that represents a chat log message fetched from the AppPartner Web Server.
 * <p/>
 * Created on 8/27/16.
 *
 * @author Thomas Colligan
 */
public class ChatLogMessageModel
{
    public int userId;
    public String avatarUrl;
    public String username;
    public String message;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
