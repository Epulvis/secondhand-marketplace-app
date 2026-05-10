package secondhand_marketplace.pengguna;

import secondhand_marketplace.produk.Produk;

// Class Penawaran
public class Penawaran {
    private String id;
    private PembeliPenjual pembeli;
    private Produk barang;
    private double hargaPenawaran;
    private String status; // "Pending", "Diterima", "Ditolak"

    public Penawaran(String id, PembeliPenjual pembeli, Produk barang, double hargaPenawaran) {
        this.id = id;
        this.pembeli = new PembeliPenjual(pembeli);
        this.barang = new Produk(barang);
        this.hargaPenawaran = hargaPenawaran;
        this.status = "Pending";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public PembeliPenjual getPembeli() {
        return new PembeliPenjual(this.pembeli);
    }

    public void tampilkanPenawaran() {
        System.out.printf("Penawaran #%s: Barang - %s, Harga Penawaran - %.2f, Status - %s%n",
                id, barang.getNamaProduk(), hargaPenawaran, status);
    }
}
