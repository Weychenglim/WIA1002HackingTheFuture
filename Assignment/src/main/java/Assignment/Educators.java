package Assignment;

public class Educators extends User {
    private int quizzesCreated;
    private int eventsCreated;

    public Educators(){

    }

    public Educators(String email, String username, String password, String role) {
        super(email, username, password, role);
        this.quizzesCreated = 0;
        this.eventsCreated = 0;
    }
}