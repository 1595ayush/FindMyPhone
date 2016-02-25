package com.se.findmyphone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by ayush on 18/2/16.
 */

public class SMSProcessor extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Make this activity click-through as it is invisible
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        //Read keyword from Shared Preferences and store it in a String 'key'
        SharedPreferences settings = getSharedPreferences("KeywordStorage", 0);
        String key = settings.getString("KeyWord", "");

        //Store SMSMessage coming from IncominSMS receiver in a String 'sms'
        Bundle b = getIntent().getExtras();
        String sms = b.getString("SMSMessage");


        //Compare 'sms' and 'key'
        if(sms.equals(key))
        {
            //Start new activity to play sound.
            Intent i = new Intent();
            i.setClass(SMSProcessor.this, AlarmRinger.class);
            startActivity(i);
        }

        finish(); //close this activity after starting new activity


    }

}