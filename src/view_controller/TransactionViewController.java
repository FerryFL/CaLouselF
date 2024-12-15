package view_controller;

import controller.TransactionController;
import database.DatabaseConnect;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.buyer.TransactionHistoryPage;

public class TransactionViewController {
	private Stage stage;
	private TransactionController controller;
	private static TransactionViewController instance;
	
	public static TransactionViewController getInstance(Stage stage, TransactionController controller) {
		if (instance == null && stage != null) {
            instance = new TransactionViewController(stage, controller);
        }
        return instance;
	}
	
	public TransactionViewController(Stage stage, TransactionController controller) {
		this.stage = stage;
		this.controller = controller;
	}
	
	public void navigateToTransaction() {
		TransactionHistoryPage transactionView = new TransactionHistoryPage(stage, controller);
		Scene scene = new Scene(transactionView.getView(),400,300);
		stage.setScene(scene);
		stage.setTitle("Transaction");
		stage.show();
	}
	
}
