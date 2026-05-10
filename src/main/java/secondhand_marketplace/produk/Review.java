package secondhand_marketplace.produk;

import secondhand_marketplace.pengguna.PembeliPenjual;

public class Review {
    private PembeliPenjual pengguna;
    private int rating; // 1-5
    private String komentar;

    public Review(PembeliPenjual pengguna, int rating, String komentar) {
        this.pengguna = new PembeliPenjual(pengguna);
        this.rating = rating;
        this.komentar = komentar;
    }

    public PembeliPenjual getPengguna() {
        return new PembeliPenjual(this.pengguna);
    }

    public int getRating() {
        return rating;
    }

    public String getKomentar() {
        return komentar;
    }

    @Override
    public String toString() {
        return String.format("Rating: %d/5%nKomentar: %s%nDiberikan oleh: %s",
                rating, komentar, pengguna.getUsername());
    }
}
