package controller;

import database.DatabaseConnect;

public class RegisterController {
	private DatabaseConnect connect = DatabaseConnect.getInstance();

    public String validateregis(String username, String password, String phoneNumber, String address, String role) {
        if (!isunique(username)) {
            return "Username harus unik!";
        }

        if (username==null || username.trim().length() < 3) {
            return "Username harus minimal 3 karakter!";
        }

        if (password==null || password.length() < 8 || !password.matches(".*[!@#$%^&*].*")) {
            return "Password harus minimal 8 karakter dan mengandung special karakter (!,@,#,$,%,^,&,*)";
        }

        if (phoneNumber==null || !phoneNumber.matches("\\+62\\d{10}")) {
            return "Nomor Telp harus diawal dengan +62 dan diikuti dengan 10 digit";
        }
        
        if (address==null || address.trim().isEmpty()) {
            return "Alamat harus diisi!";
        }

        if (role.equals("nothing") || (!role.equals("Seller") && !role.equals("Buyer"))) {
            return "Pilih role yang tersedia! (Seller or Buyer)";
        }

        return null;
    }

    private boolean isunique(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = '"+username+"'";

        try {
            int count = connect.execQueryForCount(query);
            return count == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registerUser(String username, String password, String phoneNumber, String address, String role) {
        String msg = validateregis(username, password, phoneNumber, address, role);
        if (msg!=null) {
            System.out.println(msg);
            return false;
        }

        String query = "INSERT INTO users (username, password, phoneNumber, address, role) " +
                "VALUES ('"+username+"','"+password+"','"+phoneNumber+"','"+address+"','"+role+"')";
        
        try {
            connect.execUpdate(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
