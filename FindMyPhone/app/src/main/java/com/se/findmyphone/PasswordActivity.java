package com.se.findmyphone;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class PasswordActivity extends ActionBarActivity {

    EditText pass1,pass2;
    boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        pass1=(EditText)findViewById(R.id.pass1_edt);
        pass2=(EditText)findViewById(R.id.pass2_edt);
        CheckBox cb = (CheckBox) findViewById (R.id.enable_ckbx);
        cb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) findViewById(R.id.set_btn);
                //is chkIos checked?
                checked = ((CheckBox) v).isChecked();
                if (((CheckBox) v).isChecked()) {
                    b.setEnabled(true);
                }
                else
                {
                    b.setEnabled(false);
                }

            }
        });
    }
    public void onPassSubmit(View v)
    {
        String passVal1 = pass1.getText().toString();
        String passVal2 = pass2.getText().toString();
        if(passVal1.equals(passVal2) && passVal1.length()==4)
        {
            SharedPreferences settings = getSharedPreferences("KeywordStorage", 0);
            //checked = settings.getBoolean("checked", false);
            SharedPreferences.Editor editor = settings.edit(); //call the edit method that returns to the editor instance
            editor.putBoolean("checked", true); //Open the string with the key/name - "KeyWord". If it doesn't exist, create it. Put the value of the variable "keyword" in it.
            editor.putString("pass", passVal1);
            editor.commit();
            Toast.makeText(PasswordActivity.this,"Password set.",Toast.LENGTH_SHORT).show();

        }
        else if(passVal1.length()!=4 || passVal2.length()!=4 )
        {
            Toast.makeText(PasswordActivity.this,"Password should be 4 characters long.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(PasswordActivity.this,"Passwords do not match.",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password, menu);
        return true;
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
