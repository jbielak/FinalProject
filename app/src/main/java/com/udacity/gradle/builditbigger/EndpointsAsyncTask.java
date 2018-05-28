package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.jbielak.jokesdisplay.JokeActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Justyna on 2018-05-23.
 */

public class EndpointsAsyncTask  extends AsyncTask<Context, Void, String> {

    private static MyApi sApi = null;

    protected Context mContext;
    protected LoadingListener mLoadingListener;

    public EndpointsAsyncTask() {
        this(null);
    }

    public EndpointsAsyncTask(LoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    @Override
    protected void onPreExecute() {
        if (mLoadingListener != null) {
            mLoadingListener.onStartLoading();
        }
    }

    @Override
    protected String doInBackground(final Context... params) {
        if (sApi == null) {
            MyApi.Builder builder =
                    new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
            sApi = builder.build();
        }
        mContext = params[0];
        try {
            return sApi.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(final String s) {
        if (mLoadingListener != null) {
            mLoadingListener.onFinishLoading(s);
        }
    }

    public interface LoadingListener {
        void onStartLoading();
        void onFinishLoading(String s);
    }
}