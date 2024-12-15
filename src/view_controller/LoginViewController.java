package view_controller;

import controller.ItemController;
import controller.OfferController;
import controller.UserController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginView;

public class LoginViewController {

    private static LoginViewController instance;
    private Stage primaryStage;
    private UserController userController;
    private RegisterViewController registerVC;
    private ItemController itemController;
    private BuyerViewController BuyerVC;
    private ViewController SellerVC;
    
    public static LoginViewController getInstance(Stage stage) {
        if (instance == null && stage != null) {
            instance = new LoginViewController(stage);
        }
        return instance;
    }

    public LoginViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        userController = new UserController();
        itemController = new ItemController();
        BuyerVC = BuyerViewController.getInstance(primaryStage, itemController);
    }

    public void navigateToLogin() {
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getLoginForm(), 300, 300);

        loginView.getLoginButton().setOnAction(e -> {
            String username = loginView.getUsernameField().getText();
            String password = loginView.getPasswordField().getText();
            String role = userController.validateLogin(username, password); // Get the role from DB

            if(username.equals("admin") && password.equals("admin")){
            	System.out.println("Welcome Admin!");
            }else if (role.equals("Seller")) {
                System.out.println("Welcome Seller!");
                SellerVC.navigateToSellerHomePage();
           } else if(role.equals("Buyer")) {
           	System.out.println("Welcome Buyer!");
           	BuyerVC.getInstance(primaryStage, itemController).navigateToBuyerHomePage();
           }else {
               System.out.println("Invalid Username/Password");
           }
            
            
        });

        loginView.getRegisterButton().setOnAction(e -> {
        	registerVC.getInstance(primaryStage).navigateToRegister();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}
