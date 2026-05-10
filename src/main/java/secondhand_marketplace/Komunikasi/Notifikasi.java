package secondhand_marketplace.Komunikasi;

import java.util.ArrayList;
import java.util.HashMap;

public class Notifikasi implements NotifikasiInterface {

    // Storage for notifications
    private HashMap<String, ArrayList<String>> daftarNotifikasi = new HashMap<>();

    @Override
    public void kirimNotifikasi(String penerima, String pesan) {
        daftarNotifikasi.putIfAbsent(penerima, new ArrayList<>());
        daftarNotifikasi.get(penerima).add(pesan);
        System.out.println("Notifikasi terkirim ke " + penerima + ": " + pesan);
    }

    @Override
    public void lihatNotifikasi(String idPengguna) {
        ArrayList<String> notifikasi = daftarNotifikasi.get(idPengguna);

        if (notifikasi == null || notifikasi.isEmpty()) {
            System.out.println("Tidak ada notifikasi untuk pengguna " + idPengguna);
        } else {
            System.out.println("Notifikasi untuk " + idPengguna + ":");
            for (String pesan : notifikasi) {
                System.out.println("- " + pesan);
            }
        }
    }
}
