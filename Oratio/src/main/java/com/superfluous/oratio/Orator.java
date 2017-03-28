package com.superfluous.oratio;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class Orator implements TextToSpeech. OnInitListener {
    private Activity activity;

    private MediaPlayer mediaplayer;
    private boolean voiceContinuesOnPlay = false;

    private TextToSpeech orator;

    public Orator(Activity activity) {
        this.activity = activity;

        orator = new TextToSpeech(activity.getApplicationContext(), this);
    }

    public void orate(int i) {
        if(mediaplayer != null && !voiceContinuesOnPlay) {
            mediaplayer.release();
            mediaplayer = null;
        }

        mediaplayer = MediaPlayer.create(activity, i);
        mediaplayer.start();
    }

    public void orate(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            canDrink(text);
        } else{
            cantDrink(text);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void canDrink(String text) {
        String utteranceId = this.hashCode() + "";

        if(voiceContinuesOnPlay)
            orator.speak(text, TextToSpeech.QUEUE_ADD, null, utteranceId);
        else
            orator.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    @SuppressWarnings("deprecation")
    private void cantDrink(String text) {
        HashMap<String, String> map = map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");

        if(voiceContinuesOnPlay)
            orator.speak(text, TextToSpeech.QUEUE_ADD, map);
        else
            orator.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    public void voiceContinuesOnPlay(boolean continues) {
        voiceContinuesOnPlay = continues;
    }

    public void ahhh() {
        orate(R.raw.ahhhh);
    }

    public void hello() {
        orate(R.raw.hello);
    }

    public void goodbye() {
        orate(R.raw.goodbye);
    }

    public void imAPotato() {
        orate(R.raw.becauseimapotato);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            int result = orator.setLanguage(Locale.US);

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("Orator", "This language is not supported");
        } else {
            Log.e("Orator", "Initialization Failed!");
        }
    }
}
