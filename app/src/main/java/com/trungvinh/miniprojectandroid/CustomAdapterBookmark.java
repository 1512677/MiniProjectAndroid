package com.trungvinh.miniprojectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TrungVinh on 5/16/2018.
 */

public class CustomAdapterBookmark extends BaseAdapter {
    Context context;
    ArrayList<String> mName;
    ArrayList<String> mAddress;
    LayoutInflater inflter;

    public CustomAdapterBookmark(Context applicationContext, ArrayList<String> Name, ArrayList<String> Address) {
        this.context = applicationContext;
        this.mName = Name;
        this.mAddress = Address;
        this.inflter = (LayoutInflater.from(applicationContext));
    }

    public void removeItem(int i) {
        mName.remove(i);
        mAddress.remove(i);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.listviewitem_bookmark, null);
        TextView tvi_current_name = (TextView) convertView.findViewById(R.id.tvi_bookmark_name);
        TextView tvi_current_address = (TextView) convertView.findViewById(R.id.tvi_bookmark_address);
        tvi_current_name.setText(mName.get(position));
        tvi_current_address.setText(mAddress.get(position));
        // hanlder button delete
        ((Button) convertView.findViewById(R.id.btn_deleteBookmark)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BookmarkManager.removeItem(position + 1);
                removeItem(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
