package com.microhealthllc.targetheartratecalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.microhealthllc.targetheartratecalculator.bubbleseekbar.BubbleSeekBar;
import com.microhealthllc.targetheartratecalculator.chart.TickerUtils;
import com.microhealthllc.targetheartratecalculator.chart.TickerView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.lang.String.valueOf;

/**
 * Created by Daniel Boakye on 4/28/2017.
 */

public class MainActivty2 extends AppCompatActivity {
    //Declare variables used in multiple methods
    private int restingHRValue = 60; //Default
    private int maxHRValue = 180; // Default
    private int tempMaxHRValue = 0;
    private double intensityValue = 0;
    private double targetHRValue = 0;
    boolean clickedReserveHR = false;
    boolean clickedMaxHR = false;
    SeekBar restingHRSlider;
    SeekBar maxHRSlider;
    SeekBar intensitySlider;
    RadioButton light;
    RadioButton verylight;
    RadioButton moderate;
    RadioButton hard;
    RadioButton veryhard;
    TextView restingHRDisplay;
    TextView maxHRDisplay;
    TextView agedisplay;
    TextView targetHRDisplay;
    int maximumheartRate ;
    int MaxheartRateminusRestingHeartRate;
    int user_age =40;

    int MaxTargetValue;
    int MinTargetValue;

    int MaxTargetValuetemp;
    int MinTargetValuetemp;
    double  minIntensityrate = 0.6;
    double maxIntensityrate = 0.7;
    RadioGroup intensitygroup;
    private TickerView mintarget;
    private TickerView maxtarget;
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private Toolbar toolbar;
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
        setContentView(R.layout.actvity_main2);

        //Force the screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Declare SeekBars, RadioButtons, TextViews, and Buttons to be used and updated in future methods
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        toolbar.setTitle("Target Heart Rate Calculator");
        setSupportActionBar(toolbar);


        light = (RadioButton) findViewById(R.id.light);
        verylight = (RadioButton) findViewById(R.id.very_light);
        hard = (RadioButton) findViewById(R.id.hard);
        veryhard = (RadioButton) findViewById(R.id.vhard);
        moderate =(RadioButton) findViewById(R.id.moderate);
        intensitygroup = (RadioGroup)findViewById(R.id.itensitygroup);
        restingHRDisplay = (TextView) findViewById(R.id.restingHRDisplay);
        maxHRDisplay = (TextView) findViewById(R.id.maxHRDisplay);
        agedisplay = (TextView) findViewById(R.id.agedisplay);
        targetHRDisplay = (TextView) findViewById(R.id.targetHRDisplay);
        mintarget = (TickerView) findViewById(R.id.mintarget);
        maxtarget = (TickerView) findViewById(R.id.maxtarget);
        mintarget.setCharacterList(NUMBER_LIST);
        maxtarget.setCharacterList(NUMBER_LIST);

         calculateTargetheartRate();

    intensitygroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // find which radio button is selected
            if(checkedId == R.id.very_light) {

                        minIntensityrate = 0.5;
                        maxIntensityrate = 0.6;

             /*   Toast.makeText(getApplicationContext(), "minIntensityrate="+ minIntensityrate+" , minIntensityrate="+maxIntensityrate ,
                        Toast.LENGTH_SHORT).show();*/
                calculateTargetheartRate();

            } else if(checkedId == R.id.light) {
                minIntensityrate = 0.6;
                maxIntensityrate = 0.7;
/*

                Toast.makeText(getApplicationContext(), "minIntensityrate="+ minIntensityrate+" , minIntensityrate="+maxIntensityrate ,
                        Toast.LENGTH_SHORT).show();
*/

                calculateTargetheartRate();


            }
            else if(checkedId == R.id.moderate) {

                minIntensityrate = 0.7;
                maxIntensityrate = 0.8;
             /*   Toast.makeText(getApplicationContext(), "minIntensityrate="+ minIntensityrate+" , minIntensityrate="+maxIntensityrate ,
                        Toast.LENGTH_SHORT).show();
*/
                calculateTargetheartRate();

            }


