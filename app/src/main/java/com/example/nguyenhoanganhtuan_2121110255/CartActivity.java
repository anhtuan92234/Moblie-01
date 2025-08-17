package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    public static ArrayList<Product> cartList = new ArrayList<>(); // Danh sách sản phẩm trong giỏ
    ListView listViewCart;
    TextView txtTotalPrice;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        // Ánh xạ view
        listViewCart = findViewById(R.id.listViewCart);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        bottomNav = findViewById(R.id.bottomNav);

        // Hiển thị giỏ hàng
        if (cartList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
        }

        CartAdapter adapter = new CartAdapter(this, cartList);
        listViewCart.setAdapter(adapter);

        // Tính tổng tiền
        updateTotalPrice();
    }
    private void updateTotalPrice() {
        int total = 0;
        for (Product p : cartList) {
            total += p.getPrice();
        }
        txtTotalPrice.setText("Tổng cộng: " + NumberFormat.getInstance(new Locale("vi", "VN")).format(total) + " đ");

        bottomNav.setSelectedItemId(R.id.nav_cart);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_cart) {
                return true; // đang ở giỏ hàng
            } else if (id == R.id.nav_account) {
                startActivity(new Intent(CartActivity.this, AccountActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}