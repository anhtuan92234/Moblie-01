package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName, txtPrice, txtProducTDescription;
    Button btnBuyNow, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        imgProduct = findViewById(R.id.imgProduct);
        txtName = findViewById(R.id.txtProductName);
        txtPrice = findViewById(R.id.txtProductPrice);
        txtProducTDescription = findViewById(R.id.txtProductDescription);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnBack = findViewById(R.id.btnBack);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int price = intent.getIntExtra("price", 0);
        int image = intent.getIntExtra("image", R.drawable.ic_launcher_background);
        String desc = intent.getStringExtra("description");
        String category = intent.getStringExtra("category");

        // Gán dữ liệu vào UI
        txtName.setText(name);
        txtPrice.setText(NumberFormat.getInstance(new Locale("vi", "VN")).format(price) + " đ");
        imgProduct.setImageResource(image);
        txtProducTDescription.setText(desc);

        // Xử lý khi bấm nút "Mua ngay"
        btnBuyNow.setOnClickListener(v -> {
            CartActivity.cartList.add(new Product(name, price, image, desc, category));
            Toast.makeText(this, "Đã thêm vào giỏ: " + name, Toast.LENGTH_SHORT).show();
        });
        btnBack.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
}
