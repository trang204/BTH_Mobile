package com.example.demor_cyclefile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Subactivity extends AppCompatActivity {
    Button btnok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subactivity); // Giả định layout file là activity_sub.xml

        btnok = findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish(); // Đóng Activity hiện tại
            }
        });
    }
}