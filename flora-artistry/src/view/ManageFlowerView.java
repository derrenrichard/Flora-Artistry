package view;

import controller.MainController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Flower;
import view.partials.AdminNavigation;

public class ManageFlowerView extends View {

	private TableView<Flower> flowerTable;
	private TextField nameField, typeField, priceField;
	private Button addButton, updateButton, deleteButton, cancelButton;

	public ManageFlowerView(MainController mainController) {
		super(mainController);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Pane getPane() {
		BorderPane root = new BorderPane();

		AdminNavigation adminMenuBar = new AdminNavigation(mainController);
		root.setTop(adminMenuBar.getMenuBar());

		// Create TableView
		flowerTable = new TableView<>();
		flowerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		flowerTable.setItems(mainController.getFlowerController().getAllFlowers());

		// Create Table Columns
		TableColumn<Flower, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

		TableColumn<Flower, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

		TableColumn<Flower, String> typeColumn = new TableColumn<>("Type");
		typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));

		TableColumn<Flower, Integer> priceColumn = new TableColumn<>("Price");
		priceColumn
				.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());

		flowerTable.getColumns().addAll(idColumn, nameColumn, typeColumn, priceColumn);

		// Set equal width for all columns
		for (TableColumn<Flower, ?> col : flowerTable.getColumns()) {
			col.prefWidthProperty().bind(flowerTable.widthProperty().divide(flowerTable.getColumns().size()));
		}

		// Labels
		Label nameLabel = new Label("Name");
		Label typeLabel = new Label("Type");
		Label priceLabel = new Label("Price");
		
		// Form Fields
		nameField = new TextField();
		nameField.setPromptText("Name");

		typeField = new TextField();
		typeField.setPromptText("Type");

		priceField = new TextField();
		priceField.setPromptText("Price");
		
		nameLabel.getStyleClass().add("form-label");
		typeLabel.getStyleClass().add("form-label");
		priceLabel.getStyleClass().add("form-label");

		nameField.getStyleClass().add("form-field");
        typeField.getStyleClass().add("form-field");
        priceField.getStyleClass().add("form-field");

		// Buttons
		addButton = new Button("Add");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");
		cancelButton = new Button("Cancel");
		
		updateButton.setVisible(false);
		deleteButton.setVisible(false);
		addButton.setVisible(true);

		// Set button actions
		setButtonActions();

		// Layout Setup
		GridPane formLayout = new GridPane();
		formLayout.setPadding(new Insets(10));
		formLayout.setVgap(10);
		formLayout.setHgap(10);
		formLayout.add(nameLabel, 0, 0);
		formLayout.add(nameField, 1, 0);
		formLayout.add(typeLabel, 0, 1);
		formLayout.add(typeField, 1, 1);
		formLayout.add(priceLabel, 0, 2);
		formLayout.add(priceField, 1, 2);

		HBox buttonLayout = new HBox(10);
		buttonLayout.setPadding(new Insets(10, 0, 0, 0));
		buttonLayout.getChildren().addAll(addButton, updateButton, deleteButton, cancelButton);

		formLayout.add(buttonLayout, 1, 4);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20));
		grid.setVgap(10);
		grid.setHgap(10);
		grid.add(formLayout, 0, 1, 2, 1);
		grid.setAlignment(Pos.CENTER);

		Label flowerListLbl = new Label("Flower List");
		flowerListLbl.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

		Label greetingLbl = new Label("Welcome, " + mainController.getUser().getName());
		greetingLbl.setStyle("-fx-font-size: 28px;");

		VBox vbox = new VBox();
		vbox.getChildren().addAll(flowerListLbl, greetingLbl, flowerTable, grid);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(20, 0, 0, 0));
		VBox.setMargin(flowerTable, new Insets(20, 0, 0, 0));

		root.setCenter(vbox);

		return root;
	}

	private void setButtonActions() {
		// Add Button Action
		addButton.setOnAction(e -> {
			String name = nameField.getText();
			String type = typeField.getText();
			Integer price = 0;
			if (!priceField.getText().trim().isEmpty()) {
				price = Integer.parseInt(priceField.getText());				
			}
			mainController.getFlowerController().addFlower(name, type, price);
		});

		// Update Button Action
		updateButton.setOnAction(e -> {
			Flower selectedFlower = flowerTable.getSelectionModel().getSelectedItem();
			if (selectedFlower != null) {
				selectedFlower.setName(nameField.getText());
				selectedFlower.setType(typeField.getText());
				selectedFlower.setPrice(Integer.parseInt(priceField.getText()));
				mainController.getFlowerController().updateFlower(selectedFlower);
			}
		});

		// Delete Button Action
		deleteButton.setOnAction(e -> {
			Flower selectedFlower = flowerTable.getSelectionModel().getSelectedItem();
			if (selectedFlower != null) {
				mainController.getFlowerController().deleteFlower(selectedFlower.getId());
			}
		});

		// Cancel Button Action
		cancelButton.setOnAction(e -> {
			clearForm();
			updateButton.setVisible(false);
			deleteButton.setVisible(false);
			addButton.setVisible(true);
		});

		// On Table Row Click (to populate form)
		flowerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				updateButton.setVisible(true);
				deleteButton.setVisible(true);
				addButton.setVisible(false);
				populateForm(newSelection);
			}
		});
	}

	// Populate form when a row is clicked
	private void populateForm(Flower flower) {
		nameField.setText(flower.getName());
		typeField.setText(flower.getType());
		priceField.setText(String.valueOf(flower.getPrice()));
	}

	// Clear the form fields
	private void clearForm() {
		nameField.clear();
		typeField.clear();
		priceField.clear();
	}

}
