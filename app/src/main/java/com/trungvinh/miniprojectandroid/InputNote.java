package com.trungvinh.miniprojectandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by TrungVinh on 5/15/2018.
 */

public class InputNote extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputnote);

        ((Button) findViewById(R.id.btn_saveNote)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = ((EditText) findViewById(R.id.edt_textNoteReceive)).getText().toString();
                final Intent data = new Intent();
                data.putExtra("note", note);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
