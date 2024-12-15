package database;

import java.sql.*;

public class DatabaseConnect {

    public final String USERNAME = "root";
    public final String PASSWORD = ""; 
    public final String DATABASE = "calouself"; 
    public final String HOST = "localhost:3306"; 
    public final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    public Connection con;
    public Statement st;
    private static DatabaseConnect databaseConnect;

    public ResultSet rs;
    private ResultSetMetaData rsm;

    public static DatabaseConnect getInstance() {
        if (databaseConnect == null) {
            return new DatabaseConnect();
        } else {
            return databaseConnect;
        }
    }
    
    public Connection getConnection() {
        return con;
    }


    private DatabaseConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String query) {
        try {
            rs = st.executeQuery(query);
            rsm = rs.getMetaData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void execUpdate(String query) {
        try {
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int execQueryForCount(String query) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String execQueryForSingleResult(String query) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1); // Get the first column of the result
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no result or an error occurs
    }
    

    
}
