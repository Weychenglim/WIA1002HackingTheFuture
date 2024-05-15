package Assignment;

public class User{
    private String email;
    private String username;
    private String password;
    private String role;
    private String locationCoordinate; // Stores coordinates as string in the format "(X,Y)"

    public User (){

    }

    // Constructor
    public User(String email, String username, String password, String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.locationCoordinate = generateRandomCoordinate();
    }

    // Generate random coordinate within the range [-500.0, 500.0] and convert to string format "(X,Y)"
    private String generateRandomCoordinate() {
        double x = Math.random() * 1000 - 500;
        double y = Math.random() * 1000 - 500;
        return String.format("(%.2f,%.2f)", x, y);
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocationCoordinate() {
        return locationCoordinate;
    }

    public void setLocationCoordinate(String locationCoordinate) {
        this.locationCoordinate = locationCoordinate;
    }

}
