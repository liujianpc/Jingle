package com.example.smsapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<Message> {
    int resourceId;

    public MessageAdapter(Context context, int textViewResourceId,
                          List<Message> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Message message = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder.message_leftLayout = (LinearLayout) view
                    .findViewById(R.id.message_leftLayout);
            viewHolder.message_leftTextView = (TextView) view
                    .findViewById(R.id.left_message);
            viewHolder.message_rightLayout = (LinearLayout) view
                    .findViewById(R.id.message_rightLayout);
            viewHolder.message_rightTextView = (TextView) view
                    .findViewById(R.id.right_message);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }
        if (message.getType() == Message.TYPE_RECEIVED) {
            viewHolder.message_leftLayout.setVisibility(View.VISIBLE);
            viewHolder.message_rightLayout.setVisibility(View.GONE);
            viewHolder.message_leftTextView
                    .setText(message.getMessageContent());

        } else if (message.getType() == Message.TYPE_SENDED) {
            viewHolder.message_leftLayout.setVisibility(View.GONE);
            viewHolder.message_rightLayout.setVisibility(View.VISIBLE);
            viewHolder.message_rightTextView.setText(message
                    .getMessageContent());

        }

        return view;
    }

    class ViewHolder {
        LinearLayout message_leftLayout;
        TextView message_leftTextView;
        LinearLayout message_rightLayout;
        TextView message_rightTextView;
    }

}
