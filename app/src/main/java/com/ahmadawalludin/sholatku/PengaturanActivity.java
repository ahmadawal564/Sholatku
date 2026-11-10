package com.ahmadawalludin.sholatku;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class PengaturanActivity extends AppCompatActivity {

    private PrefsManager prefs;

    private TextInputEditText etNama;
    private TextInputEditText etKota;
    private SwitchMaterial switchTema;
    private SwitchMaterial switchSubuh;
    private SwitchMaterial switchZuhur;
    private SwitchMaterial switchAsar;
    private SwitchMaterial switchMagrib;
    private SwitchMaterial switchIsya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        prefs = new PrefsManager(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbarPengaturan);
        toolbar.setNavigationOnClickListener(v -> finish());

        etNama = findViewById(R.id.etNama);
        etKota = findViewById(R.id.etKota);
        switchTema = findViewById(R.id.switchTema);
        switchSubuh = findViewById(R.id.switchSubuh);
        switchZuhur = findViewById(R.id.switchZuhur);
        switchAsar = findViewById(R.id.switchAsar);
        switchMagrib = findViewById(R.id.switchMagrib);
        switchIsya = findViewById(R.id.switchIsya);

        MaterialButton btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(v -> simpanPengaturan());
    }

    // Dipanggil setiap halaman tampil: baca ulang data tersimpan
    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void loadPreferences() {
        etNama.setText(prefs.getNama());
        etKota.setText(prefs.getLokasi());
        switchTema.setChecked(prefs.isTemaGelap());
        switchSubuh.setChecked(prefs.isPengingatAktif(PrefsManager.KEY_SUBUH));
        switchZuhur.setChecked(prefs.isPengingatAktif(PrefsManager.KEY_ZUHUR));
        switchAsar.setChecked(prefs.isPengingatAktif(PrefsManager.KEY_ASAR));
        switchMagrib.setChecked(prefs.isPengingatAktif(PrefsManager.KEY_MAGRIB));
        switchIsya.setChecked(prefs.isPengingatAktif(PrefsManager.KEY_ISYA));
    }

    private void simpanPengaturan() {
        String nama = String.valueOf(etNama.getText()).trim();
        String kota = String.valueOf(etKota.getText()).trim();
        if (kota.isEmpty()) {
            kota = PrefsManager.LOKASI_DEFAULT;
        }

        boolean temaLama = prefs.isTemaGelap();
        boolean temaBaru = switchTema.isChecked();

        // Semua perubahan disimpan memakai apply() di dalam PrefsManager
        prefs.setNama(nama);
        prefs.setLokasi(kota);
        prefs.setTemaGelap(temaBaru);
        prefs.setPengingat(PrefsManager.KEY_SUBUH, switchSubuh.isChecked());
        prefs.setPengingat(PrefsManager.KEY_ZUHUR, switchZuhur.isChecked());
        prefs.setPengingat(PrefsManager.KEY_ASAR, switchAsar.isChecked());
        prefs.setPengingat(PrefsManager.KEY_MAGRIB, switchMagrib.isChecked());
        prefs.setPengingat(PrefsManager.KEY_ISYA, switchIsya.isChecked());

        Toast.makeText(this, R.string.toast_disimpan, Toast.LENGTH_SHORT).show();

        // Tema diterapkan terakhir karena akan membuat ulang halaman ini
        if (temaLama != temaBaru) {
            PrefsManager.terapkanTema(temaBaru);
        }
    }
}
