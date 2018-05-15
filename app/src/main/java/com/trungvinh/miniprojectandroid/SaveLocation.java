package com.trungvinh.miniprojectandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by TrungVinh on 5/13/2018.
 */

public class SaveLocation extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int INPUT_NOTE_REQUEST = 2;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mIdTextView;
    private TextView mPhoneTextView;
    private TextView mWebTextView;
    private TextView mNoteTextView;
    private TextView mLatLng;
    private ImageButton ibtn_NoteInfoSave;
    private ImageButton ibtn_bookmarkSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savelocation);

        mNameTextView = (TextView) findViewById(R.id.nameSave);
        mAddressTextView = (TextView) findViewById(R.id.addressSave);
        mIdTextView = (TextView) findViewById(R.id.place_idSave);
        mPhoneTextView = (TextView) findViewById(R.id.phoneSave);
        mWebTextView = (TextView) findViewById(R.id.webSave);
        mNoteTextView = (TextView) findViewById(R.id.mynoteSave);
        mLatLng = (TextView) findViewById(R.id.locationSave);
        ibtn_NoteInfoSave = (ImageButton) findViewById(R.id.ibtn_NoteInfoSave);
        ibtn_bookmarkSave = (ImageButton) findViewById(R.id.ibtn_bookmarkSave);

        ibtn_NoteInfoSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SaveLocation.this, InputNote.class);
                startActivityForResult(i, INPUT_NOTE_REQUEST);
            }
        });

        Button pickerButton = (Button) findViewById(R.id.btn_getNearLocation);
        pickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(SaveLocation.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            final Place place = PlacePicker.getPlace(this, data);
            mNameTextView.setText(place.getName().toString());
            mAddressTextView.setText(place.getAddress().toString());
            mIdTextView.setText(place.getId().toString());
            mPhoneTextView.setText(place.getPhoneNumber().toString());
            mWebTextView.setText(place.getWebsiteUri().toString());
            mNoteTextView.setText(NoteManager.getNotebyId(place.getId().toString()));
            mLatLng.setText('(' + Double.toString(place.getLatLng().latitude) + ',' + Double.toString(place.getLatLng().longitude) + ')');
            ((LinearLayout) findViewById(R.id.layout_displaySave)).setVisibility(View.VISIBLE);
        } else if (requestCode == INPUT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            String note = data.getStringExtra("note");
            NoteManager.setNotebyId(mIdTextView.getText().toString(), note);
            mNoteTextView.setText(note);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
