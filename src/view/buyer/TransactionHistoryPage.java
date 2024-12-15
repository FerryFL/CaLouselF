package view.buyer;

import controller.ItemController;
import controller.TransactionController;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Transaction;
import view_controller.BuyerViewController;
import view_controller.LoginViewController;

public class TransactionHistoryPage {

    private TransactionController transactionController;
    private TableView<Transaction> transactionTable;
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

        menu.getItems().addAll(homeMenuItem, wishlistMenuItem, viewHistoryMenuItem, logoutMenuItem);
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private TableView<Transaction> createTableView() {
        TableView<Transaction> tableView = new TableView<>();
        tableView.setItems(transactionController.getTransactions());
        
  
        
        TableColumn<Transaction, String> itemIdCol = new TableColumn<>("Item Name");
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Transaction, String> transactionIdCol = new TableColumn<>("Transaction ID");
        transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        
        TableColumn<Transaction, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));


        tableView.getColumns().addAll(userIdCol, itemIdCol, transactionIdCol);

        return tableView;
    }

    public void refreshTable() {
        transactionTable.setItems(transactionController.getTransactions());
    }
}
