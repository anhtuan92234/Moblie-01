package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    BottomNavigationView bottomNav;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            // Chưa đăng nhập -> quay về Login
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerProducts = findViewById(R.id.recyclerProducts);
        edtSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        bottomNav = findViewById(R.id.bottomNav);

        recyclerProducts.setLayoutManager(new GridLayoutManager(this, 2));

        // Dữ liệu mẫu
        list = new ArrayList<>();
        list.add(new Product("Laptop MSI", 22500000, R.drawable.laptop_msi,"Laptop MSI hiệu năng mạnh mẽ, chip Intel thế hệ 13, RAM 16GB, card đồ họa RTX 4060 phù hợp cho gaming và đồ họa.", "Laptop"));
        list.add(new Product("Lenovo Laptop", 54490000, R.drawable.lptop_lenovo,"Laptop Lenovo cao cấp, màn hình OLED 16 inch, CPU Intel i9, RAM 32GB, pin bền bỉ cho công việc và giải trí.","Laptop"));
        list.add(new Product("SamSung Galaxy", 32490000, R.drawable.samsung_galaxy, "Samsung Galaxy với màn hình Dynamic AMOLED 2X, camera chụp đêm siêu nét, chip Snapdragon mới nhất và sạc nhanh 45W.","Điện thoại"));
        list.add(new Product("ASIphone 16", 27390000, R.drawable.iphone16, "iPhone 16 trang bị chip A18 Bionic, camera 48MP Ultra Wide, màn hình ProMotion 120Hz, iOS 18 và pin lâu hơn.", "Điện thoại"));
        list.add(new Product("tablet Surface Pro 11", 34190000, R.drawable.table_surface_pro11, "Surface Pro 11 máy tính bảng 2 trong 1, thiết kế mỏng nhẹ, hiệu năng mạnh mẽ, lý tưởng cho học tập và làm việc.", "tablet"));
        list.add(new Product("Dell Latitude 7350 Detachable", 19900000, R.drawable.dell_latitude, "Dell Latitude 7350 Detachable – laptop 2 trong 1 cao cấp, linh hoạt, hiệu năng ổn định, phù hợp làm việc di động và văn phòng", "tablet"));
        list.add(new Product("PC GAMING CORE I5 12400F | RAM 16G | RTX 3060 12G | NVME 256G", 14290000, R.drawable.pk1, "PC Gaming Core i5 12400F – RAM 16GB, RTX 3060 12GB, NVMe 256GB, hiệu năng mạnh mẽ cho game và sáng tạo nội dung.", "phụ kiện"));
        list.add(new Product("Bàn phím cơ Dell Alienware RGB (AW510K)", 1890000, R.drawable.pk2,"Bàn phím cơ Dell Alienware RGB AW510K thiết kế cao cấp, đèn RGB sống động, trải nghiệm gõ và chơi game tuyệt vời.","phụ kiện"));


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

        // Bottom navigation
        bottomNav.setSelectedItemId(R.id.nav_home); // chọn home mặc định

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true; // đang ở home
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_account) {
                startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                overridePendingTransition(0, 0);
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
            if (p.getName().toLowerCase().contains(keyword)) {
                filteredList.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }
}