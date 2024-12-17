package controller;

import database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.*;
import view_controller.ViewController;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionController {
    private DatabaseConnect connect = DatabaseConnect.getInstance();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private ObservableList<TransactionHistory> transactionHistory = FXCollections.observableArrayList();
    private Stage stage;
    
    public TransactionController() {
        ViewHistory();
    }
   
    // Method ini digunakan untuk menampilkan transaksi yang sudah pernah dilakukan
    public void ViewHistory() {
        String query = "SELECT transactions.transaction_id, items.Item_name, items.Item_category, items.Item_size, items.Item_price " +
                       "FROM transactions INNER JOIN items ON transactions.Item_id = items.Item_id";
        try {
            ResultSet rs = connect.execQuery(query);
            transactionHistory.clear();
            while (rs.next()) {
                TransactionHistory transaction = new TransactionHistory(
                    rs.getString("transaction_id"),
                    rs.getString("Item_name"),
                    rs.getString("Item_size"),
                    rs.getString("Item_price"),
                    rs.getString("Item_category")
                );
                transactionHistory.add(transaction);
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

	// Method ini digunakan untuk membuat transaction dan memasukkannya ke database
    public boolean CreateTransaction(String userId, String itemId) {
        String transactionId = generateTransactionId();
        String query = "INSERT INTO transactions (User_id, Item_id, Transaction_id) "
        		+ "VALUES ('" + userId + "', '" + itemId + "', '" + transactionId + "')";
        try {
            connect.execUpdate(query);
            transactions.add(new Transaction(userId, itemId, transactionId));
            ViewController.getInstance(stage).refreshTransactionTable();  
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
    }


    // Method ini digunakan untuk membuat ID untuk Transaction dengan Format TR ditambah 3 digit 
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
                String numericPart = lastTransactionId.substring(2);
                long newIdNumber = Long.parseLong(numericPart) + 1;
                newTransactionId = "TR" + String.format("%03d", newIdNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newTransactionId;
    }

}
