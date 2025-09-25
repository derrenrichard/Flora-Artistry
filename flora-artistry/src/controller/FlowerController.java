package controller;

import dao.FlowerDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import model.Flower;
import view.partials.CustomAlert;

public class FlowerController extends Controller<FlowerDAO> {

    public FlowerController(MainController mainController) {
		super(mainController, new FlowerDAO());
	}

	public ObservableList<Flower> getAllFlowers() {
        return dao.getAllFlowers();
    }
    
    // Validation method to check the inputs
    private boolean validateFlowerData(String name, String type, Integer price) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Debug: Invalid name");
            alert.showAlert(AlertType.ERROR, "Error", "Invalid Name", "Please enter a valid name.");
            return false;
        }
        
        if (type == null || type.trim().isEmpty()) {
            System.out.println("Debug: Invalid type");
            alert.showAlert(AlertType.ERROR, "Error", "Invalid Type", "Please enter a valid type.");
            return false;
        }
        
        if (price == null || price <= 0) {
            System.out.println("Debug: Invalid price");
            alert.showAlert(AlertType.ERROR, "Error", "Invalid Price", "Please enter a valid price greater than zero.");
            return false;
        }

        System.out.println("Debug: Validation passed");
        return true;
    }

    public void addFlower(String name, String type, Integer price) {
        // Validate the input before proceeding
        if (!validateFlowerData(name, type, price)) {
            return;
        }

        // Generate a new ID based on the highest existing ID
        String id = "FL001";
        String highestId = dao.getHighestFlowerId();

        if (highestId != null && !highestId.isEmpty()) {
            Integer numOnly = Integer.parseInt(highestId.substring(2));
            Integer nextId = numOnly + 1;
            id = String.format("FL%03d", nextId);
        }

        Flower flower = new Flower(id, name, type, price);

        // Add the flower and show appropriate alerts
        if (dao.addFlower(flower)) {
            alert.showAlert(AlertType.INFORMATION, "Success", "Add Flower Successful", "Click OK to continue.");
            mainController.getPageController().showManageFlowerView();
        } else {
            alert.showAlert(AlertType.ERROR, "Error", "Add Flower Failed", "Cannot add flower at this moment.");
        }
    }

    public void updateFlower(Flower flower) {
        // Validate the flower data before updating
        if (!validateFlowerData(flower.getName(), flower.getType(), flower.getPrice())) {
            mainController.getPageController().showManageFlowerView();
            return;
        }

        // Update the flower and show appropriate alerts
        if (dao.updateFlower(flower)) {
            alert.showAlert(AlertType.INFORMATION, "Success", "Update Flower Successful", "Click OK to continue.");
            mainController.getPageController().showManageFlowerView();
        } else {
            alert.showAlert(AlertType.ERROR, "Error", "Update Flower Failed", "Cannot update flower at this moment.");
        }
    }

    public void deleteFlower(String flowerId) {
        if (dao.deleteFlower(flowerId)) {
            alert.showAlert(AlertType.INFORMATION, "Success", "Delete Flower Successful", "Click OK to continue.");
            mainController.getPageController().showManageFlowerView();
        } else {
            alert.showAlert(AlertType.ERROR, "Error", "Delete Flower Failed", "Cannot delete flower at this moment.");
        }
    }
}
