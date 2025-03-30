import java.util.*;
public class MovieTracker {
    static Scanner sc = new Scanner(System.in);
    static DatabaseOperation db=new DatabaseOperation();
    static void user_signup(){
        System.out.println("Enter your username: ");
        String username = sc.next();
        
        System.out.println("Enter your password: ");
        String password = sc.next();
        
        System.out.println("Enter your name: ");
        String name = sc.next();
        
        System.out.println("Enter your phone number: ");
        String phone = sc.next();
        
        System.out.println("Enter your address: ");
        String address = sc.nextLine();
        address = sc.nextLine();

        String sql = "INSERT INTO users(Username,Password,Name,Phone,Address) values(?,?,?,?,?)";
        Object[] values = {username,password,name,phone,address};
        int rowsAffected = db.executeUpdate(sql, values); 
        if(rowsAffected>0)
            System.out.println("User registered successfully");
        else
            System.out.println("Something went wrong.Sign up failed.");
    }

    static void user_login(){
        System.out.println("Enter your username: ");
        String username = sc.next();
        
        System.out.println("Enter your password: ");
        String password = sc.next();

        String sql = "SELECT Password from users where username = ?";
        String pass_real = db.validatePass(sql, username);

        if (password.equals(pass_real)){
            System.out.println("Login Succesfull!");
           User u = new User();
           sql = "SELECT UserID from users where username = ?";
           int userID = db.fetchUserID(sql, username);
           u.userMenu(userID);
        }else{
            System.out.println("Invalid password! Try again!");
        }
    }

     static void admin_signup(){
        System.out.println("Enter your username: ");
        String username = sc.next();
        
        System.out.println("Enter your password: ");
        String password = sc.next();

        String sql = "INSERT INTO admin(Username,Password) values(?,?)";
        Object[] values = {username,password};
        int rowsAffected = db.executeUpdate(sql, values); 
        if(rowsAffected>0)
            System.out.println("Admin registered successfully");
        else
            System.out.println("Something went wrong.Sign up failed.");
    }
    static void admin_login(){
        System.out.println("Enter your username: ");
        String username = sc.next();
        
        System.out.println("Enter your password: ");
        String password = sc.next();

        String sql = "SELECT Password from admin where username = ?";
        String pass_real = db.validatePass(sql, username);

        if (password.equals(pass_real)){
            System.out.println("Login Succesfull!");
            Admin a = new Admin();
            a.adminMenu();
        }else{
            System.out.println("Invalid password! Try again!");
        }
    }
        public static void main(String[] args) {
            Scanner sc2 = new Scanner(System.in);
    
            int choice;
            System.out.println("----- Movie Ticket Booking System -----");
            System.out.println("Press 1 to sign up as user.");
            System.out.println("Press 2 to login as user.");
            System.out.println("Press 3 to sign up as admin.");
            System.out.println("Press 4 to login as admin.");
            System.out.println("----- --------------------------- -----");
            System.out.print("Enter your choice:");
            choice = sc2.nextInt();
            switch(choice){
                case 1:
                    user_signup();
                    break;
                case 2: 
                    user_login();
                    break;
                case 3:
                    admin_signup();
                    break;
                case 4:
                    admin_login();
                    break;
            }
            sc2.close();
        }
    }


