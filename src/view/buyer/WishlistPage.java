package view.buyer;

import controller.ItemController;
import controller.WishlistController;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Wishlist;
import view_controller.ViewController;

public class WishlistPage {

    private WishlistController wishlistController;
    private TableView<Wishlist> wishlistTable;
    private Stage stage;
    private ItemController controller;

    public WishlistPage(Stage stage, WishlistController wishlistController) {
    	this.stage = stage;
        this.wishlistController = wishlistController;
        this.wishlistTable = createTableView();
    }
    
    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(wishlistTable);

        return root;
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

        menu.getItems().addAll(homeMenuItem, makeOfferMenuItem, wishlistMenuItem, viewHistoryMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private TableView<Wishlist> createTableView() {
        TableView<Wishlist> tableView = new TableView<>();
        tableView.setItems(wishlistController.getWishlist());

        TableColumn<Wishlist, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Wishlist, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Wishlist selectedWishlist = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText("Apakah anda yakin ingin menghapus Wishlist Item ini?");
                    alert.setContentText("Item: " + selectedWishlist.getWishlistId());

                    if (alert.showAndWait().get() == ButtonType.OK) {
                        boolean isDeleted = wishlistController.RemoveWishlist(selectedWishlist.getWishlistId());

                        if (isDeleted) {
                            getTableView().getItems().remove(selectedWishlist);
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Success");
                            successAlert.setHeaderText("Item berhasil didelete");
                            successAlert.show();
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText("Item gagal didelete");
                            errorAlert.show();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tableView.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	
        tableView.getColumns().addAll(itemNameCol, actionCol);

        return tableView;
    }

    public void refreshTable() {
        wishlistTable.setItems(wishlistController.getWishlist());
    }
}
