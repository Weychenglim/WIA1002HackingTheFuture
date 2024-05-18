package Assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddRelationship<T extends Parents> {

    private static String username;

    @FXML
    private TextField AddRelationshipsChildrenUserNameTextField;

    public AddRelationship() {
    }

    public AddRelationship(String username) {
        AddRelationship.username = username;
    }

    public void initialize(TextField AddRelationshipsChildrenUserNameTextField) {
        this.AddRelationshipsChildrenUserNameTextField = AddRelationshipsChildrenUserNameTextField;
    }

    @FXML
    public void handleAddRelationship() {
        String studentUsername = AddRelationshipsChildrenUserNameTextField.getText();
        if (studentUsername == null || studentUsername.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please enter a student's username.");
            return;
        }

        try {
            // Check if the student exists
            if (childExists(studentUsername)) {
                // Check if the relationship already exists
                if (relationshipExists(username, studentUsername)) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "This relationship already exists.");
                    return;
                }
                // Check if the child already has two parents
                if (getNumberOfParents(studentUsername) >= 2) {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "This child already has two parents.");
                    return;
                }
                // Add relationship in the database
                Parents.addChild(username, studentUsername);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Relationship added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Student username does not exist.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error Message", e.getMessage());
        }
    }


    private boolean childExists(String childUsername) {
        String sql = "SELECT COUNT(*) FROM user.student WHERE STUDENT_USERNAME = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, childUsername);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean relationshipExists(String parentUsername, String studentUsername) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM user.parentchild WHERE PARENT_USERNAME = ? AND STUDENT_USERNAME = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, parentUsername);
            preparedStatement.setString(2, studentUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private int getNumberOfParents(String studentUsername) {
        String sql = "SELECT COUNT(*) FROM user.parentchild WHERE STUDENT_USERNAME = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, studentUsername);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void clear(){
        AddRelationshipsChildrenUserNameTextField.setText("");
    }
}
