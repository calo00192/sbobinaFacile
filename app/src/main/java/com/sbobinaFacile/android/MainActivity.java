package com.sbobinaFacile.android;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView textView;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      SOVRACCARICO METODI DELLA GESTIONE DEL CICLO DI VITA
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        textView = (TextView) findViewById(R.id.textDisplayer);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(recognizer != null){
                    Log.v(TAG, "Premuto pulsante di inizio registrazione");
                    if(recognizer != null){

                        Log.v(TAG, "Creating speech recognition intent ");

                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "it");

                        Log.d(TAG, "Starting recognition");
                        recognizer.startListening(intent);
                    }
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(recognizer != null){
                    Log.v(TAG, "Premuto pulsante di fine registrazione");

                    if(recognizer != null ){
                        Log.d(TAG, "Stopping speech recognition");
                        recognizer.stopListening();
                    }
                }
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        if(SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {

            Log.v(TAG, "Speech Recognition is available");

            recognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
            recognizer.setRecognitionListener(new SpeechListener(this));
        }
        else{
            Log.e(TAG, "Speech Recognition is not available");
            finish();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStop(){
        super.onStop();

        if(recognizer != null){
            recognizer.destroy();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      METOIDI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public TextView getDisplayer(){
        return this.textView;
    }
}
