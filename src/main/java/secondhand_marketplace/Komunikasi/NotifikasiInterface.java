package secondhand_marketplace.Komunikasi;

public interface NotifikasiInterface {
    void kirimNotifikasi(String penerima, String pesan);
    void lihatNotifikasi(String idPengguna);
}
