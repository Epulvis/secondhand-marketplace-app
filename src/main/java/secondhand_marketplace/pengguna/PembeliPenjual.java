package secondhand_marketplace.pengguna;

import secondhand_marketplace.Komunikasi.Notifikasi;
import secondhand_marketplace.produk.Produk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import secondhand_marketplace.transaksi.Pemesanan;

public class PembeliPenjual extends Pengguna {
    private Map<Produk, Integer> keranjang; // untuk checkout barang
    private ArrayList<Produk> barangJualan;
    private ArrayList<Pemesanan> daftarPemesanan = new ArrayList<>(); // untuk pelacakan barang
    private ArrayList<Notifikasi> notifikasiList;

    public PembeliPenjual(String username, String password, String email, String noTelepon) {
        super(username, password, email, noTelepon);
        this.keranjang = new HashMap<>();
        this.barangJualan = new ArrayList<>();
        this.notifikasiList = new ArrayList<>();
    }

    // Copy Constructor
    public PembeliPenjual(PembeliPenjual other) {
        super(other.getUsername(), other.getPassword(), other.getEmail(), other.getNoTelepon());
        this.keranjang = new HashMap<>(other.keranjang);
        this.barangJualan = new ArrayList<>(other.barangJualan);
        this.daftarPemesanan = new ArrayList<>(other.daftarPemesanan);
        this.notifikasiList = new ArrayList<>(other.notifikasiList);
    }

    // Menambah barang ke keranjang
    public void tambahKeKeranjang(Produk produk, int jumlah) {
        if (keranjang.containsKey(produk)) {
            keranjang.put(produk, keranjang.get(produk) + jumlah);
        } else {
            keranjang.put(produk, jumlah);
        }
    }

    public Map<Produk, Integer> getKeranjang() {
        return Collections.unmodifiableMap(keranjang);
    }

    // Mengembalikan stok barang setelah pembatalan
    public void clearKeranjang() {
        keranjang.clear();
    }

    @Override
    public void tampilkanRole() {
        System.out.println("Role: Pembeli dan Penjual");
    }

    public ArrayList<Produk> getBarangJualan() {
        return new ArrayList<>(barangJualan);
    }

    public void tambahKeBarangJualan(Produk produk) {
        barangJualan.add(produk);
        System.out.println(produk.getNamaProduk() + " ditambahkan ke barang jualan.");
    }

    public void tambahPemesanan(Pemesanan pemesanan) {
        daftarPemesanan.add(pemesanan);
    }

    public ArrayList<Pemesanan> getDaftarPemesanan() {
        return new ArrayList<>(daftarPemesanan);
    }

    public void tambahNotifikasi(String pesan) {
        String id = "NOTIF-" + (notifikasiList.size() + 1);
        Notifikasi notif = new Notifikasi();
        notif.kirimNotifikasi(id, pesan);
        notifikasiList.add(notif);
    }

    public void tampilkanSemuaNotifikasi() {
        System.out.println("Daftar Notifikasi:");
        for (Notifikasi notif : notifikasiList) {
            notif.lihatNotifikasi(notifikasiList.toString());
        }
    }
}
