package com.example.smsapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SmsActivity extends Activity {
    private List<Message> messages = new ArrayList<Message>();
    private EditText inputText;
    private ImageButton sendButton;
    private MessageAdapter adapter;
    private ListView listView;
    IntentFilter intentFilter;
    SendStatusReceiver receiver;


    public static void actionStart(Context context, String smsAddress) {
        Intent intent = new Intent(context, SmsActivity.class);
        intent.putExtra("address", smsAddress);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sms);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SENT_SMS_ACTION");
        receiver = new SendStatusReceiver();
        registerReceiver(receiver, intentFilter);
        Intent intent = getIntent();
        final String address = intent.getStringExtra("address");

        initMessages(address);
        adapter = new MessageAdapter(SmsActivity.this, R.layout.message_item,
                messages);
        listView = (ListView) findViewById(R.id.message_listView);
        listView.setAdapter(adapter);
        /*
         * if (!((EditText)
		 * findViewById(R.id.message_edit)).getText().toString().equals("")) {
		 * ((Button)
		 * findViewById(R.id.send_button)).setBackgroundColor(Color.parseColor
		 * ("#000000")); }
		 */
        sendButton = (ImageButton) findViewById(R.id.send_button);
        inputText = (EditText) findViewById(R.id.message_edit);
        sendButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String messageContent = inputText.getText().toString();
                if (!messageContent.equals("")) {


                    messages.add(new Message(messageContent,
                            Message.TYPE_SENDED));
                    adapter.setNotifyOnChange(true);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(messages.size());
                    inputText.setText("");
                    Intent intent_send = new Intent("SENT_SMS_ACTION");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(SmsActivity.this, 0, intent_send, 0);
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(address, null, messageContent, pendingIntent, null);

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void initMessages(String address) {
        /*
         * Message messageRecv_1 = new Message("今晚约吗?", Message.TYPE_RECEIVED);
		 * Message messageSend_1 = new Message("万水千山总是情！便宜一点行不行！",
		 * Message.TYPE_SENDED); Message messageRecv_2 = new Message("不行，不行！",
		 * Message.TYPE_RECEIVED); Message messageSend_2 = new
		 * Message("那就不约了！不约了！", Message.TYPE_SENDED);
		 * messages.add(messageRecv_1); messages.add(messageSend_1);
		 * messages.add(messageRecv_2); messages.add(messageSend_2);
		 */
        Cursor cursor = null;
        try {
            Uri smsUri = Uri.parse("content://sms/");
            //String addresString ="("+address+","+"+86"+address+")";
            cursor = this.getContentResolver()
                    .query(smsUri,
                            new String[]{"_id,address,person,date,read,status,type,body"},
                            "address = ? or address = ?", new String[]{address, "+86" + address}, "date asc");
            while (cursor.moveToNext()) {
                String smsBody = cursor
                        .getString(cursor.getColumnIndex("body"));
                int smsType = cursor.getInt(cursor.getColumnIndex("type"));
                Message message = new Message(smsBody, smsType);
                messages.add(message);

            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("exception", e.getMessage().toString());
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                    Log.e("exception2", e2.getMessage().toString());
                }
            }
        }

    }

    class SendStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                ToastUtil.showToast(SmsActivity.this, "发送成功");
            } else {
                ToastUtil.showToast(SmsActivity.this, "发送失败");
            }

        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
