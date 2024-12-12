package view_controller;

import controller.LoginController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginView;

public class LoginViewController {

    private Stage primaryStage;
    private LoginController loginController;
    private RegisterViewController registerVC; 

    // Constructor without registerVC, using setter instead
    public LoginViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginController = new LoginController();
    }

    // Setter method to inject RegisterViewController
    public void setRegisterViewController(RegisterViewController registerVC) {
        this.registerVC = registerVC;
    }

    public void navigateToLogin() {
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getLoginForm(), 300, 300);

        loginView.getLoginButton().setOnAction(e -> {
            String username = loginView.getUsernameField().getText();
            String password = loginView.getPasswordField().getText();

            String role = loginController.validateLogin(username, password); // Get the role from DB

            if (role != null) {
                System.out.println("Success");
 
            } else {
                System.out.println("Invalid Username/Password");
            }
            
            
        });

        loginView.getRegisterButton().setOnAction(e -> {
            if (registerVC != null) {
                registerVC.navigateToRegister();  // Call navigateToRegister only if registerVC is set
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
    
}
