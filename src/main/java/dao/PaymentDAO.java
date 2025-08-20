package dao;



import java.sql.*;

import com.pahanaEdu.util.DBConnection;

import model.Payment;

public class PaymentDAO {

    public boolean addPayment(Payment p) throws Exception {
        String sql = "INSERT INTO payments(bill_id, amount_paid, payment_method) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getBillId());
            ps.setDouble(2, p.getAmountPaid());
            ps.setString(3, p.getPaymentMethod());
            return ps.executeUpdate() > 0;
        }
    }
}
