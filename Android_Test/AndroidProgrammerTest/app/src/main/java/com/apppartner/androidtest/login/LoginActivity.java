package com.apppartner.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apppartner.androidtest.BaseActivity;
import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;
import com.apppartner.androidtest.helper.LoginClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A screen that displays a login prompt, allowing the user to login to the AppPartner Web Server.
 * <p>
 * Created on Aug 28, 2016
 *
 * @author Thomas Colligan
 */
public class LoginActivity extends BaseActivity
{
    protected static final String TAG = LoginActivity.class.getSimpleName();
    protected static final String STATE_USERNAME = "username";
    protected static final String STATE_PASSWORD = "password";
    protected EditText mEtUsername;
    protected EditText mEtPassword;
    protected Button mBtnLogin;
    protected LoginResponse mLoginResponse;
    protected long mStartTime;
    protected long mElapsedTime;
    protected String mUsername;
    protected String mPassword;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.activity_login_title);
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'username' and 'password' to http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are username:AppPartner password:qwerty
        // TODO: so please use those to test the login.

        mEtUsername = (EditText) this.findViewById(R.id.et_username);
        mEtPassword = (EditText) this.findViewById(R.id.et_password);
        mBtnLogin = (Button) this.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = mEtUsername.getText().toString();
                mPassword = mEtPassword.getText().toString();
                loginUser();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_USERNAME, mUsername);
        outState.putString(STATE_PASSWORD, mPassword);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUsername = savedInstanceState.getString(STATE_USERNAME);
        mPassword = savedInstanceState.getString(STATE_PASSWORD);
    }

    public void updateUI() {
        String msg = String.format("Code: %s\nMessage: %s\nThe API call took: %d milliseconds",
                mLoginResponse.getCode(), mLoginResponse.getMessage(), mElapsedTime);
        new AlertDialog.Builder(LoginActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.activity_login_title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mLoginResponse.isSuccessful()) {
                            dialog.dismiss();
                            onBackPressed();
                        }
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    protected void loginUser() {
        mStartTime = System.currentTimeMillis();
        LoginClient.LoginService service = new LoginClient().getService();
        service.login(mUsername, mPassword).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                mElapsedTime = (System.currentTimeMillis() - mStartTime);
                if (response.isSuccessful()) {
                    mLoginResponse = response.body();
                    updateUI();
                } else {
                    try {
                        String errBody = response.errorBody().string();
                        Gson gson = new Gson();
                        mLoginResponse = gson.fromJson(errBody, LoginResponse.class);
                        updateUI();
                        int d = 1;
                    } catch (IOException ioe) {
                        Log.e(TAG, ioe.getMessage(), ioe);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                updateUI();
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}