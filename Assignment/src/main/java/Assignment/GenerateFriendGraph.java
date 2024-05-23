package Assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateFriendGraph<T extends YoungStudents> {

    @FXML
    private Pane FriendGraphPane;

    private static String username;

    public GenerateFriendGraph() {
    }

    public GenerateFriendGraph(String username) {
        GenerateFriendGraph.username = username;
    }

    public void initialize(Pane FriendGraphPane) {
        this.FriendGraphPane = FriendGraphPane;
        displayConnectionGraphFromDatabase();
    }

    // Method to assign coordinates in a grid-like pattern
    private void assignCoordinates(Graph graph) {
        int width = 800; // Width of the pane
        int height = 430; // Height of the pane
        int numStudents = graph.getAdjList().size();
        double angle = 2 * Math.PI / numStudents;
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double radius = Math.min(width, height) / 2.5;

        int i = 0;
        for (String student : graph.getAdjList().keySet()) {
            double x = centerX + radius * Math.cos(i * angle);
            double y = centerY + radius * Math.sin(i * angle);
            graph.setCoordinates(student, x, y);
            i++;
        }
    }

    // Method to display connection graph of a list of students
    public void displayConnectionGraphFromDatabase() {
        Graph graph = new Graph();
        String sql = "SELECT STUDENT_username, friends FROM user.friendlist";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String user = resultSet.getString("STUDENT_username");
                graph.addStudent(user);

                String friendsJson = resultSet.getString("friends");
                JSONArray friendsArray = new JSONArray(friendsJson);
                for (int i = 0; i < friendsArray.length(); i++) {
                    String friend = friendsArray.getString(i);
                    graph.addStudent(friend);
                    graph.addFriendship(user, friend);
                }
            }

            // Assign coordinates after reading all data
            assignCoordinates(graph);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while retrieving user data.");
        }

        drawGraph(FriendGraphPane, graph);
    }

    // Method to draw the graph
    private void drawGraph(Pane root, Graph graph) {
        double radius = 20; // Radius of the circle representing each student
        for (String student : graph.getAdjList().keySet()) {
            Point point = graph.getCoordinates().get(student);
            // Draw circle for each student
            Circle circle = new Circle(point.x, point.y, radius, Color.LIGHTBLUE);
            if (student.equals(username)) {
                circle.setFill(Color.YELLOW); // Highlight the current user
            }
            circle.setStroke(Color.DARKBLUE);
            circle.setStrokeWidth(2);
            root.getChildren().add(circle);

            // Add student name as text
            Text text = new Text(student);
            text.setBoundsType(TextBoundsType.VISUAL);
            double textWidth = text.getLayoutBounds().getWidth();
            double textHeight = text.getLayoutBounds().getHeight();
            text.setX(point.x - textWidth / 2);
            text.setY(point.y + textHeight / 4); // Approximation for vertical centering
            root.getChildren().add(text);

            // Draw edges
            for (String friend : graph.getAdjList().get(student)) {
                Point friendPoint = graph.getCoordinates().get(friend);
                Line line = new Line(point.x, point.y, friendPoint.x, friendPoint.y);
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(2);
                root.getChildren().add(0, line); // Lines first so they're below circles
            }
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
