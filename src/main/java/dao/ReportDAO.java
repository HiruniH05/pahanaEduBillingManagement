package dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pahanaEdu.util.DBConnection;

import java.util.LinkedHashMap;

public class ReportDAO {

    // Returns rows: day -> total_sales
    public List<Map<String,Object>> dailySales() throws Exception {
        String sql = "SELECT DATE(bill_date) AS day, SUM(total_amount) AS total_sales " +
                     "FROM bills GROUP BY DATE(bill_date) ORDER BY day DESC";
        List<Map<String,Object>> out = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String,Object> row = new LinkedHashMap<>();
                row.put("day", rs.getDate("day"));
                row.put("total_sales", rs.getDouble("total_sales"));
                out.add(row);
            }
        }
        return out;
    }

    // Top-selling items by revenue
    public List<Map<String,Object>> topItems() throws Exception {
        String sql = "SELECT i.item_name, SUM(bi.quantity) AS qty, SUM(bi.subtotal) AS revenue " +
                     "FROM bill_items bi JOIN items i ON bi.item_id = i.item_id " +
                     "GROUP BY i.item_name ORDER BY revenue DESC LIMIT 10";
        List<Map<String,Object>> out = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String,Object> row = new LinkedHashMap<>();
                row.put("item_name", rs.getString("item_name"));
                row.put("qty", rs.getInt("qty"));
                row.put("revenue", rs.getDouble("revenue"));
                out.add(row);
            }
        }
        return out;
    }
}
