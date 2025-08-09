package com.example.tabselector2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.graphics.drawable.Drawable; // Cần thiết cho getResources().getDrawable()

import java.util.ArrayList;
import java.util.Objects; // Để sử dụng Objects.requireNonNull (tùy chọn)

public class MainActivity extends Activity {

    EditText edttim;
    ListView lv1, lv2, lv3;
    TabHost tab;
    ArrayList<Item> list1, list2, list3;
    MyArrayAdapter myarray1, myarray2, myarray3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl(); // Khởi tạo các view
        addEvent();   // Thiết lập sự kiện và tải dữ liệu ban đầu
    }

    // Phương thức để tải dữ liệu cho từng tab
    private void loadDataForTab(String tabId) {
        if (tabId.equalsIgnoreCase("t1")) {
            list1.clear();
            list1.add(new Item("52300", "Em là ai Tôi là ai", 0)); // 0 là unlike
            list1.add(new Item("52600", "Bài ca đất Phương Nam", 0));
            list1.add(new Item("52567", "Buồn của Anh", 1)); // 1 là liked
            myarray1.notifyDataSetChanged();
        } else if (tabId.equalsIgnoreCase("t2")) {
            list2.clear();
            list2.add(new Item("57236", "Gởi em ở cuối sông hồng", 0));
            list2.add(new Item("51548", "Quê hương tuổi thơ tôi", 1));
            list2.add(new Item("51748", "Em gì ơi", 0));
            myarray2.notifyDataSetChanged();
        } else if (tabId.equalsIgnoreCase("t3")) {
            list3.clear();
            list3.add(new Item("57689", "Hát với dòng sông", 1));
            list3.add(new Item("58716", "Say tình – Remix", 0));
            list3.add(new Item("58916", "Người hãy quên em đi", 1));
            myarray3.notifyDataSetChanged();
        }
    }

    private void addEvent() {
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                loadDataForTab(tabId); // Tải dữ liệu khi tab thay đổi
            }
        });

        // Thiết lập tab mặc định khi khởi động ứng dụng và tải dữ liệu cho tab đó
        tab.setCurrentTabByTag("t1");
        loadDataForTab("t1"); // Tải dữ liệu ban đầu cho tab 1
    }

    private void addControl() {
        tab = findViewById(R.id.tabhost);
        tab.setup();

        // --- Thiết lập các TabSpec ---
        // Lưu ý: Các icon này sẽ là màu đen trên nền tím nếu bạn không tạo Style cho TabWidget
        // Để có icon màu trắng, bạn cần đảm bảo các drawable (search, list, favourite) là màu trắng.
        // Hoặc bạn cần custom indicator view (phức tạp hơn).

        // Tab 1: Icon tìm kiếm
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        Drawable searchIcon = getResources().getDrawable(R.drawable.search); // Đảm bảo có `res/drawable/search.xml`
        searchIcon.setBounds(0, 0, searchIcon.getIntrinsicWidth(), searchIcon.getIntrinsicHeight());
        tab1.setIndicator("", searchIcon);
        tab.addTab(tab1);

        // Tab 2: Icon danh sách
        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        Drawable listIcon = getResources().getDrawable(R.drawable.list); // Đảm bảo có `res/drawable/list.xml`
        listIcon.setBounds(0, 0, listIcon.getIntrinsicWidth(), listIcon.getIntrinsicHeight());
        tab2.setIndicator("", listIcon);
        tab.addTab(tab2);

        // Tab 3: Icon yêu thích
        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        Drawable favoriteIcon = getResources().getDrawable(R.drawable.favourite); // Đảm bảo có `res/drawable/favourite.xml`
        favoriteIcon.setBounds(0, 0, favoriteIcon.getIntrinsicWidth(), favoriteIcon.getIntrinsicHeight());
        tab3.setIndicator("", favoriteIcon);
        tab.addTab(tab3);

        // --- Liên kết các View khác ---
        edttim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        // --- Khởi tạo ArrayLists ---
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        // --- Khởi tạo MyArrayAdapters ---
        myarray1 = new MyArrayAdapter(this, R.layout.listitem, list1);
        myarray2 = new MyArrayAdapter(this, R.layout.listitem, list2);
        myarray3 = new MyArrayAdapter(this, R.layout.listitem, list3);

        // --- Gán Adapters cho ListViews ---
        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }
}