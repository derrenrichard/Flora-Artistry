package view;

import controller.MainController;
import javafx.scene.layout.Pane;

public abstract class View {

	MainController mainController;
	
	public View(MainController mainController) {
		this.mainController = mainController;
	}

	public abstract Pane getPane();
	
}
