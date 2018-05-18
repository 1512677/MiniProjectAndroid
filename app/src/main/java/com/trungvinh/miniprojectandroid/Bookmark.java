package com.trungvinh.miniprojectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by TrungVinh on 5/13/2018.
 */

public class Bookmark extends AppCompatActivity {
    private ListView mListView;
    private CustomAdapterBookmark customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        final ArrayList<String> al_current_name = new ArrayList<String>(BookmarkManager.mName.subList(1, BookmarkManager.mName.size()));
        final ArrayList<String> al_current_address = new ArrayList<String>(BookmarkManager.mAddress.subList(1, BookmarkManager.mAddress.size()));

        customAdapter = new CustomAdapterBookmark(getApplicationContext(), al_current_name, al_current_address);
        mListView = ((ListView) findViewById(R.id.lvBookmark));
        mListView.setAdapter(customAdapter);
    }
}
