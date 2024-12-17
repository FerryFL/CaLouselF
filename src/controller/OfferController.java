package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Offer;

public class OfferController {

    private static DatabaseConnect con = DatabaseConnect.getInstance();

    public ObservableList<Offer> getOffers() {
        String query = "SELECT o.Offer_id, o.User_id, o.Item_id, o.Offer_price, o.Offer_status, i.Item_name, i.Item_category, i.Item_size, i.Item_price "
                     + "FROM Offers o "
                     + "JOIN Items i ON o.Item_id = i.Item_id "
                     + "WHERE o.Offer_status = 'Pending'";
        
        ObservableList<Offer> offerList = FXCollections.observableArrayList();

        try {
            con.rs = con.execQuery(query);

            while (con.rs.next()) {
                String offerId = con.rs.getString("Offer_id");
                String userId = con.rs.getString("User_id");
                String itemId = con.rs.getString("Item_id");
                String offerPrice = con.rs.getString("Offer_price");
                String offerStatus = con.rs.getString("Offer_status");

                String itemName = con.rs.getString("Item_name");
                String itemCategory = con.rs.getString("Item_category");
                String itemSize = con.rs.getString("Item_size");
                String itemPrice = con.rs.getString("Item_price");

                Offer offer = new Offer(
                    offerId, userId, itemId, offerPrice, offerStatus,
                    itemName, itemCategory, itemSize, itemPrice
                );

                offerList.add(offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offerList;
    }
    
    private static String generateOfferId() {
		
		String lastItemId = null;
	    String newOfferId = "OF001";
	    
	    String query = "SELECT Offer_id FROM offers ORDER BY Offer_id DESC LIMIT 1";
	    try {
	        ResultSet rs = con.execQuery(query);
	        if (rs.next()) {
	            lastItemId = rs.getString("Offer_id");
	        }

	        if (lastItemId != null) {
	            String numericPart = lastItemId.substring(2);
	            int newIdNumber = Integer.parseInt(numericPart) + 1;
	            newOfferId = "OF" + String.format("%03d", newIdNumber);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return newOfferId;
	    
	}
    
    public double getHighestOffer(String itemId) {
        String query = "SELECT MAX(Offer_price) AS highest_offer FROM Offers WHERE Item_id = ?";
        try (PreparedStatement st = con.con.prepareStatement(query)) {
            st.setString(1, itemId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble("highest_offer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; 
    }
    
    private void updateItemStatus(String itemId, String status) {
        String query = "UPDATE Items SET Item_offer_status = ? WHERE Item_id = ?";
        try (PreparedStatement st = con.con.prepareStatement(query)) {
            st.setString(1, status);
            st.setString(2, itemId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void submitOffer(String itemId, double offerPrice, String userId) {
        String offerId = generateOfferId();
        String query = "INSERT INTO Offers (Offer_id, User_id, Item_id, Offer_price, Offer_status) " +
                       "VALUES (?, ?, ?, ?, 'Pending')";
        try (PreparedStatement st = con.con.prepareStatement(query)) {
            st.setString(1, offerId);
            st.setString(2, userId);
            st.setString(3, itemId);
            st.setDouble(4, offerPrice);
            st.executeUpdate();

            updateItemStatus(itemId, "Pending");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void offerPrice(String itemId, String itemPrice, String userId) {
    	
    	
    	String offerId = generateOfferId();
    	String query = "INSERT INTO Offers(Offer_id, User_id, Item_id, Offer_price, Offer_status) " + "VALUES ('"
				+ offerId + "', '" + userId + "', '" + itemId +  "', '" + itemPrice + "', 'Pending')";

		con.execUpdate(query);
		
		String queryIt = String.format("UPDATE Item SET Item_offer_status = 'Pending' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(queryIt);
		
	}

    public void acceptOffer(Offer offer) {
        String updateOfferQuery = "UPDATE Offers SET Offer_status = 'Accepted' WHERE Offer_id = ?";
        String updateItemQuery = "UPDATE Items SET Item_offer_status = 'Accepted' WHERE Item_id = ?";
        TransactionController transactionController = new TransactionController();

        try (PreparedStatement st = DatabaseConnect.getInstance().con.prepareStatement(updateOfferQuery)) {
            st.setString(1, offer.getOfferId());
            st.executeUpdate();

            try (PreparedStatement stItem = DatabaseConnect.getInstance().con.prepareStatement(updateItemQuery)) {
                stItem.setString(1, offer.getItemId());
                stItem.executeUpdate();
            }
            transactionController.CreateTransaction(offer.getUserId(), offer.getItemId());

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while accepting the offer.");
        }
    }


    public void declineOffer(Offer offer, String reason) {
        String query = "UPDATE Offers SET Offer_status = 'Declined' WHERE Offer_id = ?";
        try (PreparedStatement st = DatabaseConnect.getInstance().con.prepareStatement(query)) {
            st.setString(1, offer.getOfferId());
            st.executeUpdate();
            
            String updateItemQuery = "UPDATE Items SET Item_offer_status = 'Declined' WHERE Item_id = ?";
            try (PreparedStatement stItem = DatabaseConnect.getInstance().con.prepareStatement(updateItemQuery)) {
                stItem.setString(1, offer.getItemId());
                stItem.executeUpdate();
            }
            
            showAlert("Offer Declined", "The offer has been declined for the following reason: " + reason);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }


	public void checkConnection() {
		System.out.println("Checking connection: " + con.con);

		
	}
}
