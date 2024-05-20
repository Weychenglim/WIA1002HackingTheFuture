package Assignment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class SubmitStatusQuiz<T extends YoungStudents> extends ViewQuiz {

    @FXML
    private Pane SubmitStatusQuizDescriptionPane;

    @FXML
    private Pane SubmitStatusQuizThemePane;

    @FXML
    private MenuButton MenuButtonQuiz;

    private static String username;

    private static QuizInfo registeredQuiz;

    public SubmitStatusQuiz() {
    }

    public SubmitStatusQuiz(String username) {
        SubmitStatusQuiz.username = username;
    }

    @FXML
    public void initialize() {
        initializeMenuButton();
    }

    public void initialize(Pane SubmitStatusQuizDescriptionPane, Pane SubmitStatusQuizThemePane, MenuButton MenuButtonQuiz) {
        this.SubmitStatusQuizDescriptionPane = SubmitStatusQuizDescriptionPane;
        this.SubmitStatusQuizThemePane = SubmitStatusQuizThemePane;
        this.MenuButtonQuiz = MenuButtonQuiz;
        initializeMenuButton();
    }

    private void initializeMenuButton() {
        List<String> availableQuizTitles = super.getAvailableQuizTitles();
        MenuButtonQuiz.getItems().clear(); // Clear existing items if any

        for (String title : availableQuizTitles) {
            MenuItem menuItem = new MenuItem(title);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Get the selected quiz title
                    String selectedTitle = ((MenuItem) event.getSource()).getText();

                    // Retrieve quiz details based on the selected title
                    QuizInfo selectedQuiz = getQuizByTitle(selectedTitle);

                    // Display quiz details
                    displayQuizDetails(selectedQuiz);

                    // Set the selected title as the text of the MenuButton
                    MenuButtonQuiz.setText(selectedTitle);
                }
            });

            MenuButtonQuiz.getItems().add(menuItem);
        }
    }

    private void displayQuizDetails(QuizInfo quiz) {
        // Display quiz details in respective panes
        Label themeLabel = new Label(quiz.getQuiz_Theme());
        SubmitStatusQuizThemePane.getChildren().clear();
        SubmitStatusQuizThemePane.getChildren().add(themeLabel);

        // Use TextFlow for dynamic text wrapping
        TextFlow descriptionFlow = new TextFlow();
        Text descriptionText = new Text(quiz.getQuiz_Description());
        descriptionFlow.getChildren().add(descriptionText);
        descriptionFlow.setPrefWidth(SubmitStatusQuizDescriptionPane.getPrefWidth());
        SubmitStatusQuizDescriptionPane.getChildren().clear();
        SubmitStatusQuizDescriptionPane.getChildren().add(descriptionFlow);

        SubmitStatusQuiz.registeredQuiz = quiz;
    }

    // Method to retrieve quiz details by title
    public QuizInfo getQuizByTitle(String title) {
        return super.getQuizByTitle(title);
    }

    public void confirmRegistration() {
        if (registeredQuiz == null) {
            // No quiz is selected
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please select a quiz first.");
            return; // Exit the method early
        }
        Alert alert;

        // Save registered information
        saveRegisteredInfoToCSV(registeredQuiz, username);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Registered!");
        alert.showAndWait();
        updateNumPoints();
    }

    private void saveRegisteredInfoToCSV(QuizInfo quiz, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_quizzes.csv", true))) {
            // Append quiz details to the CSV file
            writer.write(quiz.getQuiz_Title() + "," + quiz.getQuiz_Theme() + "," +
                    quiz.getQuiz_Description() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateNumPoints() {
        String sql = "UPDATE user.student SET CURRENT_POINTS = CURRENT_POINTS + 2, timepointsupdated = ? WHERE STUDENT_USERNAME = ?";

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the current timestamp
            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(currentDateTime);
            preparedStatement.setTimestamp(1, timestamp);

            // Set the username
            preparedStatement.setString(2, username);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "INFO Message", "Points updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Failed to update the points.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while updating the points.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void clearMenuButtonPane() {
        MenuButtonQuiz.getItems().clear();
        MenuButtonQuiz.setText("Select Category");
        SubmitStatusQuizDescriptionPane.getChildren().clear();
        SubmitStatusQuizThemePane.getChildren().clear();
        registeredQuiz = null;
    }
}
