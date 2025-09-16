package service;

import model.Kontak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service: menampung semua logika CRUD dan pencarian
 */
public class KontakService {
    private final List<Kontak> daftar;
    private int nextId;

    public KontakService() {
        this.daftar = new ArrayList<>();
        this.nextId = 1; // auto increment id
    }

    // Validasi dasar dilakukan di service (bisa ditambah lagi)
    public Kontak addKontak(String nama, String nomor) {
        Kontak k = new Kontak(nextId++, nama, nomor);
        daftar.add(k);
        return k;
    }

    public List<Kontak> getAllKontak() {
        return new ArrayList<>(daftar); // return copy to prevent external mutation
    }

    public boolean updateKontak(int id, String namaBaru, String nomorBaru) {
        Kontak k = findById(id);
        if (k == null) return false;
        k.setNama(namaBaru);
        k.setNomor(nomorBaru);
        return true;
    }

    public boolean deleteKontak(int id) {
        Kontak k = findById(id);
        if (k == null) return false;
        return daftar.remove(k);
    }

    public void sortKontakAZ() {
        Collections.sort(daftar, Comparator.comparing(k -> k.getNama().toLowerCase()));
    }

    // Search by name (partial, case-insensitive)
    public List<Kontak> searchByName(String query) {
        List<Kontak> hasil = new ArrayList<>();
        String q = query.toLowerCase();
        for (Kontak k : daftar) {
            if (k.getNama().toLowerCase().contains(q)) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    public Kontak findById(int id) {
        for (Kontak k : daftar) {
            if (k.getId() == id) return k;
        }
        return null;
    }

    public boolean isEmpty() {
        return daftar.isEmpty();
    }
}
