package view_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import view.buyer.BuyerHomePage;
import view.buyer.TransactionHistoryPage;

public class BuyerViewController {

    Stage stage;
    private static BuyerViewController instance;
    private ItemController controller;
    private WishlistViewController wishlistVC;
    private TransactionViewController transactionVC;

    // Singleton instance
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
            wishlistVC.refreshTable();  // Memanggil refreshTable di WishlistViewController
        }
	}

	public void navigateToTransactionHistoryPage() {
		if(transactionVC == null) {
			transactionVC = new TransactionViewController(stage, new TransactionController());
		}
		transactionVC.navigateToTransaction();
	}



}
