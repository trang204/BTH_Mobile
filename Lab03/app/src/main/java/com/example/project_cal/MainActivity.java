package com.example.project_cal;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText edt1, edt2, edt3;
    Button btncong, btntru, btnnhan, btnchia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Đảm bảo rằng layout XML của bạn có tên là activity_main.xml

        edt1 = findViewById(R.id.edta); // id từ layout XML
        edt2 = findViewById(R.id.edtb); // id từ layout XML
        edt3 = findViewById(R.id.edtc); // id từ layout XML

        btncong = findViewById(R.id.btncong);
        btntru = findViewById(R.id.btntru);
        btnnhan = findViewById(R.id.btnnhan);
        btnchia = findViewById(R.id.btnchia);

        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int a = Integer.parseInt("0" + edt1.getText());
                int b = Integer.parseInt("0" + edt2.getText());
                edt3.setText("a + b = " + (a + b));
            }
        });

        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int a = Integer.parseInt("0" + edt1.getText());
                int b = Integer.parseInt("0" + edt2.getText());
                edt3.setText("a - b = " + (a - b));
            }
        });

        btnnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int a = Integer.parseInt("0" + edt1.getText());
                int b = Integer.parseInt("0" + edt2.getText());
                edt3.setText("a * b = " + (a * b));
            }
        });

        btnchia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int a = Integer.parseInt("0" + edt1.getText());
                int b = Integer.parseInt("0" + edt2.getText());
                if (b == 0) {
                    edt3.setText("B phai khac 0");
                } else {
                    edt3.setText("a / b = " + ((double) a / b)); // Ép kiểu sang double để có kết quả chính xác cho phép chia
                }
            }
        });
    }
}