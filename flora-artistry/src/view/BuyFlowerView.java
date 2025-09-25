package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;
import model.Flower;
import view.partials.CustomerNavigation;

public class BuyFlowerView extends View {

	
	public BuyFlowerView(MainController mainController) {
		super(mainController);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Pane getPane() {
		BorderPane root = new BorderPane();
		root.getStyleClass().add("root");

		// Add CustomerMenuBar to the top
		CustomerNavigation customerMenuBar = new CustomerNavigation();
		root.setTop(customerMenuBar.getPartial(mainController));

		// Create TableView and TableColumns
		TableView<Flower> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Flower, String> idColumn = new TableColumn<>("");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setVisible(false);

		TableColumn<Flower, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Flower, String> typeColumn = new TableColumn<>("Type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

		TableColumn<Flower, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		// Add columns to the TableView
		table.getColumns().addAll(nameColumn, typeColumn, priceColumn);

		// Add a listener to bind column widths after the table is laid out
		table.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
			typeColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
			priceColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
		});

		// Set data
		table.setItems(mainController.getFlowerController().getAllFlowers());

		// Label to display selected flower details
		Label flowerDetails = new Label("Select a flower to see details.");
		flowerDetails.setStyle("-fx-font-size: 20px;");

		Button addToCart = new Button("Add to Cart");
		addToCart.setPrefSize(260, 60);
		addToCart.setVisible(false);

		// Set event for row selection
		table.setRowFactory(tv -> {
			TableRow<Flower> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty()) {
					Flower flower = row.getItem();
					String text = String.format("%s: %s\n%s: %s\n%s: %s", "Name",
							flower.getName() != null ? flower.getName() : "N/A", "Type",
							flower.getType() != null ? flower.getType() : "N/A", "Price",
							flower.getPrice() != null ? flower.getPrice() : "N/A");

					flowerDetails.setText(text);

					addToCart.setOnAction(e -> {
						addToCartPopup(flower, mainController, text);
					});
				}
				addToCart.setVisible(true);
			});
			return row;
		});

		// Greetings
		Label heading = new Label("Product List");
		heading.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

		Label subHeading = new Label("Welcome, " + mainController.getUser().getName());
		subHeading.setStyle("-fx-font-size: 24px;");

		// VBox to hold the table and flower details
		VBox vbox = new VBox();
		vbox.getChildren().addAll(heading, subHeading, table, flowerDetails, addToCart);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(20, 0, 0, 0));
		VBox.setMargin(table, new Insets(20, 0, 0, 0));

		// Set the VBox to the center of the BorderPane
		root.setCenter(vbox);

		return root;
	}

	private void addToCartPopup(Flower flower, MainController mainController, String text) {
		// Create a new Stage and Window for the pop-up window
		Stage popupStage = new Stage();
		Window popupWindow = new Window("Add to Cart");
		
		// Create UI elements
		Label itemLabel = new Label(text);
		Label qtyLabel = new Label("Quantity: ");
		Spinner<Integer> quantitySpinner = new Spinner<>();

		// Set up the value factory for the Spinner
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		quantitySpinner.setValueFactory(valueFactory);

		// Set the preferred size if needed
		quantitySpinner.setPrefSize(250, 40); // Adjust height as necessary

		Button addButton = new Button("Add to Cart");
		addButton.setPrefSize(250, 30);
		Button cancelButton = new Button("Cancel");
		cancelButton.setPrefSize(250, 30);

		// Set button actions
		addButton.setOnAction(e -> {
			mainController.getCartController().addToCart(popupStage, flower, quantitySpinner.getValue());
		});
		cancelButton.setOnAction(e -> popupStage.close());

		// Arrange elements in layout
		VBox layout = new VBox(10, itemLabel, qtyLabel, quantitySpinner, addButton, cancelButton);
		layout.setPadding(new Insets(20));
		
		popupWindow.getContentPane().getChildren().add(layout);
		
		// Wrap the Window in a container
	    StackPane root = new StackPane(popupWindow);

		// Set the layout to the stage
		Scene scene = new Scene(root, 300, 320);
		scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());
		popupStage.setScene(scene);
		popupStage.show();
	}

}
