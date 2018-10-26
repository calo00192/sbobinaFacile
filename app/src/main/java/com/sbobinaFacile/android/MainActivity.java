package com.sbobinaFacile.android;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sbobinaFacile.R;
import com.sbobinaFacile.speechRecognition.SpeechListener;


/**
 * Activity principale dell'app
 */

public class MainActivity extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      COSTANTI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TAG = "Activity_Main";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      CAMPI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private SpeechRecognizer recognizer;
    private Button buttonStart, buttonStop;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      SOVRACCARICO METODI DELLA GESTIONE DEL CICLO DI VITA
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        recognizer.setRecognitionListener(new SpeechListener());

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(recognizer != null){
                    Log.v(TAG, "Premuto pulsante di inizio registrazione");
                    //TODO : Start recognition
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(recognizer != null){
                    Log.v(TAG, "Premuto pulsante di fine registrazione");
                    //TODO : Stop recognition and visualize results
                }
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
