package Assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Optional;

public class AddNewEvent<T extends Educators>{

    @FXML
    private TableColumn<EventInfo,String> AddEventTime;

    @FXML
    private TableColumn<EventInfo,String> AddEventTitle;

    @FXML
    private TableColumn<EventInfo,String> AddEventVenue;

    @FXML
    private TableColumn<EventInfo,String> AddEventDate;

    @FXML
    private TableColumn<EventInfo,String> AddEventDescription;

    @FXML
    private TableColumn<EventInfo,String> AddEventNumber;

    @FXML
    private TableView<EventInfo> AddEventTable;

    @FXML
    private TextField AddEventsTimeTextField;

    @FXML
    private TextField AddEventsTitleTextField;

    @FXML
    private TextField AddEventsVenusTextField;

    @FXML
    private TextField AddEventsDateTextField;

    @FXML
    private TextField AddEventsDescriptionTextField;


    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    private static String username;

    public AddNewEvent(){

    }

    public AddNewEvent(String username){
        AddNewEvent.username = username;
    }

    public void updateNumEvent(){
        String sql = "UPDATE user.educator SET NUM_EVENTS_CREATED = NUM_EVENTS_CREATED + 1 WHERE EDUCATOR_USERNAME = ?";

        try {
            this.connection = DatabaseConnector.getConnection();
            this.preparedStatement = connection.prepareStatement(sql);
            System.out.println(username);
            preparedStatement.setString(1, username);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "INFO Message", "Number of events created updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Failed to update the number of events created.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while updating the number of events created.");
        }
    }

    public ObservableList<EventInfo> addEventsInfo() {
        ObservableList<EventInfo> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user.event";
        try {
            this.connection = DatabaseConnector.getConnection();
            this.preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            EventInfo eventInfos;

            while (resultSet.next()) {
                int event_id = resultSet.getInt("event_id");
                String event_title = resultSet.getString("event_title");
                String event_description = resultSet.getString("event_description");
                String event_venue = resultSet.getString("event_venue");
                String event_date = resultSet.getString("event_date");
                String event_time = resultSet.getString("event_time");
                eventInfos = new EventInfo(event_id, event_title, event_description, event_venue, event_date, event_time);
                listData.add(eventInfos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<EventInfo> addEventList;

    public void addEventShowEventInfo(TableColumn<EventInfo,String> AddEventNumber, TableColumn<EventInfo,String> AddEventTitle, TableColumn<EventInfo,String> AddEventDescription, TableColumn<EventInfo,String> AddEventVenue ,TableColumn<EventInfo,String> AddEventDate ,TableColumn<EventInfo,String> AddEventTime, TableView<EventInfo> AddEventTable) {
        this.AddEventNumber = AddEventNumber;
        this.AddEventTitle = AddEventTitle;
        this.AddEventDescription = AddEventDescription;
        this.AddEventVenue = AddEventVenue;
        this.AddEventDate = AddEventDate;
        this.AddEventTime = AddEventTime;
        this.AddEventTable = AddEventTable;

        addEventList = addEventsInfo();

        this.AddEventNumber.setCellValueFactory(new PropertyValueFactory<>("event_id"));
        this.AddEventTitle.setCellValueFactory(new PropertyValueFactory<>("event_title"));
        this.AddEventDescription.setCellValueFactory(new PropertyValueFactory<>("event_description"));
        this.AddEventVenue.setCellValueFactory(new PropertyValueFactory<>("event_venue"));
        this.AddEventDate.setCellValueFactory(new PropertyValueFactory<>("event_date"));
        this.AddEventTime.setCellValueFactory(new PropertyValueFactory<>("event_time"));


        // Set custom cell factory
        this.AddEventTitle.setCellFactory(column -> new WrappingTableCell<>());
        this.AddEventDescription.setCellFactory(column -> new WrappingTableCell<>());
        this.AddEventVenue.setCellFactory(column -> new WrappingTableCell<>());
        this.AddEventDate.setCellFactory(column -> new WrappingTableCell<>());
        this.AddEventTime.setCellFactory(column -> new WrappingTableCell<>());

        this.AddEventTable.setItems(addEventList);
    }

    public void setEventField(TextField AddEventsTitleTextField, TextField AddEventsDescriptionTextField, TextField AddEventsVenusTextField, TextField AddEventsTimeTextField, TextField AddEventsDateTextField) {
        this.AddEventsTitleTextField = AddEventsTitleTextField;
        this.AddEventsDescriptionTextField = AddEventsDescriptionTextField;
        this.AddEventsVenusTextField = AddEventsVenusTextField;
        this.AddEventsDateTextField = AddEventsDateTextField;
        this.AddEventsTimeTextField = AddEventsTimeTextField;

        EventInfo eventD = AddEventTable.getSelectionModel().getSelectedItem();
        int num = AddEventTable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        this.AddEventsTitleTextField.setText(eventD.getEvent_title());
        this.AddEventsDescriptionTextField.setText(eventD.getEvent_description());
        this.AddEventsVenusTextField.setText(eventD.getEvent_venue());
        this.AddEventsDateTextField.setText(eventD.getEvent_date());
        this.AddEventsTimeTextField.setText(eventD.getEvent_time());
    }

    public void addNewEvent() {
        String sql = "INSERT INTO user.event (event_title, event_description, event_venue, event_date, event_time) VALUES(?,?,?,?,?)";
        try {
            this.connection = DatabaseConnector.getConnection();

            Alert alert;
            if (AddEventsTitleTextField.getText().isEmpty() || AddEventsDescriptionTextField.getText().isEmpty() || AddEventsVenusTextField.getText().isEmpty() || AddEventsDateTextField.getText().isEmpty() || AddEventsTimeTextField.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT event_title FROM user.event WHERE event_title = ?";
                preparedStatement = connection.prepareStatement(checkData);
                preparedStatement.setString(1, AddEventsTitleTextField.getText());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Event title already exists!");
                    alert.showAndWait();
                } else {
                    this.preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, AddEventsTitleTextField.getText());
                    preparedStatement.setString(2, AddEventsDescriptionTextField.getText());
                    preparedStatement.setString(3, AddEventsVenusTextField.getText());
                    preparedStatement.setString(4, AddEventsDateTextField.getText());
                    preparedStatement.setString(5, AddEventsTimeTextField.getText());
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFO Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    updateNumEvent();

                    addEventShowEventInfo(AddEventNumber, AddEventTitle, AddEventDescription, AddEventVenue, AddEventDate, AddEventTime, AddEventTable);
                    addEventClear();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void addEventClear() {
        AddEventsTitleTextField.setText("");
        AddEventsDescriptionTextField.setText("");
        AddEventsVenusTextField.setText("");
        AddEventsDateTextField.setText("");
        AddEventsTimeTextField.setText("");
    }

    public void deleteEvent() {
        // Get the selected event title
        String selectedEventTitle = AddEventsTitleTextField.getText();

        // Check if an event title is selected
        if (selectedEventTitle.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please select an event title to delete.");
            return;
        }

        // Confirmation dialog to confirm deletion
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Delete Event");
        confirmation.setContentText("Are you sure you want to delete the selected event?");
        Optional<ButtonType> result = confirmation.showAndWait();

        // If user confirms deletion, proceed with the delete operation
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Prepare the SQL delete statement
                String sql = "DELETE FROM user.event WHERE event_title = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, selectedEventTitle);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                // Show success message if deletion is successful
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "INFO Message", "Event deleted successfully.");
                    addEventShowEventInfo(AddEventNumber, AddEventTitle, AddEventDescription, AddEventVenue, AddEventDate, AddEventTime, AddEventTable);
                    addEventClear();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Failed to delete the event.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while deleting the event.");
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
