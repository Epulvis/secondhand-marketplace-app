package secondhand_marketplace.Komunikasi;

import secondhand_marketplace.pengguna.PembeliPenjual;
import java.util.ArrayList;

public class Chat {
    private PembeliPenjual pengirim;
    private PembeliPenjual penerima;
    private String pesan;
    private static ArrayList<Chat> history = new ArrayList<>(); // Menyimpan objek Chat

    // Konstruktor untuk inisialisasi pengirim, penerima, dan pesan
    public Chat(PembeliPenjual pengirim, PembeliPenjual penerima, String pesan) {
        this.pengirim = new PembeliPenjual(pengirim);
        this.penerima = new PembeliPenjual(penerima);
        this.pesan = pesan;
    }

    // Metode untuk mengirim pesan
    public void kirimPesan() {
        Chat chat = new Chat(pengirim, penerima, pesan);
        history.add(chat); // Simpan objek Chat ke riwayat
        System.out.println(
                "Pesan berhasil dikirim: " + pengirim.getUsername() + " ke " + penerima.getUsername() + ": " + pesan);
    }

    // Metode untuk menampilkan riwayat chat hanya untuk pengguna yang terlibat
    public static void tampilkanHistory(PembeliPenjual penggunaAktif) {
        if (history.isEmpty()) {
            System.out.println("Tidak ada riwayat chat.");
        } else {
            boolean ditemukan = false;
            System.out.println("===== Riwayat Chat =====");
            // Periksa semua chat dan tampilkan yang melibatkan penggunaAktif
            for (Chat chat : history) {
                // Menampilkan chat yang melibatkan penggunaAktif, baik sebagai pengirim maupun
                // penerima
                if (chat.pengirim.getUsername().equals(penggunaAktif.getUsername()) ||
                        chat.penerima.getUsername().equals(penggunaAktif.getUsername())) {
                    System.out.println(
                            chat.pengirim.getUsername() + " ke " + chat.penerima.getUsername() + ": " + chat.pesan);
                    ditemukan = true;
                }
            }
            if (!ditemukan) {
                System.out.println("Tidak ada riwayat chat yang melibatkan Anda.");
            }
        }
    }
}
