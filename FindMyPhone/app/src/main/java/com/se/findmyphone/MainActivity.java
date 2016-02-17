package com.se.findmyphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    int counter = 0;
    String passcode = "";
    TextView pass1, pass2, pass3, pass4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Toast.makeText(MainActivity.this, passcode, Toast.LENGTH_SHORT).show();
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
                if (passcode.equals("1234")) {
                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Wohoo", Toast.LENGTH_SHORT).show();
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
