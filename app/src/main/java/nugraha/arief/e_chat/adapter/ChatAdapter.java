package nugraha.arief.e_chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import nugraha.arief.e_chat.R;
import nugraha.arief.e_chat.pojo.Chat;

/**
 * Created by farhan on 2/13/17.
 */

public class ChatAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Chat> orderItems;

    public ChatAdapter(Activity activity, List<Chat> orderItems) {
        this.activity = activity;
        this.orderItems = orderItems;
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int location) {
        return orderItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.adapter_chat, null);

        TextView title = (TextView) convertView.findViewById(R.id.textName);
        TextView chat = (TextView) convertView.findViewById(R.id.textChat);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linLayAdapter);
        View viewLeft = (View) convertView.findViewById(R.id.viewLeft);
        View viewRight = (View) convertView.findViewById(R.id.viewRight);

        // getting movie data for the row
        Chat m = orderItems.get(position);
        title.setText(m.getName());
        chat.setText(m.getChat());
        if(m.getName().equals("Saya")){
            viewLeft.setVisibility(View.GONE);
            viewRight.setVisibility(View.VISIBLE);
            linearLayout.setBackgroundResource(R.drawable.bg_chats_left);
        }else{
            viewLeft.setVisibility(View.VISIBLE);
            viewRight.setVisibility(View.GONE);
            linearLayout.setBackgroundResource(R.drawable.bg_chats_right);
        }

        return convertView;
    }

}