package view.buyer;

import controller.ItemController;
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

    public BuyerHomePage(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
        wishlistController = new WishlistController();
        controller.viewItems();  // Fetch items from the database and update ObservableList
        this.tableView = createTableView();  // Now create the TableView
    }

    public Scene createHomePageScene() {
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar()); // Tambahkan MenuBar
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

        MenuItem wishlistMenuItem = new MenuItem("Wishlist");
        wishlistMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToWishlistPage()
        );

        MenuItem viewHistoryMenuItem = new MenuItem("View Transaction History");
        viewHistoryMenuItem.setOnAction(e -> 
        	BuyerViewController.getInstance(stage,controller).navigateToTransactionHistoryPage()
        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
            LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, wishlistMenuItem, viewHistoryMenuItem, logoutMenuItem);
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

        TableColumn<Item, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        TableColumn<Item, String> wishlistCol = new TableColumn<>("Wishlist");
        wishlistCol.setCellValueFactory(new PropertyValueFactory<>("itemWishlist"));

        TableColumn<Item, String> offerStatusCol = new TableColumn<>("Offer Status");
        offerStatusCol.setCellValueFactory(new PropertyValueFactory<>("itemOfferStatus"));

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
                        showAlert("Item added to Wishlist successfully!");
                    } else {
                        showAlert("Failed to add item to Wishlist.");
                    }
                });

                buyBtn.setOnAction(e -> {
                    // Placeholder for buy action, to be filled later
                    showAlert("Buy functionality is not implemented yet.");
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(addWishlistBtn, buyBtn));
                }
            }
        });


        actionCol.setMinWidth(200);

        tableView.getColumns().addAll(idCol, nameCol, sizeCol, priceCol, categoryCol, statusCol, wishlistCol, offerStatusCol, actionCol);

        return tableView;
    }

}
