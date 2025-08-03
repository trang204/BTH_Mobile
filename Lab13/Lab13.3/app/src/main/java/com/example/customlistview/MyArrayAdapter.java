package com.example.customlistview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyArrayAdapter extends ArrayAdapter<Phone> {
    private Activity context;
    private int idlayout;
    private ArrayList<Phone> mylist;

    // Tạo Constructor để MainActivity gọi đến và truyền các tham số
    public MyArrayAdapter(Activity context, int idlayout, ArrayList<Phone> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = mylist;
    }

    // Gọi đến hàm getView để xây dựng lại Adapter
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = context.getLayoutInflater();
        convertView = myInflater.inflate(idlayout, null); // inflate layout_listview.xml

        // Ánh xạ một đối tượng và thiết lập dữ liệu
        ImageView imgphone = convertView.findViewById(R.id.imgphone);
        TextView txtnamephone = convertView.findViewById(R.id.txtnamephone);

        Phone phone = mylist.get(position); // Lấy đối tượng Phone tương ứng với vị trí

        imgphone.setImageResource(phone.getImagephone()); // Thiết lập ảnh
        txtnamephone.setText(phone.getNamephone()); // Thiết lập tên điện thoại

        return convertView;
    }
}
