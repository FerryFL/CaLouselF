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
import view.admin.AdminHomePage;
import view.buyer.MakeOfferPage;
import view.buyer.WishlistPage;

public class ViewController {

    Stage stage;
    private static ViewController instance;
    private ItemController itemController;
    private OfferController offerController;

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
        stage.setTitle("CaLouselF");
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
    	if (itemController == null) {
            itemController = new ItemController();
        }
        
        if (offerController == null) {
            offerController = new OfferController();
        }
        MakeOfferPage makeOfferPage = new MakeOfferPage(stage, itemController, offerController);
        stage.setScene(makeOfferPage.createMakeOfferScene());
    }
    
    public void navigateToAdminHomePage() {
    	AdminHomePage adminHomePage = new AdminHomePage(stage, itemController);
    	stage.setScene(adminHomePage.createAdminHomeScene());
    }
}
