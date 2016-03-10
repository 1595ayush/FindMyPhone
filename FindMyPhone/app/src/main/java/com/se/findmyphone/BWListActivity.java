package com.se.findmyphone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BWListActivity extends ActionBarActivity {
    private final int PICK_CONTACT = 1;
    private DbHelper db;
    private ArrayList<ContactList> all;
    private ListView listView;
    private MyAdapter myAdapter;
    private String conName,conNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bwlist);
        listView = (ListView) findViewById(R.id.lv);
        db = new DbHelper(this);
        all = db.getBList();
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                conName = ((TextView) findViewById(R.id.name)).getText().toString();
                conNo = ((TextView) findViewById(R.id.no)).getText().toString();
                db.deleteBL(conName, conNo);
                all.remove(position);
                myAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    class ViewHolder
    {
        TextView noTxtView,nameTxtView;
    }
    class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return all.size();
        }

        @Override
        public Object getItem(int position) {
            return all.get(position);
        }

        @Override
        public long getItemId(int position) {
            return(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null)
            {
                holder = new ViewHolder();
                LayoutInflater li = getLayoutInflater(); //to convert xml to java object
                convertView = li.inflate(R.layout.listitem, null);
                holder.nameTxtView = (TextView) convertView.findViewById(R.id.name);
                holder.noTxtView = (TextView) convertView.findViewById(R.id.no);
                //associate the holder object with the convert view
                convertView.setTag(holder);
            }
            else
            {
                //convert view is not null (got from the recycler)
                //get the holder object from the reusable convert view
                holder = (ViewHolder) convertView.getTag();
            }
            ContactList all1 = (ContactList) getItem(position);
            holder.nameTxtView.setText(all1.getName());
            holder.noTxtView.setText(all1.getNo());
            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bwlist, menu);
        return true;
    }
    public void opencontacts(View v)
    {
        Intent intent= new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if(reqCode == PICK_CONTACT)
            if (resultCode == ActionBarActivity.RESULT_OK) {
                Uri contactData = data.getData();

                Cursor c = getContentResolver().query(contactData, null, null, null, null);
                if (c.moveToFirst()) {
                    String number = null;
                    String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactId =
                            c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phones = getContentResolver().query(Phone.CONTENT_URI, null,
                            Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        number = phones.getString(phones.getColumnIndex(Phone.NUMBER));

                    }
                    phones.close();
                    if (db.searchBL(number)&&db.searchEmer(number)) {

                        db.insertBL(name, number);
                        all.add(new ContactList(name, number));
                        myAdapter.notifyDataSetChanged();
                    }
                    else
                        Toast.makeText(this, name +" "+ number+" already exists.", Toast.LENGTH_LONG).show();
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