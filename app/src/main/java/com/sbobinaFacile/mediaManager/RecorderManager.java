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

    public RecorderManager(Context appContext, File recordFile) throws IOException {

        this.appContext = appContext;


        try {

            if(recordFile != null && recordFile.isFile()){
                this.actualFile = recordFile;
            }
            else{
                createNewFile();
            }

            this.recorder = new MediaRecorder();
            this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            this.recorder.setOutputFile(actualFile);
            this.recorder.prepare();


        } catch (IOException e) {

            switch (errCode) {

                case PERMISSION_DENID: {
                    Log.d(TAG, "User has no permission to write external storage. Request it");
                    //TODO: Richiesta permesso di accesso alla memoria esterna
                    break;
                }
                case MEMORY_NOT_AVAILABLE: {
                    Log.d(TAG, "Memory is not available");
                    //TODO: GEstioe dell'errore
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

    public void startRecord(){

        try{
            recorder.start();
        }
        catch(IllegalStateException e){
            Log.e(TAG, e.toString());
        }
    }

    public void stopRecord(){

        try{
            recorder.stop();
        }
        catch(IllegalStateException e){
            Log.e(TAG, e.toString());
        }
    }


    private void createNewFile() throws IOException {

        if( ContextCompat.checkSelfPermission(appContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


                File root = Environment.getExternalStorageDirectory();

                File[] filesInRoot = root.listFiles();

                int j = -1;
                File directory = null;

                //Ricerco la cartella dell'app
                for(; j < filesInRoot.length; ++j){

                    if(filesInRoot[j].getName().equals(DIRECTORY_NAME)){
                        break;
                    }
                }

                //Caso esiste giàuna cartella
                if(j >= 0){

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

                this.actualFile = new File(directory, "20181121");


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



}
