package dao;

import model.Transaction;
import model.TransactionDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connect;

public class TransactionDAO extends DAO {

    // Create a new transaction
    public void createTransaction(String transactionId, String userId, List<TransactionDetail> details) throws SQLException {
        String headerQuery = "INSERT INTO transactionheader (transactionid, userid) VALUES (?, ?)";
        String detailQuery = "INSERT INTO transactiondetail (transactionid, flowerid, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement headerStmt = con.prepareStatement(headerQuery);
             PreparedStatement detailStmt = con.prepareStatement(detailQuery)) {

            // Insert transaction header
            headerStmt.setString(1, transactionId);
            headerStmt.setString(2, userId);
            headerStmt.executeUpdate();

            // Insert transaction details
            for (TransactionDetail detail : details) {
                detailStmt.setString(1, transactionId);
                detailStmt.setString(2, detail.getFlowerId());
                detailStmt.setInt(3, detail.getQuantity());
                detailStmt.executeUpdate();
            }
        }
    }

    // Retrieve transaction details by transaction ID
    public Transaction getTransaction(String transactionId) throws SQLException {
        String headerQuery = "SELECT * FROM transactionheader WHERE transactionid = ?";
        String detailQuery = "SELECT * FROM transactiondetail WHERE transactionid = ?";
        Transaction transaction = null;
        List<TransactionDetail> details = new ArrayList<>();

        try (PreparedStatement headerStmt = con.prepareStatement(headerQuery);
             PreparedStatement detailStmt = con.prepareStatement(detailQuery)) {

            headerStmt.setString(1, transactionId);
            ResultSet headerRs = headerStmt.executeQuery();

            if (headerRs.next()) {
                String userId = headerRs.getString("user_id");
                transaction = new Transaction(transactionId, userId);
            }

            detailStmt.setString(1, transactionId);
            ResultSet detailRs = detailStmt.executeQuery();

            while (detailRs.next()) {
                String flowerId = detailRs.getString("flower_id");
                int quantity = detailRs.getInt("quantity");
                details.add(new TransactionDetail(transactionId, flowerId, quantity));
            }

            if (transaction != null) {
                transaction.setDetails(details);
            }
        }

        return transaction;
    }
    
    public String getHighestTransactionId() {
        String query = "SELECT transactionid FROM transactionheader ORDER BY transactionid DESC LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("transactionid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
