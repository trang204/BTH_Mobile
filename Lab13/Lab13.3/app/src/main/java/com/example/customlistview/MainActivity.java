package com.example.customlistview;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv; // Khai báo ListView
    ArrayList<Phone> mylist; // Khai báo mảng chính
    MyArrayAdapter myadapter; // Khai báo Adapter

    String namephone[] = {
            "Điện thoại iPhone 12",
            "Điện thoại Samsung S20",
            "Điện thoại Nokia 6",
            "Điện thoại Bphone 2020",
            "Điện thoại Oppo 5",
            "Điện thoại VSmart Joy2"};
    int imagephone[] = {R.drawable.ip, R.drawable.samsung, R.drawable.cellphone, R.drawable.htc,
            R.drawable.lg, R.drawable.sky, R.drawable.wp}; // Mảng chứa tên và hình ảnh điện thoại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Thiết lập layout chính

        lv = findViewById(R.id.lv); // Ánh xạ ListView từ layout

        mylist = new ArrayList<>(); // Khởi tạo ArrayList
        for (int i = 0; i < namephone.length; i++) {
            mylist.add(new Phone(namephone[i], imagephone[i])); // Thêm dữ liệu vào ArrayList
        }

        myadapter = new MyArrayAdapter(this, R.layout.layout_listview, mylist); // Khởi tạo Adapter
        lv.setAdapter(myadapter); // Thiết lập Adapter cho ListView

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Khi một item được click
                Intent myIntent = new Intent(MainActivity.this, SubActivity.class); // Tạo Intent để gọi SubActivity
                myIntent.putExtra("namephone", namephone[position]); // Gửi tên điện thoại qua Intent
                startActivity(myIntent); // Bắt đầu SubActivity
            }
        });
    }
}