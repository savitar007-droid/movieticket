import java.sql.*;
import java.util.*;

public class DatabaseOperation {

    private static final String URL = "jdbc:mysql://localhost:3306/movie?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "root";

    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver successfully loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    private Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Map<String, Object>> getRecords(String sql, Object[] params) {
        List<Map<String, Object>> records = new ArrayList<>();
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    records.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching records: " + e.getMessage());
        }
        return records;
    }

    public int executeUpdate(String sql, Object[] values) {
        int rowsAffected = 0;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                ps.setObject(i + 1, values[i]);
            }

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
        }
        return rowsAffected;
    }

    public ArrayList<Integer> getBookedSeats(int showtimeID) {
        String sql = "SELECT SelectedSeats FROM bookings WHERE ShowtimeID = ?";
        ArrayList<Integer> bookedSeats = new ArrayList<>();

        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeID);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookedSeats.add(rs.getInt("SelectedSeats"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching booked seats: " + e.getMessage());
        }
        return bookedSeats;
    }

    public void getAllBookingsForUser(int userID) {
        String sql = "SELECT * FROM bookings WHERE userid = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("----- Booking Details -----");
                System.out.println("Booking ID: " + rs.getInt("BookingID"));
                System.out.println("Showtime ID: " + rs.getInt("ShowtimeID"));
                System.out.println("Seat number: " + rs.getInt("SelectedSeats"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int removeBooking(int bookingID) {
        String sql = "DELETE FROM bookings WHERE BookingID = ?";
        int rowsAffected = 0;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public String validatePass(String sql, String username) {
        String pass = "";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pass = rs.getString("Password");  // Ensure column name is correctly capitalized
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pass;
    }

    public int fetchUserID(String sql, String username) {
        int userID = 0;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userID = rs.getInt("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    }
}
