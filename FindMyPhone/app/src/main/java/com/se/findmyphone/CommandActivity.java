package com.se.findmyphone;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CommandActivity extends ActionBarActivity {

   /* SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();*/
    Button setCommand;
    TextView comm;
    EditText et_keyword;
    String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        comm = (TextView) findViewById(R.id.comm_edt);
        et_keyword = (EditText) findViewById(R.id.comm_edt);
        setCommand = (Button) findViewById(R.id.btn_set);

        SharedPreferences settings = getSharedPreferences("KeywordStorage",0); //Open SharedPreferences file named "KeywordStorage". If it does not exist, create it.
        keyword = settings.getString("KeyWord", "FindMyPhone");
        et_keyword.setText(keyword);

        setCommand.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) //events inside this method occur in sequence when the button is clicked.
            {
                // TODO Auto-generated method stub

                keyword = et_keyword.getText().toString();    //Store the string in the EditText box 'et' in the String variable 'keyword'


                //Store the freshly set keyword in SharedPrefernces
                SharedPreferences settings = getSharedPreferences("KeywordStorage", 0); //Open the same SharedPreferences file as above
                SharedPreferences.Editor editor = settings.edit(); //call the edit method that returns to the editor instance
                editor.putString("KeyWord", keyword); //Open the string with the key/name - "KeyWord". If it doesn't exist, create it. Put the value of the variable "keyword" in it.
                editor.commit(); //commit or save the edit

                //Toast to show new keyword has been set
                Toast toast = Toast.makeText(CommandActivity.this, "Key Phrase '" + keyword + "' has been set.", Toast.LENGTH_LONG);
                toast.show();

            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_command, menu);
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
