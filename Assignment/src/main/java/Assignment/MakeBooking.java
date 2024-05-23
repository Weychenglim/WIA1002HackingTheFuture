package Assignment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MakeBooking<T extends Parents> {
    @FXML
    private MenuButton MakeBookingDateMenuButton;

    @FXML
    private MenuButton MakeBookingDestinationMenuButton;

    @FXML
    private MenuButton MakeBookingUsernameButton;

    @FXML
    private Pane MakeBookingTimeSlotPane;

    private static String username;
    private static String selectedChild;
    private static String selectedDestination;
    private static String selectedDate;

    public MakeBooking() {
    }

    public MakeBooking(String username) {
        MakeBooking.username = username;
    }

    public void initialize(MenuButton MakeBookingDateMenuButton, MenuButton MakeBookingDestinationMenuButton, Pane MakeBookingTimeSlotPane, MenuButton MakeBookingUsernameButton) {
        this.MakeBookingUsernameButton = MakeBookingUsernameButton;
        this.MakeBookingDateMenuButton = MakeBookingDateMenuButton;
        this.MakeBookingDestinationMenuButton = MakeBookingDestinationMenuButton;
        this.MakeBookingTimeSlotPane = MakeBookingTimeSlotPane;
        initializeMenuButtons();
    }

    public void initializeMenuButtons() {
        populateDestinationMenuButton();
        populateUsernameMenuButton();
    }

    private void populateDestinationMenuButton() {
        String sql = "SELECT DISTINCT Destination FROM user.booking";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            MakeBookingDestinationMenuButton.getItems().clear();

            while (resultSet.next()) {
                String destination = resultSet.getString("Destination");
                MenuItem menuItem = new MenuItem(destination);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        selectedDestination = destination;
                        MakeBookingDestinationMenuButton.setText(selectedDestination);
                        populateDateMenuButton(selectedDestination);
                    }
                });
                MakeBookingDestinationMenuButton.getItems().add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while retrieving destinations.");
        }
    }

    private void populateDateMenuButton(String destination) {
        String fileName = destination + ".csv";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = "10/09/2024"; // Your specific date string
        LocalDate currentDate;

        try {
            currentDate = LocalDate.parse(dateString, dateFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            currentDate = LocalDate.now(); // Fallback to the current date if parsing fails
        }
        LocalDate oneWeekLater = currentDate.plusWeeks(1);

        MakeBookingDateMenuButton.getItems().clear();
        MakeBookingDateMenuButton.setText("Select Date");

        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String dateStr = parts[0].replaceAll("[^\\x00-\\x7F]", "").trim();

                try {
                    LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                    if (!date.isBefore(currentDate) && !date.isAfter(oneWeekLater)) {
                        MenuItem menuItem = new MenuItem(dateStr);
                        String finalDateStr = dateStr;
                        menuItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                selectedDate = finalDateStr;
                                MakeBookingDateMenuButton.setText(selectedDate);
                                populateTimeSlotPane(fileName, selectedDate);
                            }
                        });
                        MakeBookingDateMenuButton.getItems().add(menuItem);
                    }
                } catch (DateTimeParseException e) {
                    // Handle invalid date format if necessary
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while retrieving dates.");
        }
    }

    private void populateTimeSlotPane(String fileName, String date) {
        MakeBookingTimeSlotPane.getChildren().clear();

        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(date)) {
                    Label timeSlotLabel = new Label(parts[1]);
                    MakeBookingTimeSlotPane.getChildren().add(timeSlotLabel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while retrieving time slots.");
        }
    }

    private void populateUsernameMenuButton() {
        String sql = "SELECT STUDENT_USERNAME FROM user.parentchild WHERE PARENT_USERNAME = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                MakeBookingUsernameButton.getItems().clear();

                while (resultSet.next()) {
                    String studentUsername = resultSet.getString("STUDENT_USERNAME");
                    MenuItem menuItem = new MenuItem(studentUsername);
                    menuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            selectedChild = studentUsername;
                            MakeBookingUsernameButton.setText(selectedChild);
                        }
                    });
                    MakeBookingUsernameButton.getItems().add(menuItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while retrieving student usernames.");
        }
    }

    public void confirmBooking() {
        if (selectedChild == null || selectedDestination == null || selectedDate == null) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please select a child, destination, and date.");
            return;
        }

        if (checkForClashes()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "The selected date clashes with a registered event or tour.");
            return;
        }

        saveBookingToCSV();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully booked!");
        alert.showAndWait();
    }

    private boolean checkForClashes() {
        // Check for clashes in child's registered events
        String childFileName = selectedChild + ".csv";
        if (Files.exists(Paths.get(childFileName))) {
            try (Scanner scanner = new Scanner(new FileReader(childFileName))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    System.out.println(parts[1]);
                    System.out.println(selectedDate);
                    String date = removeBOM(selectedDate);
                    if (parts[1].equals(date)) {
                        return true; // Date clash found in child's events
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while checking for date clashes in child's events.");
            }
        }

        // Check for clashes in parent's booking
        String parentFileName = username + "_booking.csv";
        if (Files.exists(Paths.get(parentFileName))) {
            try (Scanner scanner = new Scanner(new FileReader(parentFileName))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts[1].equals(selectedDate)) { // Assuming the date is the second column in parent's booking CSV
                        return true; // Date clash found in parent's bookings
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while checking for date clashes in parent's bookings.");
            }
        }

        return false; // No clashes found
    }

    private void saveBookingToCSV() {
        String parentFileName = username + "_booking.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(parentFileName, true))) {
            writer.write(selectedDestination + "," + removeBOM(selectedDate) + "," + selectedChild + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while saving the booking.");
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
        MakeBookingDateMenuButton.getItems().clear();
        MakeBookingDateMenuButton.setText("Select Date");
        MakeBookingDestinationMenuButton.getItems().clear();
        MakeBookingDestinationMenuButton.setText("Select Destination");
        MakeBookingUsernameButton.getItems().clear();
        MakeBookingUsernameButton.setText("Select Child");
        MakeBookingTimeSlotPane.getChildren().clear();
        selectedChild = null;
        selectedDestination = null;
        selectedDate = null;
    }

    private String removeBOM(String str) {
        if (str.startsWith("\uFEFF")) {
            return str.substring(1);
        } else {
            return str;
        }
    }
}

