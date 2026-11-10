package com.ahmadawalludin.sholatku;

/** Model data satu catatan riwayat salat. */
public class Riwayat {

    private final long id;
    private final String namaSalat;
    private final String tanggal;
    private final boolean sudahDikerjakan;

    public Riwayat(long id, String namaSalat, String tanggal, boolean sudahDikerjakan) {
        this.id = id;
        this.namaSalat = namaSalat;
        this.tanggal = tanggal;
        this.sudahDikerjakan = sudahDikerjakan;
    }

    public long getId() {
        return id;
    }

    public String getNamaSalat() {
        return namaSalat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public boolean isSudahDikerjakan() {
        return sudahDikerjakan;
    }
}
