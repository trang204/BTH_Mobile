package com.example.karaoke;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase database = null;
    EditText edtSearch;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter adapter1, adapter2, adapter3;
    TabHost tabHost;
    ImageButton btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tạo database và dữ liệu mẫu
        createSampleData();

        addControl();
        addEvents();

        // Load dữ liệu ban đầu
        loadSearchData();
    }

    private void addControl() {
        // Setup TabHost
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        // Tab 1 - Search
        TabHost.TabSpec spec1 = tabHost.newTabSpec("search");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("🔍");
        tabHost.addTab(spec1);

        // Tab 2 - List
        TabHost.TabSpec spec2 = tabHost.newTabSpec("list");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("📋");
        tabHost.addTab(spec2);

        // Tab 3 - Favourite
        TabHost.TabSpec spec3 = tabHost.newTabSpec("favourite");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("❤️");
        tabHost.addTab(spec3);

        // Set tab mặc định
        tabHost.setCurrentTab(0);

        // Initialize views
        edtSearch = findViewById(R.id.edt_search);
        btnDelete = findViewById(R.id.btn_delete);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        // Initialize lists and adapters
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        adapter1 = new myarrayAdapter(this, R.layout.listitem, list1);
        adapter2 = new myarrayAdapter(this, R.layout.listitem, list2);
        adapter3 = new myarrayAdapter(this, R.layout.listitem, list3);

        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
    }

    private void addEvents() {
        // Tab change listener
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "search":
                        loadSearchData();
                        break;
                    case "list":
                        loadAllSongs();
                        break;
                    case "favourite":
                        loadFavouriteSongs();
                        break;
                }
            }
        });

        // Delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
            }
        });

        // Search functionality
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSongs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadSearchData() {
        list1.clear();
        try {
            Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE TENBH LIKE '%yeu em%' ORDER BY MABH", null);
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    String title = c.getString(1);
                    int like = c.getInt(2);
                    list1.add(new Item(title, id, like));
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi load search: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adapter1.notifyDataSetChanged();
    }

    private void searchSongs(String keyword) {
        list1.clear();
        try {
            if (!keyword.isEmpty()) {
                Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH LIKE '%" + keyword + "%' OR TENBH LIKE '%" + keyword + "%' ORDER BY MABH", null);
                if (c.moveToFirst()) {
                    do {
                        String id = c.getString(0);
                        String title = c.getString(1);
                        int like = c.getInt(2);
                        list1.add(new Item(title, id, like));
                    } while (c.moveToNext());
                }
                c.close();
            } else {
                loadSearchData(); // Load default search results
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi tìm kiếm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adapter1.notifyDataSetChanged();
    }

    private void loadAllSongs() {
        list2.clear();
        try {
            Cursor c = database.rawQuery("SELECT * FROM ArirangSongList ORDER BY MABH", null);
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    String title = c.getString(1);
                    int like = c.getInt(2);
                    list2.add(new Item(title, id, like));
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi load all: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adapter2.notifyDataSetChanged();
    }

    private void loadFavouriteSongs() {
        list3.clear();
        try {
            Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH=1 ORDER BY MABH", null);
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    String title = c.getString(1);
                    int like = c.getInt(2);
                    list3.add(new Item(title, id, like));
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi load favourite: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adapter3.notifyDataSetChanged();
    }

    private void createSampleData() {
        try {
            database = openOrCreateDatabase("arirang.sqlite", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS ArirangSongList (MABH TEXT PRIMARY KEY, TENBH TEXT, YEUTHICH INTEGER DEFAULT 0)");

            // Kiểm tra xem đã có dữ liệu chưa
            Cursor c = database.rawQuery("SELECT COUNT(*) FROM ArirangSongList", null);
            c.moveToFirst();
            int count = c.getInt(0);
            c.close();

            if (count == 0) {
                // Thêm dữ liệu mẫu giống hình
                database.execSQL("INSERT INTO ArirangSongList VALUES ('50839', 'CON YÊU EM MÃI', 0)");
                database.execSQL("INSERT INTO ArirangSongList VALUES ('52724', 'CHỈ CẦN ANH YÊU EM LÀ ĐỦ', 1)");
                database.execSQL("INSERT INTO ArirangSongList VALUES ('53018', 'LỞ YÊU EM RỒI', 1)");
                database.execSQL("INSERT INTO ArirangSongList VALUES ('50327', 'YÊU EM BẰNG CẢ TRÁI TIM', 0)");
                database.execSQL("INSERT INTO ArirangSongList VALUES ('50496', 'YÊU EM DÀI LÂU', 0)");
                database.execSQL("INSERT INTO ArirangSongList VALUES ('52315', 'YÊU EM HÀ NỘI', 0)");
                database.execSQL("INSERT INTO ArirangSongList VALUES ('50746', 'YÊU EM MỘT ĐỜI', 0)");
                database.execSQL("INSERT INTO ArirangSongList VALUES ('51164', 'YÊU EM TRỌN ĐỜI', 0)");
                Toast.makeText(this, "Đã tạo dữ liệu mẫu", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi tạo dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}