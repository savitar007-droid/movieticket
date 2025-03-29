import java.util.*;

public class Theatre {
    private DatabaseOperation db = new DatabaseOperation();

    public void insertTheater(String location, int seatingCapacity) {
        String checkSql = "SELECT COUNT(*) AS count FROM theaters WHERE Location = ? AND SeatingCapacity = ?";
        
       
    
      
        List<Map<String, Object>> result = db.getRecords(checkSql,new Object[]{});
    
        if (!result.isEmpty()) {
            Number count = (Number) result.get(0).get("count"); 
            if (count.intValue() > 0) {
                System.out.println(" Theater already exists: " + location);
                return;
            }
        }
    
        String sql = "INSERT INTO theaters (Location, SeatingCapacity) VALUES (?, ?)";
        Object[] values = {location, seatingCapacity};
    
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0) {
            System.out.println(" Theater inserted successfully: " + location);
        } else {
            System.out.println(" Failed to insert theater.");
        }
    }
    

    public void showTheaters() {
        String sql = "SELECT * FROM theaters";
        
        
        List<Map<String, Object>> theaters = db.getRecords(sql,new Object[]{});

        if (theaters.isEmpty()) {
            System.out.println(" No theaters found.");
            return;
        }

        System.out.println("\n Available Theaters:");
        for (Map<String, Object> theater : theaters) {
            System.out.println("Theater ID: " + theater.get("TheatreID")); 
            System.out.println("Location: " + theater.get("Location"));
            System.out.println("Seating Capacity: " + theater.get("SeatingCapacity"));
            System.out.println("-----------------------------");
        }
    }
}
