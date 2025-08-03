package com.example.customgridview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View; // Import View
import android.widget.Button; // Import Button
import android.widget.ImageView;
import android.widget.TextView;

public class SubActivity extends Activity {
    private Bundle extra;
    private TextView txtname2;
    private ImageView img2;
    private Button backButton; // Khai báo Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childlayout);

        txtname2 = findViewById(R.id.textView2);
        img2 = findViewById(R.id.imageView2);
        backButton = findViewById(R.id.backButton); // Ánh xạ Button

        extra = getIntent().getExtras();
        int position = extra.getInt("TITLE");

        txtname2.setText(MainActivity.arrayName[position]);
        img2.setImageResource(MainActivity.imageName[position]);

        // Thiết lập OnClickListener cho nút Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc SubActivity và quay về Activity trước đó
            }
        });
    }
}
