package com.ahmadawalludin.sholatku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private PrefsManager prefs;
    private TextView tvNamaPengguna;
    private TextView tvLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new PrefsManager(this);

        tvNamaPengguna = findViewById(R.id.tvNamaPengguna);
        tvLokasi = findViewById(R.id.tvLokasi);

        MaterialButton btnRiwayat = findViewById(R.id.btnRiwayat);
        MaterialButton btnPengaturan = findViewById(R.id.btnPengaturan);

        btnRiwayat.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RiwayatActivity.class)));

        btnPengaturan.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, PengaturanActivity.class)));
    }

    // Dipanggil setiap Beranda tampil lagi (mis. kembali dari Pengaturan)
    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    // Membaca ulang pengaturan tersimpan lalu menampilkannya
    private void loadPreferences() {
        String nama = prefs.getNama().trim();
        if (nama.isEmpty()) {
            tvNamaPengguna.setText(R.string.nama_default);
        } else {
            tvNamaPengguna.setText(nama);
        }

        tvLokasi.setText(getString(R.string.format_lokasi, prefs.getLokasi()));
    }
}
