package com.example.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    TextView txt_subphone; // Khai báo TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub); // Thiết lập layout

        txt_subphone = findViewById(R.id.txt_subphone); // Ánh xạ TextView từ layout

        Intent myIntent = getIntent(); // Lấy Intent đã gửi
        String namephone = myIntent.getStringExtra("namephone"); // Lấy dữ liệu "namephone"
        txt_subphone.setText(namephone); // Đặt tên điện thoại vào TextView
    }
}