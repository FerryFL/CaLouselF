package view_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.seller.SellerHomePage;
import view.seller.UploadItemPage;
import view.seller.EditItemPage;
import controller.ItemController;
import model.Item;

public class ViewController {

    Stage stage;
    private static ViewController instance;
    private ItemController controller;

    // Singleton instance
    public static ViewController getInstance(Stage stage, ItemController controller) {
        if (instance == null && stage != null) {
            instance = new ViewController(stage, controller);
        }
        return instance;
    }

    private ViewController(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void navigateToSellerHomePage() {
        SellerHomePage homePage = new SellerHomePage(stage, controller);
        stage.setScene(homePage.createHomePageScene());  
    }

    public void navigateToUploadItemPage() {
        UploadItemPage uploadItemPage = new UploadItemPage(stage, controller);
        stage.setScene(new Scene(uploadItemPage.getRoot(), 600, 400));
    }

    public void navigateToEditItemPage(Item item) {
        EditItemPage editItemPage = new EditItemPage(item, controller, stage);
        stage.setScene(new Scene(editItemPage.getRoot(), 600, 400));
    }
}
