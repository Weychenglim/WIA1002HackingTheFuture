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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RegisterEvent<T extends YoungStudents> extends ViewEvent {
    @FXML
    private Pane RegisterEventDatePane;

    @FXML
    private Pane RegisterEventDescriptionPane;

    @FXML
    private Pane RegisterEventTimePane;

    @FXML
    private Pane RegisterEventVenusPane;

    @FXML
    private MenuButton MenuButtonRegisterEvent;

    private static String username;

    private static EventInfo registeredEvent;

    public RegisterEvent() {
    }

    public RegisterEvent(String username) {
        RegisterEvent.username = username;
    }

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;


    public void updateNumPoints() {
        String sql = "UPDATE user.student SET CURRENT_POINTS = CURRENT_POINTS + 5, timepointsupdated = ? WHERE STUDENT_USERNAME = ?";

        try {
            this.connection = DatabaseConnector.getConnection();
            this.preparedStatement = connection.prepareStatement(sql);

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

    public void initialize(Pane RegisterEventDatePane, Pane RegisterEventDescriptionPane, Pane RegisterEventTimePane, Pane RegisterEventVenusPane, MenuButton MenuButtonRegisterEvent) {
        this.RegisterEventDatePane = RegisterEventDatePane;
        this.RegisterEventDescriptionPane = RegisterEventDescriptionPane;
        this.RegisterEventVenusPane = RegisterEventVenusPane;
        this.RegisterEventTimePane = RegisterEventTimePane;
        this.MenuButtonRegisterEvent = MenuButtonRegisterEvent;
        initializeMenuButton();
    }

    public void initializeMenuButton() {
        List<String> eventTitles = getEventTitles();

        MenuButtonRegisterEvent.getItems().clear(); // Clear existing items if any

        for (String title : eventTitles) {
            MenuItem menuItem = new MenuItem(title);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Get the selected event title
                    String selectedTitle = ((MenuItem) event.getSource()).getText();

                    // Retrieve event details based on the selected title
                    EventInfo selectedEvent = getEventByTitle(selectedTitle);

                    // Display event details
                    displayEventDetails(selectedEvent);

                    // Set the selected title as the text of the MenuButton
                    MenuButtonRegisterEvent.setText(selectedTitle);
                }
            });

            MenuButtonRegisterEvent.getItems().add(menuItem);
        }
    }

    private void displayEventDetails(EventInfo event) {
        // Display event details in respective panes
        Label dateLabel = new Label(event.getEvent_date());
        RegisterEventDatePane.getChildren().clear();
        RegisterEventDatePane.getChildren().add(dateLabel);

        // Use TextFlow for dynamic text wrapping
        TextFlow descriptionFlow = new TextFlow();
        Text descriptionText = new Text(event.getEvent_description());
        descriptionFlow.getChildren().add(descriptionText);
        descriptionFlow.setPrefWidth(RegisterEventDescriptionPane.getPrefWidth());
        RegisterEventDescriptionPane.getChildren().clear();
        RegisterEventDescriptionPane.getChildren().add(descriptionFlow);

        TextFlow venueFlow = new TextFlow();
        Text venueText = new Text(event.getEvent_venue());
        venueFlow.getChildren().add(venueText);
        venueFlow.setPrefWidth(RegisterEventVenusPane.getPrefWidth());
        RegisterEventVenusPane.getChildren().clear();
        RegisterEventVenusPane.getChildren().add(venueFlow);

        Label timeLabel = new Label(event.getEvent_time());
        RegisterEventTimePane.getChildren().clear();
        RegisterEventTimePane.getChildren().add(timeLabel);

        RegisterEvent.registeredEvent = event;
    }

    // Add method to retrieve event by title
    private EventInfo getEventByTitle(String title) {
        for (EventInfo event : addEventsInfo()) {
            if (event.getEvent_title().equals(title)) {
                return event;
            }
        }
        return null; // If event with given title is not found
    }

    public void confirmRegistration() {
        if (registeredEvent == null) {
            // No event is selected
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please select an event first.");
            return; // Exit the method early
        }
        Alert alert;
        if (checkEventClash(registeredEvent, username)) {
            // Event clashes with existing events
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Registration unsuccessful due to event or tour clash");
            alert.showAndWait();
        } else {
            // Save registered information
            saveRegisteredInfoToCSV(registeredEvent, username);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Registered!");
            alert.showAndWait();
            updateNumPoints();
        }
    }

    private void saveRegisteredInfoToCSV(EventInfo event, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + ".csv", true))) {
            // Append event details to the CSV file
            writer.write(event.getEvent_title() + "," + event.getEvent_date() + "," +
                    event.getEvent_time() + "," + event.getEvent_venue() + "," +
                    event.getEvent_description() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkEventClash(EventInfo event, String username) {
        if (!Files.exists(Paths.get(username + ".csv"))) {
            try {
                Files.createFile(Paths.get(username + ".csv"));
            } catch (IOException e) {
                e.printStackTrace();
            }// No clash found since the file was just created
        }else {
            try {
                List<String> lines = Files.readAllLines(Paths.get(username + ".csv"));
                for (String line : lines) {
                    String[] parts = line.split(",");
                    String existingEventDate = parts[1];
                    String[] existingEventTimes = parts[2].split("-");
                    String existingEventStartTime = existingEventTimes[0].trim();
                    String existingEventEndTime = existingEventTimes[1].trim();

                    String[] newEventTimes = event.getEvent_time().split("-");
                    String newEventStartTime = newEventTimes[0].trim();
                    String newEventEndTime = newEventTimes[1].trim();

                    // Check for date clash
                    if (existingEventDate.equals(event.getEvent_date())) {
                        // Check if the new event's start time falls within the time range of an existing event
                        if (isTimeWithinRange(newEventStartTime, existingEventStartTime, existingEventEndTime) ||
                                // Check if the new event's end time falls within the time range of an existing event
                                isTimeWithinRange(newEventEndTime, existingEventStartTime, existingEventEndTime)) {
                            return true; // Clash found
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String parentFileName = getParentUsername(username) + "_booking.csv";
        System.out.println(parentFileName);
        if (Files.exists(Paths.get(parentFileName))) {
            try (Scanner scanner = new Scanner(new FileReader(parentFileName))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    System.out.println(parts[1]);
                    System.out.println(event.getEvent_date());
                    if (parts[1].equals(event.getEvent_date())) { // Assuming the date is the second column in parent's booking CSV
                        return true; // Date clash found in parent's bookings
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while checking for date clashes in parent's bookings.");
            }
        }
        return false; // No clash found
    }

    private boolean isTimeWithinRange(String time, String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime newTime = LocalTime.parse(time, formatter);
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);

        // Check if the new time falls within the range of start and end times
        return !newTime.isBefore(start) && !newTime.isAfter(end);
    }

    public void clearMenuButtonPane() {
        MenuButtonRegisterEvent.getItems().clear();
        MenuButtonRegisterEvent.setText("Select Category");
        RegisterEventDatePane.getChildren().clear();
        RegisterEventDescriptionPane.getChildren().clear();
        RegisterEventVenusPane.getChildren().clear();
        RegisterEventTimePane.getChildren().clear();
        registeredEvent = null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String getParentUsername(String studentUsername) {
        String sql = "SELECT PARENT_USERNAME FROM user.parentchild WHERE STUDENT_USERNAME = ?";
        String parentUsername = null;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, studentUsername);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    parentUsername = resultSet.getString("PARENT_USERNAME");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parentUsername;
    }
}
