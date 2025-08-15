package com.example.nguyenhoanganhtuan_2121110255;

import com.example.nguyenhoanganhtuan_2121110255.R;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerProducts;
    ProductAdapter adapter;
    ArrayList<Product> list, filteredList;
    EditText edtSearch;
    ImageView btnSearch;
    Button btnLogout;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerProducts = findViewById(R.id.recyclerProducts);
        edtSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnLogout = findViewById(R.id.btnLogout);
        bottomNav = findViewById(R.id.bottomNav);

        recyclerProducts.setLayoutManager(new GridLayoutManager(this, 2));

        // Dữ liệu mẫu
        list = new ArrayList<>();
        list.add(new Product("Laptop MSI", 15500000, R.drawable.laptop_msi));
        list.add(new Product("Lenovo Laptop", 54490000, R.drawable.lptop));
        list.add(new Product("SamSung Galaxy", 32490000, R.drawable.samsung_galaxy));
        list.add(new Product("ASIphone 16", 27390000, R.drawable.iphone16));

        filteredList = new ArrayList<>(list);
        adapter = new ProductAdapter(this, filteredList);
        recyclerProducts.setAdapter(adapter);

        // Xử lý tìm kiếm khi bấm icon kính lúp
        btnSearch.setOnClickListener(v -> searchProduct());

        // Tìm kiếm khi nhập chữ
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                searchProduct();
            }
        });
        // Đăng xuất
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        // Bottom navigation
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(this, "Trang chủ", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_cart:
                    Toast.makeText(this, "Giỏ hàng", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_account:
                    Toast.makeText(this, "Tài khoản", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
    }
    // Hàm tìm kiếm
    private void searchProduct() {
        String keyword = edtSearch.getText().toString().trim().toLowerCase();
        filteredList.clear();
        for (Product p : list) {
            if (p.name.toLowerCase().contains(keyword)) {
                filteredList.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }
    }