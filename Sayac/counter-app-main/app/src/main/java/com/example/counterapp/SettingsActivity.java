package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    EditText upperLimitText;
    Button upperLimitIncrementButton;
    Button upperLimitDecrementButton;
    Switch upperLimitSoundSwitch;
    Switch upperLimitVibrationSwitch;

    EditText lowerLimitText;
    Button lowerLimitIncrementButton;
    Button lowerLimitDecrementButton;
    Switch lowerLimitSoundSwitch;
    Switch lowerLimitVibrationSwitch;

    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPref = SharedPref.getInstance(this);

        upperLimitText = (EditText) findViewById(R.id.upperLimitText);
        upperLimitIncrementButton = (Button) findViewById(R.id.upperLimitIncrementButton);
        upperLimitDecrementButton = (Button) findViewById(R.id.upperLimitDecrementButton);
        upperLimitSoundSwitch = (Switch)findViewById(R.id.upperLimitSoundSwitch);
        upperLimitVibrationSwitch = (Switch)findViewById(R.id.upperLimitVibrationSwitch);

        lowerLimitText = (EditText) findViewById(R.id.lowerLimitText);
        lowerLimitIncrementButton = (Button) findViewById(R.id.lowerLimitIncrementButton);
        lowerLimitDecrementButton = (Button) findViewById(R.id.lowerLimitDecrementButton);
        lowerLimitSoundSwitch = (Switch)findViewById(R.id.lowerLimitSoundSwitch);
        lowerLimitVibrationSwitch = (Switch)findViewById(R.id.lowerLimitVibrationSwitch);


        upperLimitText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(upperLimitText.getText().toString().length() == 0){
                    upperLimitText.setText("0");

                    sharedPref.upperLimit = 0;
                }else if(upperLimitText.getText().toString().equals("-")){
                    return;
                }
                sharedPref.upperLimit = Integer.valueOf(upperLimitText.getText().toString());
            }
        });

        upperLimitIncrementButton.setOnClickListener(view -> {
            sharedPref.upperLimit++;
            upperLimitText.setText(String.valueOf(sharedPref.upperLimit));
        });

        upperLimitDecrementButton.setOnClickListener(view -> {
            sharedPref.upperLimit--;
            upperLimitText.setText(String.valueOf(sharedPref.upperLimit));
        });

        upperLimitSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPref.upperLimitSound = b;
            }
        });

        upperLimitVibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPref.upperLimitVibration = b;
                Log.d("AAA", b + "");
            }
        });

        lowerLimitText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(lowerLimitText.getText().toString().length() == 0){
                    lowerLimitText.setText("0");

                    sharedPref.lowerLimit = 0;
                }else if(lowerLimitText.getText().toString().equals("-")) {
                    return;
                }
                sharedPref.lowerLimit = Integer.valueOf(lowerLimitText.getText().toString());
            }
        });

        lowerLimitIncrementButton.setOnClickListener(view -> {
            sharedPref.lowerLimit++;
            lowerLimitText.setText(String.valueOf(sharedPref.lowerLimit));
        });

        lowerLimitDecrementButton.setOnClickListener(view -> {
            sharedPref.lowerLimit--;
            lowerLimitText.setText(String.valueOf(sharedPref.lowerLimit));
        });

        lowerLimitSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPref.lowerLimitSound = b;
            }
        });

        lowerLimitVibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPref.lowerLimitVibration = b;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        upperLimitText.setText(String.valueOf(sharedPref.upperLimit));
        upperLimitSoundSwitch.setChecked(sharedPref.upperLimitSound);
        upperLimitVibrationSwitch.setChecked(sharedPref.upperLimitVibration);

        lowerLimitText.setText(String.valueOf(sharedPref.lowerLimit));
        lowerLimitSoundSwitch.setChecked(sharedPref.lowerLimitSound);
        lowerLimitVibrationSwitch.setChecked(sharedPref.lowerLimitVibration);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sharedPref.saveValues();
    }
}