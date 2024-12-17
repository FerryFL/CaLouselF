package view_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.*;
import model.Item;
import view.seller.*;
import view.admin.AdminHomePage;
import view.admin.ViewRequestedItemPage;
import view.buyer.*;
import view.guest.*;

public class ViewController {

    private Stage stage;
    private static ViewController instance;

    private ItemController itemController;
    private OfferController offerController;
    private UserController userController;
    private WishlistController wishlistController;
    private TransactionController transactionController;

    private void setScene(Scene scene, String title) {
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
    
    public ViewController(Stage stage) {
        this.stage = stage;
        this.itemController = new ItemController();
        this.offerController = new OfferController();
        this.userController = new UserController();
        this.wishlistController = new WishlistController();
        this.transactionController = new TransactionController();
    }

    public static ViewController getInstance(Stage stage) {
        if (instance == null && stage != null) {
            instance = new ViewController(stage);
        }
        return instance;
    }

    // Guest Page
    public void navigateToLogin() {
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getLoginForm(), 300, 250);

        loginView.getLoginButton().setOnAction(e -> {
            String username = loginView.getUsernameField().getText();
            String password = loginView.getPasswordField().getText();
            String role = userController.Login(username, password);

            if(role!= null) {
            	if(username.equals("admin") && password.equals("admin")){
                	System.out.println("Selamat Datang Admin!");
                	navigateToAdminHomePage();
                }else if (role.equals("Seller")) {
                    System.out.println("Selamat Datang Seller!");
                    navigateToSellerHomePage();
               } else if(role.equals("Buyer")) {
    	           	System.out.println("Selamat Datang Buyer!");
    	           	navigateToBuyerHomePage();
               }else {
                   System.out.println("Invalid Username/Password");
               }
            }else {
            	System.out.println("Invalid Username/Password");
            }
        });

        setScene(scene, "Login");
    }

    public void navigateToRegister() {
        RegisterView registerView = new RegisterView();
        setScene(new Scene(registerView.display(), 300, 400), "Register");
    }

    // Seller Page
    public void navigateToSellerHomePage() {
        SellerHomePage homePage = new SellerHomePage(stage, itemController);
        setScene(homePage.createHomePageScene(), "Seller Home Page");
    }

    public void navigateToUploadItemPage() {
        UploadItemPage uploadPage = new UploadItemPage(stage, itemController);
        setScene(new Scene(uploadPage.getRoot(), 1000, 600), "Upload Item");
    }

    public void navigateToEditItemPage(Item item) {
        EditItemPage editPage = new EditItemPage(item, itemController, stage);
        setScene(new Scene(editPage.getRoot(), 1000, 600), "Edit Item");
    }

    public void navigateToViewOfferPage() {
        ViewOfferPage viewOfferPage = new ViewOfferPage(stage, offerController);
        setScene(viewOfferPage.createViewOfferScene(), "View Offers");
    }

    // Buyer Page
    public void navigateToBuyerHomePage() {
        BuyerHomePage homePage = new BuyerHomePage(stage, itemController);
        setScene(homePage.createHomePageScene(), "Buyer Home Page");
    }

    public void navigateToMakeOfferPage() {
        MakeOfferPage makeOfferPage = new MakeOfferPage(stage, itemController, offerController);
        setScene(makeOfferPage.createMakeOfferScene(), "Make an Offer");
    }

    public void navigateToWishlistPage() {
        WishlistPage wishlistPage = new WishlistPage(stage, wishlistController);
        setScene(new Scene(wishlistPage.getView(), 1000, 600), "Wishlist Page");
    }

    public void navigateToAddWishlistPage() {
        AddWishlistPage addWishlistPage = new AddWishlistPage(stage, wishlistController);
        setScene(new Scene(addWishlistPage.getView(), 400, 300), "Add to Wishlist");
    }

    public void refreshWishlistTable() {
        wishlistController.getWishlist().clear();
        wishlistController.ViewWishlist();
    }

    public void navigateToTransactionHistoryPage() {
        TransactionHistoryPage transactionPage = new TransactionHistoryPage(stage, transactionController);
        setScene(new Scene(transactionPage.getView(), 1000, 600), "Transaction History");
    }

    public void refreshTransactionTable() {
        transactionController.getTransactions().clear();
        transactionController.ViewHistory();
    }

    // Admin Page
    public void navigateToAdminHomePage() {
        AdminHomePage adminPage = new AdminHomePage(stage, itemController);
        setScene(adminPage.createAdminHomeScene(), "Admin Home Page");
    }
    
    public void navigateToViewRequestedItem() {
    	ViewRequestedItemPage viewRequestedItemPage = new ViewRequestedItemPage(stage, itemController);
    	setScene(viewRequestedItemPage.createViewRequestedItemScene(), "Requested Item Page");
    }
}
