package view.seller;

import controller.ItemController;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Item;
import view_controller.*;

public class EditItemPage {

    private Item item;
    private ItemController controller;
    private Stage owner;
    private VBox root;

    public EditItemPage(Item item, ItemController controller, Stage owner) {
        this.item = item;
        this.controller = controller;
        this.owner = owner;
        this.root = createUpdateItemPage();
    }

    private VBox createUpdateItemPage() {
        TextField nameTf = new TextField(item.getItemName());
        TextField sizeTf = new TextField(item.getItemSize());
        TextField priceTf = new TextField(item.getItemPrice());
        TextField categoryTf = new TextField(item.getItemCategory());
        TextField statusTf = new TextField(item.getItemStatus());
        TextField wishlistTf = new TextField(item.getItemWishlist());
        TextField offerStatusTf = new TextField(item.getItemOfferStatus());

        Button updateBtn = new Button("Update Item");
        updateBtn.setOnAction(e -> {
            String name = nameTf.getText();
            String size = sizeTf.getText();
            String price = priceTf.getText();
            String category = categoryTf.getText();
//            String status = statusTf.getText();
//            String wishlist = wishlistTf.getText();
//            String offerStatus = offerStatusTf.getText();
            
            if (!name.isEmpty() && !size.isEmpty() && !price.isEmpty() && !category.isEmpty()) {
                controller.editItem(item.getItemId(), name, size, price, category);
                showAlert("Item updated successfully!");
                ViewController.getInstance(owner, controller).navigateToSellerHomePage();
            } else {
                showAlert("All fields are required.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            ViewController.getInstance(owner, controller).navigateToSellerHomePage();
        });

        VBox buttonContainer = new VBox(10, updateBtn, backBtn);
        return new VBox(10, nameTf, sizeTf, priceTf, categoryTf, statusTf, wishlistTf, offerStatusTf, buttonContainer);
    }

    public VBox getRoot() {
        return root;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
