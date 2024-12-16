package view.seller;

import controller.OfferController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Offer;
import view_controller.LoginViewController;
import view_controller.ViewController;

public class ViewOfferPage {

    private Stage stage;
    private OfferController offerController;
    private TableView<Offer> tableView;

    public ViewOfferPage(Stage stage, OfferController offerController) {
        this.stage = stage;
        this.offerController = offerController;
        this.tableView = createTableView();
    }

    public Scene createViewOfferScene() {
        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setTop(createMenuBar());
        root.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	
        
        return new Scene(root, 1000, 600);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");

        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> {
        	ViewController.getInstance(stage, null).navigateToSellerHomePage();
        });
        
        MenuItem viewOfferMenuItem = new MenuItem("View Offer");
        viewOfferMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage, null).navigateToViewOfferPage()
        );
        
        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
        	LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, viewOfferMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    @SuppressWarnings("unchecked")
	private TableView<Offer> createTableView() {
        TableView<Offer> tableView = new TableView<>();
        tableView.setItems(offerController.getOffers());
        
        TableColumn<Offer, String> itemCategoryCol = new TableColumn<>("Item Category");
        itemCategoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        
        TableColumn<Offer, String> itemSizeCol = new TableColumn<>("Item Size");
        itemSizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        
        TableColumn<Offer, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        TableColumn<Offer, String> itemPriceCol = new TableColumn<>("Initial Price");
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Offer, String> offerPriceCol = new TableColumn<>("Offered Price");
        offerPriceCol.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));

        TableColumn<Offer, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");
            private final Button declineButton = new Button("Decline");

            {
                acceptButton.setOnAction(e -> {
                    Offer offer = getTableView().getItems().get(getIndex());
                    offerController.acceptOffer(offer);
                    offerController.showAlert("Offer Accepted", "The offer has been accepted successfully.");
                    tableView.getItems().remove(offer);
                });

                declineButton.setOnAction(e -> {
                    Offer offer = getTableView().getItems().get(getIndex());
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Decline Offer");
                    dialog.setHeaderText("Decline Offer for Item: " + offer.getItemName());
                    dialog.setContentText("Alasan Decline :");

                    dialog.showAndWait().ifPresent(reason -> {
                        if (reason.trim().isEmpty()) {
                            offerController.showAlert("Invalid", "Alasan decline tidak boleh kosong");
                        } else {
                            offerController.declineOffer(offer, reason);
                            tableView.getItems().remove(offer);
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(acceptButton, declineButton);
                    actionButtons.setSpacing(10);
                    setGraphic(actionButtons);
                }
            }
        });

        tableView.getColumns().addAll(itemNameCol,itemCategoryCol, itemSizeCol, itemPriceCol, offerPriceCol, actionsCol);
        return tableView;
    }
}
