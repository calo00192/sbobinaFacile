package com.sbobinaFacile.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.util.Log;
import com.sbobinaFacile.R;

import com.sbobinaFacile.mediaManager.*;

import java.io.IOException;

public class RecordingActivity extends AppCompatActivity {

    private static final String TAG = "Recoring_Activity";

    private RecorderManager recorderManager;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            recorderManager = new RecorderManager(getApplicationContext(), null);
        }
        catch (IOException e){
            Log.e(TAG, "Errore nella creazione del file di registrazione");
            Log.e(TAG, e.toString());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.recordButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "Premuto il pulsante di registrazione");

                if(!isRecording){
                    isRecording = true;
                    recorderManager.startRecord();
                }
                else {
                    isRecording = false;
                    recorderManager.stopRecord();
                    recorderManager.finalizeRecord();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
