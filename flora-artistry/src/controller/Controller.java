package controller;

import view.partials.CustomAlert;

public abstract class Controller<T> {

	MainController mainController;
	T dao;
	CustomAlert alert;
	
	public Controller(MainController mainController, T dao) {
		this.mainController = mainController;
		this.dao = dao;
		this.alert = new CustomAlert();
	}
	
}
