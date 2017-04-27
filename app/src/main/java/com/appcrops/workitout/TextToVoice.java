package com.appcrops.workitout;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by mraavi on 26/04/17.
 */

public class TextToVoice {

    private static TextToSpeech sTextToSpeech = null;

    public static void init(Context appContext) {
        if (sTextToSpeech == null) {
            sTextToSpeech = new TextToSpeech(appContext, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        sTextToSpeech.setLanguage(Locale.UK);
                    }
                }
            });
        }
    }

    public static void speak(String strToSpeak) {
        sTextToSpeech.speak(strToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
}
