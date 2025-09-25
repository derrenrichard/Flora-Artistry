package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cart;
import model.Flower;
import model.User;

public class CartDAO extends DAO {
	

	public boolean insertCart(Cart cart) {
		String query = "INSERT INTO mscart (userid, flowerid, quantity) VALUES (?,?,?)";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, cart.getUserId());
			ps.setString(2, cart.getFlower().getId());
			ps.setInt(3, cart.getQuantity());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public Cart getCart(String userId, Flower flower) {
		String query = "SELECT * FROM mscart WHERE userid = ? AND flowerid = ?";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, userId);
			ps.setString(2, flower.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Integer quantity = rs.getInt("quantity");
				return new Cart(userId, flower, quantity);
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public boolean updateCart(String userId, String flowerId, Integer quantity) {
		String query = "UPDATE mscart SET quantity = quantity + ? WHERE userid = ? AND flowerid = ?";
		
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, quantity);
			ps.setString(2, userId);
			ps.setString(3, flowerId);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public ObservableList<Cart> getCart(String userId) {
	    ObservableList<Cart> carts = FXCollections.observableArrayList();
	    String query = "SELECT * FROM mscart WHERE userid = ?";

	    FlowerDAO flowerDAO = new FlowerDAO();

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, userId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            String flowerId = rs.getString("flowerid");
	            Integer quantity = rs.getInt("quantity");

	            // Retrieve the Flower object based on flower_id
	            Flower flower = flowerDAO.getFlowerById(flowerId);

	            if (flower != null) {
	                // Add new Cart object to the list
	                carts.add(new Cart(userId, flower, quantity));
	            }
	        }
	        return carts;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
    public void clearCart(String userId) throws SQLException {
        String query = "DELETE FROM mscart WHERE userid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userId);
            ps.executeUpdate();
        }
    }
    
    public void clearCart(String userId, String itemId) throws SQLException {
        String query = "DELETE FROM mscart WHERE userid = ? AND flowerid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userId);
            ps.setString(2, itemId);
            ps.executeUpdate();
        }
    }

}
