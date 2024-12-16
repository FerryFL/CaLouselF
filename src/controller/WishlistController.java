package controller;

import database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Wishlist;
import view_controller.BuyerViewController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistController {
    private DatabaseConnect connect = DatabaseConnect.getInstance();
    private ObservableList<Wishlist> wishlist = FXCollections.observableArrayList();
    
    private Stage stage;
    private ItemController controller;
    
    public WishlistController() {
        loadWishlistFromDatabase();
    }

    public void loadWishlistFromDatabase() {
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

    public boolean addWishlist(String itemId, String userId) {
        String wishlistId = generateWishlistId();
        String query = "INSERT INTO wishlists (Wishlist_id, Item_id, User_id) VALUES ('"
                + wishlistId + "', '" + itemId + "', '" + userId + "')";

        try {
            connect.execUpdate(query);
            wishlist.add(new Wishlist(wishlistId, itemId, userId)); // Update the ObservableList
            BuyerViewController.getInstance(stage, controller).refreshTable();  // Memanggil metode refreshTable
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
    }

    
    public boolean removeWishlist(String wishlistId) {
        String query = "DELETE FROM wishlists WHERE Wishlist_id = '" + wishlistId + "'";
        try {
            connect.execUpdate(query);
            wishlist.removeIf(wl -> wl.getWishlistId().equals(wishlistId));  // Update the ObservableList
            BuyerViewController.getInstance(stage, controller).refreshTable();  // Memanggil metode refreshTable
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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
                // Extract the numeric part, starting after the "WL" prefix
                String numericPart = lastWishlistId.substring(2);

                // Use Long to handle large numbers
                long newIdNumber = Long.parseLong(numericPart) + 1;

                // Format back to "WL" + padded number
                newWishlistId = "WL" + String.format("%03d", newIdNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Log or handle the case where the numeric part is invalid
        }

        return newWishlistId;
    }
}
