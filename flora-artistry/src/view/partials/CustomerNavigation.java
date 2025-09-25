package view.partials;

import controller.MainController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class CustomerNavigation extends Navigation {
	
	public CustomerNavigation(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MenuBar getMenuBar() {
		MenuItem buyFlower = new MenuItem("Buy Flower");
		buyFlower.setOnAction(e -> {
			mainController.getPageController().showBuyFlowerView();			
		});
		
		MenuItem cart = new MenuItem("Cart");
		cart.setOnAction(e -> {
			mainController.getPageController().showCartView();
		});
		
		MenuItem logout = new MenuItem("Log Out");
		logout.setOnAction(e -> {
			mainController.getLoginController().logout();
		});
		
		Menu pageMenu = new Menu("Page");
		pageMenu.getItems().addAll(buyFlower, cart, logout);
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(pageMenu);
		
		return menuBar;
	}
}
