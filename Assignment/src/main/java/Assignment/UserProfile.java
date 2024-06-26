package Assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfile {

    @FXML
    private Pane UserProfileEducatorAchievementNumberOfQuizesCreatedPane;

    @FXML
    private Pane UserProfileEmailPane;

    @FXML
    private Pane UserProfileLocationCoordinatePane;

    @FXML
    private Pane UserProfileParentAchiementNumberOfEventsCreatedPane;

    @FXML
    private Pane UserProfileParentAchiementPastBookingMadePane;

    @FXML
    private Pane UserProfileRolePane;

    @FXML
    private Pane UserProfileStudentAchievementFriendsPane;

    @FXML
    private Pane UserProfileStudentAchievementTotalPointsPane;

    @FXML
    private Pane UserProfileUsernamePane;

    @FXML
    private Pane UserProfileParentChildPane;

    private static String username;
    private static String role;

    public UserProfile() {
    }

    public UserProfile(String username, String role) {
        UserProfile.username = username;
        UserProfile.role = role;
    }

    public void initialize(Pane UserProfileEducatorAchievementNumberOfQuizesCreatedPane, Pane UserProfileEmailPane, Pane UserProfileLocationCoordinatePane, Pane UserProfileParentAchiementNumberOfEventsCreatedPane, Pane UserProfileParentAchiementPastBookingMadePane, Pane UserProfileRolePane, Pane UserProfileStudentAchievementFriendsPane, Pane UserProfileStudentAchievementTotalPointsPane, Pane UserProfileUsernamePane, Pane UserProfileParentChildPane) {
        this.UserProfileParentChildPane = UserProfileParentChildPane;
        UserProfileParentChildPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileRolePane = UserProfileRolePane;
        UserProfileRolePane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileUsernamePane = UserProfileUsernamePane;
        UserProfileUsernamePane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileStudentAchievementFriendsPane = UserProfileStudentAchievementFriendsPane;
        UserProfileStudentAchievementFriendsPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileEmailPane = UserProfileEmailPane;
        UserProfileEmailPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileEducatorAchievementNumberOfQuizesCreatedPane = UserProfileEducatorAchievementNumberOfQuizesCreatedPane;
        UserProfileEducatorAchievementNumberOfQuizesCreatedPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileLocationCoordinatePane = UserProfileLocationCoordinatePane;
        UserProfileLocationCoordinatePane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileParentAchiementNumberOfEventsCreatedPane = UserProfileParentAchiementNumberOfEventsCreatedPane;
        UserProfileParentAchiementNumberOfEventsCreatedPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileParentAchiementPastBookingMadePane = UserProfileParentAchiementPastBookingMadePane;
        UserProfileParentAchiementPastBookingMadePane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileStudentAchievementTotalPointsPane = UserProfileStudentAchievementTotalPointsPane;
        UserProfileStudentAchievementTotalPointsPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        displayUserProfile();
    }

    private void displayUserProfile() {
        if ("EDUCATOR".equals(role)) {
            displayEducatorProfile();
        } else if ("PARENT".equals(role)) {
            displayParentProfile();
        } else if ("STUDENT".equals(role)) {
            displayStudentProfile();
        }
    }

    private void displayEducatorProfile() {
        String sql = "SELECT EDUCATOR_USERNAME, EDUCATOR_EMAIL, EDUCATOR_LOCATION_COORDINATE, NUM_QUIZZES_CREATED, NUM_EVENTS_CREATED FROM user.educator WHERE EDUCATOR_USERNAME = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("EDUCATOR_EMAIL");
                String coordinates = resultSet.getString("EDUCATOR_LOCATION_COORDINATE");
                int quizzesCount = resultSet.getInt("NUM_QUIZZES_CREATED");
                int eventsCount = resultSet.getInt("NUM_EVENTS_CREATED");

                UserProfileUsernamePane.getChildren().clear();
                UserProfileUsernamePane.getChildren().add(createStyledLabel(username));

                UserProfileEmailPane.getChildren().clear();
                UserProfileEmailPane.getChildren().add(createStyledLabel(email));

                UserProfileRolePane.getChildren().clear();
                UserProfileRolePane.getChildren().add(createStyledLabel("EDUCATOR"));

                UserProfileLocationCoordinatePane.getChildren().clear();
                UserProfileLocationCoordinatePane.getChildren().add(createStyledLabel(coordinates));

                UserProfileEducatorAchievementNumberOfQuizesCreatedPane.getChildren().clear();
                UserProfileEducatorAchievementNumberOfQuizesCreatedPane.getChildren().add(createStyledLabel(String.valueOf(quizzesCount)));

                UserProfileParentAchiementNumberOfEventsCreatedPane.getChildren().clear();
                UserProfileParentAchiementNumberOfEventsCreatedPane.getChildren().add(createStyledLabel(String.valueOf(eventsCount)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayParentProfile() {
        String sql = "SELECT PARENT_USERNAME, PARENT_EMAIL, PARENT_LOCATION_COORDINATE FROM user.parent WHERE PARENT_USERNAME = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("PARENT_EMAIL");
                String coordinates = resultSet.getString("PARENT_LOCATION_COORDINATE");
                String childUsernames = getChildUsernames(username);
                String pastBookings = getPastBookings(username);

                UserProfileUsernamePane.getChildren().clear();
                UserProfileUsernamePane.getChildren().add(createStyledLabel(username));

                UserProfileEmailPane.getChildren().clear();
                UserProfileEmailPane.getChildren().add(createStyledLabel(email));

                UserProfileRolePane.getChildren().clear();
                UserProfileRolePane.getChildren().add(createStyledLabel("PARENT"));

                UserProfileLocationCoordinatePane.getChildren().clear();
                UserProfileLocationCoordinatePane.getChildren().add(createStyledLabel(coordinates));

                UserProfileParentChildPane.getChildren().clear();
                UserProfileParentChildPane.getChildren().add(createStyledLabel(childUsernames));

                UserProfileParentAchiementPastBookingMadePane.getChildren().clear();
                UserProfileParentAchiementPastBookingMadePane.getChildren().add(createStyledLabel(pastBookings));
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void displayStudentProfile() {
        String sql = "SELECT STUDENT_USERNAME, STUDENT_EMAIL, STUDENT_LOCATION_COORDINATE, CURRENT_POINTS FROM user.student WHERE STUDENT_USERNAME = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("STUDENT_EMAIL");
                String coordinates = resultSet.getString("STUDENT_LOCATION_COORDINATE");
                int points = resultSet.getInt("CURRENT_POINTS");
                String parentUsername = getParentUsername(username);
                String friends = getFriendsFromDatabase(username);

                UserProfileUsernamePane.getChildren().clear();
                UserProfileUsernamePane.getChildren().add(createStyledLabel(username));

                UserProfileEmailPane.getChildren().clear();
                UserProfileEmailPane.getChildren().add(createStyledLabel(email));

                UserProfileRolePane.getChildren().clear();
                UserProfileRolePane.getChildren().add(createStyledLabel("YOUNG STUDENT"));

                UserProfileLocationCoordinatePane.getChildren().clear();
                UserProfileLocationCoordinatePane.getChildren().add(createStyledLabel(coordinates));

                UserProfileStudentAchievementTotalPointsPane.getChildren().clear();
                UserProfileStudentAchievementTotalPointsPane.getChildren().add(createStyledLabel(String.valueOf(points)));

                UserProfileParentChildPane.getChildren().clear();
                UserProfileParentChildPane.getChildren().add(createStyledLabel(parentUsername));

                UserProfileStudentAchievementFriendsPane.getChildren().clear();
                TextFlow descriptionFlow = new TextFlow();
                Text descriptionText = new Text(friends);
                descriptionFlow.getChildren().add(descriptionText);
                descriptionFlow.setPrefWidth(UserProfileStudentAchievementFriendsPane.getPrefWidth());
                descriptionFlow.getStyleClass().add("label-content");
                UserProfileStudentAchievementFriendsPane.getChildren().clear();
                UserProfileStudentAchievementFriendsPane.getChildren().add(descriptionFlow);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("label-content");
        return label;
    }


    private String getChildUsernames(String parentUsername) throws SQLException {
        String sql = "SELECT STUDENT_USERNAME FROM user.parentchild WHERE PARENT_USERNAME = ?";
        StringBuilder childUsernames = new StringBuilder();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, parentUsername);

            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 1;
            while (resultSet.next()) {
                if (childUsernames.length() > 0) {
                    childUsernames.append("\n");
                }
                childUsernames.append(i +".  " + resultSet.getString("STUDENT_USERNAME"));
                i++;
            }
        }

        return childUsernames.toString();
    }

    private String getParentUsername(String studentUsername) throws SQLException {
        String sql = "SELECT PARENT_USERNAME FROM user.parentchild WHERE STUDENT_USERNAME = ?";
        StringBuilder parentUsername = new StringBuilder();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, studentUsername);

            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 1;
            if (resultSet.next()) {
                parentUsername.append(i + ".  " + resultSet.getString("PARENT_USERNAME") + "\n");
        }

        return parentUsername.toString();
    }}

    private String getPastBookings(String parentUsername) throws IOException {
        String fileName = parentUsername + "_booking.csv";
        StringBuilder pastBookings = new StringBuilder();

        if (Files.exists(Paths.get(fileName))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                int i = 1 ;
                while ((line = reader.readLine()) != null) {
                    if (pastBookings.length() > 0) {
                        pastBookings.append("\n");
                    }
                    String [] part = line.split(",");
                    pastBookings.append(i + ".  " + part[0] + " " + part[1]);
                    i++;
                }
            }
        }

        return pastBookings.toString();
    }

    private String getFriendsFromDatabase(String studentUsername) {
        StringBuilder friends = new StringBuilder();
        String sql = "SELECT friends FROM user.friendlist WHERE STUDENT_username = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, studentUsername);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String friendsJson = resultSet.getString("friends");
                JSONArray friendsArray = new JSONArray(friendsJson);
                for (int i = 0; i < friendsArray.length(); i++) {
                    if (friends.length() > 0) {
                        friends.append(", ");
                    }
                    friends.append(friendsArray.getString(i));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while retrieving friends data.");
        }

        return friends.toString();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
