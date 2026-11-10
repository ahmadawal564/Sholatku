package com.ahmadawalludin.sholatku;

import android.app.Application;

/**
 * Dijalankan satu kali setiap aplikasi dibuka (termasuk setelah Force Stop).
 * Tugasnya membaca tema tersimpan agar mode gelap/terang langsung benar.
 */
public class SholatKuApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefsManager prefs = new PrefsManager(this);
        PrefsManager.terapkanTema(prefs.isTemaGelap());
    }
}
