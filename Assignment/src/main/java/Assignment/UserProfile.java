package Assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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

    public void initialize(Pane UserProfileEducatorAchievementNumberOfQuizesCreatedPane,Pane UserProfileEmailPane, Pane UserProfileLocationCoordinatePane, Pane UserProfileParentAchiementNumberOfEventsCreatedPane ,Pane UserProfileParentAchiementPastBookingMadePane , Pane UserProfileRolePane, Pane UserProfileStudentAchievementFriendsPane , Pane UserProfileStudentAchievementTotalPointsPane , Pane UserProfileUsernamePane, Pane UserProfileParentChildPane) {
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
        this.UserProfileEducatorAchievementNumberOfQuizesCreatedPane =UserProfileEducatorAchievementNumberOfQuizesCreatedPane;
        UserProfileEducatorAchievementNumberOfQuizesCreatedPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileLocationCoordinatePane = UserProfileLocationCoordinatePane;
        UserProfileLocationCoordinatePane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileParentAchiementNumberOfEventsCreatedPane = UserProfileParentAchiementNumberOfEventsCreatedPane;
        UserProfileParentAchiementNumberOfEventsCreatedPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        this.UserProfileParentAchiementPastBookingMadePane  = UserProfileParentAchiementPastBookingMadePane;
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
                UserProfileLocationCoordinatePane.getChildren().add(createStyledLabel( coordinates));

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
                String friends = getFriends(username);

                UserProfileUsernamePane.getChildren().clear();
                UserProfileUsernamePane.getChildren().add(createStyledLabel(username));

                UserProfileEmailPane.getChildren().clear();
                UserProfileEmailPane.getChildren().add(createStyledLabel( email));

                UserProfileRolePane.getChildren().clear();
                UserProfileRolePane.getChildren().add(createStyledLabel("YOUNG STUDENT"));

                UserProfileLocationCoordinatePane.getChildren().clear();
                UserProfileLocationCoordinatePane.getChildren().add(createStyledLabel(coordinates));

                UserProfileStudentAchievementTotalPointsPane.getChildren().clear();
                UserProfileStudentAchievementTotalPointsPane.getChildren().add(createStyledLabel(String.valueOf(points)));

                UserProfileParentChildPane.getChildren().clear();
                UserProfileParentChildPane.getChildren().add(createStyledLabel(parentUsername));

                UserProfileStudentAchievementFriendsPane.getChildren().clear();
                UserProfileStudentAchievementFriendsPane.getChildren().add(createStyledLabel(friends));
            }

        } catch (SQLException | IOException e) {
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
            while (resultSet.next()) {
                if (childUsernames.length() > 0) {
                    childUsernames.append(", ");
                }
                childUsernames.append(resultSet.getString("STUDENT_USERNAME"));
            }
        }

        return childUsernames.toString();
    }

    private String getParentUsername(String studentUsername) throws SQLException {
        String sql = "SELECT PARENT_USERNAME FROM user.parentchild WHERE STUDENT_USERNAME = ?";
        String parentUsername = "";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, studentUsername);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                parentUsername = resultSet.getString("PARENT_USERNAME");
            }
        }

        return parentUsername;
    }

    private String getPastBookings(String parentUsername) throws IOException {
        String fileName = parentUsername + "_booking.csv";
        StringBuilder pastBookings = new StringBuilder();

        if (Files.exists(Paths.get(fileName))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pastBookings.length() > 0) {
                        pastBookings.append(", ");
                    }
                    pastBookings.append(line);
                }
            }
        }

        return pastBookings.toString();
    }

    private String getFriends(String studentUsername) throws IOException {
        String fileName = studentUsername + "_friend.csv";
        StringBuilder friends = new StringBuilder();

        if (Files.exists(Paths.get(fileName))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (friends.length() > 0) {
                        friends.append(", ");
                    }
                    friends.append(line);
                }
            }
        }

        return friends.toString();
    }


}
