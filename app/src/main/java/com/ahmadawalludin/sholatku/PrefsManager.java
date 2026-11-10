package com.ahmadawalludin.sholatku;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Kelas khusus untuk menyimpan dan membaca pengaturan aplikasi
 * menggunakan SharedPreferences.
 */
public class PrefsManager {

    private static final String PREF_NAME = "sholatku_preferences";

    private static final String KEY_NAMA = "nama_panggilan";
    private static final String KEY_TEMA = "tema_gelap";
    private static final String KEY_LOKASI = "lokasi_kota";

    public static final String KEY_SUBUH = "pengingat_subuh";
    public static final String KEY_ZUHUR = "pengingat_zuhur";
    public static final String KEY_ASAR = "pengingat_asar";
    public static final String KEY_MAGRIB = "pengingat_magrib";
    public static final String KEY_ISYA = "pengingat_isya";

    public static final String LOKASI_DEFAULT = "Kotabumi";

    private final SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ---------- Nama panggilan (String) ----------
    public void setNama(String nama) {
        prefs.edit()
                .putString(KEY_NAMA, nama)
                .apply();
    }

    public String getNama() {
        return prefs.getString(KEY_NAMA, "");
    }

    // ---------- Tema gelap (boolean) ----------
    public void setTemaGelap(boolean gelap) {
        prefs.edit()
                .putBoolean(KEY_TEMA, gelap)
                .apply();
    }

    public boolean isTemaGelap() {
        return prefs.getBoolean(KEY_TEMA, false);
    }

    // ---------- Lokasi kota (String) ----------
    public void setLokasi(String kota) {
        prefs.edit()
                .putString(KEY_LOKASI, kota)
                .apply();
    }

    public String getLokasi() {
        return prefs.getString(KEY_LOKASI, LOKASI_DEFAULT);
    }

    // ---------- Pengaturan pengingat per salat (boolean) ----------
    // Isi parameter key dengan KEY_SUBUH, KEY_ZUHUR, KEY_ASAR, KEY_MAGRIB, atau KEY_ISYA
    public void setPengingat(String key, boolean aktif) {
        prefs.edit()
                .putBoolean(key, aktif)
                .apply();
    }

    public boolean isPengingatAktif(String key) {
        return prefs.getBoolean(key, true);
    }

    // ---------- Menerapkan tema terang/gelap ke seluruh aplikasi ----------
    public static void terapkanTema(boolean gelap) {
        AppCompatDelegate.setDefaultNightMode(
                gelap ? AppCompatDelegate.MODE_NIGHT_YES
                      : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
