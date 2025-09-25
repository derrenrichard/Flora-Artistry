package view.partials;

import controller.MainController;
import controller.PageController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class AuthNavigation extends Navigation {

	public AuthNavigation(MainController mainController) {
		super(mainController);
	}

	@Override
	public MenuBar getMenuBar() {
		MenuItem loginItem = new MenuItem("Login");
		loginItem.setOnAction(e -> {
			mainController.getPageController().showLoginView();
		});
		MenuItem registerItem = new MenuItem("Register");
		registerItem.setOnAction(e -> {
			mainController.getPageController().showRegisterView();
		});
		
		Menu pageMenu = new Menu("Page");
		pageMenu.getItems().addAll(loginItem, registerItem);
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(pageMenu);
		
		return menuBar;
	}
}
