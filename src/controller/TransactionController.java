package controller;

import database.DatabaseConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionController {
    private DatabaseConnect connect = DatabaseConnect.getInstance();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public TransactionController() {
        loadTransactionsFromDatabase();
    }

    public void loadTransactionsFromDatabase() {
        String query = "SELECT transactions.User_id, items.Item_name, Transaction_id FROM transactions INNER JOIN items ON transactions.Item_id = items.Item_id ";

        try {
            ResultSet rs = connect.execQuery(query);
            transactions.clear(); // Clear existing transactions
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getString("Transaction_id"),
                    rs.getString("User_id"),
                    rs.getString("Item_name")
                );
                transactions.add(transaction); // Add to the observable list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter for the transactions list
    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean addTransaction(String userId, String itemId) {
        String transactionId = generateTransactionId();
        String query = "INSERT INTO transactions (User_id, Item_id, Transaction_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connect.getConnection().prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, itemId);
            stmt.setString(3, transactionId);

            stmt.executeUpdate(); 
            transactions.add(new Transaction(userId, itemId, transactionId)); 
            return true;
        } catch (SQLException e) {
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
