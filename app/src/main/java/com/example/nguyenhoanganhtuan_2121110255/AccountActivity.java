package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword;
    Button btnUpdate, btnLogout;
    BottomNavigationView bottomNav;
    SessionManager sessionManager;

    // API MockAPI
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
        btnLogout = findViewById(R.id.btnLogout);
        bottomNav = findViewById(R.id.bottomNav);

        sessionManager = new SessionManager(this);

        // ✅ Hiển thị thông tin user từ session
        edtUsername.setText(sessionManager.getUsername());
        edtEmail.setText(sessionManager.getEmail());
        edtPassword.setText(sessionManager.getPassword());
        String userId = sessionManager.getUserId();

        btnUpdate.setOnClickListener(v -> updateAccount(userId));
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // ✅ Bottom Navigation
        bottomNav.setSelectedItemId(R.id.nav_account);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_account) {
                return true; // đang ở account
            }
            return false;
        });
    }

    private void updateAccount(String userId) {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId == null) {
            Toast.makeText(this, "Không tìm thấy ID người dùng!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("email", email);
            body.put("password", password);

            String url = API_URL + "/" + userId;

            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT, url, body,
                    response -> {
                        try {
                            String updatedUsername = response.getString("username");
                            String updatedEmail = response.getString("email");
                            String updatedPassword = response.getString("password");

                            // ✅ Cập nhật lại vào SessionManager
                            sessionManager.updateUser(updatedUsername, updatedEmail, updatedPassword);

                            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
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
    }
}
