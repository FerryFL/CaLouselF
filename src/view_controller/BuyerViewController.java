package view_controller;

import javafx.stage.Stage;
import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import view.buyer.BuyerHomePage;

public class BuyerViewController {

    Stage stage;
    private static BuyerViewController instance;
    private ItemController controller;
    private WishlistViewController wishlistVC;
    private TransactionViewController transactionVC;

    public static BuyerViewController getInstance(Stage stage, ItemController controller) {
        if (instance == null && stage != null) {
            instance = new BuyerViewController(stage, controller);
        }
        return instance;
    }

    private BuyerViewController(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void navigateToBuyerHomePage() {
        BuyerHomePage homePage = new BuyerHomePage(stage, controller);
        stage.setScene(homePage.createHomePageScene());  
    }
    
    public void navigateToWishlistPage() {
    	if (wishlistVC == null) {
            wishlistVC = new WishlistViewController(stage, new WishlistController());
        }
    	wishlistVC.navigateToWishlistPage();
    }

	public void refreshTable() {
		if (wishlistVC != null) {
            wishlistVC.refreshTable();
        }
	}
	
	public void refreshTransactionTable() {
		if (transactionVC != null) {
			transactionVC.refreshTable();
		}
	}

	public void navigateToTransactionHistoryPage() {
		if(transactionVC == null) {
			transactionVC = new TransactionViewController(stage, new TransactionController());
		}
		transactionVC.navigateToTransaction();
	}



}
