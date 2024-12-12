package view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class RegisterView {

    private Label usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, roleLbl;
    private TextField usernameField, phoneNumberField;
    private PasswordField passwordField;
    private TextArea addressField;
    private RadioButton sellerRole, buyerRole;
    private Button registerButton, loginButton;
    private ToggleGroup roleGroup;

    public RegisterView() {

        usernameLbl = new Label("Username:");
        passwordLbl = new Label("Password:");
        phoneNumberLbl = new Label("Phone Number:");
        addressLbl = new Label("Address:");
        roleLbl = new Label("Role:");

        usernameField = new TextField();
        usernameField.setPromptText("Masukkan Username (min 3 karakter)");

        passwordField = new PasswordField();
        passwordField.setPromptText("Masukkan Password (min 8 karakter + special karakter (!,@,#,$,%,^,&,*))");

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Masukkan Nomor Telp (+62[10 digit])");

        addressField = new TextArea();
        addressField.setPromptText("Masukkan Alamat");

        sellerRole = new RadioButton("Seller");
        buyerRole = new RadioButton("Buyer");

        roleGroup = new ToggleGroup();
        sellerRole.setToggleGroup(roleGroup);
        buyerRole.setToggleGroup(roleGroup);

        registerButton = new Button("Register");
        loginButton = new Button("Have Account? Login");
    }

    public VBox display() {
        VBox form = new VBox(10);

        form.getChildren().addAll(
            usernameLbl, usernameField,
            passwordLbl, passwordField,
            phoneNumberLbl, phoneNumberField,
            addressLbl, addressField,
            roleLbl, new HBox(5, sellerRole, buyerRole),
            registerButton, loginButton
        );

        form.setStyle("-fx-padding: 20; -fx-background-color: #FFCCE1;");
        return form;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public TextArea getAddressField() {
        return addressField;
    }

    public RadioButton getSellerRole() {
        return sellerRole;
    }

    public RadioButton getBuyerRole() {
        return buyerRole;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public String getSelectedRole() {
        if (sellerRole.isSelected()) return "Seller";
        if (buyerRole.isSelected()) return "Buyer";
        return "nothing";
    }
}
