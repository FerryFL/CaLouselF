package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnect;

public class UserController {
	private DatabaseConnect connect = DatabaseConnect.getInstance();
	
	// Method ini digunakan untuk autentikasi login user
    public String Login(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return "Admin";
        }
        String query = "SELECT role FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        try {
            ResultSet rs = connect.execQuery(query);
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    // Method ini digunakan mengecek validasi akuns saat melakukan registrasi
    public String CheckAccountValidation(String Username, String Password, String Phone_Number, String Address, String Role) {
        if (!isunique(Username)) {
            return "Username harus unik!";
        }

        if (Username==null || Username.trim().length() < 3) {
            return "Username harus minimal 3 karakter!";
        }

        if (Password==null || Password.length() < 8 || !Password.matches(".*[!@#$%^&*].*")) {
            return "Password harus minimal 8 karakter dan mengandung special karakter (!,@,#,$,%,^,&,*)";
        }

        if (Phone_Number==null || !Phone_Number.matches("\\+62\\d{9}")) {
            return "Nomor Telp harus diawal dengan +62 dan diikuti dengan 9 digit";
        }
        
        if (Address==null || Address.trim().isEmpty()) {
            return "Alamat harus diisi!";
        }

        if (Role.equals("nothing") || (!Role.equals("Seller") && !Role.equals("Buyer"))) {
            return "Pilih role yang tersedia! (Seller or Buyer)";
        }

        return null;
    }

    // Method ini digunakan untuk mengecek apakah username yang dibuat telah unik
    private boolean isunique(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = '"+username+"'";
        try {
            int count = connect.execQueryForCount(query);
            return count == 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Method ini digunakan untuk membuat ID untuk User dengan Format US ditambah 3 digit 
    public String generateUserId() {
		
		String lastUserId = null;
	    String newUserId = "US001";
	    String query = "SELECT User_id FROM users ORDER BY User_id DESC LIMIT 1";
	    try {
	        ResultSet rs = connect.execQuery(query);
	        if (rs.next()) {
	            lastUserId = rs.getString("User_id");
	        }

	        if (lastUserId != null) {
	            String numericPart = lastUserId.substring(2);
	            int newIdNumber = Integer.parseInt(numericPart) + 1;
	            newUserId = "US" + String.format("%03d", newIdNumber);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return newUserId;
	    
	}
    
    // Method ini digunakan untuk melakukan registrasi akund dan menambahkannya ke database
    public boolean Register(String username, String password, String phoneNumber, String address, String role) {
        String msg = CheckAccountValidation(username, password, phoneNumber, address, role);
        if (msg!=null) {
            System.out.println(msg);
            return false;
        }

        String query = "INSERT INTO users " + "VALUES ('"+generateUserId()+"','"+username+"','"+password+"','"+phoneNumber+"','"+address+"','"+role+"')";
        try {
            connect.execUpdate(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
