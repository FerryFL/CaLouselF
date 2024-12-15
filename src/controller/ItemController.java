package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;

public class ItemController {
	
    private ObservableList<Item> items = FXCollections.observableArrayList();
    private static DatabaseConnect con = DatabaseConnect.getInstance();
    
    public ObservableList<Item> getItems() {
        return items;
    }
    
    private String generateItemId() {
		
		String lastItemId = null;
	    String newItemId = "IT001";
	    
	    String query = "SELECT Item_id FROM items ORDER BY Item_id DESC LIMIT 1";
	    try {
	        ResultSet rs = con.execQuery(query);
	        if (rs.next()) {
	            lastItemId = rs.getString("Item_id");
	        }

	        if (lastItemId != null) {
	            String numericPart = lastItemId.substring(2);
	            int newIdNumber = Integer.parseInt(numericPart) + 1;
	            newItemId = "IT" + String.format("%03d", newIdNumber);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return newItemId;
	    
	}
    
    
    public void addItemToDatabase(String name, String size, String price, String category) {
        String itemId = generateItemId();
        String defaultStatus = "Pending";
        String defaultWishlist = "0";
        String defaultOfferStatus = "No Offer";

        String query = "INSERT INTO Items (item_id, item_name, item_size, item_price, item_category, item_status, item_wishlist, item_offer_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnect.getInstance().con.prepareStatement(query)) {
            stmt.setString(1, itemId); // itemId
            stmt.setString(2, name);  // itemName
            stmt.setString(3, size);  // itemSize
            stmt.setString(4, price); // itemPrice
            stmt.setString(5, category); // itemCategory
            stmt.setString(6, defaultStatus); // itemStatus
            stmt.setString(7, defaultWishlist); // itemWishlist
            stmt.setString(8, defaultOfferStatus); // itemOfferStatus
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewItems() {
        String query = "SELECT * FROM Items WHERE Item_status LIKE 'Approved'"; 
        ArrayList<Item> itemsList = new ArrayList<>();
        
        con.rs = con.execQuery(query);  
        
        try {
            while (con.rs.next()) {
                String itemId = con.rs.getString("Item_id");
                String itemName = con.rs.getString("Item_name");
                String itemSize = con.rs.getString("Item_size");
                String itemPrice = con.rs.getString("Item_price");
                String itemCategory = con.rs.getString("Item_category");
                String itemStatus = con.rs.getString("Item_status");
                String itemWishlist = con.rs.getString("Item_wishlist");
                String itemOfferStatus = con.rs.getString("Item_offer_status");

                Item item = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus);
                itemsList.add(item);
            }
            ObservableList<Item> observableItems = FXCollections.observableArrayList(itemsList);
            this.items.setAll(observableItems); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ObservableList<Item> getPendingItems() {
        ObservableList<Item> pendingItems = FXCollections.observableArrayList();
        String query = "SELECT * FROM Items WHERE Item_status = 'Pending'";

        try (ResultSet rs = con.execQuery(query)) {
            while (rs.next()) {
                String itemId = rs.getString("Item_id");
                String itemName = rs.getString("Item_name");
                String itemSize = rs.getString("Item_size");
                String itemPrice = rs.getString("Item_price");
                String itemCategory = rs.getString("Item_category");
                String itemStatus = rs.getString("Item_status");
                String itemWishlist = rs.getString("Item_wishlist");
                String itemOfferStatus = rs.getString("Item_offer_status");

                Item item = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus);
                pendingItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingItems;
    }

    public void approveItem(Item item) {
        String query = "UPDATE Items SET Item_status = 'Approved' WHERE Item_id = ?";

        try (PreparedStatement stmt = con.con.prepareStatement(query)) {
            stmt.setString(1, item.getItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void declineItem(Item item, String reason) {
        System.out.println("Item declined: " + item.getItemId() + ", Reason: " + reason);

        String query = "DELETE FROM Items WHERE Item_id = ?";

        try (PreparedStatement stmt = con.con.prepareStatement(query)) {
            stmt.setString(1, item.getItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editItem(String itemId, String name, String size, String price, String category) {
    	String query = String.format(
				"UPDATE Items\n" + "SET Item_name = '%s', Item_category = '%s', Item_size = '%s', Item_price = '%s'\n"
						+ "WHERE Item_id = '%s'",
				name, category, size, price, itemId);

		con.execUpdate(query);
    }

    public void deleteItem(String id) {
    	String query = String.format("DELETE FROM Items WHERE Item_id = '%s'", id);
		con.execUpdate(query);
		items.removeIf(item -> item.getItemId().equals(id));
    }
    
    public void addItemToWishlist(Item item) {
        String query = "UPDATE Items SET Item_wishlist = '1' WHERE Item_id = ?";
        
        try (PreparedStatement stmt = DatabaseConnect.getInstance().con.prepareStatement(query)) {
            stmt.setString(1, item.getItemId()); 
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
