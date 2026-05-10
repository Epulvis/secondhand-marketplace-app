package secondhand_marketplace.produk;

import java.util.ArrayList;
import secondhand_marketplace.pengguna.PembeliPenjual;
import secondhand_marketplace.utils.Utils;

public class Produk {
    private String idProduk;
    private String namaProduk;
    private double harga;
    private int stok;
    private String deskripsi;
    private String lokasi;
    private PembeliPenjual penjual;
    private ArrayList<Review> reviews = new ArrayList<>(); // Daftar review

    // Konstruktor utama
    public Produk(String idProduk, String namaProduk, double harga, int stok, String deskripsi, String lokasi,
            PembeliPenjual penjual) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.penjual = new PembeliPenjual(penjual);
    }

    public Produk(Produk other) {
        this.idProduk = other.idProduk;
        this.namaProduk = other.namaProduk;
        this.harga = other.harga;
        this.stok = other.stok;
        this.deskripsi = other.deskripsi;
        this.lokasi = other.lokasi;
        this.penjual = other.penjual;
        this.reviews = new ArrayList<>(other.reviews);
    }

    // Konstruktor overload dengan default deskripsi dan lokasi
    public Produk(String idProduk, String namaProduk, double harga, int stok, PembeliPenjual penjual) {
        this(idProduk, namaProduk, harga, stok, "Deskripsi tidak tersedia", "Lokasi tidak diketahui", penjual);
    }

    // Konstruktor overload dengan stok default
    public Produk(String idProduk, String namaProduk, double harga, PembeliPenjual penjual) {
        this(idProduk, namaProduk, harga, 0, "Deskripsi tidak tersedia", "Lokasi tidak diketahui", penjual);
    }

    // Getter dan setter untuk deskripsi
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    // Getter dan setter untuk lokasi
    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    // Getter dan setter lainnya
    public String getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    // public void setHarga(double harga) {
    // this.harga = harga;
    // }

    public void kurangiStok(int quantity) throws IllegalArgumentException {
        if (quantity > stok) {
            throw new IllegalArgumentException("Stok tidak mencukupi.");
        } else {
            stok -= quantity;
        }
    }

    public void tambahStok(int jumlah) {
        if (jumlah > 0) {
            this.stok += jumlah;
        } else {
            throw new IllegalArgumentException("Jumlah stok yang ditambahkan tidak valid.");
        }
    }

    public PembeliPenjual getPenjual() {
        return new PembeliPenjual(this.penjual);
    }

    public void tambahReview(Review review) {
        reviews.add(review);
    }

    public void lihatReview() {
        if (reviews.isEmpty()) {
            System.out.println("Belum ada review untuk produk ini.");
            return;
        }

        System.out.println("===== Review Produk =====");
        for (int i = 0; i < reviews.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, reviews.get(i).toString());
        }
    }

    @Override
    public String toString() {
        return "----------------------------------------------"
                + "%nID: " + idProduk
                + "%nNama: " + namaProduk
                + "%nHarga: " + Utils.formatRupiah(harga)
                + "%nStok: " + stok
                + "%nDeskripsi: " + deskripsi
                + "%nLokasi: " + lokasi
                + "%nPenjual: " + penjual.getUsername();
    }
}
