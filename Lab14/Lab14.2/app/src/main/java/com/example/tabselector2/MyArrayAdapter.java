package com.example.tabselector2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Item> {

    private int resourceLayout;
    private Context mContext;

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resourceLayout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtMaso = convertView.findViewById(R.id.txtmaso);
            viewHolder.txtTieude = convertView.findViewById(R.id.txttieude); // Rất quan trọng: Tìm đúng ID
            viewHolder.btnLike = convertView.findViewById(R.id.btnlike);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Item item = getItem(position);

        if (item != null) {
            viewHolder.txtMaso.setText(item.getMaSo());
            viewHolder.txtTieude.setText(item.getTieuDe()); // Rất quan trọng: Gán tên bài hát vào TextView này

            // Gán icon dựa trên trạng thái like
            if (item.getLike() == 1) { // Nếu đã like
                viewHolder.btnLike.setImageResource(R.drawable.liked); // Icon trái tim đỏ
            } else { // Nếu chưa like
                viewHolder.btnLike.setImageResource(R.drawable.unlike); // Icon trái tim rỗng
            }

            // Xử lý sự kiện click cho nút like/unlike
            viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getLike() == 1) {
                        item.setLike(0); // Bỏ thích
                        Toast.makeText(mContext, "Đã bỏ thích: " + item.getTieuDe(), Toast.LENGTH_SHORT).show();
                    } else {
                        item.setLike(1); // Thích
                        Toast.makeText(mContext, "Đã thích: " + item.getTieuDe(), Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged(); // Cập nhật lại ListView để icon thay đổi
                }
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtMaso;
        TextView txtTieude; // Đảm bảo có TextView này trong ViewHolder
        ImageButton btnLike;
    }
}