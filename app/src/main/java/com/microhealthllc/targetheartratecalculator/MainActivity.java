/*
    Target Heart Rate Calculator
    Author: Christian Moons
    Date last modified: 4/14/17

    An Android application that calculates the target heart rate for exercise based on the user's resting heart rate and maximum heart rate, both given by the user.  They can also decide whether it is calculated as a percentage of the reserve heart rate or maximum heart rate.
 */
package com.microhealthllc.targetheartratecalculator;

//Import necessary assets
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.lang.String.valueOf;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    //Declare variables used in multiple methods
    private int restingHRValue = 20;
    private int maxHRValue = 20;
    private int tempMaxHRValue = 0;
    private double intensityValue = 0;
    private double targetHRValue = 0;
    boolean clickedReserveHR = false;
    boolean clickedMaxHR = false;

    //Create a method to calculate the targetHRValue based on which radio button is pressed, it is used in all listeners to update the targetHRDisplay in real time
    private void calcTargetHR() {
        if (clickedReserveHR) {
            targetHRValue = (intensityValue / 100) * (maxHRValue - restingHRValue) + restingHRValue;
        } else if (clickedMaxHR) {
            targetHRValue = (intensityValue / 100) * maxHRValue;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Force the screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Declare SeekBars, RadioButtons, TextViews, and Buttons to be used and updated in future methods
        final SeekBar restingHRSlider = (SeekBar) findViewById(R.id.restingHRSlider);
        final SeekBar maxHRSlider = (SeekBar) findViewById(R.id.maxHRSlider);
        final SeekBar intensitySlider = (SeekBar) findViewById(R.id.intensitySlider);
        final RadioButton reserveHRButton = (RadioButton) findViewById(R.id.reserveHRButton);
        final RadioButton maxHRButton = (RadioButton) findViewById(R.id.maxHRButton);
        final TextView restingHRDisplay = (TextView) findViewById(R.id.restingHRDisplay);
        final TextView maxHRDisplay = (TextView) findViewById(R.id.maxHRDisplay);
        final TextView intensityDisplay = (TextView) findViewById(R.id.intensityDisplay);
        final TextView targetHRDisplay = (TextView) findViewById(R.id.targetHRDisplay);
        final Button aboutButton = (Button) findViewById(R.id.aboutButton);
        final Button termsButton = (Button) findViewById(R.id.termsButton);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        final TextView textView3 = (TextView) findViewById(R.id.textView3);
        final TextView textView4 = (TextView) findViewById(R.id.textView4);
        final TextView textView5 = (TextView) findViewById(R.id.textView5);
        final TextView textView6 = (TextView) findViewById(R.id. textView6);
        final TextView textView7 = (TextView) findViewById(R.id. textView7);

        //Set a tag for aboutButton to be used later
        aboutButton.setTag(1);

        //Create a listener to update the maxHRValue based on the maxHRSlider in real time
        maxHRSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar maxHRSlider, int progress, boolean fromUser) {
                //set the maxHRValue to the progress of the maxHRSlider plus the resting HR
                maxHRValue = progress + restingHRValue;
                tempMaxHRValue = progress;
                maxHRDisplay.setText(valueOf(maxHRValue) + " BPM");

                //Update the targetHRDisplay
                calcTargetHR();
                targetHRDisplay.setText(valueOf((int) targetHRValue) + " BPM");
            }

            public void onStartTrackingTouch(SeekBar maxHRSlider) {
            }

            public void onStopTrackingTouch(SeekBar maxHRSlider) {
            }
        });

        //Create a listener to update the restingHRValue based on the restingHRSlider in real time
        restingHRSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar restingHRSlider, int progress, boolean fromUser) {
                //set the restingHRValue to 20 more than the progress of the restingHRSlider and update the maxHRValue to reflect the change in the restingHRValue
                restingHRValue = 20 + progress;
                restingHRDisplay.setText(valueOf(restingHRValue) + " BPM");
                maxHRValue = restingHRValue + tempMaxHRValue;
                maxHRDisplay.setText(valueOf(maxHRValue) + " BPM");

                //Update the targetHRDisplay
                calcTargetHR();
                targetHRDisplay.setText(valueOf((int) targetHRValue) + " BPM");
            }

            public void onStartTrackingTouch(SeekBar restingHRSlider) {
            }

            public void onStopTrackingTouch(SeekBar restingHRSlider) {
            }
        });

        //Create a listener to update the intensityValue based on the intensitySlider in real time
        intensitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar intensitySlider, int progress, boolean fromUser) {
                //Set the intensityValue equal to the progress of the intensitySlider's progress
                intensityValue = progress;
                intensityDisplay.setText(valueOf((int) intensityValue) + " %");

                //Update the targetHRDisplay
                calcTargetHR();
                targetHRDisplay.setText(valueOf((int) targetHRValue) + " BPM");
            }

            public void onStartTrackingTouch(SeekBar maxHRSlider) {
            }

            public void onStopTrackingTouch(SeekBar maxHRSlider) {
            }
        });

        //Create a listener to change the values of the two clicked booleans to reflect the reserveHRButton being pressed
        reserveHRButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickedReserveHR = true;
                clickedMaxHR = false;

                //Update the targetHRDisplay
                calcTargetHR();
                targetHRDisplay.setText(valueOf((int) targetHRValue) + " BPM");
            }
        });

        //Create a listener to change the values of the two clicked booleans to reflect the maxHRButton being pressed
        maxHRButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickedMaxHR = true;
                clickedReserveHR = false;

                //Update the targetHRDisplay
                calcTargetHR();
                targetHRDisplay.setText(valueOf((int) targetHRValue) + " BPM");
            }
        });

        //Create a method that shows an about page whenever aboutButton is clicked and returns the page to normal when it is clicked again
        aboutButton.setOnClickListener( new View.OnClickListener() {
            public void onClick (View v) {
                final int status =(Integer) v.getTag();
                if(status == 1) {
                    restingHRSlider.setVisibility(INVISIBLE);
                    maxHRSlider.setVisibility(INVISIBLE);
                    maxHRButton.setVisibility(INVISIBLE);
                    maxHRDisplay.setVisibility(INVISIBLE);
                    reserveHRButton.setVisibility(INVISIBLE);
                    restingHRDisplay.setVisibility(INVISIBLE);
                    intensityDisplay.setVisibility(INVISIBLE);
                    intensitySlider.setVisibility(INVISIBLE);
                    textView.setVisibility(INVISIBLE);
                    textView2.setVisibility(INVISIBLE);
                    textView3.setVisibility(INVISIBLE);
                    textView4.setVisibility(INVISIBLE);
                    textView5.setVisibility(INVISIBLE);
                    targetHRDisplay.setVisibility(INVISIBLE);

                    textView6.setVisibility(VISIBLE);
                    textView7.setVisibility(VISIBLE);
                    termsButton.setVisibility(VISIBLE);

                    aboutButton.setText("Back");
                    v.setTag(0);
                } else {
                    restingHRSlider.setVisibility(VISIBLE);
                    maxHRSlider.setVisibility(VISIBLE);
                    maxHRButton.setVisibility(VISIBLE);
                    maxHRDisplay.setVisibility(VISIBLE);
                    reserveHRButton.setVisibility(VISIBLE);
                    restingHRDisplay.setVisibility(VISIBLE);
                    intensityDisplay.setVisibility(VISIBLE);
                    intensitySlider.setVisibility(VISIBLE);
                    textView.setVisibility(VISIBLE);
                    textView5.setVisibility(VISIBLE);
                    textView2.setVisibility(VISIBLE);
                    textView3.setVisibility(VISIBLE);
                    textView4.setVisibility(VISIBLE);
                    targetHRDisplay.setVisibility(VISIBLE);

                    textView6.setVisibility(INVISIBLE);
                    textView7.setVisibility(INVISIBLE);
                    termsButton.setVisibility(INVISIBLE);

                    aboutButton.setText("About");
                    v.setTag(1);
                }
            }
        });

        //Create a listener to link to a webpage when termsButton is clicked
        termsButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://microhealthllc.com/about/terms-of-use/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }
}