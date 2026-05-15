package secondhand_marketplace.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import secondhand_marketplace.akun.ManajerAkun;
import secondhand_marketplace.pengguna.PembeliPenjual;
import secondhand_marketplace.produk.Produk;
import secondhand_marketplace.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BarangManagementTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream systemOutContent;

    @Before
    public void setUp() {
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    @After
    public void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        resetUtilsScanner(originalSystemIn);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        resetUtilsScanner(testIn);
    }

    private void resetUtilsScanner(InputStream in) {
        try {
            Field scannerField = Utils.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(null, new Scanner(in));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int countOccurrences(String text, String needle) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(needle, index)) != -1) {
            count++;
            index += needle.length();
        }
        return count;
    }

    @Test
    public void testTambahBarangRequiresNonEmptyFields() {
        provideInput("\nSepatu\n10000\n2\n\nSepatu bekas\n\nBandung\n");

        BarangManagement barangManagement = new BarangManagement(new ManajerAkun());
        PembeliPenjual penggunaAktif = new PembeliPenjual("userTest", "passTest", "user@test.com", "0812");

        barangManagement.tambahBarang(penggunaAktif);

        String output = systemOutContent.toString();
        assertTrue(output.contains("[Error] Wajib memasukkan data barang."));
        assertEquals(3, countOccurrences(output, "[Error] Wajib memasukkan data barang."));

        List<Produk> barang = barangManagement.getBarangJualan();
        assertEquals(1, barang.size());
        Produk produk = barang.get(0);
        assertEquals("Sepatu", produk.getNamaProduk());
        assertEquals("Sepatu bekas", produk.getDeskripsi());
        assertEquals("Bandung", produk.getLokasi());
    }
}
