package view.guest;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {

    private Label usernameLbl, passwordLbl;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginBtn;
    private Button registerBtn;

    // Method ini digunakan untuk membuat tampilan login
    public LoginView() {
        usernameLbl = new Label("Username");
        passwordLbl = new Label("Password");
    	
        usernameField = new TextField();
        usernameField.setPromptText("Masukan Username");

        
        passwordField = new PasswordField();
        passwordField.setPromptText("Masukan Password");

        loginBtn = new Button("Login");
        registerBtn = new Button("No Account? Register");
    }
    
    // Method ini digunakan untuk membuat login form
    public VBox getLoginForm() {
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(usernameLbl, usernameField, passwordLbl, passwordField, loginBtn,registerBtn);

        vbox.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");
        return vbox;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginBtn;
    }

    public Button getRegisterButton() {
        return registerBtn;
    }
}
