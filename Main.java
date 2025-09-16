package main;

import service.KontakService;
import model.Kontak;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point: menampilkan menu dan berinteraksi dengan user
 * (berperan sebagai Controller + View sederhana)
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final KontakService service = new KontakService();

    public static void main(String[] args) {
        int pilihan;
        do {
            showMenu();
            pilihan = readInt("Pilih menu: ");
            switch (pilihan) {
                case 1:
                    tambahKontak();
                    break;
                case 2:
                    lihatSemuaKontak();
                    break;
                case 3:
                    ubahKontak();
                    break;
                case 4:
                    hapusKontak();
                    break;
                case 5:
                    service.sortKontakAZ();
                    System.out.println("Kontak berhasil diurutkan (A-Z).");
                    break;
                case 6:
                    cariKontak();
                    break;
                case 7:
                    System.out.println("Keluar dari program. Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
            System.out.println();
        } while (pilihan != 7);

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("===== SISTEM CATATAN KONTAK =====");
        System.out.println("1. Tambah Kontak");
        System.out.println("2. Lihat Semua Kontak");
        System.out.println("3. Ubah Kontak (by id)");
        System.out.println("4. Hapus Kontak (by id)");
        System.out.println("5. Sortir Kontak (A-Z)");
        System.out.println("6. Cari Kontak (by nama)");
        System.out.println("7. Keluar");
    }

    private static void tambahKontak() {
        System.out.println("--- Tambah Kontak ---");
        String nama = readNonEmptyString("Masukkan nama: ");
        String nomor = readValidPhone("Masukkan nomor (digits, minimal 6 angka): ");
        Kontak k = service.addKontak(nama, nomor);
        System.out.println("Kontak berhasil ditambahkan: " + k);
    }

    private static void lihatSemuaKontak() {
        System.out.println("--- Daftar Kontak ---");
        List<Kontak> all = service.getAllKontak();
        if (all.isEmpty()) {
            System.out.println("Belum ada kontak.");
            return;
        }
        for (Kontak k : all) {
            System.out.println(k);
        }
    }

    private static void ubahKontak() {
        System.out.println("--- Ubah Kontak ---");
        int id = readInt("Masukkan ID kontak yang ingin diubah: ");
        Kontak found = service.findById(id);
        if (found == null) {
            System.out.println("Kontak dengan ID " + id + " tidak ditemukan.");
            return;
        }
        System.out.println("Kontak ditemukan: " + found);
        String namaBaru = readNonEmptyString("Nama baru (enter untuk tetap): ", true);
        String nomorBaru = readValidPhone("Nomor baru (enter untuk tetap): ", true);

        if (namaBaru.isEmpty()) namaBaru = found.getNama();
        if (nomorBaru.isEmpty()) nomorBaru = found.getNomor();

        boolean ok = service.updateKontak(id, namaBaru, nomorBaru);
        System.out.println(ok ? "Kontak berhasil diubah." : "Gagal mengubah kontak.");
    }

    private static void hapusKontak() {
        System.out.println("--- Hapus Kontak ---");
        int id = readInt("Masukkan ID kontak yang ingin dihapus: ");
        Kontak found = service.findById(id);
        if (found == null) {
            System.out.println("Kontak dengan ID " + id + " tidak ditemukan.");
            return;
        }
        System.out.println("Kontak akan dihapus: " + found);
        String confirm = readNonEmptyString("Ketik 'YA' untuk konfirmasi: ");
        if (confirm.equalsIgnoreCase("YA")) {
            boolean ok = service.deleteKontak(id);
            System.out.println(ok ? "Kontak berhasil dihapus." : "Gagal menghapus kontak.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    private static void cariKontak() {
        System.out.println("--- Cari Kontak ---");
        String q = readNonEmptyString("Masukkan nama atau sebagian nama: ");
        List<Kontak> hasil = service.searchByName(q);
        if (hasil.isEmpty()) {
            System.out.println("Tidak ditemukan kontak yang cocok.");
            return;
        }
        System.out.println("Hasil pencarian:");
        for (Kontak k : hasil) {
            System.out.println(k);
        }
    }

    // ---------- Utility input methods with validation ----------
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka. Coba lagi.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        return readNonEmptyString(prompt, false);
    }

    /**
     * @param allowEmpty kalau true, enter kosong dianggap valid (mengembalikan "")
     */
    private static String readNonEmptyString(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line == null) line = "";
            if (allowEmpty && line.trim().isEmpty()) return "";
            if (!line.trim().isEmpty()) return line.trim();
            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static String readValidPhone(String prompt) {
        return readValidPhone(prompt, false);
    }

    /**
     * validasi nomor: minimal 6 digit, hanya angka (boleh disesuaikan)
     * allowEmpty: jika true, enter diterima (mengembalikan "")
     */
    private static String readValidPhone(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (allowEmpty && line.isEmpty()) return "";
            if (line.matches("\\d{6,}")) {
                return line;
            } else {
                System.out.println("Nomor tidak valid. Gunakan hanya angka dan minimal 6 digit.");
            }
        }
    }
}
