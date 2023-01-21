package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import java.security.Key;
import java.util.EventListener;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorManager;
    Sensor acc;
    long lastShake = 0;

    TextView textCounterValue;
    Button buttonIncrement;
    Button buttonDecrement;
    Button buttonSettings;

    SharedPref sharedPref;

    MediaPlayer mediaPlayer;
    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = SharedPref.getInstance(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.beep);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        textCounterValue = (TextView)findViewById(R.id.counterValue);
        buttonIncrement = (Button)findViewById(R.id.incrementButton);
        buttonDecrement = (Button)findViewById(R.id.decrementButton);
        buttonSettings = (Button)findViewById(R.id.settingsButton);

        buttonDecrement.setOnClickListener(view -> {
            UpdateValue(-1);
        });

        buttonIncrement.setOnClickListener(view -> {
            UpdateValue(1);
        });

        buttonSettings.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        sharedPref.saveValues();
        sensorManager.unregisterListener(this, acc);
    }

    @Override
    protected void onResume() {
        super.onResume();

        textCounterValue.setText(String.valueOf(sharedPref.counterValue));
        sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void UpdateValue(int step){
        if(step > 0){
            if(sharedPref.counterValue + step > sharedPref.upperLimit){
                if(sharedPref.upperLimitSound){
                    Beep();
                }

                if(sharedPref.upperLimitVibration){
                    Vibrate();
                }

                sharedPref.counterValue = sharedPref.upperLimit;
            }else{
                sharedPref.counterValue += step;
            }
        }else{
            if(sharedPref.counterValue + step < sharedPref.lowerLimit){
                if(sharedPref.lowerLimitSound){
                    Beep();
                }

                if(sharedPref.lowerLimitVibration){
                    Vibrate();
                }

                sharedPref.counterValue = sharedPref.lowerLimit;
            }else{
                sharedPref.counterValue += step;
            }
        }

        textCounterValue.setText(String.valueOf(sharedPref.counterValue));
    }

    private void Beep(){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    private void Vibrate(){
        if(!vibrator.hasVibrator())
            return;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            vibrator.vibrate(1000);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            UpdateValue(5);
        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            UpdateValue(-5);
        }

        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        double length = Math.sqrt(x * x + y * y + z * z);

        if(length > 15){
            if(System.currentTimeMillis() - lastShake > 500){
                sharedPref.counterValue = sharedPref.lowerLimit;
                textCounterValue.setText(String.valueOf(sharedPref.counterValue));

                lastShake = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}