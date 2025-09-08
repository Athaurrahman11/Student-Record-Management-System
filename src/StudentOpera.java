

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

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentOpera {

    public static boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM admin_users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insert(int id, String gender, String stuFullName, String address, String email, String faculty, String Stuphone, String parentName, String parentAddress, String parentPhone) {
        String sql = "INSERT INTO students (id, gender, stuFullName, address, email, faculty, Stuphone, parentName, parentAddress, parentPhone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting student record: " + e.getMessage());
            e.printStackTrace();
            return false;
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
                    rs.getInt("id"),
                    rs.getString("gender"),
                    rs.getString("stuFullName"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("faculty"),
                    rs.getString("Stuphone"),
                    rs.getString("parentName"),
                    rs.getString("parentAddress"),
                    rs.getString("parentPhone"),};
                model.addRow(row);
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
                    rs.getString("parentPhone"),};
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int deleteStudent(int id) {
        int rows = 0;
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement("DELETE FROM students WHERE id=?")) {
            pst.setInt(1, id);
            rows = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static int update(int id, String gender, String stuFullName, String address, String email,
            String faculty, String Stuphone, String parentName, String parentAddress, String parentPhone) {

        String sql = "UPDATE students SET gender = ?, stuFullName = ?, address = ?, email = ?, faculty = ?, "
                + "Stuphone = ?, parentName = ?, parentAddress = ?, parentPhone = ? WHERE id = ?";
        int rowsUpdated = 0;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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

            rowsUpdated = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating student record: " + e.getMessage());
        }

        return rowsUpdated;
    }

    public static void loadStudentsIntoComboBox(JComboBox<String> comboBox) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT stuFullName FROM students";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            comboBox.removeAllItems();

            while (rs.next()) {
                String fullName = rs.getString("stuFullName");
                comboBox.addItem(fullName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean insertEnrollments(int studentId, String[] courses) {
        String sql = "INSERT INTO enrollments (student_id, course_name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (String course : courses) {
                if (course != null && !course.isEmpty()) {
                    pstmt.setInt(1, studentId);
                    pstmt.setString(2, course);
                    pstmt.executeUpdate();
                }
            }
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }

    public static void searchEnrollments(String studentId, JTable table) {
        String sql = "SELECT e.student_id, s.stuFullName, e.course_name "
                + "FROM enrollments e "
                + "JOIN students s ON e.student_id = s.id "
                + "WHERE e.student_id LIKE ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + studentId + "%");
            ResultSet rs = stmt.executeQuery();

            // Setup table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getString("student_id"),
                    rs.getString("stuFullName"),
                    rs.getString("course_name")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching enrollments.");
        }
    }

    public static void loadAllEnrollment(JTable table) {
        String sql = "SELECT e.student_id, s.stuFullName, e.course_name "
                + "FROM enrollments e "
                + "JOIN students s ON e.student_id = s.id";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getString("student_id"),
                    rs.getString("stuFullName"),
                    rs.getString("course_name")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading enrollments.");
        }
    }

}
