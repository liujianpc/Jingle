package com.example.smsapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySmsAdapter extends ArrayAdapter<SmsItem> {
    private int resorcesId;

    public MySmsAdapter(Context context, int textViewResourceId,
                        List<SmsItem> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        resorcesId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        SmsItem smsItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resorcesId, null);
            viewHolder = new ViewHolder();
            viewHolder.smsContenTextView = (TextView) view
                    .findViewById(R.id.sms_content);
            viewHolder.smsTimeTextView = (TextView) view
                    .findViewById(R.id.sms_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.smsContenTextView.setText(smsItem.getSmsContent());
        viewHolder.smsTimeTextView.setText(smsItem.getSmsTime());
        return view;
    }

    class ViewHolder {
        TextView smsContenTextView;
        TextView smsTimeTextView;
    }

}
