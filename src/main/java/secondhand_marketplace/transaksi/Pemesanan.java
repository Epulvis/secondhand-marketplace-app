package secondhand_marketplace.transaksi;

import secondhand_marketplace.pengguna.PembeliPenjual;
import secondhand_marketplace.produk.Produk;

public class Pemesanan {
    private Produk produk;
    private int jumlah;
    private String statusPengiriman;
    private PembeliPenjual pembeli;

    public Pemesanan(Produk produk, int jumlah, PembeliPenjual pembeli) {
        this.produk = produk;
        this.jumlah = jumlah;
        this.pembeli = pembeli;
        this.statusPengiriman = "Menunggu Dicheckout"; // Status awal
    }
    
    public PembeliPenjual getPembeli() {
        return pembeli;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    
    public String getStatusPengiriman() {
        return statusPengiriman;
    }

    public void setStatusPengiriman(String statusPengiriman) {
        this.statusPengiriman = statusPengiriman;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s %-8d %-25s %-20s",
            produk.getPenjual().getUsername(),
            produk.getNamaProduk(),
            jumlah,
            statusPengiriman,
            pembeli.getUsername());
    }
}