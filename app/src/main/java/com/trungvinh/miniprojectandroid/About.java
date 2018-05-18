package com.trungvinh.miniprojectandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by TrungVinh on 5/13/2018.
 */

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implement);

        ((TextView)findViewById(R.id.aboutText)).setText("Miniproject1\n" +
                "Tên: Bùi Phúc Trung Vĩnh\n" +
                "MSSV: 1512677\n" +
                "Ứng dụng dùng để tìm kiếm và ghi chú một địa điểm");
    }
}
