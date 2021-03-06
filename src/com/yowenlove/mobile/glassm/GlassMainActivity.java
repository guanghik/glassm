package com.yowenlove.mobile.glassm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;


/**
 * Created by yowenlove on 14-7-25.
 */
public class GlassMainActivity extends Activity {

    private Handler mHandler = new Handler();

    private AudioManager mAudioManager;
    private GestureDetector mGestureDetector;
    private MediaPlayer music;

    /**
     * Listener that displays the options menu when the touchpad is tapped.
     */
    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override
        public boolean onGesture(Gesture gesture) {
            if (gesture == Gesture.TAP) {
                mAudioManager.playSoundEffect(Sounds.TAP);
                openOptionsMenu();
                return true;
            } else {
                return false;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);

//        music = MediaPlayer.create(this, R.raw.music);
        if (music != null) {
            music.setLooping(true);
            music.start();
        }

    }

    @Override
    protected void onDestroy() {
        if (music != null) {
            if (music.isPlaying()) {
                music.stop();
            }

            music.release();
        }

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.say_yes:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        flower();
                    }
                });
                return true;

            default:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        oops();
                    }
                });
                return false;
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }


    private void flower() {
        startActivity(new Intent(this, ARPreviewActivity.class));
    }

    private void oops() {

    }
}
