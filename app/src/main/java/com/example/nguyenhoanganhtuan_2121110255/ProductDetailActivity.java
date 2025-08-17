package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName, txtPrice;
    Button btnBuyNow, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        imgProduct = findViewById(R.id.imgProduct);
        txtName = findViewById(R.id.txtProductName);
        txtPrice = findViewById(R.id.txtProductPrice);
        btnBuyNow = findViewById(R.id.btnBuyNow);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int price = intent.getIntExtra("price", 0);
        int imageRes = intent.getIntExtra("image", R.drawable.ic_launcher_background);

        // Gán dữ liệu vào UI
        txtName.setText(name);
        txtPrice.setText(NumberFormat.getInstance(new Locale("vi", "VN")).format(price) + " đ");
        imgProduct.setImageResource(imageRes);

        // Xử lý khi bấm nút "Mua ngay"
        btnBuyNow.setOnClickListener(v -> {
            // Sau này có thể mở giỏ hàng hoặc màn hình thanh toán
            // Tạm thời chỉ hiện thông báo
            android.widget.Toast.makeText(
                    this,
                    "Bạn đã chọn mua: " + name,
                    android.widget.Toast.LENGTH_SHORT
            ).show();
        });
        btnBack.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
}
