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
    private Stage stage;
    private VBox root;

    public EditItemPage(Item item, ItemController controller, Stage stage) {
        this.item = item;
        this.controller = controller;
        this.stage = stage;
        this.root = createUpdateItemPage();
        root.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	
    }
    
    
    // Method ini digunakan untuk membuat tampilan page UpdateItem
    private VBox createUpdateItemPage() {

        Label nameLabel = new Label("Item Name:");
        TextField nameTf = new TextField(item.getItemName());

        Label sizeLabel = new Label("Item Size:");
        TextField sizeTf = new TextField(item.getItemSize());

        Label priceLabel = new Label("Item Price:");
        TextField priceTf = new TextField(item.getItemPrice());

        Label categoryLabel = new Label("Item Category:");
        TextField categoryTf = new TextField(item.getItemCategory());

        Button updateBtn = new Button("Update Item");
        updateBtn.setOnAction(e -> {
            String name = nameTf.getText();
            String size = sizeTf.getText();
            String price = priceTf.getText();
            String category = categoryTf.getText();
            String msg = controller.CheckItemValidation(name, size, price, category);
            
            boolean isSuccess = controller.EditItem(item.getItemId(), name, size, price, category);
            if(isSuccess) {
            	showAlert("Item updated successfully!");
                ViewController.getInstance(stage).navigateToSellerHomePage();
            }else {
            	showAlert(msg);
            }

        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            ViewController.getInstance(stage).navigateToSellerHomePage();
        });

        VBox buttonContainer = new VBox(10, updateBtn, backBtn);
        return new VBox(10, nameLabel, nameTf, sizeLabel, sizeTf, priceLabel, priceTf, categoryLabel, categoryTf, buttonContainer);
    }

    public VBox getRoot() {
        return root;
    }
    
    // Method ini digunakan untuk menampilkan pop up untuk success message atau error message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
