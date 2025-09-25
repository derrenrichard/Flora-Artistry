package view;

import controller.MainController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Cart;
import view.partials.CustomerNavigation;

public class CartView extends View {

    public CartView(MainController mainController) {
		super(mainController);
	}

    @Override
	public Pane getPane() {
        BorderPane root = new BorderPane();
        
        // Customer Navbar
        CustomerNavigation customerMenuBar = new CustomerNavigation();
        root.setTop(customerMenuBar.getPartial(mainController));
        
        Label heading = new Label("Your Cart List");
        heading.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        // Cart TableView
        TableView<Cart> cartTableView = createCartTable(mainController.getCartController().getUserCart());
        
     // Label to display selected flower details
        Label cartDetails = new Label("Select a cart item to see details.");
        cartDetails.setStyle("-fx-font-size: 20px;");
        
        Integer totalPrice = mainController.getCartController().getTotal();
        Label total = new Label("Total: " + totalPrice);
        total.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Button checkout = new Button("Checkout");
        checkout.setPrefSize(260, 60);
        checkout.setVisible(false);
        
        Button checkoutAll = new Button("Checkout All Items");
        checkoutAll.setPrefSize(260, 60);
        checkoutAll.setOnAction(e -> {
        	mainController.getTransactionController().checkoutAllItems();
        });
        
        // Set event for row selection
        cartTableView.setRowFactory(tv -> {
            TableRow<Cart> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Cart cart = row.getItem();
                    
                    String text = String.format(
                        "%s: %s\n"
                        + "%s: %s\n"
                        + "%s: %s\n"
                        + "%s: %s", 
                        "Name", cart.getFlower().getName() != null ? cart.getFlower().getName() : "N/A",
                        "Type", cart.getFlower().getType() != null ? cart.getFlower().getType() : "N/A",
                        "Price", cart.getFlower().getPrice() != null ? cart.getFlower().getPrice() : "N/A",
                        "Subtotal", cart.getFlower().getPrice() != null && cart.getQuantity() != null ? cart.getFlower().getPrice() * cart.getQuantity() : "N/A" 
                    );
                    
                    cartDetails.setText(text);

                    checkout.setOnAction(e -> {
                    	// checkout function
                    	mainController.getTransactionController().checkoutItem(cart);
                    });
                }
                checkout.setVisible(true);
            });
            return row;
        });
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(heading, cartTableView, cartDetails, total, checkout, checkoutAll);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 0, 0, 0));
        VBox.setMargin(cartTableView, new Insets(20, 0, 0, 0));
        
        root.setCenter(vbox);
        
        return root;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
	private TableView<Cart> createCartTable(ObservableList<Cart> cartItems) {
        TableView<Cart> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // Create columns
        TableColumn<Cart, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlower().getName()));
        
        TableColumn<Cart, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlower().getType()));
        
        TableColumn<Cart, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFlower().getPrice()).asObject());
        
        TableColumn<Cart, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<Cart, Integer> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(cellData -> {
            int totalPrice = cellData.getValue().getFlower().getPrice() * cellData.getValue().getQuantity();
            return new SimpleIntegerProperty(totalPrice).asObject();
        });

        // Add columns to the table
        table.getColumns().addAll(nameColumn, typeColumn, priceColumn, quantityColumn, totalColumn);

        // Set equal width for all columns
        for (TableColumn<Cart, ?> col : table.getColumns()) {
            col.prefWidthProperty().bind(table.widthProperty().divide(table.getColumns().size()));
        }

        // Set data to the table
        table.setItems(cartItems);
        
        return table;
    }

}
