package com.kaleshsingh.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView countDownTextView;
    SeekBar countDownSeekBar;
    Boolean counterIsActive = false;
    Button countDownButton;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft){
        int minutes = (int) (secondsLeft / 60);
        int seconds = secondsLeft % 60;

        String secondString = (seconds < 10)
                ? "0" + Integer.toString(seconds) : Integer.toString(seconds);

        String minuteString = (minutes < 10)
                ? "0" + Integer.toString(minutes) : Integer.toString(minutes);

        countDownTextView.setText(minuteString + ":" + secondString);
    }

    public void resetTimer(){
        counterIsActive = false;
        countDownTextView.setText("0:30");
        countDownSeekBar.setProgress(30);
        countDownTimer.cancel();
        countDownButton.setText("Go!");
        countDownSeekBar.setEnabled(true);
    }


    public void startStop(View view){

        if(!counterIsActive) {
            counterIsActive = true;
            countDownSeekBar.setEnabled(false);
            countDownButton.setText("Stop");

            countDownTimer = new CountDownTimer((countDownSeekBar.getProgress() * 1000) + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    countDownTextView.setText("00:00");
                    Log.i("Finished", "Timer done!");
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ringout);
                    mediaPlayer.start();

                    new CountDownTimer(2000, 1000) {
                        public void onTick(long millisUntilFinished){

                        }

                        public void onFinish(){
                            resetTimer();
                        }
                    }.start();
                }
            }.start();
        }
        else{
            resetTimer();
        }

        Log.i("Button", "Pressed!");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownTextView = (TextView) findViewById(R.id.countDownTextView);
        countDownButton = (Button) findViewById(R.id.countDownButton);
        countDownSeekBar = (SeekBar) findViewById(R.id.countDownSeekBar);

        countDownSeekBar.setMax(600);   // 600s (10 minutes)
        countDownSeekBar.setProgress(30);   //30s

        countDownSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update timer
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
