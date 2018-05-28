package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.jbielak.jokesdisplay.JokeActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private InterstitialAd mInterstitialAd;
    private boolean isAdClosed = false;
    private boolean isJokeLoadingFinished = false;
    private String joke ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        setupAd();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        isAdClosed = false;

        new EndpointsAsyncTask(new EndpointsAsyncTask.LoadingListener() {
            @Override
            public void onStartLoading() {
                isJokeLoadingFinished = false;
                mProgressBar.setVisibility(View.VISIBLE);
                showInterstitialAd();
            }

            @Override
            public void onFinishLoading(String s) {
                isJokeLoadingFinished = true;
                joke = s;
                if (isAdClosed) {
                    displayJoke();
                }
            }
        }).execute(this);
    }

    private void setupAd() {
        MobileAds.initialize(this,
                getString(R.string.init_ad_unit_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadNewAd();
                isAdClosed = true;
                if (isJokeLoadingFinished) {
                    displayJoke();
                }
            }
        });
        loadNewAd();
    }

    private void loadNewAd() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void showInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void displayJoke() {
        mProgressBar.setVisibility(View.GONE);
        startActivity(new Intent(getApplicationContext(), JokeActivity.class)
                .putExtra(JokeActivity.EXTRA_JOKE, joke));
    }
}
