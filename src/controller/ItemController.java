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
    
    public boolean UploadItem(String name, String size, String price, String category) {
        String itemId = generateItemId();
        String defaultStatus = "Pending";
        String defaultWishlist = "0";
        String defaultOfferStatus = "No Offer";
        String msg = CheckItemValidation(name, size, price, category);
        
        if (msg!=null) {
            System.out.println(msg);
            return false;
        }

        String query = "INSERT INTO Items (item_id, item_name, item_size, item_price, item_category, item_status, item_wishlist, item_offer_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = DatabaseConnect.getInstance().con.prepareStatement(query)) {
            st.setString(1, itemId); 
            st.setString(2, name); 
            st.setString(3, size);  
            st.setString(4, price); 
            st.setString(5, category); 
            st.setString(6, defaultStatus);
            st.setString(7, defaultWishlist);
            st.setString(8, defaultOfferStatus); 
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public boolean EditItem(String itemId, String name, String size, String price, String category) {
        String msg = CheckItemValidation(name, size, price, category);
        
        if (msg!=null) {
            System.out.println(msg);
            return false;
        }
        
        String query = String.format(
				"UPDATE Items\n" + "SET Item_name = '%s', Item_category = '%s', Item_size = '%s', Item_price = '%s'\n"
						+ "WHERE Item_id = '%s'",
				name, category, size, price, itemId);;
				con.execUpdate(query);
				return true;

    }
    
    public void DeleteItem(String id) {
    	String query = String.format("DELETE FROM Items WHERE Item_id = '%s'", id);
		con.execUpdate(query);
		items.removeIf(item -> item.getItemId().equals(id));
    }
    
    public void browseItemByName(String name) {
        String query = "SELECT * FROM Items WHERE Item_name LIKE ?"; 
        ArrayList<Item> itemsList = new ArrayList<>();
        
        try (PreparedStatement st = con.con.prepareStatement(query)) {
            st.setString(1, "%" + name + "%"); 
            ResultSet rs = st.executeQuery();
            
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
                itemsList.add(item);
            }

            ObservableList<Item> observableItems = FXCollections.observableArrayList(itemsList);
            this.items.setAll(observableItems);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ViewItem() {
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
    
    public String CheckItemValidation(String name, String size, String price, String category) {

        if (name.trim().isEmpty()) {
            return "Nama harus diisi";
        }
        if (name.trim().length() < 3) {
            return "Name harus minimal 3 karakter!";
        }
        if(size.trim().isEmpty()) {
        	return "Size harus diisi!";
        }
        
        if (price.trim().isEmpty()) {
        	return "Price harus diisi!";
        }
        
        try {
        	double parsedPrice = Double.parseDouble(price.trim());
        	if (parsedPrice <= 0) {
        		return "Price harus lebih dari 0";
        	}
        } catch (NumberFormatException e) {
        	return "Price harus berupa angka!";
        }
        
        if (category.trim().isEmpty()) {
        	return "Category harus diisi!";
        }
        if (category.trim().length() < 3) {
        	return "Category harus minimal 3 karakter!";
        }


        return null;
    }
    
    public ObservableList<Item> ViewRequestedItem() {
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

    public void ApproveItem(Item item) {
        String query = "UPDATE Items SET Item_status = 'Approved' WHERE Item_id = ?";
        try (PreparedStatement st = con.con.prepareStatement(query)) {
            st.setString(1, item.getItemId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeclineItem(Item item, String reason) {
        System.out.println("Item declined: " + item.getItemId() + ", Reason: " + reason);
        String query = "DELETE FROM Items WHERE Item_id = ?";
        try (PreparedStatement st = con.con.prepareStatement(query)) {
            st.setString(1, item.getItemId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void ViewAcceptedItem(Item item) {
    	String query = "SELECT * FROM Items WHERE Item_offer_status LIKE 'Accepted'"; 
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

                Item item1 = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus);
                itemsList.add(item1);
            }
            ObservableList<Item> observableItems = FXCollections.observableArrayList(itemsList);
            this.items.setAll(observableItems); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItemToWishlist(Item item) {
        String query = "UPDATE Items SET Item_wishlist = '1' WHERE Item_id = ?";
        try (PreparedStatement st = DatabaseConnect.getInstance().con.prepareStatement(query)) {
            st.setString(1, item.getItemId()); 
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
