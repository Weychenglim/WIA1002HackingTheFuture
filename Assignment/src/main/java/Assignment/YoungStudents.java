package Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YoungStudents extends User {
    private int points;
    private List<String> friends;

    public YoungStudents() {
    }

    public YoungStudents(String email, String username, String password, String role) {
        super(email, username, password, role);
        this.friends = new ArrayList<>();
        this.points = 0;
    }


    public static void printChildParentMap() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM user.parentchild";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Child-Parent Relationships:");
            while (resultSet.next()) {
                System.out.println("Student: " + resultSet.getString("STUDENT_USERNAME") +
                        " -> Parent: " + resultSet.getString("PARENT_USERNAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
