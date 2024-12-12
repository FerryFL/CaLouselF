package view.seller;

import controller.ItemController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import routes.ViewController;

public class SellerHomePage {

    private Stage stage;
    private ItemController controller;
    private TableView<Item> tableView;

    public SellerHomePage(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
        this.tableView = createTableView();
    }

    public Scene createHomePageScene() {
        BorderPane root = new BorderPane();
        root.setCenter(tableView);

        Button addItemButton = new Button("Add Item");
        addItemButton.setOnAction(e -> ViewController.getInstance(stage, controller).navigateToUploadItemPage());

        HBox buttonContainer = new HBox(10, addItemButton);
        buttonContainer.setStyle("-fx-padding: 10; -fx-alignment: center;");
        root.setBottom(buttonContainer);

        return new Scene(root, 1000, 600);
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
            Button deleteBtn = new Button("Delete");
            Button updateBtn = new Button("Update");

            {
                deleteBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    controller.deleteItem(item.getItemId());
                    tableView.setItems(controller.getItems());
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

        tableView.getColumns().addAll(idCol, nameCol, sizeCol, priceCol, categoryCol, statusCol, wishlistCol, offerStatusCol, actionCol);

        return tableView;
    }
}