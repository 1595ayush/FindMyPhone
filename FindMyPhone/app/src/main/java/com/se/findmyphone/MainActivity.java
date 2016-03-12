package com.se.findmyphone;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    int counter = 0,i=2;
    String passcode = "";
    TextView pass1, pass2, pass3, pass4;
    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences("KeywordStorage", 0);
        boolean check = settings.getBoolean("checked",false);
        System.out.println("check=" +check );
        if(check)
        setContentView(R.layout.activity_main);
        else
        {
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void password(View v) {
        TextView textView = (TextView) findViewById(v.getId());
        String text = textView.getText().toString();
        pass1 = (TextView) findViewById(R.id.pass1);
        pass2 = (TextView) findViewById(R.id.pass2);
        pass3 = (TextView) findViewById(R.id.pass3);
        pass4 = (TextView) findViewById(R.id.pass4);
        if (counter <= 4) {
            passcode = passcode + text;
            counter++;
            if (counter == 1)
                pass1.setBackgroundResource(R.drawable.circle3);
            else if (counter == 2)
                pass2.setBackgroundResource(R.drawable.circle3);
            else if (counter == 3)
                pass3.setBackgroundResource(R.drawable.circle3);
            else if (counter == 4)
                pass4.setBackgroundResource(R.drawable.circle3);
            if (counter == 4) {
                String passSP =  settings.getString("pass","");
                if (passcode.equals(passSP)) {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    passcode = "";
                    counter = 0;
                    pass1.setBackgroundResource(R.drawable.circle2);
                    pass2.setBackgroundResource(R.drawable.circle2);
                    pass3.setBackgroundResource(R.drawable.circle2);
                    pass4.setBackgroundResource(R.drawable.circle2);
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
