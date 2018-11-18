package com.sbobinaFacile.mediaManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class RecorderManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      COSTANTI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TAG = "RecorderManager";

    private static final String DIRECTORY_NAME = "SbobinaFacile";

    public static final int PERMISSION_DENID = 0x0001;
    public static final int MEMORY_NOT_AVAILABLE = 0x0002;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      CAMPI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private File actualFile;
    private MediaRecorder recorder;
    private Context appContext;
    private int errCode = 0x0001;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      COSTRUTTORI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Istanzia un nuovo RecorderManager.
     * @param appContext Context dell'app
     * @param recordFile File su cui verrà salvata la registrazione. Se Null ne viene creato uno nuovo.
     * @throws IOException
     */
    public RecorderManager(Context appContext, File recordFile) throws IOException {

        Log.v(TAG, "Costruttore");

        this.appContext = appContext;


        try {

            if(recordFile != null && recordFile.isFile()){
                Log.d(TAG, "E' stato passato un file.");
                this.actualFile = recordFile;
            }
            else{
                Log.d(TAG, "Non è stato passato nessun file. Si crea.");
                createNewFile();
            }

            Log.v(TAG, "Inizio creazione MediaRecorder");

            this.recorder = new MediaRecorder();
            this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            this.recorder.setOutputFile(actualFile);
            this.recorder.setOnErrorListener(new RecorderManager.ErrorListener());
            this.recorder.setOnInfoListener(new RecorderManager.InfoListener());
            this.recorder.prepare();

            Log.v(TAG, "MediaRecorder pronto.");

        } catch (IOException e) {

            switch (errCode) {

                case PERMISSION_DENID: {
                    Log.d(TAG, "User has no permission to write external storage. Request it");
                    //TODO: Richiesta permesso di accesso alla memoria esterna
                    break;
                }
                case MEMORY_NOT_AVAILABLE: {
                    Log.d(TAG, "Memory is not available");
                    //TODO: GEstioe dell'errore memoria non disponibile
                    break;
                }
                default: {
                    throw e;
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      METODI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Avvia la registrazione di un nuovo audio in formato AAC
     */
    public void startRecord(){

        Log.d(TAG, "Inizio a registrare");

        try{
            recorder.start();
        }
        catch(IllegalStateException e){
            Log.e(TAG, e.toString());
        }
    }

    /**
     * Stoppa la registrazione corrente
     */
    public void stopRecord(){

        Log.d(TAG, "Fermo la registrazione");

        try{
            recorder.stop();
        }
        catch(IllegalStateException e){
            Log.e(TAG, e.toString());
        }
    }

    public void finalizeRecord(){

        Log.v(TAG, "Finalize Record");

        if(recorder != null){

            recorder.reset();
            recorder.release();
            Log.d(TAG, "Risorse associate al MediaRecorder rilasciate.");
        }
        else{
            Log.e(TAG, "Chiamato metodo finalizeRecord con recorder nullo");
        }
    }


    /**
     * Crea un nuovo file per la registrazione nel caso non ne sia stato passato giù uno
     * nel costruttore della classe
     * @throws IOException
     */
    private void createNewFile() throws IOException {

        if( ContextCompat.checkSelfPermission(appContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


                Log.v(TAG, "Inizio creazione del file di registrazione");

                File root = Environment.getExternalStorageDirectory();

                File[] filesInRoot = root.listFiles();

                boolean founded = false;
                int j = 0;
                File directory = null;

                //Ricerco la cartella dell'app
                for(; j < filesInRoot.length; ++j){

                    if(filesInRoot[j].getName().equals(DIRECTORY_NAME)){
                        founded = true;
                        break;
                    }
                }

                //Caso esiste giàuna cartella
                if(founded){

                    Log.v(TAG, "Directory trovata in posizione " + j);
                    directory = filesInRoot[j];
                }
                //Caso la cartella non esiste
                else {

                    Log.v(TAG, "Cartella non esistente. Si crea.");

                    directory = new File(root, DIRECTORY_NAME);
                    directory.mkdirs();
                }

                //Creo il nuovo file di registrazione
                Calendar calendar = Calendar.getInstance();
               //TODO: Usare i metodi di Calendar per creare il nome del file

                this.actualFile = new File(directory, "20181121.aac");


            }
            else{

                errCode = MEMORY_NOT_AVAILABLE;
                throw new IOException();
            }
        }
        else{

            errCode = PERMISSION_DENID;
            throw new IOException();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      CLASSI INNESTATE
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Listener delle info del MediaRecorder
     */
    private class InfoListener implements MediaRecorder.OnInfoListener {

        private static final String TAG = "Recorder_Info_Listener";

        public void onInfo(MediaRecorder mr, int what, int extra){

            switch(what){

                case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN : {
                    //TODO : Gestione dei warnig sconosciuto
                    Log.e(TAG, "Warning sconosciuto");
                    break;
                }
                case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED : {
                    //TODO : Gestione del ragiungimento della durata massima di registrazione
                    Log.e(TAG, "Raggiunta durata massima registrazione");
                    break;
                }
                case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_APPROACHING : {
                    //TODO : Gestione dell'errore di memoria quasi piena
                    Log.e(TAG, "Memoria quasi piena");
                    break;
                }

            }
        }
    }

    /**
     * Listener degli errori del Media Recorder
     */
    private class ErrorListener implements MediaRecorder.OnErrorListener {

        private static final String TAG = "Recorder_Error_Listener";

        public void onError(MediaRecorder mr, int what, int extra){

            switch(what){

                case MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN : {
                    //TODO : Gestione degli errori sconosciuti
                    Log.e(TAG, "Errore sconosciuto");
                    break;
                }
                case MediaRecorder.MEDIA_ERROR_SERVER_DIED : {
                    //TODO : Media server died. In this case, the application must
                    // release the MediaRecorder object and instantiate a new one.
                    Log.e(TAG, "Media Server Died");
                    break;
                }

            }
        }
    }



}
