package com.example.uts_fernando_24si3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etNama, etTempatLahir, etTanggalLahir, etHobi, etBio;
    private Button btnSave, btnChangeImage;
    private ImageView ivProfile;
    private Uri selectedImageUri;
    
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Inisialisasi View
        etNama = findViewById(R.id.etEditNama);
        etTempatLahir = findViewById(R.id.etEditTempatLahir);
        etTanggalLahir = findViewById(R.id.etEditTanggalLahir);
        etHobi = findViewById(R.id.etEditHobi);
        etBio = findViewById(R.id.etEditBio);
        btnSave = findViewById(R.id.btnSave);
        btnChangeImage = findViewById(R.id.btnChangeImage);
        ivProfile = findViewById(R.id.ivEditProfile);

        // Ambil data awal dari Intent
        Intent intent = getIntent();
        etNama.setText(intent.getStringExtra("nama"));
        etTempatLahir.setText(intent.getStringExtra("tempat_lahir"));
        etTanggalLahir.setText(intent.getStringExtra("tanggal_lahir"));
        etHobi.setText(intent.getStringExtra("hobi"));
        etBio.setText(intent.getStringExtra("bio"));
        String imageUriString = intent.getStringExtra("image_uri");
        if (imageUriString != null) {
            selectedImageUri = Uri.parse(imageUriString);
            ivProfile.setImageURI(selectedImageUri);
        }

        // Click listener untuk Tanggal Lahir (Keluar Kalender)
        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Click listener untuk Ganti Foto
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etTanggalLahir.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ivProfile.setImageURI(selectedImageUri);
        }
    }

    private void saveProfile() {
        String nama = etNama.getText().toString().trim();
        String tempat = etTempatLahir.getText().toString().trim();
        String tanggal = etTanggalLahir.getText().toString().trim();
        String hobi = etHobi.getText().toString().trim();
        String bio = etBio.getText().toString().trim();

        // Validasi: Tidak boleh ada yang kosong
        if (nama.isEmpty() || tempat.isEmpty() || tanggal.isEmpty() || hobi.isEmpty() || bio.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kirim balik data ke Dashboard
        Intent resultIntent = new Intent();
        resultIntent.putExtra("nama", nama);
        resultIntent.putExtra("tempat_lahir", tempat);
        resultIntent.putExtra("tanggal_lahir", tanggal);
        resultIntent.putExtra("hobi", hobi);
        resultIntent.putExtra("bio", bio);
        if (selectedImageUri != null) {
            resultIntent.putExtra("image_uri", selectedImageUri.toString());
        }
        setResult(RESULT_OK, resultIntent);
        
        Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
        finish();
    }
}