package view.seller;

import controller.ItemController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import view_controller.*;

public class SellerHomePage {

    private Stage stage;
    private ItemController controller;
    private TableView<Item> tableView;

    public SellerHomePage(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
        controller.viewItems(); 
        this.tableView = createTableView(); 
    }


    public Scene createHomePageScene() {
        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        
        root.setTop(createMenuBar());

        Button addItemButton = new Button("Add Item");
        addItemButton.setOnAction(e -> ViewController.getInstance(stage, controller).navigateToUploadItemPage());

        HBox buttonContainer = new HBox(10, addItemButton);
        buttonContainer.setStyle("-fx-padding: 10; -fx-alignment: center;");
        root.setBottom(buttonContainer);

        return new Scene(root, 1000, 600);
    }
    
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Menu");

        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage, controller).navigateToSellerHomePage()
        );

        MenuItem viewOfferMenuItem = new MenuItem("View Offer");
        viewOfferMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage, controller).navigateToViewOfferPage()
        );
        
        MenuItem makeOfferMenuItem = new MenuItem("Make Offer");
        makeOfferMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage, controller).navigateToMakeOfferPage()
        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
            LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, viewOfferMenuItem, makeOfferMenuItem, logoutMenuItem);
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

        TableColumn<Item, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        TableColumn<Item, String> wishlistCol = new TableColumn<>("Wishlist");
        wishlistCol.setCellValueFactory(new PropertyValueFactory<>("itemWishlist"));

        TableColumn<Item, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {
            Button deleteBtn = new Button("Delete");
            Button updateBtn = new Button("Update");

            {
            	deleteBtn.setOnAction(e -> {
            	    Item item = getTableView().getItems().get(getIndex());
            	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            	    alert.setTitle("Delete Confirmation");
            	    alert.setContentText("Are you sure you want to delete this item?");
            	    alert.showAndWait().ifPresent(response -> {
            	        if (response == ButtonType.OK) {
            	            controller.deleteItem(item.getItemId());
            	            tableView.refresh();  
            	            showAlert("Item deleted successfully!");
            	        }
            	    });
            	});


                updateBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    ViewController.getInstance(stage, controller).navigateToEditItemPage(item);
                });
            }

            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, updateBtn, deleteBtn);
                    setGraphic(buttons);
                }
            }
        });

        actionCol.setMinWidth(200);

        tableView.getColumns().addAll(idCol, nameCol, sizeCol, priceCol, categoryCol, statusCol, wishlistCol, actionCol);

        return tableView;
    }
}
