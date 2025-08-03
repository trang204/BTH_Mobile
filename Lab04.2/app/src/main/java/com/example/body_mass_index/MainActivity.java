package com.example.body_mass_index;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Khai báo các thành phần UI
    private EditText edtTen;
    private EditText edtChieuCao;
    private EditText edtCanNang;
    private Button btnTinhBMI;
    private EditText edtBMI;
    private EditText edtChuanDoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Gán layout UI

        // Ánh xạ các thành phần UI từ layout XML sang biến Java
        edtTen = findViewById(R.id.edtTen);
        edtChieuCao = findViewById(R.id.edtChieuCao);
        edtCanNang = findViewById(R.id.edtCanNang);
        btnTinhBMI = findViewById(R.id.btnTinhBMI);
        edtBMI = findViewById(R.id.edtBMI);
        edtChuanDoan = findViewById(R.id.edtChuanDoan);

        // Thiết lập sự kiện click cho nút "Tính BMI"
        btnTinhBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinhBMI(); // Gọi hàm tính BMI khi nút được click
            }
        });
    }

    private void tinhBMI() {
        // Lấy dữ liệu từ EditText
        String ten = edtTen.getText().toString().trim();
        String chieuCaoStr = edtChieuCao.getText().toString().trim();
        String canNangStr = edtCanNang.getText().toString().trim();

        // Kiểm tra xem các trường có trống không
        if (ten.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (chieuCaoStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập chiều cao.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (canNangStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập cân nặng.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Chuyển đổi chuỗi sang số thực (float hoặc double)
            float chieuCaoCm = Float.parseFloat(chieuCaoStr);
            float canNangKg = Float.parseFloat(canNangStr);

            // Kiểm tra giá trị hợp lệ
            if (chieuCaoCm <= 0 || canNangKg <= 0) {
                Toast.makeText(this, "Chiều cao và cân nặng phải lớn hơn 0.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển chiều cao từ cm sang mét
            float chieuCaoMet = chieuCaoCm / 100.0f;

            // Tính toán BMI
            float bmi = canNangKg / (chieuCaoMet * chieuCaoMet);

            // Hiển thị kết quả BMI
            edtBMI.setText(String.format("%.2f", bmi)); // Định dạng 2 chữ số thập phân

            // Đưa ra chuẩn đoán
            String chuanDoan = "";
            if (bmi < 18.5) {
                chuanDoan = "Thiếu cân";
            } else if (bmi >= 18.5 && bmi < 24.9) {
                chuanDoan = "Bình thường";
            } else if (bmi >= 25 && bmi < 29.9) {
                chuanDoan = "Thừa cân";
            } else if (bmi >= 30) {
                chuanDoan = "Béo phì";
            }
            edtChuanDoan.setText(chuanDoan);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Chiều cao và cân nặng phải là số hợp lệ.", Toast.LENGTH_SHORT).show();
        }
    }
}