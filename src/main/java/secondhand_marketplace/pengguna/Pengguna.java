package secondhand_marketplace.pengguna;

public abstract class Pengguna {
    private String username;
    private String password;
    private String email;
    private String noTelepon;

    public Pengguna(String username, String password, String email, String noTelepon) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.noTelepon = noTelepon;
    }

    public Pengguna(String username, String password) {
        this(username, password, "default@username.com", "0000000");
    }

    // Getter dan Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    // Verifikasi Password
    public boolean verifikasiPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Abstract method untuk role
    public abstract void tampilkanRole();
}
