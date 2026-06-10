package com.example.uts_fernando_24si3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etStudentId, etName, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi View
        etStudentId = findViewById(R.id.etStudentId);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = etStudentId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validasi data spesifik
                if (studentId.equals("03081240044") && 
                    name.equalsIgnoreCase("Fernando") && 
                    password.equals("admin")) { // Password tetap admin sebagai contoh
                    
                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    
                    // Pindah ke Dashboard dan kirim nama
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("nama", name);
                    startActivity(intent);
                    finish(); 
                } else {
                    Toast.makeText(LoginActivity.this, "Student ID, Nama, atau Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}