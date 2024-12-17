	package view.admin;

import controller.ItemController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Item;
import view_controller.*;

public class ViewRequestedItemPage {

    private final Stage stage;
    private final ItemController itemController;
    private final TableView<Item> tableView;

    public ViewRequestedItemPage(Stage stage, ItemController itemController) {
        this.stage = stage;
        this.itemController = itemController;
        this.tableView = createTableView();
    }
    
    // Method ini digunakan untuk membuat scene 
    public Scene createViewRequestedItemScene() {
        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setTop(createMenuBar());
        root.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	

        return new Scene(root, 1000, 600);
    }
    
    // Method ini digunakan untuk membuat navbar
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");

        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage).navigateToAdminHomePage()
        );
        
        MenuItem viewRequestedItem = new MenuItem("View Requested Item");
        viewRequestedItem.setOnAction(e->
        	ViewController.getInstance(stage).navigateToViewRequestedItem()
		);

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
            ViewController loginViewController = new ViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, viewRequestedItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    // Method ini digunakan untuk menampilkan data requested item dalam bentuk table
    private TableView<Item> createTableView() {
        TableView<Item> tableView = new TableView<>();
        tableView.setItems(itemController.ViewRequestedItem()); 

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        
        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button declineButton = new Button("Decline");
            {
                approveButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        itemController.ApproveItem(item);
                        showAlert("Item Approved", "The item has been approved.");
                        tableView.getItems().remove(item);
                    }
                });

                declineButton.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        openDeclineReasonDialog(item);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(approveButton, declineButton);
                    actionButtons.setSpacing(10);
                    setGraphic(actionButtons);
                }
            }
        });

       
        tableView.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, actionsCol);
        return tableView;
    }
    
    // Method ini digunakan untuk membuka pop up reason untuk decline
    private void openDeclineReasonDialog(Item item) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Decline Item");
        dialog.setHeaderText("Decline Item: " + item.getItemName());
        dialog.setContentText("Alasan decline: ");

        dialog.showAndWait().ifPresent(reason -> {
            if (reason.trim().isEmpty()) {
                showAlert("Invalid", "Alasan tidak boleh kosong");
            } else {
                itemController.DeclineItem(item, reason);
                showAlert("Valid", "Item terdecline dan dihapus dari list");
                tableView.getItems().remove(item);
            }
        });
    }
    
    // Method ini digunakan untuk menampilkan pop up untuk success message atau error message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
