package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.partials.AuthNavigation;

public class LoginView extends View {

    public LoginView(MainController mainController) {
		super(mainController);
	}

    @Override
	public Pane getPane() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");
        
        // Create the login form
        GridPane loginForm = new GridPane();
        loginForm.setPadding(new Insets(30));
        loginForm.setHgap(15);
        loginForm.setVgap(15);
        loginForm.setAlignment(Pos.CENTER);

        // Create labels and fields
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPrefSize(250, 40);
        Label passLabel = new Label("Password");
        PasswordField passField = new PasswordField();
        passField.setPrefSize(250, 40);
        
        Button loginButton = new Button("Login");
        loginButton.setPrefSize(360, 50);
        loginButton.setOnAction(e -> {
        	mainController.getLoginController().login(
    			emailField.getText(), 
    			passField.getText()
    		);
        });

        // Add them to the grid
        loginForm.add(emailLabel, 0, 0);
        loginForm.add(emailField, 1, 0);
        loginForm.add(passLabel, 0, 1);
        loginForm.add(passField, 1, 1);
        
        Label label = new Label("Login");
        label.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        VBox formBox = new VBox(label, loginForm, loginButton);
        formBox.setAlignment(Pos.CENTER);

        // Add CSS classes
        formBox.getStyleClass().add("grid");
        emailLabel.getStyleClass().add("form-label");
        passLabel.getStyleClass().add("form-label");
        emailField.getStyleClass().add("form-field");
        passField.getStyleClass().add("form-field");
        loginButton.getStyleClass().add("button");

        AuthNavigation authMenuBar = new AuthNavigation(mainController);
        
        // Set root position
        root.setTop(authMenuBar.getMenuBar());
        root.setCenter(formBox);
        
        return root;
    }
    
}
