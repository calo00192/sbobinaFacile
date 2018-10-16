package com.sbobinaFacile.speechRecognition;

import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * Listener per le callback dello SpeechRecognizer di Android
 */

public class SpeechListener implements RecognitionListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      COSTANTI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TAG = "Recognition_Listener";

    @Override
    public void onBeginningOfSpeech(){
        Log.v(TAG, "L'utente ha iniziato a parlare");

    }

    @Override
    public void onBufferReceived(byte[] buffer){
        Log.v(TAG, "Ricevuti pacchetti bafferizzati");

    }

    @Override
    public void onEndOfSpeech(){
        Log.v(TAG, "L'utente ha finito di parlare");

    }

    @Override
    public void onError(int err){
        Log.e(TAG, "Errore " + err);

    }

    @Override
    public void onEvent(int eType, Bundle params){
        Log.v(TAG, "Evento " + eType);

    }

    @Override
    public void onPartialResults(Bundle pRes){
        Log.v(TAG, "Risultato parziale ricevuto");

    }

    @Override
    public void onReadyForSpeech(Bundle params){
        Log.v(TAG, "Ready to speech");

    }

    @Override
    public void onResults(Bundle results){
        Log.d(TAG, "Risultato ricevuto");
        ArrayList<String> phrases =  results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Log.d(TAG, "Result size = " + phrases.size());

    }

    @Override
    public void onRmsChanged(float rms){
        Log.v(TAG, "Volume cambiato " + rms);

    }

}
