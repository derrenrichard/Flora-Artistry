package controller;

import javafx.stage.Stage;
import model.Role;
import model.User;

public class MainController {
	
	private Stage stage;
	private User user;
	
	private PageController pageController;
	private LoginController loginController;
	private RegisterController registerController;
	private FlowerController flowerController;
	private CartController cartController;
	private TransactionController transactionController;
	
	public MainController(Stage stage) {
		this.stage = stage;
		this.user = null;
		this.pageController = new PageController(this);
		this.loginController = new LoginController(this);
		this.registerController = new RegisterController(this);
		this.flowerController = new FlowerController(this);
		this.cartController = new CartController(this);
		this.transactionController = new TransactionController(this);
	}

	public PageController getPageController() {
		return pageController;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public RegisterController getRegisterController() {
		return registerController;
	}

	public FlowerController getFlowerController() {
		return flowerController;
	}

	public CartController getCartController() {
		return cartController;
	}
	
	public TransactionController getTransactionController() {
		return transactionController;
	}

	public Stage getStage() {
		return stage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isAdmin() {
		if (user.getRole().equals(Role.ADMIN)) {
			return true;
		}
		return false;
	}
	
}
