package controller;

import database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Transaction;
import model.TransactionHistory;
import model.Wishlist;
import view_controller.BuyerViewController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionController {
    private DatabaseConnect connect = DatabaseConnect.getInstance();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private ObservableList<TransactionHistory> transactionHistory = FXCollections.observableArrayList();
    private Stage stage;
    private ItemController controller;
    
    public TransactionController() {
        ViewHistory();
    }
   
    public void ViewHistory() {
        String query = "SELECT transactions.transaction_id, items.Item_name, items.Item_category, items.Item_size, items.Item_price FROM transactions "
        		+ "INNER JOIN items ON transactions.Item_id = items.Item_id";
        try {
            ResultSet rs = connect.execQuery(query);
            transactions.clear(); 
            while (rs.next()) {
                TransactionHistory transactions = new TransactionHistory(
                    rs.getString("transaction_id"),
                    rs.getString("Item_name"),
                    rs.getString("Item_size"),
                    rs.getString("Item_price"),
                    rs.getString("Item_category")
                );
                transactionHistory.add(transactions);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ObservableList<TransactionHistory> getTransactionHistory() {
		return transactionHistory;
	}

	public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean CreateTransaction(String userId, String itemId) {
        String transactionId = generateTransactionId();
        String query = "INSERT INTO transactions (User_id, Item_id, Transaction_id) "
        		+ "VALUES ('" + userId + "', '" + itemId + "', '" + transactionId + "')";
       
        try {
            connect.execUpdate(query);
            transactions.add(new Transaction(userId, itemId, transactionId));
            BuyerViewController.getInstance(stage, controller).refreshTransactionTable();  
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
    }



    private String generateTransactionId() {
        String lastTransactionId = null;
        String newTransactionId = "TR001";

        String query = "SELECT Transaction_id FROM transactions ORDER BY Transaction_id DESC LIMIT 1";
        try {
            ResultSet rs = connect.execQuery(query);
            if (rs.next()) {
                lastTransactionId = rs.getString("Transaction_id");
            }

            if (lastTransactionId != null) {
                // Extract numeric part after "TR"
                String numericPart = lastTransactionId.substring(2);
                long newIdNumber = Long.parseLong(numericPart) + 1;

                // Format new ID
                newTransactionId = "TR" + String.format("%03d", newIdNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newTransactionId;
    }

 // Remove a transaction by ID
    public boolean removeTransaction(String transactionId) {
        String query = String.format("DELETE FROM transactions WHERE Transaction_id = '%s'", transactionId);

        try {
            connect.execUpdate(query);
            transactions.removeIf(transaction -> transaction.getTransactionId().equals(transactionId)); // Update ObservableList
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
