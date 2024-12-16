package view.buyer;

import controller.ItemController;
import controller.TransactionController;
import controller.UserController;
import controller.WishlistController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import view_controller.*;

public class BuyerHomePage {    

    private Stage stage;
    private ItemController controller;
    private TableView<Item> tableView;
    private WishlistController wishlistController;
    private TransactionController transactionController;
    
    public BuyerHomePage(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
        this.wishlistController = new WishlistController();
        this.transactionController = new TransactionController();
        controller.viewItems();  
        this.tableView = createTableView(); 
    }

    public Scene createHomePageScene() {
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(tableView);

        return new Scene(root, 1000, 600);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Menu");

        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToBuyerHomePage()
        );
        
        MenuItem makeOfferMenuItem = new MenuItem("Make Offer");
        makeOfferMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage, controller).navigateToMakeOfferPage()
        );
        

        MenuItem wishlistMenuItem = new MenuItem("Wishlist");
        wishlistMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToWishlistPage()
        );

        MenuItem viewHistoryMenuItem = new MenuItem("View Transaction History");
        viewHistoryMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToTransactionHistoryPage()
        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
            LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, makeOfferMenuItem , wishlistMenuItem, viewHistoryMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    @SuppressWarnings("unchecked")
    private TableView<Item> createTableView() {
        TableView<Item> tableView = new TableView<>();
        tableView.setItems(controller.getItems());

        TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> sizeCol = new TableColumn<>("Item Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, String> priceCol = new TableColumn<>("Item Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> categoryCol = new TableColumn<>("Item Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {
            Button addWishlistBtn = new Button("Add to Wishlist");
            Button buyBtn = new Button("Buy");
            
            {
                addWishlistBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    String userId = "US001";
                    boolean success = wishlistController.addWishlist(item.getItemId(), userId);

                    if (success) {  
                        showAlert("Item berhasil dimasukkan ke Wishlist");
                    } else {
                        showAlert("Item gagal dimasukkan ke Wishlist");
                    }
                });

                buyBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    String userId = "US001";

                    boolean success = transactionController.addTransaction(userId, item.getItemId());

                    if (success) {
                        showAlert("Transaksi pembelian berhasil dilakukan");
                    } else {
                        showAlert("Transaksi pembelian gagal dilakukan");
                    }
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(10, addWishlistBtn, buyBtn));
                }
            }
        });

        actionCol.setMinWidth(200);
        
        tableView.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");
        tableView.getColumns().addAll(nameCol, sizeCol, priceCol, categoryCol, actionCol);

        return tableView;
    }

}
