package view.partials;

import controller.MainController;
import javafx.scene.control.MenuBar;

public abstract class Navigation {

	MainController mainController;

	public Navigation(MainController mainController) {
		this.mainController = mainController;
	}
	
	public abstract MenuBar getMenuBar();
	
}
