package com.example.customgridview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView; // Khai báo GridView
    ArrayList<Image> myArray; // Khai báo mảng chính
    MyArrayAdapter adapterDanhSach; // Khai báo Adapter

    public static String arrayName[] = {"Ảnh 1", "Ảnh 2", "Ảnh 3", "Ảnh 4", "Ảnh 5", "Ảnh 6",
            "Ảnh 7", "Ảnh 8", "Ảnh 9", "Ảnh 10", "Ảnh 11", "Ảnh 12"};
    public static int imageName[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7,
            R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Thiết lập layout

        gridView = findViewById(R.id.grid1); // Ánh xạ GridView
        myArray = new ArrayList<>(); // Khởi tạo ArrayList

        // Duyệt từng phần tử và gán Data source
        for (int i = 0; i < imageName.length; i++) {
            myArray.add(new Image(imageName[i], arrayName[i]));
        }

        adapterDanhSach = new MyArrayAdapter(this, R.layout.listitem, myArray); // Khởi tạo Adapter
        gridView.setAdapter(adapterDanhSach); // Thiết lập Adapter cho GridView
        adapterDanhSach.notifyDataSetChanged(); // Cập nhật giao diện (nếu dữ liệu thay đổi sau khi thiết lập)


        // Xử lý sự kiện khi click vào đối tượng trong GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, SubActivity.class); // Khai báo Intent để gọi SubActivity
                Bundle bundle = new Bundle(); // Khai báo bundle và đưa dữ liệu vào Bundle
                bundle.putInt("TITLE", position); // Đưa vị trí vào Bundle
                myIntent.putExtras(bundle); // Đưa Bundle vào Intent
                startActivity(myIntent); // Bắt đầu SubActivity
            }
        });
    }
}