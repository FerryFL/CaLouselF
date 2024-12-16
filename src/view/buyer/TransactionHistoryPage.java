package view.buyer;

import controller.ItemController;
import controller.TransactionController;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Transaction;
import model.TransactionHistory;
import view_controller.BuyerViewController;
import view_controller.LoginViewController;
import view_controller.ViewController;

public class TransactionHistoryPage {

    private TransactionController transactionController;
    private TableView<TransactionHistory> transactionTable;
    private Stage stage;
    private ItemController controller;

    public TransactionHistoryPage(Stage stage, TransactionController transactionController) {
        this.stage = stage;
        this.transactionController = transactionController;
        this.transactionTable = createTableView();
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(transactionTable);

        return root;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Menu");

        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToBuyerHomePage()
        );
        
        MenuItem makeOfferMenuItem = new MenuItem("Make Offer");
        makeOfferMenuItem.setOnAction(e -> 
            ViewController.getInstance(stage, controller).navigateToMakeOfferPage()
        );

        MenuItem wishlistMenuItem = new MenuItem("Wishlist");
        wishlistMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToWishlistPage()
        );

        MenuItem viewHistoryMenuItem = new MenuItem("View Transaction History");
        viewHistoryMenuItem.setOnAction(e -> 
            BuyerViewController.getInstance(stage, controller).navigateToTransactionHistoryPage()
        );

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> {
            LoginViewController loginViewController = new LoginViewController(stage);
            loginViewController.navigateToLogin();
        });

        menu.getItems().addAll(homeMenuItem, makeOfferMenuItem, wishlistMenuItem, viewHistoryMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private TableView<TransactionHistory> createTableView() {
        TableView<TransactionHistory> tableView = new TableView<>();
        tableView.setItems(transactionController.getTransactionHistory());

        TableColumn<TransactionHistory, String> transactionIdCol = new TableColumn<>("Transaction ID");
        transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        
        TableColumn<TransactionHistory, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        TableColumn<TransactionHistory, String> itemCategoryCol = new TableColumn<>("Item Category");
        itemCategoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        
        TableColumn<TransactionHistory, String> itemSizeCol = new TableColumn<>("Item Size");
        itemSizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        
        TableColumn<TransactionHistory, String> itemPriceCol = new TableColumn<>("Item Price");
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        
        tableView.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");	
        tableView.getColumns().addAll(transactionIdCol, itemNameCol, itemCategoryCol, itemSizeCol, itemPriceCol);

        return tableView;
    }

    public void refreshTable() {
        transactionTable.setItems(transactionController.getTransactionHistory());
    }
}
