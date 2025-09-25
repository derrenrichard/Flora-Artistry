package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.TransactionDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import model.Cart;
import model.TransactionDetail;
import view.partials.CustomAlert;

public class TransactionController extends Controller<TransactionDAO> {

	public TransactionController(MainController mainController) {
		super(mainController, new TransactionDAO());
	}

	public MainController getMainController() {
		return mainController;
	}
	
	// Method to checkout a single item
    public void checkoutItem(Cart cartItem) {
        String transactionId = generateTransactionId();
        
        String userId = mainController.getUser().getId(); // Get the current user ID
        String flowerId = cartItem.getFlower().getId();

        // Create transaction details
        List<TransactionDetail> details = new ArrayList<>();
        details.add(new TransactionDetail(transactionId, cartItem.getFlower().getId(), cartItem.getQuantity()));

        // Create the transaction in the database
        try {
            dao.createTransaction(transactionId, userId, details);
            mainController.getCartController().getDao().clearCart(userId, flowerId);
            System.out.println("Transaction successful: " + transactionId);
            
            alert.showAlert(AlertType.INFORMATION, "Success", "Checkout Successful", "One item checked out successfully.");
            mainController.getPageController().showCartView();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
    
    public void checkoutAllItems() {
    	String userId = mainController.getUser().getId(); // Get the current user ID

        // Retrieve all cart items for the user
        ObservableList<Cart> cartItems = mainController.getCartController().getUserCart();

        if (cartItems.isEmpty()) {
        	System.out.println("User's cart is empty");
        	alert.showAlert(AlertType.ERROR, "Error", "Checkout Failed", "Your cart is empty.");
        	return;
        }
        
        // List to hold transaction details
        List<TransactionDetail> transactionDetails = new ArrayList<>();

        // Generate transaction ID
        String transactionId = generateTransactionId(); // You may want to use the same logic as in checkoutItem()

        try {
            // Loop through all cart items and create transaction details
            for (Cart cartItem : cartItems) {
                String flowerId = cartItem.getFlower().getId();
                Integer quantity = cartItem.getQuantity();

                // Add transaction details for each item
                transactionDetails.add(new TransactionDetail(transactionId, flowerId, quantity));
            }

            // Create the transaction in the database
            dao.createTransaction(transactionId, userId, transactionDetails);

            // Clear the cart after successful transaction
            mainController.getCartController().getDao().clearCart(userId);

            System.out.println("All items checked out successfully: " + transactionId);
            
            alert.showAlert(AlertType.INFORMATION, "Success", "Checkout Successful", "All items checked out successfully.");
            mainController.getPageController().showCartView();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Checkout failed: " + e.getMessage());
        }
    }

	private String generateTransactionId() {
		String transactionId = "TR001";

		// Get the highest user ID from the database
		String highestId = dao.getHighestTransactionId();

		if (highestId != null && !highestId.isEmpty()) {
			Integer numOnly = Integer.parseInt(highestId.substring(2));
			Integer nextId = numOnly + 1;
			transactionId = String.format("TR%03d", nextId);
		}
		
		return transactionId;
	}
	
	
}
