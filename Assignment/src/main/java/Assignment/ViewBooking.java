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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class ViewBooking<T extends Parents> {

    @FXML
    private TableColumn<BookingInfo, String> BookingDistance;

    @FXML
    private TableColumn<BookingInfo, String> BookingNumber;

    @FXML
    private TableColumn<BookingInfo, String> BookingSlot;

    @FXML
    private TableView<BookingInfo> BookingTable;

    @FXML
    private TableColumn<BookingInfo, String> BookingVenue;

    private static String username;

    public ViewBooking(String username) {
        ViewBooking.username = username;
    }

    public void initialize(TableColumn<BookingInfo, String> BookingDistance, TableColumn<BookingInfo, String> BookingNumber, TableColumn<BookingInfo, String> BookingSlot, TableView<BookingInfo> BookingTable, TableColumn<BookingInfo, String> BookingVenue) {
        this.BookingDistance = BookingDistance;
        this.BookingNumber = BookingNumber;
        this.BookingSlot = BookingSlot;
        this.BookingTable = BookingTable;
        this.BookingVenue = BookingVenue;

        showBookingInfo();
    }

    private void showBookingInfo() {
        System.out.println("aa");
        ObservableList<BookingInfo> bookingList = addBookingInfo();

        BookingDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        BookingNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        BookingSlot.setCellValueFactory(new PropertyValueFactory<>("slots"));
        BookingVenue.setCellValueFactory(new PropertyValueFactory<>("destination"));

        BookingTable.setItems(bookingList);
    }

    public ObservableList<BookingInfo> addBookingInfo() {
        ObservableList<BookingInfo> listData = FXCollections.observableArrayList();
        String sql = "SELECT Destination, x_coordinate, y_coordinate FROM user.booking";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String destination = resultSet.getString("Destination");
                double xCoordinate = resultSet.getDouble("x_coordinate");
                double yCoordinate = resultSet.getDouble("y_coordinate");

                double distance = calculateDistance(username, xCoordinate, yCoordinate);
                String formattedDistance = String.format("%.2f km", distance);

                String slots = getSlotCount(destination);

                listData.add(new BookingInfo(null, destination, formattedDistance, slots)); // Temporarily set ID to null
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        listData.sort(Comparator.comparingDouble(info -> Double.parseDouble(info.getDistance().replace(" km", ""))));

        // Reassign booking numbers
        for (int i = 0; i < listData.size(); i++) {
            listData.get(i).setId(String.valueOf(i + 1)); // Set ID starting from 1
        }

        return listData;
    }

    private double calculateDistance(String username, double xCoordinate, double yCoordinate) throws SQLException {
        String sql = "SELECT PARENT_LOCATION_COORDINATE FROM user.parent WHERE PARENT_USERNAME = ?";
        double parentX = 0;
        double parentY = 0;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String[] coordinates = resultSet.getString("PARENT_LOCATION_COORDINATE").split(",");
                    // Clean up and parse the coordinates
                    String xStr = coordinates[0].replaceAll("[^\\d.]", ""); // Remove all non-digit and non-dot characters
                    String yStr = coordinates[1].replaceAll("[^\\d.]", ""); // Remove all non-digit and non-dot characters
                    parentX = Double.parseDouble(xStr);
                    parentY = Double.parseDouble(yStr);
                    System.out.println(xCoordinate + " " + yCoordinate);
                    System.out.println(xStr + " " + yStr);
                }
            }
        }

        return (Math.sqrt(Math.pow((xCoordinate - parentX), 2) + Math.pow((yCoordinate - parentY), 2))/50);
    }


    private String getSlotCount(String destination) throws IOException {
        String fileName = destination + ".csv";
        if (Files.exists(Paths.get(fileName))) {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            return String.valueOf(lines.size());
        }
        return "0";
    }

}
