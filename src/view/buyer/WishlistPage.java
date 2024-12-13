package view.buyer;

import controller.ItemController;
import controller.WishlistController;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Wishlist;
import view_controller.BuyerViewController;
import view_controller.LoginViewController;

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
            BuyerViewController.getInstance(stage, controller).navigateToBuyerHomePage()
        );

        MenuItem wishlistMenuItem = new MenuItem("Wishlist");
        wishlistMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToWishlistPage()
        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
            LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, wishlistMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private TableView<Wishlist> createTableView() {
        TableView<Wishlist> tableView = new TableView<>();
        tableView.setItems(wishlistController.getWishlist());

        TableColumn<Wishlist, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("wishlistId"));

        TableColumn<Wishlist, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Wishlist, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Wishlist, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Wishlist selectedWishlist = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText("Are you sure you want to delete this wishlist item?");
                    alert.setContentText("Item: " + selectedWishlist.getWishlistId());

                    if (alert.showAndWait().get() == ButtonType.OK) {
                        boolean isDeleted = wishlistController.removeWishlist(selectedWishlist.getWishlistId());

                        if (isDeleted) {
                            getTableView().getItems().remove(selectedWishlist);
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Success");
                            successAlert.setHeaderText("Item deleted successfully!");
                            successAlert.show();
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error");
                            errorAlert.setHeaderText("Failed to delete the item.");
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

        tableView.getColumns().addAll(idCol, itemNameCol, userIdCol, actionCol);

        return tableView;
    }

    public void refreshTable() {
        wishlistTable.setItems(wishlistController.getWishlist());
    }
}
