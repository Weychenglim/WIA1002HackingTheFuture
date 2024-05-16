package Assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaderboardRanking {

    @FXML
    private TableView<LeaderboardInfo> LeaderBoardTable;

    @FXML
    private TableColumn<LeaderboardInfo,Integer> LeaderboardNumber;

    @FXML
    private TableColumn<LeaderboardInfo,Integer> LeaderboardPoints;

    @FXML
    private TableColumn<LeaderboardInfo,String> LeaderboardStudentName;

    @FXML
    private TableColumn<LeaderboardInfo,String> LeaderboardTime;

    private ObservableList<LeaderboardInfo> addLeaderboardList;

    public void initialize(TableView<LeaderboardInfo> LeaderBoardTable,TableColumn<LeaderboardInfo,Integer> LeaderboardNumber, TableColumn<LeaderboardInfo,Integer> LeaderboardPoints, TableColumn<LeaderboardInfo,String> LeaderboardStudentName,TableColumn<LeaderboardInfo,String> LeaderboardTime) {
        this.LeaderBoardTable = LeaderBoardTable;
        this.LeaderboardNumber = LeaderboardNumber;
        this.LeaderboardPoints = LeaderboardPoints;
        this.LeaderboardStudentName = LeaderboardStudentName;
        this.LeaderboardTime = LeaderboardTime;

        addLeaderboardList = addLeaderboardInfo();

        this.LeaderboardNumber.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        this.LeaderboardPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        this.LeaderboardStudentName.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.LeaderboardTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        this.LeaderBoardTable.setItems(addLeaderboardList);
    }

    public ObservableList<LeaderboardInfo> addLeaderboardInfo() {
        ObservableList<LeaderboardInfo> leaderboardList = FXCollections.observableArrayList();

        // Establish connection to the database
        try (Connection connection = DatabaseConnector.getConnection()) {
            // SQL query to select data from user.student table
            String sql = "SELECT STUDENT_USERNAME, CURRENT_POINTS, timepointsupdated FROM user.student ORDER BY CURRENT_POINTS DESC, timepointsupdated ASC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                int ranking = 1;

                // Iterate through the result set
                while (resultSet.next()) {
                    String username = resultSet.getString("STUDENT_USERNAME");
                    int points = resultSet.getInt("CURRENT_POINTS");
                    String time = resultSet.getString("timepointsupdated");

                    // Create LeaderboardInfo object and add it to the list
                    leaderboardList.add(new LeaderboardInfo(ranking, username, points, time));

                    ranking++; // Increment ranking
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboardList;
    }
}
