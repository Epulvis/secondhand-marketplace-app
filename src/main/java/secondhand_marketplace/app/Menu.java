package secondhand_marketplace.app;

import secondhand_marketplace.akun.ManajerAkun;
import secondhand_marketplace.pengguna.PembeliPenjual;
import secondhand_marketplace.utils.Utils;

public class Menu {
    private ManajerAkun manajerAkun = new ManajerAkun();
    private PembeliPenjual penggunaAktif = null;
    private BarangManagement barangManagement;
    private AkunManagement akunManagement = new AkunManagement();

    public Menu() {
        this.barangManagement = new BarangManagement(manajerAkun);
    }

    public void run() {
        int menu = printMenu();
        while (menu != 0) {
            switch (menu) {
                case 1:
                    akunManagement.handleRegistrasi();
                    break;
                case 2:
                    akunManagement.handleLogin();
                    penggunaAktif = akunManagement.getPenggunaAktif();
                    break;
                case 3:
                    akunManagement.handleLogout();
                    penggunaAktif = akunManagement.getPenggunaAktif();
                    break;
                case 4:
                    barangManagement.lihatBarangJualan();
                    break;
                case 5:
                    barangManagement.handlePembelian(penggunaAktif);
                    break;
                case 6:
                    barangManagement.checkoutPembelian(penggunaAktif);
                    break;
                case 7:
                    barangManagement.lacakBarang(penggunaAktif);
                    break;
                case 8:
                    barangManagement.reviewProduk(penggunaAktif);
                    break;
                case 9:
                    barangManagement.cekBarangJualan(penggunaAktif);
                    break;
                case 10:
                    barangManagement.tambahBarang(penggunaAktif);
                    break;
                case 11:
                    barangManagement.perbaruiStokBarang(penggunaAktif);
                    break;
                case 12:
                    barangManagement.lihatReviewJualan(penggunaAktif);
                    break;
                case 13:
                    barangManagement.chat(penggunaAktif);
                    break;
                case 14:
                    barangManagement.ubahStatusPengiriman(penggunaAktif);
                    break;
                case 15:
                    barangManagement.handleBuatPenawaran(penggunaAktif);
                    break;
                case 16:
                    barangManagement.tampilkanPenawaran();
                    break;
                case 17:
                    akunManagement.daftarPengguna();
                    break;
                default:
                    System.out.println("Menu tidak valid.");
                    break;
            }
            menu = printMenu();
        }
        System.out.println("Program keluar.");
    }

    public int printMenu() {
        int menu;
        if (penggunaAktif != null) {
            System.out.println("Akun sedang login: " + penggunaAktif.getUsername());
        } else {
            System.out.println("|--------------------------------------------------|");
            System.out.println("|           Belum ada akun yang login              |");
            System.out.println("|--------------------------------------------------|");
            System.out.println("%n");
        }
        System.out.println("|=================== Daftar Menu ==================|");
        System.out.println("|    1. Register                                   |");
        System.out.println("|    2. Login                                      |");
        System.out.println("|    3. Logout                                     |");
        System.out.println("|    4. Daftar Barang Yang Dijual                  |");
        System.out.println("|    5. Beli Barang                                |");
        System.out.println("|    6. Checkout Pembelian                         |");
        System.out.println("|    7. Lacak Barang Pembelian                     |");
        System.out.println("|    8. Review Produk Pembelian                    |");
        System.out.println("|    9. Cek Barang Jualan Anda                     |");
        System.out.println("|    10. Tambah Barang yang Dijual                 |");
        System.out.println("|    11. Perbarui Stok Barang                      |");
        System.out.println("|    12. Lihat Review Jualan                       |");
        System.out.println("|    13. Chat                                      |");
        System.out.println("|    14. Ubah Status Pengiriman Barang             |");
        System.out.println("|    15. Buat Penawaran                            |");
        System.out.println("|    16. Lihat Notifikasi                          |");
        System.out.println("|    17. [System] Daftar Akun Pengguna             |");
        System.out.println("|    0. Keluar                                     |");
        System.out.println("|==================================================|");
        System.out.print("> Input Menu : ");
        menu = Utils.inputInt();
        return menu;
    }
}
