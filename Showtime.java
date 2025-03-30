import java.sql.Timestamp;
import java.util.*;

public class Showtime {

    DatabaseOperation db = new DatabaseOperation();

    public void insertShowtime(int MovieID, int TheaterID, Timestamp showtime) {
        // Check if showtime already exists
        String checkSql = "SELECT COUNT(*) AS count FROM Showtimes WHERE MovieID = ? AND TheaterID = ? AND Showtime = ?";
        Object[] checkParams = {MovieID, TheaterID, showtime};
        List<Map<String, Object>> result = db.getRecords(checkSql, checkParams);

        if (!result.isEmpty()) {
            Number count = (Number) result.get(0).get("count");
            if (count.intValue() > 0) {
                System.out.println("Showtime already exists for this movie in the selected theater.");
                return;
            }
        }

        // Insert the new showtime
        String sql = "INSERT INTO Showtimes(MovieID, TheaterID, Showtime) VALUES(?, ?, ?)";
        Object[] values = {MovieID, TheaterID, showtime};
        int rowsAffected = db.executeUpdate(sql, values);

        if (rowsAffected > 0) {
            System.out.println("Showtime added successfully.");
        } else {
            System.out.println("Something went wrong. Showtime not inserted.");
        }
    }

    public void showShowtimes() {
        String sql = "SELECT * FROM Showtimes";
        List<Map<String, Object>> showtimes = db.getRecords(sql, new Object[]{});

        if (showtimes.isEmpty()) {
            System.out.println("No showtimes available.");
            return;
        }

        for (Map<String, Object> showtime : showtimes) {
            System.out.println("Showtime ID: " + showtime.get("ShowtimeID"));
            System.out.println("Movie ID: " + showtime.get("MovieID"));
            System.out.println("Theater ID: " + showtime.get("TheaterID"));
            System.out.println("Showtime: " + showtime.get("Showtime"));
            System.out.println("-----------------------------");
        }
    }

    public void showShowtimesDetails(int showtimeID) {
        String sql = "SELECT s.ShowtimeID, m.Title, m.Duration, s.Showtime " +
                     "FROM Showtimes s " +
                     "JOIN Movies m ON s.MovieID = m.MovieID " +
                     "WHERE s.ShowtimeID = ?";
        
        Object[] params = {showtimeID};
        List<Map<String, Object>> result = db.getRecords(sql, params);
        
        if (result.isEmpty()) {
            System.out.println("No details found for Showtime ID: " + showtimeID);
            return;
        }

        for (Map<String, Object> details : result) {
            System.out.println("Showtime ID: " + details.get("ShowtimeID"));
            System.out.println("Movie Title: " + details.get("Title"));
            System.out.println("Duration: " + details.get("Duration") + " mins");
            System.out.println("Showtime: " + details.get("Showtime"));
            System.out.println("-----------------------------");
        }
    }

    public int getTheaterCapacity(int showtime) {
        String sql = "SELECT SeatingCapacity FROM Theaters " +
                     "WHERE TheatreID = (SELECT TheatreID FROM Showtimes WHERE ShowtimeID = ?)";

        Object[] params = {showtime};
        List<Map<String, Object>> result = db.getRecords(sql, params);
        
        if (result.isEmpty()) {
            System.out.println("No capacity found for this Showtime ID.");
            return -1; 
        }

        return ((Number) result.get(0).get("SeatingCapacity")).intValue();
    }
}
