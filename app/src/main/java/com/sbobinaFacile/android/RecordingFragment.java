package com.sbobinaFacile.android;

import android.os.Bundle;
import android.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sbobinaFacile.R;

public class RecordingFragment extends DialogFragment {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      COSTANTI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static final String TAG = "Recording_Fragment";

    public static final boolean ICON_HIGH = true;

    public static final boolean ICON_LOW = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      CAMPI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private ImageView micIcon;

    private boolean currIconStatus;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      METODI DI FRAGMENT
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static RecordingFragment newInstance(/*Eventuali argomenti*/){

        /*
        Codice per fornire argomenti
        Bundle argument = new Bundle();
        argument.putInt(INSTANCE_NUMBER, instanceNumber);
        */

        RecordingFragment fragment =  new RecordingFragment();

        //fragment.setArguments(argument);

        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currIconStatus = true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View thisView = inflater.inflate(R.layout.fragment_recording, container, false);

        micIcon = (ImageView) thisView.findViewById(R.id.iconRecordingStatus);

        return thisView;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      MIEI METODI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public synchronized void setIconStatus(boolean status){

        currIconStatus = status;

        Log.d(TAG, "Setto stato icona a " + status);

        if(status == ICON_HIGH){

            micIcon.setImageResource(R.drawable.round_mic_black_48dp);
        }
        else {
            micIcon.setImageResource(R.drawable.round_mic_none_black_48dp);
        }
    }

    public synchronized boolean getIconStatus(){

        Log.d(TAG, "Ritorno stato icona " + currIconStatus);
        return this.currIconStatus;
    }


}
