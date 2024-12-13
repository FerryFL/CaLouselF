package view.buyer;

import controller.WishlistController;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AddWishlistPage {

    private WishlistController wishlistController;
    private TextField itemIdField;
    private Button addButton;
    private Stage stage;

    public AddWishlistPage(Stage stage, WishlistController wishlistController) {
    	this.stage = stage;
        this.wishlistController = wishlistController;
        initializeView();
    }

    private void initializeView() {
        VBox root = new VBox(10);

        Label itemIdLabel = new Label("Item ID:");
        itemIdField = new TextField();

        root.getChildren().addAll(itemIdLabel, itemIdField, addButton);
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setCenter(itemIdField);

        VBox buttonContainer = new VBox(10, addButton);
        buttonContainer.setStyle("-fx-padding: 10; -fx-alignment: center;");
        root.setBottom(buttonContainer);

        return root;
    }

}
