package controller;

import database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Wishlist;
import view_controller.ViewController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistController {
    private DatabaseConnect connect = DatabaseConnect.getInstance();
    private ObservableList<Wishlist> wishlist = FXCollections.observableArrayList();
    
    private Stage stage;
    
    public WishlistController() {
        ViewWishlist();
    }
    
    // Method ini digunakan untuk menampilkan wishlist
    public void ViewWishlist() {
        String query = "SELECT wishlists.Wishlist_id, items.Item_name, wishlists.User_id "
        		+ "FROM wishlists "
        		+ "INNER JOIN items ON wishlists.Item_id = items.Item_id";
        try {
            ResultSet rs = connect.execQuery(query);
            wishlist.clear();
            while (rs.next()) {
                Wishlist wl = new Wishlist(
                    rs.getString("Wishlist_id"),
                    rs.getString("Item_name"),
                    rs.getString("User_id")
                );
                wishlist.add(wl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Wishlist> getWishlist() {
        return wishlist;
    }

    // Method ini digunakan untuk menambahkan item ke wishlist dan dimasukkan ke database
    public boolean AddWishlist(String itemId, String userId) {
        String wishlistId = generateWishlistId();
        String query = "INSERT INTO wishlists (Wishlist_id, Item_id, User_id) VALUES ('"
                + wishlistId + "', '" + itemId + "', '" + userId + "')";
        try {
            connect.execUpdate(query);
            wishlist.add(new Wishlist(wishlistId, itemId, userId)); 
            ViewController.getInstance(stage).refreshWishlistTable();  
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
    }
    
    // Method ini digunakan untuk menghapus item dari wishlist
    public boolean RemoveWishlist(String wishlistId) {
        String query = "DELETE FROM wishlists WHERE Wishlist_id = '" + wishlistId + "'";
        try {
            connect.execUpdate(query);
            wishlist.removeIf(wl -> wl.getWishlistId().equals(wishlistId));  // Update the ObservableList
            ViewController.getInstance(stage).refreshWishlistTable();  // Memanggil metode refreshTable
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Method ini digunakan untuk membuat ID untuk Wishlist dengan Format WL ditambah 3 digit 
    private String generateWishlistId() {
        String lastWishlistId = null;
        String newWishlistId = "WL001";
        String query = "SELECT Wishlist_id FROM wishlists ORDER BY Wishlist_id DESC LIMIT 1";
        try {
            ResultSet rs = connect.execQuery(query);
            if (rs.next()) {
                lastWishlistId = rs.getString("Wishlist_id");
            }

            if (lastWishlistId != null) {
                String numericPart = lastWishlistId.substring(2);
                long newIdNumber = Long.parseLong(numericPart) + 1;
                newWishlistId = "WL" + String.format("%03d", newIdNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return newWishlistId;
    }
}
