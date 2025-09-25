package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import model.Role;
import model.User;

public class UserDAO extends DAO {

	// Create a new user in the database
    public boolean addUser(User user) {
        String query = "INSERT INTO msuser (userid, username, useremail, userpassword, useraddress, userphonenumber, userrole) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
        	ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getRole().name());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String getHighestUserId() {
        String query = "SELECT userid FROM msuser ORDER BY userid DESC LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("userid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM msuser WHERE useremail = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("userid"));
                    user.setName(rs.getString("username"));
                    user.setEmail(rs.getString("useremail"));
                    user.setPassword(rs.getString("userpassword"));
                    user.setAddress(rs.getString("useraddress"));
                    user.setPhone(rs.getString("userphonenumber"));
                    user.setRole(Role.valueOf(rs.getString("userrole").toUpperCase()));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserById(int userId) {
        String query = "SELECT * FROM msuser WHERE userid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("userid"));
                    user.setName(rs.getString("username"));
                    user.setEmail(rs.getString("useremail"));
                    user.setPassword(rs.getString("userpassword"));
                    user.setAddress(rs.getString("useraddress"));
                    user.setPhone(rs.getString("userphonenumber"));
                    user.setRole(Role.valueOf(rs.getString("userrole").toUpperCase()));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
