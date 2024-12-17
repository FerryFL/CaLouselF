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
        controller.ViewItem(); 
        this.tableView = createTableView(); 
    }

    public Scene createHomePageScene() {
        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        
        root.setTop(createMenuBar());
        root.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	
        

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

//        MenuItem adminHomeMenuItem = new MenuItem("Admin Home");
//        adminHomeMenuItem.setOnAction(e->
//        	ViewController.getInstance(stage, controller).navigateToAdminHomePage()
//        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
            LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, viewOfferMenuItem, logoutMenuItem);
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
            Button deleteBtn = new Button("Delete");
            Button updateBtn = new Button("Update");

            {
            	deleteBtn.setOnAction(e -> {
            	    Item item = getTableView().getItems().get(getIndex());
            	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            	    alert.setTitle("Delete Confirmation");
            	    alert.setContentText("Apakah kamu ingin menghapus item ini");
            	    alert.showAndWait().ifPresent(response -> {
            	        if (response == ButtonType.OK) {
            	            controller.DeleteItem(item.getItemId());
            	            tableView.refresh();  
            	            showAlert("Item berhasil di tambahkan");
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

        tableView.getColumns().addAll(nameCol, sizeCol, priceCol, categoryCol, actionCol);
        

        return tableView;
    }
}
