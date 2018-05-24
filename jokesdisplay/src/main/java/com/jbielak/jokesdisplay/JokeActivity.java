package com.jbielak.jokesdisplay;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "EXTRA_JOKE";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView textView = (TextView) findViewById(R.id.tv_joke);
        textView.setText(getIntent().getStringExtra(EXTRA_JOKE));
    }
}