package view.buyer;

import controller.ItemController;
import controller.OfferController;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Item;
import view_controller.BuyerViewController;
import view_controller.LoginViewController;
import view_controller.ViewController;

public class MakeOfferPage {

    private final Stage stage; 
    private final ItemController itemController; 
    private final OfferController offerController; 

    public MakeOfferPage(Stage stage, ItemController itemController, OfferController offerController) {
        this.stage = stage;
        this.itemController = itemController;
        this.offerController = offerController;
    }

    public Scene createMakeOfferScene() {
        BorderPane root = new BorderPane();

        TableView<Item> tableView = createTableView();
        root.setCenter(tableView);
        root.setTop(createMenuBar());

        return new Scene(root, 1000, 600);
    }
   

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");

        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, itemController).navigateToBuyerHomePage()
        );

        MenuItem makeOfferMenuItem = new MenuItem("Make Offer");
        makeOfferMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage, itemController).navigateToMakeOfferPage()
        );
        
        MenuItem wishlistMenuItem = new MenuItem("Wishlist");
        wishlistMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, itemController).navigateToWishlistPage()
        );
        
        MenuItem viewHistoryMenuItem = new MenuItem("View Transaction History");
        viewHistoryMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, itemController).navigateToTransactionHistoryPage()
        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
        	LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, makeOfferMenuItem, wishlistMenuItem, viewHistoryMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private TableView<Item> createTableView() {
        TableView<Item> tableView = new TableView<>();
        ObservableList<Item> items = itemController.getItems();
        tableView.setItems(items);

        TableColumn<Item, String> idCol = new TableColumn<>("Item ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));

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
            private final Button makeOfferButton = new Button("Make Offer");

            {
                makeOfferButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        openMakeOfferForm(item);
                    }
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(makeOfferButton);
                    setGraphic(buttonBox);
                }
            }
        });

        tableView.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");
        tableView.getColumns().addAll(idCol, nameCol, sizeCol, priceCol, categoryCol, actionCol);
        return tableView;
    }

    private void openMakeOfferForm(Item item) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Make Offer");
        dialog.setHeaderText("Offer a Price for Item: " + item.getItemName());

        TextField offerPriceField = new TextField();
        offerPriceField.setPromptText("Enter your offer price");

        VBox form = new VBox(10, new Label("Offer Price:"), offerPriceField);
        form.setStyle("-fx-padding: 10;");
        dialog.getDialogPane().setContent(form);

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return offerPriceField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(offerPrice -> {
            try {
                double price = Double.parseDouble(offerPrice);
                double currentHighestOffer = offerController.getHighestOffer(item.getItemId());
                if (price <= 0) {
                    showAlert("Invalid", "Harga Offer harus lebih besar dari 0");
                } else if (price <= currentHighestOffer) {
                    showAlert("Invalid", "Harga Offer harus lebih besar dari Harga Offer sekarang.");
                } else {
                    offerController.submitOffer(item.getItemId(), price, "US001"); 
                    showAlert("Valid", "Offer berhasil dikirimkan");
                }
            } catch (Exception e) {
                showAlert("Invalid", "Masukkan angka untuk harga");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
