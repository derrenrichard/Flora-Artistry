package controller;

import dao.CartDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Cart;
import model.Flower;
import view.partials.CustomAlert;

public class CartController extends Controller<CartDAO> {

	public CartController(MainController mainController) {
		super(mainController, new CartDAO());
	}

	public MainController getMainController() {
		return mainController;
	}
	
	public CartDAO getDao() {
		return dao;
	}

	public void addToCart(Stage popupStage, Flower flower, Integer quantity) {
		if (flower == null || quantity == 0) {
			alert.showAlert(AlertType.ERROR, "Error", "Add to Cart Failed", "Invalid flower or quantity.");
			return;
		}
		
		String userId = mainController.getUser().getId();
		if (dao.getCart(userId, flower) != null) {
			System.out.println("Already have product in cart");
			dao.updateCart(userId, flower.getId(), quantity);
			
			popupStage.close();
			alert.showAlert(AlertType.INFORMATION, "Success", "Item Already in Cart", "Added the quantity.");
			
			return;
		}
		
		Cart cart = new Cart(userId, flower, quantity);
		if (dao.insertCart(cart)) {
			System.out.println("Add to cart success");
			
			popupStage.close();
			alert.showAlert(AlertType.INFORMATION, "Success", "Added to Cart Successfully", "Please click OK to continue.");
		} else {
			alert.showAlert(AlertType.ERROR, "Error", "Add to cart Failed", "Please try again later.");
		}
	}
	
	public ObservableList<Cart> getUserCart() {
		return dao.getCart(mainController.getUser().getId());
	}
	
	public Integer getTotal() {
		ObservableList<Cart> userCart = getUserCart();
		Integer total = 0;
		for (Cart c : userCart) {
			total += c.getQuantity() * c.getFlower().getPrice();
		}
		return total;
	}

}
