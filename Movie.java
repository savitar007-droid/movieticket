import java.util.List;
import java.util.Map;

public class Movie {
    private String title;
    private String genre;
    private double rating;
    private int duration;
    private String synopsis;

    private static DatabaseOperation db = new DatabaseOperation(); // Reuse DatabaseOperation

    // Default Constructor
    public Movie() {}

    // Parameterized Constructor
    public Movie(String title, String genre, double rating, int duration, String synopsis) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.synopsis = synopsis;
    }

    // Insert Movie using Instance Variables
    public void insertMovie() {
        if (title == null || title.isEmpty()) {
            System.out.println("Movie title cannot be empty.");
            return;
        }

        // Check if movie already exists
        String checkSql = "SELECT COUNT(*) AS count FROM movies WHERE title = ?";
        Object[] checkParams = {this.title};
        List<Map<String, Object>> result = db.getRecords(checkSql, checkParams);

        if (!result.isEmpty() && ((Number) result.get(0).get("count")).intValue() > 0) {
            System.out.println("Movie already exists: " + title);
            return;
        }

        // Insert movie if not duplicate
        String sql = "INSERT INTO movies (title, genre, rating, duration, synopsis) VALUES (?, ?, ?, ?, ?)";
        Object[] values = {this.title, this.genre, this.rating, this.duration, this.synopsis};

        int rowsAffected = db.executeUpdate(sql, values);
        System.out.println(rowsAffected > 0 ? "Movie inserted successfully: " + title : "Failed to insert movie.");
    }

    // Display All Movies
    public static void showMovies() {
        String sql = "SELECT * FROM movies";
        List<Map<String, Object>> movies = db.getRecords(sql, new Object[]{});

        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        System.out.println("\nAvailable Movies:");
        for (Map<String, Object> movie : movies) {
            System.out.println("Movie ID: " + movie.get("MovieID"));
            System.out.println("Title: " + movie.get("Title"));
            System.out.println("Genre: " + movie.get("Genre"));
            System.out.println("Rating: " + movie.get("Rating"));
            System.out.println("Duration: " + movie.get("Duration") + " mins");
            System.out.println("Synopsis: " + movie.get("Synopsis"));
            System.out.println("----------------------------");
        }
    }
}
