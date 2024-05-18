package Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Parents extends User {

    public Parents() {
    }

    public Parents(String email, String username, String password, String role) {
        super(email, username, password, role);
    }

    public static void addChild(String parentUsername, String studentUsername) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO user.parentchild (PARENT_USERNAME, STUDENT_USERNAME) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, parentUsername);
            preparedStatement.setString(2, studentUsername);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printParentChildMap() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM user.parentchild";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Parent-Child Relationships:");
            while (resultSet.next()) {
                System.out.println("Parent: " + resultSet.getString("PARENT_USERNAME") +
                        " -> Student: " + resultSet.getString("STUDENT_USERNAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

