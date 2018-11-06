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
    private static int errCode = 0x0001;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      COSTRUTTORI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public RecorderManager(){

        this.recorder = new MediaRecorder();
        this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        this.recorder.setOutputFile(PATH_NAME);
        this.recorder.prepare();
    }

    public RecorderManager(File mediaFile){}

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      METODI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static RecorderManager createNewRecorderManager(Context appContext) throws IOException {

        try{



        }
        catch(IOException){

            switch(errCode){

                case PERMISSION_DENID : {
                    Log.d(TAG, "User has no permission to write external storage. Request it");
                    //TODO: Richiesta permesso di accesso alla memoria esterna
                    break;
                }
                case MEMORY_NOT_AVAILABLE : {
                    Log.d(TAG, "Memory is not available");
                    //TODO: GEstioe dell'errore
                    break;
                }
                default: {
                    throw new IOException("Error in opening a new file for the recorder");
                    break;
                }
            }
        }


    }

    private void createNewFile() throws IOException {

        if( ContextCompat.checkSelfPermission(appContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


                //File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                File root = Environment.getExternalStorageDirectory();

                File[] filesInRoot = root.listFiles();

                int j = -1;
                File directory = null;

                //Ricerco la cartella dell'app
                for(; j < filesInRoot.length; ++i){

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

                    File directory = new File(root, DIRECTORY_NAME);
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
