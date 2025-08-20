package dao;



import java.sql.*;
import java.util.List;

import com.pahanaEdu.util.DBConnection;

import model.BillItem;

public class BillDAO {

    public int createBillHeader(int customerId, int userId, double totalAmount) throws Exception {
        String sql = "INSERT INTO bills(customer_id, user_id, total_amount) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setInt(2, userId);
            ps.setDouble(3, totalAmount);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return 0;
    }

    public void addBillItems(int billId, List<BillItem> items) throws Exception {
        String sql = "INSERT INTO bill_items(bill_id, item_id, quantity, subtotal) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (BillItem bi : items) {
                ps.setInt(1, billId);
                ps.setInt(2, bi.getItemId());
                ps.setInt(3, bi.getQuantity());
                ps.setDouble(4, bi.getSubtotal());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void updateBillTotal(int billId, double total) throws Exception {
        String sql = "UPDATE bills SET total_amount=? WHERE bill_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, billId);
            ps.executeUpdate();
        }
    }
}
