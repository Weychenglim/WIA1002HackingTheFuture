package Assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchFriend<T extends YoungStudents> {

    private static String username;

    @FXML
    private Pane FriendsProfileEmailPane;

    @FXML
    private Pane FriendsProfileFriendsPane;

    @FXML
    private Pane FriendsProfileLocationCoordinatePane;

    @FXML
    private Pane FriendsProfileTotalPointsPane;

    @FXML
    private Pane FriendsProfileUsernamePane;

    @FXML
    private TextField SearchFriendUsernameTextField;

    @FXML
    private Button SearchFriendSendFriendRequestButton;

    public SearchFriend() {
    }

    public SearchFriend(String username) {
        SearchFriend.username = username;
    }

    public void initialize(Pane FriendsProfileEmailPane, Pane FriendsProfileLocationCoordinatePane, Pane FriendsProfileTotalPointsPane, Pane FriendsProfileUsernamePane, TextField SearchFriendUsernameTextField, Button SearchFriendSendFriendRequestButton) {
        this.FriendsProfileEmailPane = FriendsProfileEmailPane;
        this.FriendsProfileLocationCoordinatePane = FriendsProfileLocationCoordinatePane;
        this.FriendsProfileTotalPointsPane = FriendsProfileTotalPointsPane;
        this.FriendsProfileUsernamePane = FriendsProfileUsernamePane;
        this.SearchFriendUsernameTextField = SearchFriendUsernameTextField;
        this.SearchFriendSendFriendRequestButton = SearchFriendSendFriendRequestButton;
    }

    @FXML
    public void searchAndDisplayFriendProfile() {
        String searchedUsername = SearchFriendUsernameTextField.getText().trim();

        if (searchedUsername.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please enter a username.");
            return;
        }

        if (searchedUsername.equals(username)) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "You cannot search for your own username.");
            return;
        }

        String sql = "SELECT STUDENT_USERNAME, STUDENT_EMAIL, STUDENT_LOCATION_COORDINATE, CURRENT_POINTS FROM user.student WHERE STUDENT_USERNAME = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, searchedUsername);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("STUDENT_EMAIL");
                String coordinates = resultSet.getString("STUDENT_LOCATION_COORDINATE");
                int points = resultSet.getInt("CURRENT_POINTS");

                displayProfile(searchedUsername, email, coordinates, points);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error Message", "No student found with the username: " + searchedUsername);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while searching for the student.");
        }
    }

    private void displayProfile(String username, String email, String coordinates, int points) {
        // Clear previous profile information
        FriendsProfileEmailPane.getChildren().clear();
        FriendsProfileLocationCoordinatePane.getChildren().clear();
        FriendsProfileTotalPointsPane.getChildren().clear();
        FriendsProfileUsernamePane.getChildren().clear();

        // Create and add new labels with profile information
        FriendsProfileEmailPane.getChildren().add(new Label(email));
        FriendsProfileLocationCoordinatePane.getChildren().add(new Label(coordinates));
        FriendsProfileTotalPointsPane.getChildren().add(new Label(String.valueOf(points)));
        FriendsProfileUsernamePane.getChildren().add(new Label(username));
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void sendFriendRequest() {
        String friendUsername = SearchFriendUsernameTextField.getText().trim();

        if (friendUsername.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please enter a username.");
            return;
        }

        if (friendUsername.equals(username)) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "You cannot send a friend request to yourself.");
            return;
        }

        if (isRequestAlreadySent(friendUsername)) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Friend request already sent to: " + friendUsername);
            return;
        }

        if (areAlreadyFriends(friendUsername)) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "You are already friends with: " + friendUsername);
            return;
        }

        // Send friend request
        saveFriendRequest(friendUsername);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Friend request sent to: " + friendUsername);
    }

    private boolean isRequestAlreadySent(String friendUsername) {
        File file = new File(friendUsername + "_request.csv");
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(username + "," + friendUsername)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while checking friend request.");
        }
        return false;
    }

    private boolean areAlreadyFriends(String friendUsername) {
        String sql = "SELECT Friends FROM user.friendlist WHERE STUDENT_username = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String friendsJson = resultSet.getString("Friends");
                // Assuming friendsJson is a JSON array of friend usernames
                return friendsJson.contains(friendUsername);
            } else {
                // If no result is found, add the user to the friend list
                addUserToFriendList();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while checking friend list.");
        }
        return false;
    }

    private void addUserToFriendList() {
        String sql = "INSERT INTO user.friendlist (STUDENT_username, Friends) VALUES (?, '[]')";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while adding user to friend list.");
        }
    }


    private void saveFriendRequest(String friendUsername) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(friendUsername+"_request.csv", true))) {
            bw.write(username + "," + friendUsername);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while sending friend request.");
        }
    }
}
