package secondhand_marketplace.utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    private static Scanner scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8.name());

    public static String inputString() {
        return scanner.nextLine();
    }

    public static int inputInt() {
        while (true) {
            try {
                int result = scanner.nextInt();
                clearBuffer(); // Menyapu karakter newline yang tertinggal
                return result;
            } catch (InputMismatchException e) {
                System.out.print("[Error] Input salah! Harap masukkan angka yang valid. Masukkan Ulang : ");
                clearBuffer(); // Menyapu karakter yang tersisa
            }
        }
    }

    public static double inputDouble() {
        while (true) {
            try {
                double result = scanner.nextDouble();
                clearBuffer(); // Menyapu karakter newline yang tertinggal
                return result;
            } catch (InputMismatchException e) {
                System.out.print("[Error] Input salah! Harap masukkan angka desimal yang valid. Masukkan Ulang : ");
                clearBuffer(); // Menyapu karakter yang tersisa
            }
        }
    }

    // Mengkonsumsi newline atau karakter lain yang tertinggal di buffer
    public static void clearBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // Konsumsi newline atau karakter sisa lainnya
        }
    }

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    public static String formatRupiah(double harga) {
        NumberFormat formatHarga = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatHarga.format(harga);
    }
}
