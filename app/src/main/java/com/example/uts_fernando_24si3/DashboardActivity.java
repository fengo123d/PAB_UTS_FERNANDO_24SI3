package com.example.uts_fernando_24si3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome, tvNama, tvStudentId, tvTempatLahir, tvTanggalLahir, tvHobi, tvBio;
    private ImageView ivProfile;
    private Uri currentImageUri;
    private static final int REQUEST_CODE_EDIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi View
        tvWelcome = findViewById(R.id.tvWelcome);
        tvNama = findViewById(R.id.tvNama);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvTempatLahir = findViewById(R.id.tvTempatLahir);
        tvTanggalLahir = findViewById(R.id.tvTanggalLahir);
        tvHobi = findViewById(R.id.tvHobi);
        tvBio = findViewById(R.id.tvBio);
        ivProfile = findViewById(R.id.ivProfile);
        
        // Ambil data dari Intent login jika ada
        String namaLogin = getIntent().getStringExtra("nama");
        if (namaLogin == null) namaLogin = "Fernando";
        
        // Data default awal
        updateUI(namaLogin, "03081240044", "Medan", "9/5/2006", "Catur, Basket", "Designer", null);
    }

    private void updateUI(String nama, String studentId, String tempat, String tanggal, String hobi, String bio, @Nullable String imageUri) {
        tvWelcome.setText("Welcome, " + nama + " !");
        tvNama.setText(nama);
        tvStudentId.setText(studentId);
        tvTempatLahir.setText(tempat);
        tvTanggalLahir.setText(tanggal);
        tvHobi.setText(hobi);
        tvBio.setText(bio);
        
        if (imageUri != null) {
            currentImageUri = Uri.parse(imageUri);
            ivProfile.setImageURI(currentImageUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_edit_profile) {
            Intent intent = new Intent(DashboardActivity.this, EditProfileActivity.class);
            intent.putExtra("nama", tvNama.getText().toString());
            intent.putExtra("student_id", tvStudentId.getText().toString());
            intent.putExtra("tempat_lahir", tvTempatLahir.getText().toString());
            intent.putExtra("tanggal_lahir", tvTanggalLahir.getText().toString());
            intent.putExtra("hobi", tvHobi.getText().toString());
            intent.putExtra("bio", tvBio.getText().toString());
            if (currentImageUri != null) {
                intent.putExtra("image_uri", currentImageUri.toString());
            }
            startActivityForResult(intent, REQUEST_CODE_EDIT);
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            // Kembali ke halaman Login
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Tutup Dashboard agar tidak bisa kembali ke sini via tombol back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            String nama = data.getStringExtra("nama");
            String tempat = data.getStringExtra("tempat_lahir");
            String tanggal = data.getStringExtra("tanggal_lahir");
            String hobi = data.getStringExtra("hobi");
            String bio = data.getStringExtra("bio");
            String imageUri = data.getStringExtra("image_uri");
            
            // Student ID biasanya tetap, tapi kita update dengan nilai yang ada
            updateUI(nama, tvStudentId.getText().toString(), tempat, tanggal, hobi, bio, imageUri);
        }
    }
}