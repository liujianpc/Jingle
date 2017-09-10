package com.example.smsapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<SmsItem> smsList = new ArrayList<SmsItem>();
    MySmsAdapter smsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsAdapter = new MySmsAdapter(MainActivity.this,
                R.layout.sms_item, smsList);
        ListView smsListView = (ListView) findViewById(R.id.sms_list_view);
        smsListView.setAdapter(smsAdapter);
        getSms();
        smsListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                SmsItem smsItem = smsList.get(arg2);
                String smsAddress = smsItem.getSmsAddress();
                SmsActivity.actionStart(MainActivity.this, smsAddress);


            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getSms() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                try {
                    Uri smsUri = Uri.parse("content://sms/");
                    cursor = getContentResolver()//substr(address,4) as
                            .query(smsUri,
                                    new String[]{"_id,address,person,date,read,status,type,body,count(address) as "
                                            + "totleCount from (select _id,substr(address,4) as address,person,date,read,status,type,body "
                                            + "from sms where address like \"+86%\" union select _id,address,person,date,read,status,type,body "
                                            + "from sms where address not like \"+86%\") r group by r.address order by r.date desc --"},
                                    null, null, null);
                    //int index = cursor.getCount();
                    while (cursor.moveToNext()) {
                        String smsAddressRaw = cursor.getString(cursor
                                .getColumnIndex("address"));
                /*String smsAddress;
                if (smsAddressRaw.contains("+86")) {
					smsAddress = smsAddressRaw.substring(3);
				}
				else {
					 smsAddress = smsAddressRaw;
				}*/
                        String smsPerson = cursor.getString(cursor
                                .getColumnIndex("person"));

                        String smsBody = cursor
                                .getString(cursor.getColumnIndex("body"));
                        String smsTime = cursor
                                .getString(cursor.getColumnIndex("date")) + "";
                        String smsType = cursor
                                .getString(cursor.getColumnIndex("type"));
                /*
                 * SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd");
				 * smsDate = spdf.format(new Date(Long.parseLong(smsDate)));
				 * String smsContent = sendAddress + "\n" + smsBody; SmsItem
				 * smsItem = new SmsItem(smsContent, smsDate);
				 */
                        long timeLong = Long.parseLong(smsTime);
                        SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd");
                        smsTime = spdf.format(new Date(timeLong));

                        smsPerson = getPeople(smsAddressRaw);
                        if (smsPerson.equals("")) {
                            smsPerson = smsAddressRaw;
                        }
                        SmsItem smsItem = new SmsItem(smsBody, smsTime, smsAddressRaw,
                                smsPerson, smsType);
                        smsItem.setSmsContent();
                        smsList.add(smsItem);

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            smsAdapter.notifyDataSetChanged();

                        }
                    });

                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("exception", e.getMessage().toString());
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception e2) {
                            // TODO: handle exception
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public String getPeople(String smsAddress) {
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor = null;
        String name = "";
        try {
            cursor = this.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection, // Which columns to return.
                    ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
                            + smsAddress + "'", // WHERE clause.
                    null, // WHERE clause value substitution
                    null); // Sort order.

            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("display_name"));
            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.d("exception", e.getMessage().toString());
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                    Log.d("Exception2", e2.getMessage().toString());
                }
            }
        }
        return name;

    }

}
