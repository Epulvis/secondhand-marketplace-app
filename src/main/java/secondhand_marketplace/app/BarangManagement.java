package secondhand_marketplace.app;

import secondhand_marketplace.akun.ManajerAkun;
import secondhand_marketplace.pengguna.Penawaran;
import secondhand_marketplace.produk.Produk;
import secondhand_marketplace.transaksi.Pemesanan;
import secondhand_marketplace.pengguna.PembeliPenjual;
import secondhand_marketplace.pengguna.Pengguna;
import secondhand_marketplace.Komunikasi.Chat;
import secondhand_marketplace.utils.Utils;
import secondhand_marketplace.produk.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarangManagement {
    private ArrayList<Produk> barangJualan = new ArrayList<>();
    private ManajerAkun manajerAkun;
    private ArrayList<Penawaran> penawaranList;

    // Konstruktor untuk menerima ManajerAkun
    public BarangManagement(ManajerAkun manajerAkun) {
        this.manajerAkun = manajerAkun;
        this.penawaranList = new ArrayList<>();

    }

    public Produk cariBarang(String idProduk) {
        // Iterasi melalui daftar barang untuk mencari produk berdasarkan ID
        for (Produk produk : barangJualan) {
            if (produk.getIdProduk().equals(idProduk)) {
                return produk; // Jika ditemukan, kembalikan produk
            }
        }
        // Jika tidak ditemukan, kembalikan null
        System.out.println("[Error] Produk dengan ID " + idProduk + " tidak ditemukan.");
        return null;
    }

    public void lihatBarangJualan() {
        System.out.println("===========Daftar Barang Yang Dijual============");

        if (barangJualan.isEmpty()) {
            System.out.println("Tidak ada barang yang dijual.");
            return;
        }

        System.out.printf("%-3s %-20s %-15s %-5s %-32s %-15s %-15s%n",
                "ID", "Nama Barang", "Harga", "Stok", "Deskripsi", "Lokasi", "Penjual");
        System.out.println(
                "=======================================================================================================");
        for (Produk produk : barangJualan) {
            System.out.printf("%-3s %-20s %-15s %-5d %-32s %-15s %-15s%n",
                    produk.getIdProduk(),
                    produk.getNamaProduk(),
                    Utils.formatRupiah(produk.getHarga()),
                    produk.getStok(),
                    produk.getDeskripsi(),
                    produk.getLokasi(),
                    produk.getPenjual().getUsername());
        }
        System.out.println(
                "=======================================================================================================");
    }

    public void handlePembelian(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }

        lihatBarangJualan();
        if (barangJualan.isEmpty()) {
            return;
        }

        System.out.print("Masukkan ID barang yang ingin dibeli: ");
        String idBarang = Utils.inputString();

        System.out.print("Masukkan jumlah yang ingin dibeli: ");
        int jumlah = Utils.inputInt();

        for (Produk produk : barangJualan) {
            if (produk.getIdProduk().equals(idBarang)) {
                // if (produk.getPenjual() == null) {
                // System.out.println("Penjual barang tidak ditemukan.");
                // return;
                // }

                if (produk.getPenjual().equals(penggunaAktif)) {
                    System.out.println("Anda tidak bisa membeli barang yang Anda jual sendiri.");
                    return;
                }

                if (produk.getStok() < jumlah) {
                    System.out.println("Stok barang tidak mencukupi.");
                    return;
                }

                try {
                    // Cek apakah barang sudah ada di keranjang
                    if (penggunaAktif.getKeranjang().containsKey(produk)) {
                        // Jika sudah ada, tambahkan jumlahnya
                        int jumlahSebelumnya = penggunaAktif.getKeranjang().get(produk);
                        penggunaAktif.getKeranjang().put(produk, jumlahSebelumnya + jumlah);
                    } else {
                        // Jika belum ada, tambahkan barang baru ke keranjang
                        penggunaAktif.tambahKeKeranjang(produk, jumlah);
                    }

                    produk.kurangiStok(jumlah); // Mengurangi stok barang

                    // Cek apakah sudah ada pemesanan untuk produk ini
                    Pemesanan pemesananYangAda = null;
                    for (Pemesanan pemesanan : penggunaAktif.getDaftarPemesanan()) {
                        if (pemesanan.getProduk().equals(produk)) {
                            pemesananYangAda = pemesanan;
                            break;
                        }
                    }

                    if (pemesananYangAda != null) {
                        // Jika sudah ada, tambahkan jumlah ke pemesanan yang ada
                        pemesananYangAda.setJumlah(pemesananYangAda.getJumlah() + jumlah);
                    } else {
                        // Jika belum ada, buat pemesanan baru
                        Pemesanan pemesanan = new Pemesanan(produk, jumlah, penggunaAktif);
                        penggunaAktif.tambahPemesanan(pemesanan);
                        produk.getPenjual().tambahPemesanan(pemesanan);
                    }

                    System.out.println("Barang berhasil ditambahkan ke pemesanan.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
        }
        System.out.println("ID barang tidak ditemukan.");
    }

    public void checkoutPembelian(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }

        // Cek apakah pengguna memiliki barang yang dibeli di keranjang
        if (penggunaAktif.getKeranjang().isEmpty()) {
            System.out.println("Tidak ada barang dalam keranjang.");
            return;
        }

        double totalHarga = 0;
        System.out.println("======== Daftar Barang yang Dibeli ========");
        for (Map.Entry<Produk, Integer> entry : penggunaAktif.getKeranjang().entrySet()) {
            Produk produk = entry.getKey();
            int jumlah = entry.getValue();
            System.out.printf("%-20s %-15s %-10d %-10s%n",
                    produk.getNamaProduk(),
                    Utils.formatRupiah(produk.getHarga()),
                    jumlah,
                    Utils.formatRupiah(produk.getHarga() * jumlah));
            totalHarga += produk.getHarga() * jumlah;
        }
        System.out.println("======================================");
        System.out.println("Total Harga: " + Utils.formatRupiah(totalHarga));

        // Menanyakan konfirmasi pembayaran
        System.out.print("Apakah Anda ingin melanjutkan pembayaran? (y/n): ");
        String konfirmasi = Utils.inputString();
        if (konfirmasi.equalsIgnoreCase("y")) {
            // Proses checkout
            for (Map.Entry<Produk, Integer> entry : penggunaAktif.getKeranjang().entrySet()) {
                Produk produk = entry.getKey();

                // Cari pemesanan terkait
                Pemesanan pemesananTerkait = penggunaAktif.getDaftarPemesanan()
                        .stream()
                        .filter(p -> p.getProduk().equals(produk)
                                && p.getStatusPengiriman().equals("Menunggu Dicheckout"))
                        .findFirst()
                        .orElse(null);

                if (pemesananTerkait != null) {
                    // Perbarui status pengiriman
                    pemesananTerkait.setStatusPengiriman("Diproses");

                    // Tambahkan pemesanan ke daftar penjual (jika belum ada)
                    PembeliPenjual penjual = produk.getPenjual();
                    if (!penjual.getDaftarPemesanan().contains(pemesananTerkait)) {
                        penjual.tambahPemesanan(pemesananTerkait);
                    }
                    System.out.println("Status pemesanan diubah menjadi 'Diproses' untuk " + produk.getNamaProduk());
                }
            }
        } else {
            // Membatalkan transaksi dan mengembalikan stok
            System.out.println("Pembelian dibatalkan.");
            for (Map.Entry<Produk, Integer> entry : penggunaAktif.getKeranjang().entrySet()) {
                Produk produk = entry.getKey();
                int jumlah = entry.getValue();
                produk.tambahStok(jumlah); // Mengembalikan stok yang berkurang

                // Menghapus pemesanan yang dibatalkan dari daftar pemesanan
                penggunaAktif.getDaftarPemesanan().removeIf(pemesanan -> pemesanan.getProduk().equals(produk));

                // Hapus pemesanan terkait pada penjual
                // Pastikan hanya pemesanan terkait yang dihapus, bukan seluruh daftar
                produk.getPenjual().getDaftarPemesanan().removeIf(pemesanan -> pemesanan.getProduk().equals(produk));

                // Hapus produk dari keranjang
                penggunaAktif.getKeranjang().remove(produk);
            }
            penggunaAktif.clearKeranjang(); // Mengosongkan keranjang setelah pembatalan
        }
    }

    public void lacakBarang(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }

        ArrayList<Pemesanan> daftarPemesanan = penggunaAktif.getDaftarPemesanan();
        if (daftarPemesanan.isEmpty()) {
            System.out.println("Tidak ada barang yang sedang dilacak.");
            return;
        }

        System.out.println("===== Lacak Barang =====");
        System.out.printf("%-10s %-20s %-8s %-25s %-20s%n", "Penjual", "Nama Produk", "Jumlah", "Status",
                "Nama Pembeli");
        System.out.println(
                "=============================================================================================");
        for (Pemesanan pemesanan : daftarPemesanan) {
            System.out.println(pemesanan);
        }
        System.out.println(
                "=============================================================================================");
    }

    public void ubahStatusPengiriman(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }

        // Daftar pemesanan penjual
        List<Pemesanan> daftarPemesananPenjual = penggunaAktif.getDaftarPemesanan();

        if (daftarPemesananPenjual.isEmpty()) {
            System.out.println("Tidak ada barang yang perlu diperbarui status pengirimannya.");
            return;
        }

        // Filter hanya barang yang sudah di-checkout
        List<Pemesanan> daftarSiapDiubah = new ArrayList<>();
        for (Pemesanan pemesanan : daftarPemesananPenjual) {
            if (!pemesanan.getStatusPengiriman().equals("Menunggu Dicheckout")) {
                daftarSiapDiubah.add(pemesanan);
            }
        }

        if (daftarSiapDiubah.isEmpty()) {
            System.out.println("Tidak ada barang yang statusnya dapat diubah.");
            return;
        }

        System.out.println("===== Daftar Barang dengan Status Pengiriman =====");
        System.out.printf("%-3s %-20s %-15s %-30s %-15s %-15s%n",
                "No.", "Nama Barang", "ID Produk", "Status", "Pembeli", "Penjual");
        System.out.println("==============================================================================");
        for (int i = 0; i < daftarSiapDiubah.size(); i++) {
            Pemesanan pemesanan = daftarSiapDiubah.get(i);
            System.out.printf("%-3d %-20s %-15s %-30s %-15s%n",
                    i + 1,
                    pemesanan.getProduk().getNamaProduk(),
                    pemesanan.getProduk().getIdProduk(),
                    pemesanan.getStatusPengiriman(),
                    pemesanan.getPembeli().getUsername(),
                    pemesanan.getProduk().getPenjual());
        }
        System.out.println("==============================================================================");

        System.out.print("Masukkan nomor barang yang ingin diubah statusnya: ");
        int pilihanBarang = Utils.inputInt();

        if (pilihanBarang < 1 || pilihanBarang > daftarSiapDiubah.size()) {
            System.out.println("[Error] Pilihan tidak valid.");
            return;
        }

        Pemesanan pemesananDipilih = daftarSiapDiubah.get(pilihanBarang - 1);

        // Validasi kepemilikan
        if (!penggunaAktif.equals(pemesananDipilih.getProduk().getPenjual())) {
            System.out.println("[Error] Anda tidak memiliki izin untuk mengubah status pengiriman barang ini.");
            return;
        }

        // Menampilkan opsi status pengiriman
        System.out.println("Pilih status pengiriman baru:");
        System.out.println("1. Dalam Pengiriman");
        System.out.println("2. Tiba di Gudang");
        System.out.println("3. Dikirim ke Alamat Pembeli");
        System.out.print("> ");
        int pilihan = Utils.inputInt();

        String statusBaru = "";
        // Menggunakan if-else untuk menangani pilihan
        if (pilihan == 1) {
            statusBaru = "Dalam Pengiriman";
        } else if (pilihan == 2) {
            statusBaru = "Tiba di Gudang";
        } else if (pilihan == 3) {
            statusBaru = "Dikirim ke Alamat Pembeli";
        } else {
            System.out.println("[Error] Pilihan tidak valid.");
            return; // keluar dari fungsi jika pilihan tidak valid
        }

        // Mengubah status pengiriman
        pemesananDipilih.setStatusPengiriman(statusBaru);
        System.out.printf("Status pengiriman untuk produk '%s' berhasil diperbarui menjadi '%s'.%n",
                pemesananDipilih.getProduk().getNamaProduk(), statusBaru);
    }

    public void reviewProduk(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }

        // Filter hanya barang yang sudah di-checkout
        List<Pemesanan> daftarSudahCheckout = new ArrayList<>();
        for (Pemesanan pemesanan : penggunaAktif.getDaftarPemesanan()) {
            if (pemesanan.getStatusPengiriman().equals("Diproses") ||
                    pemesanan.getStatusPengiriman().equals("Dalam Pengiriman") ||
                    pemesanan.getStatusPengiriman().equals("Tiba di Gudang") ||
                    pemesanan.getStatusPengiriman().equals("Dikirim ke Alamat Pembeli")) {
                daftarSudahCheckout.add(pemesanan);
            }
        }

        if (daftarSudahCheckout.isEmpty()) {
            System.out.println("Anda belum membeli barang yang bisa direview.");
            return;
        }

        System.out.println("===== Daftar Barang yang Bisa Direview =====");
        for (int i = 0; i < daftarSudahCheckout.size(); i++) {
            Pemesanan pemesanan = daftarSudahCheckout.get(i);
            System.out.printf("%d. %s (ID Produk: %s)%n",
                    i + 1,
                    pemesanan.getProduk().getNamaProduk(),
                    pemesanan.getProduk().getIdProduk());
        }

        System.out.print("Pilih nomor barang untuk memberikan review: ");
        int pilihan = Utils.inputInt();

        if (pilihan < 1 || pilihan > daftarSudahCheckout.size()) {
            System.out.println("[Error] Pilihan tidak valid.");
            return;
        }

        Pemesanan pemesananDipilih = daftarSudahCheckout.get(pilihan - 1);

        System.out.print("Masukkan rating (1-5): ");
        int rating = Utils.inputInt();

        if (rating < 1 || rating > 5) {
            System.out.println("[Error] Rating harus antara 1 hingga 5.");
            return;
        }

        System.out.print("Masukkan komentar Anda: ");
        String komentar = Utils.inputString();

        // Membuat review baru
        Review reviewBaru = new Review(penggunaAktif, rating, komentar);

        // Tambahkan review ke produk terkait
        pemesananDipilih.getProduk().tambahReview(reviewBaru);

        System.out.println("Review berhasil ditambahkan!");
    }

    public void cekBarangJualan(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }
        System.out.println("===============Barang Jualan Anda================");
        if (penggunaAktif.getBarangJualan().isEmpty()) {
            System.out.println("Anda belum memiliki barang yang dijual.");
        } else {
            for (Produk produk : penggunaAktif.getBarangJualan()) {
                System.out.println(produk);
            }
        }
    }

    public void tambahBarang(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }

        double harga = -1;
        int stok = -1;

        System.out.println("===========Tambah Barang yang Dijual============");
        System.out.print("Masukkan nama barang: ");
        String namaBarang = Utils.inputString();

        while (harga < 0) {
            System.out.print("Masukkan harga barang: ");
            harga = Utils.inputDouble();
            if (harga < 0) {
                System.out.println("[Error] Harga barang tidak bisa negatif.");
            }
        }

        while (stok < 0) {
            System.out.print("Masukkan jumlah stok: ");
            stok = Utils.inputInt();
            if (stok < 0) {
                System.out.println("[Error] Stok barang tidak bisa negatif.");
            }
        }

        System.out.print("Masukkan deskripsi barang: ");
        String deskripsi = Utils.inputString();

        System.out.print("Masukkan lokasi barang: ");
        String lokasi = Utils.inputString();

        Produk produkBaru = new Produk("P" + (barangJualan.size() + 1), namaBarang, harga, stok, deskripsi, lokasi,
                penggunaAktif);
        penggunaAktif.tambahKeBarangJualan(produkBaru);
        barangJualan.add(produkBaru);
        System.out.println("Barang berhasil ditambahkan!");
    }

    public void perbaruiStokBarang(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }

        System.out.println("===========Daftar Barang Jualan Andal============");
        if (penggunaAktif.getBarangJualan().isEmpty()) {
            System.out.println("Anda belum memiliki barang yang dijual.");
            return;
        }

        // Menampilkan barang yang dijual oleh pengguna aktif
        for (Produk produk : penggunaAktif.getBarangJualan()) {
            System.out.println(produk);
        }

        System.out.print("Masukkan ID barang yang ingin diperbarui stoknya: ");
        String idBarang = Utils.inputString();

        System.out.print("Masukkan jumlah stok baru: ");
        int stokBaru = Utils.inputInt();

        // Mencari barang berdasarkan ID dan memperbarui stoknya
        for (Produk produk : penggunaAktif.getBarangJualan()) {
            if (produk.getIdProduk().equals(idBarang)) {
                int stokSaatIni = produk.getStok();
                int stokTerbaru = stokSaatIni + stokBaru;
                produk.setStok(stokTerbaru); // Memperbarui stok barang
                System.out.println("Stok barang berhasil diperbarui.");
                return;
            }
        }

        System.out.println("Barang dengan ID " + idBarang + " tidak ditemukan.");
    }

    public void lihatReviewJualan(PembeliPenjual penggunaAktif) {
        // Validasi pengguna aktif
        if (penggunaAktif == null) {
            System.out.println("[Error] Anda harus login terlebih dahulu.");
            return;
        }

        // Mendapatkan daftar produk yang dijual oleh pengguna aktif
        List<Produk> barangDijual = new ArrayList<>();
        for (Produk produk : barangJualan) {
            if (produk.getPenjual().equals(penggunaAktif)) {
                barangDijual.add(produk);
            }
        }

        // Validasi jika tidak ada barang yang dijual
        if (barangDijual.isEmpty()) {
            System.out.println("[Info] Anda belum memiliki barang untuk dijual.");
            return;
        }

        // Menampilkan daftar barang jualan
        System.out.println("===========Daftar Barang Jualan Anda============");
        for (int i = 0; i < barangDijual.size(); i++) {
            Produk produk = barangDijual.get(i);
            System.out.printf("%d. %s (ID: %s)%n", i + 1, produk.getNamaProduk(), produk.getIdProduk());
        }

        // Meminta pengguna memilih barang
        System.out.print("Pilih nomor barang untuk melihat review: ");
        int pilihan = Utils.inputInt();

        // Validasi pilihan
        if (pilihan < 1 || pilihan > barangDijual.size()) {
            System.out.println("[Error] Pilihan tidak valid.");
            return;
        }

        // Menampilkan review barang yang dipilih
        Produk produkDipilih = barangDijual.get(pilihan - 1);
        System.out.println("===== Review untuk Produk: " + produkDipilih.getNamaProduk() + " =====");
        produkDipilih.lihatReview();
    }

    public void chat(PembeliPenjual penggunaAktif) {
        if (penggunaAktif == null) {
            System.out.println("Harap login terlebih dahulu!");
            return;
        }
        System.out.println("=================Fitur Chat==================");
        System.out.println("1. Kirim pesan");
        System.out.println("2. Lihat riwayat chat");
        System.out.print("> Pilih opsi: ");
        int opsi = Utils.inputInt();

        switch (opsi) {
            case 1:
                System.out.print("Masukkan username penerima: ");
                String penerimaUsername = Utils.inputString();
                for (Pengguna pengguna : manajerAkun.getDaftarPengguna().values()) {
                    if (pengguna.getUsername().equals(penerimaUsername)) {
                        System.out.print("Masukkan pesan: ");
                        String pesan = Utils.inputString();
                        Chat chat = new Chat(penggunaAktif, (PembeliPenjual) pengguna, pesan); // Melakukan casting
                                                                                               // objek pengguna
                        chat.kirimPesan();
                        return;
                    }
                }
                System.out.println("Pengguna tidak ditemukan.");
                break;

            case 2:
                Chat.tampilkanHistory(penggunaAktif);
                break;

            default:
                System.out.println("Opsi tidak valid.");
                break;
        }
    }

    public ArrayList<Produk> getBarangJualan() {
        return new ArrayList<>(barangJualan);
    }

    public void buatPenawaran(PembeliPenjual pembeli, Produk barang, double hargaPenawaran) {
        if (barang.getPenjual().getUsername().equals(pembeli.getUsername())) {
            System.out.println("Anda tidak dapat membuat penawaran untuk barang Anda sendiri.");
            return;
        }

        String id = "PNWR-" + (penawaranList.size() + 1);
        Penawaran penawaran = new Penawaran(id, pembeli, barang, hargaPenawaran);
        penawaranList.add(penawaran);

        String pesan = "Penawaran baru untuk barang: " + barang.getNamaProduk() + " oleh " + pembeli.getUsername();
        barang.getPenjual().tambahNotifikasi(pesan);
        System.out.println("Penawaran berhasil dibuat!");
    }

    public void handleBuatPenawaran(PembeliPenjual penggunaAktif) {
        lihatBarangJualan();

        System.out.print("Masukkan ID barang yang ingin Anda tawar: ");
        String idBarang = Utils.inputString();
        Produk barang = null;

        for (Produk produk : getBarangJualan()) {
            if (produk.getIdProduk().equals(idBarang)) {
                barang = produk;
                break;
            }
        }

        if (barang == null) {
            System.out.println("Barang dengan ID tersebut tidak ditemukan.");
            return;
        }

        System.out.print("Masukkan harga penawaran: ");
        double hargaPenawaran = Utils.inputDouble();

        buatPenawaran(penggunaAktif, barang, hargaPenawaran);
    }

    public void tampilkanPenawaran() {
        System.out.println("Daftar Penawaran:");
        for (Penawaran penawaran : penawaranList) {
            penawaran.tampilkanPenawaran();
        }
    }
}