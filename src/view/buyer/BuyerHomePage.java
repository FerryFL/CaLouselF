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
    private ItemController itemController;
    private TableView<Item> tableView;
    private WishlistController wishlistController;
    private TransactionController transactionController;
    
    public BuyerHomePage(Stage stage, ItemController controller) {
        this.stage = stage;
        this.itemController = controller;
        this.wishlistController = new WishlistController();
        this.transactionController = new TransactionController();
        controller.ViewItem();  
        this.tableView = createTableView(); 
    }

    public Scene createHomePageScene() {
        BorderPane root = new BorderPane();
        HBox searchBox = createSearchBox();
        MenuBar menuBar = createMenuBar();
        HBox topBox = new HBox(10, menuBar, searchBox);
        
        root.setTop(topBox);
        root.setCenter(tableView);

        return new Scene(root, 1000, 600);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Menu");

        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage).navigateToBuyerHomePage()
        );
        
        MenuItem makeOfferMenuItem = new MenuItem("Make Offer");
        makeOfferMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage).navigateToMakeOfferPage()
        );
        

        MenuItem wishlistMenuItem = new MenuItem("Wishlist");
        wishlistMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage).navigateToWishlistPage()
        );

        MenuItem viewHistoryMenuItem = new MenuItem("View Transaction History");
        viewHistoryMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage).navigateToTransactionHistoryPage()
        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
        	ViewController loginViewController = new ViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, makeOfferMenuItem , wishlistMenuItem, viewHistoryMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }
    
    private HBox createSearchBox() {
        HBox searchBox = new HBox(10);
        searchBox.setStyle("-fx-padding: 10;");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter item name");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                itemController.browseItemByName(searchTerm); 
            } else {
                itemController.ViewItem(); 
            }
        });

        searchBox.getChildren().addAll(searchField, searchButton);
        return searchBox;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    @SuppressWarnings("unchecked")
    private TableView<Item> createTableView() {
        TableView<Item> tableView = new TableView<>();
        tableView.setItems(itemController.getItems());

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {
            Button addWishlistBtn = new Button("Add to Wishlist");
            Button buyBtn = new Button("Buy");
            
            {
                addWishlistBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    String userId = "US001";
                    boolean success = wishlistController.AddWishlist(item.getItemId(), userId);

                    if (success) {  
                        showAlert("Item berhasil dimasukkan ke Wishlist");
                    } else {
                        showAlert("Item gagal dimasukkan ke Wishlist");
                    }
                });

                buyBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    String userId = "US001";

                    boolean success = transactionController.CreateTransaction(userId, item.getItemId());

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
