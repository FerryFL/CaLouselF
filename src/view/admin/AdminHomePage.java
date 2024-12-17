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

public class AdminHomePage {

    private final Stage stage;
    private final ItemController itemController;
    private final TableView<Item> tableView;

    public AdminHomePage(Stage stage, ItemController itemController) {
        this.stage = stage;
        this.itemController = itemController;
        itemController.ViewItem();
        this.tableView = createTableView();
    }
    
    // Method ini digunakan untuk membua scene pada admin home page
    public Scene createAdminHomeScene() {
        BorderPane root = new BorderPane();
        HBox searchBox = createSearchBox();
        MenuBar menuBar = createMenuBar();
        HBox topBox = new HBox(10, menuBar, searchBox);
        
        root.setTop(topBox);
        root.setCenter(tableView);
        root.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	

        return new Scene(root, 1000, 600);
    }

    // method ini digunakan untuk membuat navbar
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
    
    // method ini digunakan untuk membuat search bar
    private HBox createSearchBox() {
        HBox searchBox = new HBox(10);
        searchBox.setStyle("-fx-padding: 10;");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter item name");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                itemController.browseItemByName(searchTerm); 
            } else {
                itemController.ViewItem(); 
            }
        });

        searchBox.getChildren().addAll(searchField, searchButton);
        return searchBox;
    }

    // method ini digunakan untuk menampilkan tableview yang berisi item dari function getItems
    private TableView<Item> createTableView() {
    	TableView<Item> tableView = new TableView<>();
        tableView.setItems(itemController.getItems());

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

       
        tableView.getColumns().addAll(nameCol, sizeCol, priceCol, categoryCol);
        return tableView;
    }
}
