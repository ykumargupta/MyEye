package com.android.thelightmarshmallow.myeye;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private ImageView imageView;
    private MediaPlayer mMediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager= (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        releseMediaPlayer();
        int result= audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            mMediaplayer = MediaPlayer.create(MainActivity.this, R.raw.heyhowcan);
            mMediaplayer.start();
            mMediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaplayer) {
                    releseMediaPlayer();
                }
            });
        }
        imageView= findViewById(R.id.mainActivityImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int FocusChange) {

            if(FocusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||FocusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaplayer.pause();
                mMediaplayer.seekTo(0);

            }
            else if(FocusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaplayer.start();
            }
            else if(FocusChange==AudioManager.AUDIOFOCUS_LOSS){
                releseMediaPlayer();
            }
        }
    };
    private void releseMediaPlayer(){
        if(mMediaplayer!=null){
            mMediaplayer.release();

            mMediaplayer=null;
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);}
    }
}
