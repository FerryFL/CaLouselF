package view_controller;

import controller.UserController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.RegisterView;

public class RegisterViewController {

    private Stage primaryStage;
    private UserController registerController;
    private LoginViewController loginVC; 
    
    public RegisterViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.registerController = new UserController();
    }

    public void setLoginViewController(LoginViewController loginVC) {
        this.loginVC = loginVC;
    }

    public void navigateToRegister() {
        RegisterView registerView = new RegisterView();
        Scene scene = new Scene(registerView.display());

        registerView.getRegisterButton().setOnAction(e -> {
            String username = registerView.getUsernameField().getText();
            String password = registerView.getPasswordField().getText();
            String phoneNumber = registerView.getPhoneNumberField().getText();
            String address = registerView.getAddressField().getText();
            String role = registerView.getSelectedRole();

            boolean success = registerController.registerUser(username, password, phoneNumber, address, role);

            if (success) {
                System.out.println("Registration successful!");
                navigateToLogin();
            }
        });

        registerView.getLoginButton().setOnAction(e -> {
            if (loginVC != null) {
                loginVC.navigateToLogin();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Registration Form");
        primaryStage.show();
    }

    public void navigateToLogin() {
    	loginVC.navigateToLogin();
    }
}
