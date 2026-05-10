package secondhand_marketplace.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import secondhand_marketplace.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MenuTest {

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

    @Test
    public void testPrintMenuWithoutLogin() {
        // Provide input for printMenu()
        provideInput("5\n");

        Menu menu = new Menu();
        int result = menu.printMenu();

        assertEquals(5, result);
        assertTrue(systemOutContent.toString().contains("Belum ada akun yang login"));
        assertTrue(systemOutContent.toString().contains("Daftar Menu"));
    }

    @Test
    public void testRunInvalidMenuThenExit() {
        // Input: 99 (invalid), then 0 (exit)
        provideInput("99\n0\n");

        Menu menu = new Menu();
        menu.run();

        String output = systemOutContent.toString();
        assertTrue(output.contains("Menu tidak valid."));
        assertTrue(output.contains("Program keluar."));
    }

    @Test
    public void testRunRegisterThenExit() {
        // Input: 1 (Register) -> "userTest", "passTest", "user@test.com", "0812" -> 0
        // (Exit)
        provideInput("1\nuserTest\npassTest\nuser@test.com\n0812\n0\n");

        Menu menu = new Menu();
        menu.run();

        String output = systemOutContent.toString();
        assertTrue(output.contains("Registrasi berhasil!"));
        assertTrue(output.contains("Program keluar."));
    }
}
