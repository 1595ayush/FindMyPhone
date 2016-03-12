package com.se.findmyphone;

/**
 * Created by ayush on 17/2/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyCallReceiver extends BroadcastReceiver {
    private DbHelper db;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            db= new DbHelper(context);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            //Toast.makeText(context, "Call from:" +incomingNumber, Toast.LENGTH_LONG).show();
            AudioManager profileCheck = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            if(!db.searchEmer(incomingNumber) && profileCheck.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                Intent i = new Intent(context, AlarmRinger.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("type",1);
                context.startActivity(i);
            }
        }
    }
}
