package com.apppartner.androidtest.helper;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by foo on 2/12/18.
 */

public class ChatFetchr {

    protected static final String TAG = "ChatFetchr";

    public String getUrlString(String url) {
        String resp = null;
        try {
            byte[] bytes = this.getUrlBytes(url);
            resp = new String(bytes);
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage(), ioe);
        }
        return resp;
    }

    protected byte[] getUrlBytes(String spec) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            URL url = new URL(spec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            in = conn.getInputStream();
            out = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = in.read(bytes)) > -1) {
                out.write(bytes, 0, bytesRead);
            }
            conn.disconnect();
            return out.toByteArray();
        } catch (MalformedURLException mue) {
            Log.e(TAG, mue.getMessage(), mue);
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage(), ioe);
        } finally {
            in.close();
            out.close();
        }
        return null;
    }

    protected Uri parseUrl(String url) {
        return Uri.parse(url).buildUpon()
                .build();
    }
}
