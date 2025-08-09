package com.example.sumof2numbers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edta, edtb, edtkq;
    Button btntong, btnclear;
    TextView txtlichsu;
    String lichsu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        edtkq = findViewById(R.id.edtkq);
        btntong = findViewById(R.id.btntong);
        btnclear = findViewById(R.id.btnclear);
        txtlichsu = findViewById(R.id.txtlichsu);

        // Lấy lịch sử từ SharedPreferences
        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        lichsu = myprefs.getString("ls", "");
        txtlichsu.setText(lichsu);

        // Xử lý nút TỔNG
        btntong.setOnClickListener(v -> {
            String sa = edta.getText().toString();
            String sb = edtb.getText().toString();
            if (sa.isEmpty() || sb.isEmpty()) return;
            int a = Integer.parseInt(sa);
            int b = Integer.parseInt(sb);
            int res = a + b;
            edtkq.setText(String.valueOf(res));
            lichsu += a + " + " + b + " = " + res + "\n";
            txtlichsu.setText(lichsu);
        });

        // Xử lý nút CLEAR
        btnclear.setOnClickListener(v -> {
            lichsu = "";
            txtlichsu.setText(lichsu);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        SharedPreferences.Editor myedit = myprefs.edit();
        myedit.putString("ls", lichsu);
        myedit.apply();
    }
}