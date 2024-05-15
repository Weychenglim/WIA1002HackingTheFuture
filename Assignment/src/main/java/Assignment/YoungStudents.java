package Assignment;

import java.util.*;

public class YoungStudents extends User{
private int points;
private List<String> friends;

    public YoungStudents(){

    }

    public YoungStudents(String email, String username, String password, String role) {
        super(email, username, password, role);
        this.friends = new ArrayList<>();
        this.points = 0;
    }
}
