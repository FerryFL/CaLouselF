package controller;

import java.sql.ResultSet;

import database.DatabaseConnect;

public class LoginController {

    private DatabaseConnect connect = DatabaseConnect.getInstance();

    public String validateLogin(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return "Admin";
        }

        String query = "SELECT role FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        try {
            ResultSet rs = connect.execQuery(query);
            if (rs.next()) {
                return rs.getString("role"); // Return the role (Seller/Buyer)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Invalid username/password or no role found
    }
}
