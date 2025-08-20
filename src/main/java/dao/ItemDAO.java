package dao;

import com.pahanaEdu.util.DBConnection;

import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    
    public void addItem(Item item) throws Exception {
        String sql = "INSERT INTO items (item_name, category, price, stock_quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getCategory());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getStockQuantity());
            stmt.executeUpdate();
        }
    }

    public List<Item> getAllItems() throws Exception {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                items.add(item);
            }
        }
        return items;
    }

    public void deleteItem(int id) throws Exception {
        String sql = "DELETE FROM items WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public Item findById(int id) throws SQLException {
        String sql = "SELECT * FROM items WHERE item_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setCategory(rs.getString("category"));
                    item.setPrice(rs.getDouble("price"));
                    item.setStockQuantity(rs.getInt("stock_quantity"));
                    return item;
                }
            }
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

}
