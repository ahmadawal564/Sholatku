package com.ahmadawalludin.sholatku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RiwayatActivity extends AppCompatActivity
        implements RiwayatAdapter.OnHapusListener {

    private DatabaseHelper db;
    private RiwayatAdapter adapter;
    private TextView tvKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        db = new DatabaseHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbarRiwayat);
        toolbar.setNavigationOnClickListener(v -> finish());

        tvKosong = findViewById(R.id.tvKosong);

        RecyclerView rv = findViewById(R.id.rvRiwayat);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RiwayatAdapter(this);
        rv.setAdapter(adapter);

        MaterialButton btnTambah = findViewById(R.id.btnTambahRiwayat);
        btnTambah.setOnClickListener(v -> tampilkanDialogTambah());
    }

    // Muat ulang data dari database setiap halaman tampil
    @Override
    protected void onResume() {
        super.onResume();
        muatData();
    }

    private void muatData() {
        List<Riwayat> daftar = db.getSemuaRiwayat();
        adapter.setData(daftar);
        tvKosong.setVisibility(daftar.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void tampilkanDialogTambah() {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.dialog_tambah_riwayat, null);
        Spinner spinner = view.findViewById(R.id.spinnerSalat);
        CheckBox cbSudah = view.findViewById(R.id.cbSudah);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.daftar_salat, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.judul_dialog_tambah)
                .setView(view)
                .setPositiveButton(R.string.tombol_simpan, (dialog, which) -> {
                    String nama = String.valueOf(spinner.getSelectedItem());
                    String tanggal = new SimpleDateFormat("dd MMM yyyy", new Locale("id", "ID"))
                            .format(new Date());
                    long hasil = db.tambahRiwayat(nama, tanggal, cbSudah.isChecked());
                    if (hasil == -1) {
                        Toast.makeText(this, R.string.toast_riwayat_gagal,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.toast_riwayat_ditambah,
                                Toast.LENGTH_SHORT).show();
                        muatData();
                    }
                })
                .setNegativeButton(R.string.tombol_batal, null)
                .show();
    }

    // Dipanggil adapter saat tombol hapus ditekan
    @Override
    public void onHapus(Riwayat riwayat) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.judul_hapus)
                .setMessage(getString(R.string.pesan_hapus,
                        riwayat.getNamaSalat(), riwayat.getTanggal()))
                .setPositiveButton(R.string.tombol_hapus, (dialog, which) -> {
                    db.hapusRiwayat(riwayat.getId());
                    Toast.makeText(this, R.string.toast_riwayat_dihapus,
                            Toast.LENGTH_SHORT).show();
                    muatData();
                })
                .setNegativeButton(R.string.tombol_batal, null)
                .show();
    }
}
