package com.example.counterapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPref instance;

    public int counterValue;

    public int upperLimit;
    public boolean upperLimitSound;
    public boolean upperLimitVibration;

    public int lowerLimit;
    public boolean lowerLimitSound;
    public boolean lowerLimitVibration;

    private static final String KEY_COUNTER_VALUE = "COUNTERVALUE";

    private static final String KEY_UPPERLIMIT= "UPPERLIMIT";
    private static final String KEY_UPPERLIMIT_SOUND = "UPPERLIMIT_SOUND";
    private static final String KEY_UPPERLIMIT_VIBRATION = "UPPERLIMIT_VIBRATION";

    private static final String KEY_LOWERLIMIT= "LOWERLIMIT";
    private static final String KEY_LOWERLIMIT_SOUND = "LOWERLIMIT_SOUND";
    private static final String KEY_LOWERLIMIT_VIBRATION = "LOWERLIMIT_VIBRATION";

    private SharedPreferences sharedPreferences;

    private SharedPref(Context context){
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        loadValues();
    }

    public static SharedPref getInstance(Context context){
        if(instance == null){
            instance = new SharedPref(context);
        }

        return instance;
    }

    private void loadValues(){
        counterValue = sharedPreferences.getInt(KEY_COUNTER_VALUE, 0);

        upperLimit = sharedPreferences.getInt(KEY_UPPERLIMIT, 20);
        upperLimitSound = sharedPreferences.getBoolean(KEY_UPPERLIMIT_SOUND, true);
        upperLimitVibration = sharedPreferences.getBoolean(KEY_UPPERLIMIT_VIBRATION, true);

        lowerLimit = sharedPreferences.getInt(KEY_LOWERLIMIT, 0);
        lowerLimitSound = sharedPreferences.getBoolean(KEY_LOWERLIMIT_SOUND, true);
        lowerLimitVibration = sharedPreferences.getBoolean(KEY_LOWERLIMIT_VIBRATION, true);
    }

    public void saveValues(){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_COUNTER_VALUE, counterValue);

        editor.putInt(KEY_UPPERLIMIT, upperLimit);
        editor.putBoolean(KEY_UPPERLIMIT_SOUND, upperLimitSound);
        editor.putBoolean(KEY_UPPERLIMIT_VIBRATION, upperLimitVibration);

        editor.putInt(KEY_LOWERLIMIT, lowerLimit);
        editor.putBoolean(KEY_LOWERLIMIT_SOUND, lowerLimitSound);
        editor.putBoolean(KEY_LOWERLIMIT_VIBRATION, lowerLimitVibration);
        editor.commit();
    }
}
