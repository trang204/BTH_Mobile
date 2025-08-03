package com.example.spinneractivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

// Không cần các import này nếu bạn không sử dụng chúng
// import androidx.activity.EdgeToEdge;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.core.graphics.Insets;
// import androidx.core.view.ViewCompat;
// import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {
    String arr[] = {"Hàng Điện tử", "Hàng Hóa Chất", "Hàng Gia dụng", "Hàng xây dựng"};

    TextView txt1;
    Spinner spin1; // Thêm khai báo Spinner ở đây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (TextView) findViewById(R.id.txt1);
        spin1 = (Spinner) findViewById(R.id.spinner1); // Khởi tạo Spinner ở đây

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_spinner_item, // Bố cục cho mục được chọn hiển thị trong Spinner
                arr);


        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin1.setAdapter(adapter1);

        // Đặt lựa chọn ban đầu cho Spinner khớp với TextView
        int initialSelectionIndex = 0; // "Hàng Điện tử" là mục đầu tiên trong mảng
        spin1.setSelection(initialSelectionIndex);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt1.setText(arr[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}