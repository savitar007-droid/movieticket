import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Admin extends User {
    Scanner sc = new Scanner(System.in);
    int choice, userID, duration, movieid, theaterid, showmtime_hour, showtime_min;
    double rating;
    String title, genre, synopsis;
    Timestamp showtime;

    // Objects of different classes
    Movie m;
    Theatre t = new Theatre();
    Showtime st = new Showtime();
    Booking b = new Booking();

    public int takeUserID() {
        System.out.print("Enter user ID: ");
        return sc.nextInt();
    }

    public void adminMenu() {
        while (true) {
            System.out.println("\n---------- Admin Menu ----------");
            System.out.println("1. Add movies");
            System.out.println("2. See all movies");
            System.out.println("3. Put a movie up for showtime");
            System.out.println("4. See all showtimes");
            System.out.println("5. Add theaters");
            System.out.println("6. See all theaters");
            System.out.println("7. Book a ticket");
            System.out.println("8. See ticket bookings");
            System.out.println("9. Cancel ticket booking");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // ✅ Consume newline to prevent skipping

            switch (choice) {
                case 1:
                    // Add movies
                    System.out.print("Enter movie title: ");
                    title = sc.nextLine();

                    System.out.print("Enter movie genre: ");
                    genre = sc.nextLine();

                    System.out.print("Enter movie rating: ");
                    rating = sc.nextDouble();

                    System.out.print("Enter movie duration (in mins): ");
                    duration = sc.nextInt();
                    sc.nextLine(); // ✅ Consume newline

                    System.out.print("Enter movie synopsis: ");
                    synopsis = sc.nextLine();

                    // ✅ Create a new Movie object with parameters
                    m = new Movie(title, genre, rating, duration, synopsis);
                    m.insertMovie();
                    break;

                case 2:
                    // See all movies
                    Movie.showMovies();
                    break;

                case 3:
                    // Set a movie available for showtime
                    System.out.print("Enter Movie ID: ");
                    movieid = sc.nextInt();

                    System.out.print("Enter Theater ID: ");
                    theaterid = sc.nextInt();

                    System.out.print("Enter Showtime hour: ");
                    showmtime_hour = sc.nextInt();

                    System.out.print("Enter Showtime minute: ");
                    showtime_min = sc.nextInt();

                    LocalDateTime localDateTime = LocalDateTime.now()
                            .withHour(showmtime_hour)
                            .withMinute(showtime_min)
                            .withSecond(0);
                    showtime = Timestamp.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                    st.insertShowtime(movieid, theaterid, showtime);
                    break;

                case 4:
                    // See all showtimes
                    st.showShowtimes();
                    break;

                case 5:
                    // Add theaters
                    System.out.print("Enter theater location: ");
                    String address = sc.nextLine();

                    System.out.print("Enter theater seating capacity: ");
                    int seating_capacity = sc.nextInt();

                    t.insertTheater(address, seating_capacity);
                    break;

                case 6:
                    t.showTheaters();
                    break;

                case 7:
                    userID = takeUserID();
                    b.bookTicket(userID);
                    break;

                case 8:
                    userID = takeUserID();
                    b.seeTicket(userID);
                    break;

                case 9:
                    userID = takeUserID();
                    b.cancelTicket(userID);
                    break;

                case 10:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Wrong choice entered! Retry.");
                    break;
            }
        }
    }
}
