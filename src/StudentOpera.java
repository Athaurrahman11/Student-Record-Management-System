

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Athaurrahman
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentOpera {

    public static void insert(int id, String gender, String stuFullName, String address, String email,String faculty,String Stuphone,String parentName,String parentAddress,String parentPhone) {
        String sql = "INSERT INTO students (id, gender, stuFullName, address, email,faculty,Stuphone,parentName,parentAddress,parentPhone) VALUES (?, ?, ?,?, ?, ?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, gender);
            stmt.setString(3, stuFullName);
            stmt.setString(4, address);
            stmt.setString(5, email);            
           
            stmt.setString(6, faculty);
            stmt.setString(7, Stuphone);            
            stmt.setString(8, parentName);            
            stmt.setString(9, parentAddress);           
            stmt.setString(10, parentPhone);






            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student record inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting student record: " + e.getMessage());
            e.printStackTrace();
        }
    }

public static void searchStudent(String id, JTable table) {
    try {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM students WHERE id LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + id + "%");
        ResultSet rs = stmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
            Object[] row = {
               
                rs.getString("id"),               
                rs.getString("gender"),
                rs.getString("stuFullName"),               
                rs.getString("address"),
                rs.getString("email"),               
                rs.getString("faculty"),
                rs.getString("Stuphone"),               
                rs.getString("parentName"),
                rs.getString("parentAddress"),               
                rs.getString("parentPhone"),
                
            };
            model.addRow(row);  // Add new row
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void viewStudent(JTable table) {
    try {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM students ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 

        while (rs.next()) {
            Object[] row = {
               
                rs.getString("id"),               
                rs.getString("gender"),
                rs.getString("stuFullName"),               
                rs.getString("address"),
                rs.getString("email"),               
                rs.getString("faculty"),
                rs.getString("Stuphone"),               
                rs.getString("parentName"),
                rs.getString("parentAddress"),               
                rs.getString("parentPhone"),
                
            };
            model.addRow(row);  
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static void deleteStudent(int id) {
    try {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM students WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static void update(int id, String gender, String stuFullName, String address, String email,
                          String faculty, String Stuphone, String parentName, String parentAddress, String parentPhone) {
    
    String sql = "UPDATE students SET gender = ?, stuFullName = ?, address = ?, email = ?, faculty = ?, " +
                 "Stuphone = ?, parentName = ?, parentAddress = ?, parentPhone = ? WHERE id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, gender);
        stmt.setString(2, stuFullName);
        stmt.setString(3, address);
        stmt.setString(4, email);
        stmt.setString(5, faculty);
        stmt.setString(6, Stuphone);
        stmt.setString(7, parentName);
        stmt.setString(8, parentAddress);
        stmt.setString(9, parentPhone);
        stmt.setInt(10, id); 

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Student record updated successfully!");
        } else {
            System.out.println("No student found with ID: " + id);
        }

    } catch (SQLException e) {
        System.out.println("Error updating student record: " + e.getMessage());
        e.printStackTrace();
    }
}

}


