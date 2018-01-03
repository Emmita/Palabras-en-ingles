package com.emmita.english.POJO;

/**
 * Created by JESUS on 02/01/2018.
 */

public class Word {

    //Texto en español
    private int mSpanish;
    //Texto en inglés
    private int mEnglish;
    //Audio de la palabra en inglés
    private int mAudioResource;


    public Word(int spanish, int english, int audioResource){
        mSpanish = spanish;
        mEnglish = english;
        mAudioResource = audioResource;
    }



    public int getSpanish(){
        return mSpanish;
    }

    public int getEnglish(){
        return mEnglish;
    }

    public int getAudioResource(){
        return mAudioResource;
    }



}
