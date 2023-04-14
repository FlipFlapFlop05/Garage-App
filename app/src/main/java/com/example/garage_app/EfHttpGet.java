package com.example.garage_app;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EfHttpGet {

    public static void stringResponse(
            @NonNull String url,
            @NonNull efResponseListener efResponseListener){
        new Thread(() -> {
            try {
                URL u = new URL(url);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                c.setRequestProperty("Accept","/");
                c.connect();
                Sb s = new Sb();
                B b;
                if (c.getResponseCode() == 200) {
                    b = new B(new I(c.getInputStream()));
                    String sc;
                    while ((sc = b.readLine()) != null) {
                        s.conc(sc);
                    }
                    c.disconnect();
                    b.close();
                    new Handler(Looper.getMainLooper())
                            .post(() -> efResponseListener.onSuccess(s.toStr()));
                } else {
                    b = new B(new I(c.getErrorStream()));
                    String s1;
                    while ((s1 = b.readLine()) != null) {
                        s.conc(s1);
                    }
                    c.disconnect();
                    b.close();
                    new Handler(Looper.getMainLooper())
                            .post(() -> efResponseListener.onError(s.toStr()));
                }
                c.disconnect();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("hhhh", "run: "+e.getStackTrace()[0]);
                new Handler(Looper.getMainLooper())
                        .post(() -> efResponseListener.onError(e.getMessage()));
            }
        }).start();
    }
    private static class Sb{
        String ss;
        Sb(){ ss = ""; }
        @NonNull
        public String toStr() {
            return this.ss;
        }
        public void conc(String ss) {
            this.ss = this.ss+ss;
        }
    }
    private static class B extends java.io.BufferedReader {
        public B(java.io.Reader in) {
            super(in);
        }
    }
    private static class I extends java.io.InputStreamReader {
        public I(InputStream in) {
            super(in);
        }
    }
    public interface efResponseListener {
        void onSuccess(String response);
        void onError(String errorResponse);
    }
}