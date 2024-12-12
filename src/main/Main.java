package main;

import controller.ItemController;
import javafx.application.Application;
import javafx.stage.Stage;

import view_controller.*;
import view_controller.LoginViewController;
import view_controller.RegisterViewController;

public class Main extends Application {

    
    private final ItemController controller = new ItemController();
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        LoginViewController loginVC = new LoginViewController(primaryStage);
//        RegisterViewController registerVC = new RegisterViewController(primaryStage);
//
//        loginVC.setRegisterViewController(registerVC);
//        registerVC.setLoginViewController(loginVC);
//
//        loginVC.navigateToLogin();
    	
    	ViewController viewController = ViewController.getInstance(primaryStage, controller);
        
        viewController.navigateToSellerHomePage(); 

        primaryStage.setTitle("CalouselF");
        primaryStage.show();
    }
}

