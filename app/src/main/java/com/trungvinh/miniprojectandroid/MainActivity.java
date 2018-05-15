package com.trungvinh.miniprojectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView search, recentSearch, bookmark, saveLocation, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get id
        search = (CardView) findViewById(R.id.cardView_search);
        recentSearch = (CardView) findViewById(R.id.cardView_recentSearch);
        bookmark = (CardView) findViewById(R.id.cardView_bookmark);
        saveLocation = (CardView) findViewById(R.id.cardView_saveLocation);
        about = (CardView) findViewById(R.id.cardView_about);

        // set onClick
        search.setOnClickListener(this);
        recentSearch.setOnClickListener(this);
        bookmark.setOnClickListener(this);
        saveLocation.setOnClickListener(this);
        about.setOnClickListener(this);

        // Start service location
        startService(new Intent(this, LocationService.class));
        com.trungvinh.miniprojectandroid.NoteManager.initNoteManager(this);
        com.trungvinh.miniprojectandroid.CurrentSearchManager.initCurrentSearchManager(this);
        com.trungvinh.miniprojectandroid.BookmarkManager.initBookmarkManager(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;

        switch (v.getId()) {
            case R.id.cardView_search:
                i = new Intent(this, Search.class);
                break;
            case R.id.cardView_recentSearch:
                i = new Intent(this, RecentSearch.class);
                break;
            case R.id.cardView_bookmark:
                i = new Intent(this, Bookmark.class);
                break;
            case R.id.cardView_saveLocation:
                i = new Intent(this, SaveLocation.class);
                break;
            case R.id.cardView_about:
                i = new Intent(this, About.class);
                break;
            default:
                break;
        }
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), LocationService.class));
        com.trungvinh.miniprojectandroid.NoteManager.UpdateFile(this);
        com.trungvinh.miniprojectandroid.CurrentSearchManager.UpdateFile(this);
        com.trungvinh.miniprojectandroid.BookmarkManager.UpdateFile(this);
    }
}
