package com.example.customgridview;

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

public class MyArrayAdapter extends ArrayAdapter<Image> {
    private Activity context;
    private int Layoutid;
    private ArrayList<Image> myArray;

    // Constructor này dùng để khởi tạo các giá trị
    // @param context: là Activity truyền vào
    // @param layoutId: là layout custom do ta tao (listitem.xml)
    // @param arr: Danh sách đối tượng truyền từ Main
    public MyArrayAdapter(Activity context, int Layoutid, ArrayList<Image> arr) {
        super(context, Layoutid, arr);
        this.context = context;
        this.Layoutid = Layoutid;
        this.myArray = arr;
    }

    // Gọi đến hàm getView để xây dựng lại Adapter
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(Layoutid, null);

        ImageView imgItem = convertView.findViewById(R.id.imageView1); // Ánh xạ ImageView
        TextView txtName = convertView.findViewById(R.id.textView1); // Ánh xạ TextView

        Image myImage = myArray.get(position); // Lấy đối tượng Image
        imgItem.setImageResource(myImage.getImg()); // Thiết lập hình ảnh
        txtName.setText(myImage.getName()); // Thiết lập tên ảnh

        return convertView;
    }
}