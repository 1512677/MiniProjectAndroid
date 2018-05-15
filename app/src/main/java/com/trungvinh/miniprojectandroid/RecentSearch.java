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

public class RecentSearch extends AppCompatActivity {
    private ListView mListView;
    private CustomAdapterCurrent customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentsearch);

        final ArrayList<String> al_current_name = new ArrayList<String>(CurrentSearchManager.mName.subList(1, CurrentSearchManager.mName.size()));
        final ArrayList<String> al_current_address = new ArrayList<String>(CurrentSearchManager.mAddress.subList(1, CurrentSearchManager.mAddress.size()));

        customAdapter = new CustomAdapterCurrent(getApplicationContext(), al_current_name, al_current_address);
        mListView = ((ListView) findViewById(R.id.lvCurrent));
        mListView.setAdapter(customAdapter);

        ((Button) findViewById(R.id.btn_deleteCurrent)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSearchManager.initData(RecentSearch.this);
                customAdapter.clearData();
                customAdapter.notifyDataSetChanged();
            }
        });

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(RecentSearch.this, Search.class);
                i.putExtra("calling-activity", 1);
                i.putExtra("calling-name", al_current_name.get(position));
                startActivity(i);
            }
        });
    }
}
