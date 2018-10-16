package com.sbobinaFacile.data;

/**
 * Wrapper della sbobinatura. Contiene la registrazione e il testo. Fornisce Setter e Getter,
 */

public class Transcription {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      CAMPI
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Registrazione
     */
    private Object audioRecord;

    /**
     * Testo
     */
    private Object text;

    /**
     * Costruttore vuoto
     */
    public Transcription(){}

    /**
     * Costruttore
     * @param audioRecord
     * @param text
     */
    public Transcription(Object audioRecord, Object text){
        setAudioRecord(audioRecord);
        setText(text);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //      Setter e Getter
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setAudioRecord(Object audioRecord) {
        this.audioRecord = audioRecord;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public Object getAudioRecord() {
        return audioRecord;
    }

    public Object getText() {
        return text;
    }
}
