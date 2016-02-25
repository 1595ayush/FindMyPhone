package com.se.findmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class ServiceReceiver extends BroadcastReceiver {

    DbHelper db;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //---get the SMS message passed in---
        db = new DbHelper(context);
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String sms = null;
        //String str = "";
        String number="";
        AudioManager profileCheck = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if(profileCheck.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
        {
            if (bundle != null)
            {
                //---retrieve the SMS message received---
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i=0; i<msgs.length; i++)
                {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    number = msgs[i].getOriginatingAddress();
                    System.out.println(number);
                    // str += " :";
                    // str += msgs[i].getMessageBody().toString();
                    // str += "\n";
                    sms = msgs[i].getMessageBody().toString();

                }
                //Start new activity to play sound.
                if(db.searchBL(number)) {
                    Intent i = new Intent();
                    i.putExtra("SMSMessage", sms);
                    i.setClass(context, SMSProcessor.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        }
    }
}
