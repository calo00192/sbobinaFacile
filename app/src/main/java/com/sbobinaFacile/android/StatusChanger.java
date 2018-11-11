package com.sbobinaFacile.android;

import android.util.Log;

public class StatusChanger extends Thread {

    private static final String TAG = "Status_Changer";

    private RecordingFragment rf;

    public StatusChanger(RecordingFragment rf){
        this.rf = rf;
    }

    public void run(){

        try{

            while(true) {
                sleep(1000);
                Log.d(TAG, "Cambio stato dell'icona");
                rf.setIconStatus(!rf.getIconStatus());
            }

        }
        catch(InterruptedException e){
            Log.e(TAG, e.toString());

        }
    }
}
