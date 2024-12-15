package view_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.seller.UploadItemPage;
import view.seller.EditItemPage;
import view.seller.SellerHomePage;
import view.seller.ViewOfferPage;
import controller.ItemController;
import controller.OfferController;
import model.Item;
import view.buyer.MakeOfferPage;

public class ViewController {

    Stage stage;
    private static ViewController instance;
    private ItemController itemController;

    public static ViewController getInstance(Stage stage, ItemController controller) {
        if (instance == null && stage != null) {
            instance = new ViewController(stage, controller);
        }
        return instance;
    }

    private ViewController(Stage stage, ItemController controller) {
        this.stage = stage;
        this.itemController = controller;
    }

    public void navigateToSellerHomePage() {
        SellerHomePage homePage = new SellerHomePage(stage, itemController);
        stage.setScene(homePage.createHomePageScene());  
    }

    public void navigateToUploadItemPage() {
        UploadItemPage uploadItemPage = new UploadItemPage(stage, itemController);
        stage.setScene(new Scene(uploadItemPage.getRoot(), 1000, 600));
    }

    public void navigateToEditItemPage(Item item) {
        EditItemPage editItemPage = new EditItemPage(item, itemController, stage);
        stage.setScene(new Scene(editItemPage.getRoot(), 1000, 600));
    }
    
    public void navigateToViewOfferPage() {
        ViewOfferPage viewOfferPage = new ViewOfferPage(stage, new OfferController());
        stage.setScene(viewOfferPage.createViewOfferScene());
    }
    
    public void navigateToMakeOfferPage() {
        MakeOfferPage makeOfferPage = new MakeOfferPage(stage, itemController, new OfferController());
        stage.setScene(makeOfferPage.createMakeOfferScene());
    }
}
