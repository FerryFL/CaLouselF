package view_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.buyer.AddWishlistPage;
import view.buyer.WishlistPage;
import controller.WishlistController;

public class WishlistViewController {
	private Stage stage;
    private WishlistController wishlistController;

    public WishlistViewController(Stage stage, WishlistController wishlistController) {
        this.stage = stage;
        this.wishlistController = wishlistController;
    }

    public void navigateToWishlistPage() {
        WishlistPage wishlistPage = new WishlistPage(stage, wishlistController);
        Scene scene = new Scene(wishlistPage.getView(), 800, 600);  // Example dimensions
        stage.setScene(scene);
        stage.setTitle("Wishlist Page");
        stage.show();
    }
    
    public void navigateToAddWishlistPage() {
        AddWishlistPage addWishlistPage = new AddWishlistPage(stage, wishlistController);
        Scene scene = new Scene(addWishlistPage.getView(), 400, 300);  
        stage.setScene(scene);
        stage.setTitle("Add Wishlist");
        stage.show();
    }

    public void refreshTable() {
        if (wishlistController != null) {
            wishlistController.getWishlist().clear();  // Clear existing data
            wishlistController.loadWishlistFromDatabase();  // Reload data from the database
        }
    }
}