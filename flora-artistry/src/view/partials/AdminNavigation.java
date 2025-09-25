package view.partials;

import controller.MainController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class AdminNavigation extends Navigation {

	public AdminNavigation(MainController mainController) {
		super(mainController);
	}

	@Override
	public MenuBar getMenuBar() {
		MenuItem logout = new MenuItem("Logout");
		logout.setOnAction(e -> {
			mainController.getLoginController().logout();
		});
		
		Menu accountMenu = new Menu("Account");
		accountMenu.getItems().add(logout);
		
		MenuBar adminMenuBar = new MenuBar();
		adminMenuBar.getMenus().add(accountMenu);
		
		return adminMenuBar;
	}
	
}
