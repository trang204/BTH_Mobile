package com.example.demor_cyclefile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.Activity; // Hoặc androidx.appcompat.app.AppCompatActivity nếu bạn sử dụng AndroidX
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity { // Hoặc AppCompatActivity
    Button btncall;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(this, "CR424 - onDestroy()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Toast.makeText(this, "CR424 - onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Toast.makeText(this, "CR424 - onRestart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Toast.makeText(this, "CR424 - onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Toast.makeText(this, "CR424 - onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Toast.makeText(this, "CR424 - onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Giả định layout file là activity_main.xml

        Toast.makeText(this, "CR424 - onCreate()", Toast.LENGTH_SHORT).show();

        btncall = findViewById(R.id.btncall);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(MainActivity.this, Subactivity.class);
                startActivity(intent1);
            }
        });
    }
}