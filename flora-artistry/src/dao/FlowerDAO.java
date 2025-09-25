package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Flower;

public class FlowerDAO extends DAO {

	public ObservableList<Flower> getAllFlowers() {
		ObservableList<Flower> flowers = FXCollections.observableArrayList();
		
		String query = "SELECT * FROM msflower";
		try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
            	String id = rs.getString("flowerid");
            	String name = rs.getString("flowername");
            	String type = rs.getString("flowertype");
            	Integer price = rs.getInt("flowerprice");
            	flowers.add(new Flower(id, name, type, price));
            }
            
            return flowers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public Flower getFlowerById(String flowerId) {
        String query = "SELECT * FROM msflower WHERE flowerid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, flowerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String id = rs.getString("flowerid");
                String name = rs.getString("flowername");
                String type = rs.getString("flowertype");
                Integer price = rs.getInt("flowerprice");
                
                // Create a Flower object with the retrieved data
                return new Flower(id, name, type, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the flower was not found
    }

	public boolean addFlower(Flower flower) {
	    String query = "INSERT INTO msflower (flowerid, flowername, flowertype, flowerprice) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, flower.getId());
	        ps.setString(2, flower.getName());
	        ps.setString(3, flower.getType());
	        ps.setInt(4, flower.getPrice());
	        
	        // Execute the update, it returns the number of affected rows
	        return ps.executeUpdate() > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Return false if insertion fails
	}

	public boolean updateFlower(Flower flower) {
	    String query = "UPDATE msflower SET flowername = ?, flowertype = ?, flowerprice = ? WHERE flowerid = ?";
	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, flower.getName());
	        ps.setString(2, flower.getType());
	        ps.setInt(3, flower.getPrice());
	        ps.setString(4, flower.getId());
	        
	        // Execute the update, it returns the number of affected rows
	        return ps.executeUpdate() > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Return false if update fails
	}

	public boolean deleteFlower(String flowerId) {
	    String query = "DELETE FROM msflower WHERE flowerid = ?";
	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, flowerId);
	        
	        // Execute the update, it returns the number of affected rows
	        return ps.executeUpdate() > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Return false if deletion fails
	}

	public String getHighestFlowerId() {
		String query = "SELECT flowerid FROM msflower ORDER BY flowerid DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String id = rs.getString("flowerid");
				return id;
			}
		}catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
}
