package controller;

import dao.UserDAO;
import javafx.scene.control.Alert.AlertType;
import model.Role;
import model.User;
import view.partials.CustomAlert;

public class LoginController extends Controller<UserDAO> {

	public LoginController(MainController mainController) {
		super(mainController, new UserDAO());
	}

	public void login(String email, String password) {
		User user = dao.getUserByEmail(email);
		if (user != null) {
			if (verifyPassword(password, user.getPassword())) {
				// Success
				System.out.println("Login Success");
				
				alert.showAlert(AlertType.INFORMATION, "Success", "Login Success", "You will be redirected to home page.");
				
				loginRedirect(user);
			} else {
				alert.showAlert(AlertType.ERROR, "Error", "Incorrect Password", "Please check your password.");
			}
		} else {
			alert.showAlert(AlertType.ERROR, "Error", "Account Doesn't Exists", "Please check your credentials.");
		}
	}
	
	private boolean verifyPassword(String password, String hashedPassword) {
        String hashedInputPassword = mainController.getRegisterController().hashPassword(password);
        return hashedInputPassword.equals(hashedPassword);
    }
	
	public void loginRedirect(User user) {
		mainController.setUser(user);
		
		if (user.getRole().equals(Role.CUSTOMER)) {
			mainController.getPageController().showBuyFlowerView();			
		} else if (user.getRole().equals(Role.ADMIN)) {
			mainController.getPageController().showManageFlowerView();
		}
	}
	
	public void logout() {
		mainController.setUser(null);
		System.out.println("Logout Success");
		mainController.getPageController().showLoginView();
	}
	
}
