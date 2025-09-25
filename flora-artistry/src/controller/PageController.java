package controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.BuyFlowerView;
import view.CartView;
import view.LoginView;
import view.ManageFlowerView;
import view.RegisterView;

public class PageController {
	
	private MainController mainController;
	
	public PageController(MainController mainController) {
		this.mainController = mainController;
	}

	public void showLoginView() {
		setPane(new LoginView(mainController).getPane());
	}
	
	public void showRegisterView() {
		setPane(new RegisterView(mainController).getPane());
	}
	
	public void showBuyFlowerView() {
		setPane(new BuyFlowerView(mainController).getPane());
	}
	
	public void showCartView() {
		setPane(new CartView(mainController).getPane());
	}
	
	public void showManageFlowerView() {
		if (!mainController.isAdmin()) {
			showLoginView();
			return;
		}
		setPane(new ManageFlowerView(mainController).getPane());
	}
	
	private void setPane(Pane pane) {		
		Scene scene = new Scene(pane, 1200, 900);
		scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());
		mainController.getStage().setScene(scene);
		mainController.getStage().show();
	}
	
}
