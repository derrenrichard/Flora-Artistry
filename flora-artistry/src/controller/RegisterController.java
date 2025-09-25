package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import dao.UserDAO;
import javafx.scene.control.Alert.AlertType;
import model.Role;
import model.User;
import view.partials.CustomAlert;

public class RegisterController extends Controller<UserDAO> {

	public RegisterController(MainController mainController) {
		super(mainController, new UserDAO());
	}

	public void register(String email, String name, String password, String confirmPassword,
			String address, String phone, boolean isTermsChecked, Role role) {
		// Check if fields are empty
		if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || address.isEmpty() || phone.isEmpty() || role == null) {
			alert.showAlert(AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
		}
		// Check if username length is 4-20
		else if (name.length() < 4 || name.length() > 20) {
			alert.showAlert(AlertType.ERROR, "Error", "Invalid Username", "Username must be 4 - 20 characters");
		}
		// Check if email unique
		else if (dao.getUserByEmail(email) != null) {
			alert.showAlert(AlertType.ERROR, "Error", "Email Already Taken", "Please choose a different email.");
		}
		// Check if passwords match
		else if (!password.equals(confirmPassword)) {
			alert.showAlert(AlertType.ERROR, "Error", "Password Mismatch", "Passwords do not match. Please try again.");
		}
		// Check if password is at least 8 characters
		else if (password.length() < 8) {
			alert.showAlert(AlertType.ERROR, "Error", "Password Too Short", "Password must be at least 8 characters long.");
		}
		// Check if email contains '@' and '.'
		else if (!email.contains("@") || !email.contains(".")) {
			alert.showAlert(AlertType.ERROR, "Error", "Invalid Email", "Email must contain '@' and '.'");
		}
		// Check if terms and conditions are accepted
		else if (!isTermsChecked) {
			alert.showAlert(AlertType.ERROR, "Error", "Terms Not Accepted", "You must accept the terms and conditions.");
		} 
		// Passes all the validations
		else {
			String id = "US001";

			// Get the highest user ID from the database
			String highestId = dao.getHighestUserId();

			if (highestId != null && !highestId.isEmpty()) {
				Integer numOnly = Integer.parseInt(highestId.substring(2));
				Integer nextId = numOnly + 1;
				id = String.format("US%03d", nextId);
			}
			
			String hashedPassword = hashPassword(password);

			User user = new User(id, name, email, hashedPassword, address, phone, role);

			if (dao.addUser(user)) {
				// Success
				System.out.println("Register Success");
				
				alert.showAlert(AlertType.INFORMATION, "Success", "Registration Success", "You will be redirected to home page.");
				
				mainController.getLoginController().loginRedirect(user);
			} else {
				alert.showAlert(AlertType.ERROR, "Error", "Cannot Make Account", "Please try again later.");
			}
		}
	}
	
	public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256 algorithm", e);
        }
    }
}
