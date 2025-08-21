package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword;
    Button btnUpdate;
    SharedPreferences prefs;
    BottomNavigationView bottomNav;

    private final String API_URL = "https://6895908c039a1a2b288f7f07.mockapi.io/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);

        edtUsername = findViewById(R.id.editUsername);
        edtEmail = findViewById(R.id.editEmail);
        edtPassword = findViewById(R.id.editPassword);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Lấy thông tin đã đăng ký từ SharedPreferences
        prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        edtUsername.setText(prefs.getString("USERNAME", ""));
        edtEmail.setText(prefs.getString("EMAIL", ""));
        edtPassword.setText(prefs.getString("PASSWORD", ""));

        btnUpdate.setOnClickListener(v -> updateAccount());
    }
    private void updateAccount() {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("email", email);
            body.put("password", password);

            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, API_URL, body, response -> {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                                // Lưu lại vào SharedPreferences
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("USERNAME", username);
                                editor.putString("EMAIL", email);
                                editor.putString("PASSWORD", password);
                                editor.apply();
                            } else {
                                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Lỗi kết nối API!", Toast.LENGTH_SHORT).show();
                    }
            );
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bottomNav.setSelectedItemId(R.id.nav_account); // chọn account mặc định

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(AccountActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(AccountActivity.this, CartActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_account) {
                return true; // đang ở account
            }
            return false;
        });
    }
}