            else if(checkedId == R.id.hard) {
                minIntensityrate = 0.8;
                maxIntensityrate = 0.9;
/*
                Toast.makeText(getApplicationContext(), "minIntensityrate="+ minIntensityrate+" , minIntensityrate="+maxIntensityrate ,
                        Toast.LENGTH_SHORT).show();*/
                calculateTargetheartRate();

            }
            else {

                minIntensityrate = 0.9;
                maxIntensityrate = 1.0;
/*

                Toast.makeText(getApplicationContext(), "minIntensityrate="+ minIntensityrate+" , minIntensityrate="+maxIntensityrate ,
                        Toast.LENGTH_SHORT).show();
*/

                calculateTargetheartRate();

            }
        }

    });
        final BubbleSeekBar age = (BubbleSeekBar) findViewById(R.id.age);
        age.setProgress(40);
       age.getConfigBuilder()

                .min(16)
                .max(100)
                .sectionCount(10)
                .progress(40)
                .showSectionText()
                .showThumbText()
                .sectionTextInterval(10)
                .trackColor(ContextCompat.getColor(this, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(this, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(this, R.color.color_blue))
                .sectionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .sectionTextSize(12)
                .thumbTextColor(ContextCompat.getColor(this, R.color.color_blue))
                .thumbTextSize(12)
                .bubbleColor(ContextCompat.getColor(this, R.color.color_green))
                .bubbleTextSize(12)
                .showSectionMark()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

       age.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {

                //onChangePressWaistChanged(progress);
                user_age = progress;
                maxHRValue = 220 - user_age;

                calculateTargetheartRate();
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });



        final BubbleSeekBar restingHRSlider = (BubbleSeekBar) findViewById(R.id.restingHRSlider);
        restingHRSlider.setProgress(60);
        restingHRSlider.getConfigBuilder()
                .min(30)
                .max(100)
                .progress(60)
                .sectionCount(5)
                .showSectionText()
                .showThumbText()
                .sectionTextInterval(10)
                .trackColor(ContextCompat.getColor(this, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(this, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(this, R.color.color_blue))
                .sectionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .sectionTextSize(12)
                .thumbTextColor(ContextCompat.getColor(this, R.color.color_blue))
                .thumbTextSize(12)
                .bubbleColor(ContextCompat.getColor(this, R.color.color_green))
                .bubbleTextSize(12)
                .showSectionMark()

                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

        restingHRSlider.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                // onChangePressHeightChanged(progress);
                restingHRValue = progress;
                calculateTargetheartRate();

            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.terms) {
            Terms();
            return true;
        }
        if (id ==R.id.about){
            startActivity(new Intent(this, About.class));

        }
        if (id ==R.id.how){
            startActivity(new Intent(this, How_to.class));

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void calculateTargetheartRate(){
        MaxheartRateminusRestingHeartRate = maxHRValue-restingHRValue;
        MaxTargetValuetemp  = (int) (MaxheartRateminusRestingHeartRate * maxIntensityrate);
        MinTargetValuetemp = (int) (MaxheartRateminusRestingHeartRate * minIntensityrate);
        MaxTargetValue = MaxTargetValuetemp + restingHRValue;
        MinTargetValue = MinTargetValuetemp +restingHRValue;
        restingHRDisplay.setText(valueOf(restingHRValue) + " ~BPM");
        //maxHRValue = restingHRValue + tempMaxHRValue;
        maxHRDisplay.setText(valueOf(maxHRValue) + " ~BPM");
        agedisplay.setText(""+user_age);
        //Update the targetHRDisplay
        //  calcTargetHR();

        mintarget.setText(""+MinTargetValue);
        maxtarget.setText(""+MaxTargetValue);

    }

    public void Terms()
    {
        Uri uri = Uri.parse("https://microhealthllc.com/about/terms-of-use/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}