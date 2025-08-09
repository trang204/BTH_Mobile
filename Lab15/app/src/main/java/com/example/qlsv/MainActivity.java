package com.example.qlsv;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtID, edtName, edtNumber;
    Button btnInsert, btnDelete, btnUpdate, btnQuery;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> adapter;
    SQLiteDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần
        edtID = findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);
        edtNumber = findViewById(R.id.edt_number);
        btnInsert = findViewById(R.id.btn_insert);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        btnQuery = findViewById(R.id.btn_query);
        lv = findViewById(R.id.lv);

        mylist = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(adapter);

        // Tạo database nếu chưa có
        database = openOrCreateDatabase("sinhvien.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE IF NOT EXISTS tbllop(malop TEXT PRIMARY KEY, tenlop TEXT, siso INTEGER)";
            database.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String numberStr = edtNumber.getText().toString().trim();
                if (id.isEmpty() || name.isEmpty() || numberStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }
                int number;
                try {
                    number = Integer.parseInt(numberStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Sĩ số phải là số nguyên", Toast.LENGTH_LONG).show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("malop", id);
                values.put("tenlop", name);
                values.put("siso", number);
                String msg;
                if(database.insert("tbllop", null, values) == -1){
                    msg = "Thêm thất bại (trùng mã lớp?)";
                }else{
                    msg = "Thêm thành công";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();
                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nhập mã lớp cần xóa", Toast.LENGTH_LONG).show();
                    return;
                }
                int n = database.delete("tbllop", "malop=?", new String[]{id});
                String msg;
                if(n == 0){
                    msg = "Xóa thất bại (không tìm thấy mã lớp)";
                }else{
                    msg = "Xóa thành công";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();
                String numberStr = edtNumber.getText().toString().trim();
                if (id.isEmpty() || numberStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nhập mã lớp và sĩ số cần cập nhật", Toast.LENGTH_LONG).show();
                    return;
                }
                int number;
                try {
                    number = Integer.parseInt(numberStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Sĩ số phải là số nguyên", Toast.LENGTH_LONG).show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("siso", number);
                int n = database.update("tbllop", values, "malop=?", new String[]{id});
                String msg;
                if(n == 0){
                    msg = "Cập nhật thất bại (không tìm thấy mã lớp)";
                }else {
                    msg = "Cập nhật thành công";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                Cursor cursor = database.query("tbllop", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String data = cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getInt(2);
                        mylist.add(data);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                adapter.notifyDataSetChanged();
            }
        });
    }
}