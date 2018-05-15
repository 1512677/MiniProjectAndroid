package com.trungvinh.miniprojectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TrungVinh on 5/16/2018.
 */

public class CustomAdapterCurrent extends BaseAdapter {
    Context context;
    ArrayList<String> mName;
    ArrayList<String> mAddress;
    LayoutInflater inflter;

    public CustomAdapterCurrent(Context applicationContext, ArrayList<String> Name, ArrayList<String> Address) {
        this.context = applicationContext;
        this.mName = Name;
        this.mAddress = Address;
        this.inflter = (LayoutInflater.from(applicationContext));
    }

    public void clearData() {
        mName.clear();
        mAddress.clear();
    }

    @Override
    public int getCount() {
        return mName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.listviewitem_current, null);
        TextView tvi_current_name = (TextView) convertView.findViewById(R.id.tvi_current_name);
        TextView tvi_current_address = (TextView) convertView.findViewById(R.id.tvi_current_address);
        tvi_current_name.setText(mName.get(position));
        tvi_current_address.setText(mAddress.get(position));
        return convertView;
    }
}
