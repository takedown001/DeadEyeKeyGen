package com.game.sploitkeygen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;

public class AppUpdaterActivity extends AppCompatActivity {

    //app
    private static final String TAG_APP_NEWVERSION = "newversion";

    private TextView forceUpdateNote;
    private final String isForceUpdate = "true";
    private TextView newVersion;
    private Button update;
    private TextView updateDate;

    private TextView whatsNew;
    private String whatsNewData;

    private String newversion,updateurl;

    public AppUpdaterActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_updater);
        newversion = getIntent().getStringExtra(TAG_APP_NEWVERSION);
        whatsNewData = getIntent().getStringExtra("data");
        updateurl = getIntent().getStringExtra("update");
        newVersion = (TextView) findViewById(R.id.version);
        whatsNew = (TextView) findViewById(R.id.whatsnew);
        forceUpdateNote = (TextView) findViewById(R.id.forceUpdateNote);
        update = (Button) findViewById(R.id.updateButton);
        newVersion.setText("New Version: v"+newversion);
        whatsNew.setText(whatsNewData);


        update.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateurl));
            startActivity(browserIntent);
        });
    }
}
