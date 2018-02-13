package com.apppartner.androidtest.helper;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by foo on 2/13/18.
 */

public class HttpClient {

    protected static final String TAG = HttpClient.class.getSimpleName();

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
        HttpURLConnection conn = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            URL url = new URL(spec);
            conn = (HttpURLConnection) url.openConnection();
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
            return out.toByteArray();
        } catch (MalformedURLException mue) {
            Log.e(TAG, mue.getMessage(), mue);
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage(), ioe);
        } finally {
            conn.disconnect();
            in.close();
            out.close();
        }
        return null;
    }
}
