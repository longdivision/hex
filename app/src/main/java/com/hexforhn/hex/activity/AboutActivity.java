package com.hexforhn.hex.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import com.hexforhn.hex.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupToolbar();

        TextView aboutText = (TextView) findViewById(R.id.aboutText);
        aboutText.setMovementMethod(LinkMovementMethod.getInstance());

        TextView feedbackText = (TextView) findViewById(R.id.feedbackText);
        feedbackText.setMovementMethod(LinkMovementMethod.getInstance());

        TextView contributingText = (TextView) findViewById(R.id.contributingText);
        contributingText.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private Toolbar setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.aboutTitle);
        }

        return toolbar;
    }
}
