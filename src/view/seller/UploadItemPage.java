package view.seller;

import controller.ItemController;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view_controller.*;

public class UploadItemPage {

    private Stage stage;
    private ItemController controller;
    private VBox root;

    public UploadItemPage(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
        this.root = createUploadItemPage();
        root.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	
    }

    private VBox createUploadItemPage() {

    	Label nameLabel = new Label("Item Name:");
        TextField nameTf = new TextField();
        nameTf.setPromptText("Name");

        Label sizeLabel = new Label("Item Size:");
        TextField sizeTf = new TextField();
        sizeTf.setPromptText("Size");

        Label priceLabel = new Label("Item Price:");
        TextField priceTf = new TextField();
        priceTf.setPromptText("Price");

        Label categoryLabel = new Label("Item Category:");
        TextField categoryTf = new TextField();
        categoryTf.setPromptText("Category");

        Button addBtn = new Button("Add Item");
        addBtn.setOnAction(e -> {
            String name = nameTf.getText();
            String size = sizeTf.getText();
            String price = priceTf.getText();
            String category = categoryTf.getText();
            
            String msg = controller.CheckItemValidation(name, size, price, category);
            
            boolean isSuccess = controller.addItemToDatabase(name, size, price, category);
            if(isSuccess) {
            	showAlert("Item berhasil dimasukkan ke database");
            }else {
            	showAlert(msg);
            }
        });


        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            ViewController.getInstance(stage, controller).navigateToSellerHomePage();
        });

        VBox buttonContainer = new VBox(10, addBtn, backBtn);
        return new VBox(10, nameLabel, nameTf, sizeLabel, sizeTf, priceLabel, priceTf, categoryLabel, categoryTf, buttonContainer);
    }

    public VBox getRoot() {
        return root;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    private void clearInputs(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
