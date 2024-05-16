package Assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ViewEvent {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @FXML
    private TableColumn<EventInfo, String> EventDate;

    @FXML
    private TableColumn<EventInfo, String> EventDescription;

    @FXML
    private TableColumn<EventInfo, Integer> EventNumber;

    @FXML
    private TableView<EventInfo> EventTable;

    @FXML
    private TableColumn<EventInfo, String> EventTime;

    @FXML
    private TableColumn<EventInfo, String> EventTitle;

    @FXML
    private TableColumn<EventInfo, String> EventsStatus;

    @FXML
    private TableColumn<EventInfo, String> EventsVenue;

    private static String username;

    private static ObservableList<EventInfo> addEventList2;

    public ViewEvent() {

    }

    public ViewEvent(String username) {
        this.username = username;
    }

    public ObservableList<EventInfo> addEventsInfo() {
        ObservableList<EventInfo> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user.event";
        EventInfo eventInfo;

        try {
            this.connection = DatabaseConnector.getConnection();
            this.preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            while (resultSet.next()) {
                int event_id = resultSet.getInt("event_id");
                String event_title = resultSet.getString("event_title");
                String event_description = resultSet.getString("event_description");
                String event_venue = resultSet.getString("event_venue");
                String event_date = resultSet.getString("event_date");
                String event_time = resultSet.getString("event_time");

                LocalDate eventDate = LocalDate.parse(event_date, formatter);

                List<String> registeredEventTitles = Files.lines(Paths.get(username + ".csv"))
                        .map(line -> line.split(",")[0]) // Assuming title is the first element in each line
                        .collect(Collectors.toList());

                String event_status;
                if (registeredEventTitles.contains(event_title)) {
                    event_status = "Registered";
                } else {
                    event_status = "Available";
                }

                if (!eventDate.isBefore(LocalDate.now())) {
                    eventInfo = new EventInfo(event_id, event_title, event_description, event_venue, event_date, event_time,event_status);
                    listData.add(eventInfo);
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LocalDate currentDate = LocalDate.now();

        // Separate live events and upcoming events
        List<EventInfo> liveEvents = listData.stream()
                .filter(event -> LocalDate.parse(event.getEvent_date(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).isEqual(currentDate))
                .collect(Collectors.toList());

        List<EventInfo> upcomingEvents = listData.stream()
                .filter(event -> LocalDate.parse(event.getEvent_date(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).isAfter(currentDate))
                .sorted(Comparator.comparing(event -> LocalDate.parse(event.getEvent_date(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .limit(3)
                .collect(Collectors.toList());

        // Combine live events and upcoming events
        liveEvents.addAll(upcomingEvents);

        return FXCollections.observableArrayList(liveEvents);
    }

    private ObservableList<EventInfo> addEventList;

    public void ShowEventInfo(TableColumn<EventInfo, Integer> EventNumber, TableColumn<EventInfo, String> EventTitle, TableColumn<EventInfo, String> EventDescription, TableColumn<EventInfo, String> EventVenue, TableColumn<EventInfo, String> EventDate, TableColumn<EventInfo, String> EventTime, TableView<EventInfo> EventTable, TableColumn<EventInfo, String> EventsStatus) {
        this.EventNumber = EventNumber;
        this.EventTitle = EventTitle;
        this.EventDescription = EventDescription;
        this.EventsVenue = EventVenue;
        this.EventDate = EventDate;
        this.EventTime = EventTime;
        this.EventTable = EventTable;
        this.EventsStatus = EventsStatus;

        addEventList = addEventsInfo();

        this.EventNumber.setCellValueFactory(new PropertyValueFactory<>("event_id"));
        this.EventTitle.setCellValueFactory(new PropertyValueFactory<>("event_title"));
        this.EventDescription.setCellValueFactory(new PropertyValueFactory<>("event_description"));
        this.EventsVenue.setCellValueFactory(new PropertyValueFactory<>("event_venue"));
        this.EventDate.setCellValueFactory(new PropertyValueFactory<>("event_date"));
        this.EventTime.setCellValueFactory(new PropertyValueFactory<>("event_time"));
        this.EventsStatus.setCellValueFactory(new PropertyValueFactory<>("event_status"));

        // Set custom cell factory
        this.EventTitle.setCellFactory(column -> new WrappingTableCell<>());
        this.EventDescription.setCellFactory(column -> new WrappingTableCell<>());
        this.EventsVenue.setCellFactory(column -> new WrappingTableCell<>());
        this.EventDate.setCellFactory(column -> new WrappingTableCell<>());
        this.EventTime.setCellFactory(column -> new WrappingTableCell<>());
        this.EventsStatus.setCellFactory(column -> new WrappingTableCell<>());

        addEventList2=addEventList;
        this.EventTable.setItems(addEventList);
    }

    public List<String> getEventTitles() {
        return addEventList.stream()
                .map(EventInfo::getEvent_title)
                .collect(Collectors.toList());
    }
}