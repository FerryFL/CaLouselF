package view.seller;

import controller.ItemController;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import routes.ViewController;

public class UploadItemPage {

    private Stage stage;
    private ItemController controller;
    private VBox root;

    public UploadItemPage(Stage stage, ItemController controller) {
        this.stage = stage;
        this.controller = controller;
        this.root = createUploadItemPage();
    }

    private VBox createUploadItemPage() {
        TextField nameTf = new TextField();
        nameTf.setPromptText("Name");

        TextField sizeTf = new TextField();
        sizeTf.setPromptText("Size");

        TextField priceTf = new TextField();
        priceTf.setPromptText("Price");

        TextField categoryTf = new TextField();
        categoryTf.setPromptText("Category");

        Button addBtn = new Button("Add Item");
        addBtn.setOnAction(e -> {
            String name = nameTf.getText();
            String size = sizeTf.getText();
            String price = priceTf.getText();
            String category = categoryTf.getText();

            if (!name.isEmpty() && !size.isEmpty() && !price.isEmpty() && !category.isEmpty()) {
                controller.addItem(name, size, price, category);
                showAlert("Item added successfully!");
                clearInputs(nameTf, sizeTf, priceTf, categoryTf);
            } else {
                showAlert("All fields are required.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            ViewController.getInstance(stage, controller).navigateToSellerHomePage();
        });

        VBox buttonContainer = new VBox(10, addBtn, backBtn);
        return new VBox(10, nameTf, sizeTf, priceTf, categoryTf, buttonContainer);
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
