package model;

/**
 * Model: merepresentasikan struktur data Kontak
 */
public class Kontak {
    private int id;
    private String nama;
    private String nomor;

    public Kontak(int id, String nama, String nomor) {
        this.id = id;
        this.nama = nama;
        this.nomor = nomor;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    // id tidak diset ulang setelah dibuat, jadi tidak ada setId publik

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nama + " - " + nomor;
    }
}
