package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Role;
import view.partials.AuthNavigation;

public class RegisterView extends View {

    public RegisterView(MainController mainController) {
		super(mainController);
	}

    @Override
	public Pane getPane() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        // Create the registration form
        GridPane registerForm = new GridPane();
        registerForm.setPadding(new Insets(30));
        registerForm.setHgap(15);
        registerForm.setVgap(15);
        registerForm.setAlignment(Pos.CENTER);

        // Create labels and fields
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPrefSize(250, 40);

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.setPrefSize(250, 40);

        Label passLabel = new Label("Password");
        PasswordField passField = new PasswordField();
        passField.setPrefSize(250, 40);

        Label confirmPassLabel = new Label("Confirm Password");
        PasswordField confirmPassField = new PasswordField();
        confirmPassField.setPrefSize(250, 40);

        Label addressLabel = new Label("Address");
        TextArea addressField = new TextArea();
        addressField.setPrefSize(250, 100);
        addressField.setWrapText(true);

        Label phoneLabel = new Label("Phone Number");
        TextField phoneField = new TextField();
        phoneField.setPrefSize(250, 40);
        
        Label roleLabel = new Label("Role");
        ComboBox<Role> roleDropdown = new ComboBox<>();
        for (Role role : Role.values()) {
			roleDropdown.getItems().add(role);
		}
        roleDropdown.setPromptText("Select Role");

        CheckBox termsCheckBox = new CheckBox("I agree to the terms and conditions");
        termsCheckBox.setStyle("-fx-font-size: 16px;");

        Button registerButton = new Button("Register");
        registerButton.setPrefSize(360, 50);
        registerButton.setOnAction(e -> {
            // Call the registration method from the RegisterController
            mainController.getRegisterController().register(
                emailField.getText(),
                usernameField.getText(),
                passField.getText(),
                confirmPassField.getText(),
                addressField.getText(),
                phoneField.getText(),
                termsCheckBox.isSelected(),
                roleDropdown.getValue()
            );
        });

        // Add them to the grid
        registerForm.add(emailLabel, 0, 0);
        registerForm.add(emailField, 1, 0);
        registerForm.add(usernameLabel, 0, 1);
        registerForm.add(usernameField, 1, 1);
        registerForm.add(passLabel, 0, 2);
        registerForm.add(passField, 1, 2);
        registerForm.add(confirmPassLabel, 0, 3);
        registerForm.add(confirmPassField, 1, 3);
        registerForm.add(addressLabel, 0, 4);
        registerForm.add(addressField, 1, 4);
        registerForm.add(phoneLabel, 0, 5);
        registerForm.add(phoneField, 1, 5);
        registerForm.add(roleLabel, 0, 6);
        registerForm.add(roleDropdown, 1, 6);
        registerForm.add(termsCheckBox, 1, 7);
        
        Label label = new Label("Register");
        label.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        VBox formBox = new VBox(label, registerForm, registerButton);
        formBox.setAlignment(Pos.CENTER);

        // Add CSS classes
        formBox.getStyleClass().add("grid");
        emailLabel.getStyleClass().add("form-label");
        usernameLabel.getStyleClass().add("form-label");
        passLabel.getStyleClass().add("form-label");
        confirmPassLabel.getStyleClass().add("form-label");
        addressLabel.getStyleClass().add("form-label");
        phoneLabel.getStyleClass().add("form-label");
        roleLabel.getStyleClass().add("form-label");
        roleDropdown.getStyleClass().add("form-field");
        emailField.getStyleClass().add("form-field");
        usernameField.getStyleClass().add("form-field");
        passField.getStyleClass().add("form-field");
        confirmPassField.getStyleClass().add("form-field");
        addressField.getStyleClass().add("form-field");
        phoneField.getStyleClass().add("form-field");
        registerButton.getStyleClass().add("button");

        AuthNavigation authMenuBar = new AuthNavigation(mainController);

        // Set root positions
        root.setTop(authMenuBar.getMenuBar());
        root.setCenter(formBox);

        return root;
    }
    
}
