package com.ahmadawalludin.sholatku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/** Mengelola database SQLite "sholatku.db" untuk riwayat salat. */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sholatku.db";
    private static final int DB_VERSION = 1;

    private static final String TABEL = "riwayat";
    private static final String COL_ID = "id";
    private static final String COL_NAMA = "nama_salat";
    private static final String COL_TANGGAL = "tanggal";
    private static final String COL_STATUS = "sudah_dikerjakan"; // 1 = sudah, 0 = belum

    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABEL + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAMA + " TEXT NOT NULL, "
                + COL_TANGGAL + " TEXT NOT NULL, "
                + COL_STATUS + " INTEGER NOT NULL DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABEL);
        onCreate(db);
    }

    /** Menambah riwayat. Mengembalikan id baris baru, atau -1 jika gagal. */
    public long tambahRiwayat(String namaSalat, String tanggal, boolean sudahDikerjakan) {
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, namaSalat);
        values.put(COL_TANGGAL, tanggal);
        values.put(COL_STATUS, sudahDikerjakan ? 1 : 0);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABEL, null, values);
    }

    /** Mengambil semua riwayat, yang terbaru di atas. */
    public List<Riwayat> getSemuaRiwayat() {
        List<Riwayat> daftar = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABEL, null, null, null, null, null, COL_ID + " DESC");
        try {
            int idxId = cursor.getColumnIndexOrThrow(COL_ID);
            int idxNama = cursor.getColumnIndexOrThrow(COL_NAMA);
            int idxTanggal = cursor.getColumnIndexOrThrow(COL_TANGGAL);
            int idxStatus = cursor.getColumnIndexOrThrow(COL_STATUS);
            while (cursor.moveToNext()) {
                daftar.add(new Riwayat(
                        cursor.getLong(idxId),
                        cursor.getString(idxNama),
                        cursor.getString(idxTanggal),
                        cursor.getInt(idxStatus) == 1));
            }
        } finally {
            cursor.close();
        }
        return daftar;
    }

    /** Menghapus satu riwayat berdasarkan id. Mengembalikan jumlah baris terhapus. */
    public int hapusRiwayat(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABEL, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
