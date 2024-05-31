package Assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewQuiz<T extends YoungStudents> {
    private static String username; // Static variable to hold the username
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @FXML
    private TableView<QuizInfo> QuizTable; // Table to display quiz information

    @FXML
    private TableColumn<QuizInfo, String> QuizesDone; // Column for displaying quizzes done

    @FXML
    private TableColumn<QuizInfo, Integer> QuizesNumber; // Column for displaying quiz number

    @FXML
    private TableColumn<QuizInfo, String> QuizesQuizLink; // Column for displaying quiz link

    @FXML
    private TableColumn<QuizInfo, String> QuizesQuizTheme; // Column for displaying quiz theme

    @FXML
    private TableColumn<QuizInfo, String> QuizesQuizTitle; // Column for displaying quiz title

    @FXML
    private TableColumn<QuizInfo, String> QuizesStatus; // Column for displaying quiz status

    @FXML
    private TableColumn<QuizInfo, String> QuizezQuizDescribtion; // Column for displaying quiz description

    @FXML
    private CheckBox ScienceTickBox; // Checkbox for filtering quizzes by science theme

    @FXML
    private CheckBox TechnologyTickBox; // Checkbox for filtering quizzes by technology theme

    @FXML
    private CheckBox EngineeringTickBox; // Checkbox for filtering quizzes by engineering theme

    @FXML
    private CheckBox MathematicTickBox; // Checkbox for filtering quizzes by mathematics theme

    private static ObservableList<QuizInfo> addQuizList2; // Static list to hold the quiz data

    // Default constructor
    public ViewQuiz() {
    }

    // Constructor to initialize the username
    public ViewQuiz(String username) {
        this.username = username;
    }

    // Method to initialize the checkboxes and add listeners to them
    public void initializeTickBox(CheckBox ScienceTickBox, CheckBox TechnologyTickBox, CheckBox EngineeringTickBox, CheckBox MathematicTickBox) {
        this.ScienceTickBox = ScienceTickBox;
        this.TechnologyTickBox = TechnologyTickBox;
        this.EngineeringTickBox = EngineeringTickBox;
        this.MathematicTickBox = MathematicTickBox;

        // Add listeners to the checkboxes to filter the quiz list when they are selected or deselected
        this.ScienceTickBox.setOnAction(event -> filterQuizList());
        this.TechnologyTickBox.setOnAction(event -> filterQuizList());
        this.EngineeringTickBox.setOnAction(event -> filterQuizList());
        this.MathematicTickBox.setOnAction(event -> filterQuizList());
    }

    // Method to retrieve quiz information from the database
    public ObservableList<QuizInfo> addQuizInfo() {
        ObservableList<QuizInfo> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user.quiz";
        try {
            this.connection = DatabaseConnector.getConnection();
            this.preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            QuizInfo quizInfo;

            while (resultSet.next()) {
                int quizId = resultSet.getInt("quiz_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String theme = resultSet.getString("theme");
                String quizizzLink = resultSet.getString("quizizz_link");
                String quiz_status = "Pending"; // Default status

                // Check if the user has completed the quiz by checking a CSV file
                if (Files.exists(Paths.get(username + "_quizzes.csv"))) {
                    List<String> registeredQuizTitles = Files.lines(Paths.get(username + "_quizzes.csv"))
                            .map(line -> line.split(",")[0]) // Assuming title is the first element in each line
                            .collect(Collectors.toList());

                    if (registeredQuizTitles.contains(title)) {
                        quiz_status = "Completed";
                    }
                }

                // Add the quiz info to the list
                quizInfo = new QuizInfo(quizId, title, description, theme, quizizzLink, quiz_status);
                listData.add(quizInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listData;
    }

    private ObservableList<QuizInfo> addQuizList; // List to hold the quiz data

    // Method to display quiz information in the table
    public void ShowQuizInfo(TableColumn<QuizInfo, Integer> QuizesNumber, TableColumn<QuizInfo, String> QuizesTitle, TableColumn<QuizInfo, String> QuizesDescription, TableColumn<QuizInfo, String> QuizesTheme, TableColumn<QuizInfo, String> QuizesContent, TableView<QuizInfo> QuizesTable, TableColumn<QuizInfo, String> QuizesStatus) {
        this.QuizesNumber = QuizesNumber;
        this.QuizesQuizTitle = QuizesTitle;
        this.QuizezQuizDescribtion = QuizesDescription;
        this.QuizesQuizTheme = QuizesTheme;
        this.QuizesQuizLink = QuizesContent;
        this.QuizTable = QuizesTable;
        this.QuizesStatus = QuizesStatus;
        addQuizList = addQuizInfo();

        // Set the cell value factories for the table columns
        this.QuizesNumber.setCellValueFactory(new PropertyValueFactory<>("quiz_ID"));
        this.QuizesQuizTitle.setCellValueFactory(new PropertyValueFactory<>("quiz_Title"));
        this.QuizezQuizDescribtion.setCellValueFactory(new PropertyValueFactory<>("quiz_Description"));
        this.QuizesQuizTheme.setCellValueFactory(new PropertyValueFactory<>("quiz_Theme"));
        this.QuizesQuizLink.setCellValueFactory(new PropertyValueFactory<>("quiz_content"));
        this.QuizesStatus.setCellValueFactory(new PropertyValueFactory<>("quiz_status"));

        // Set custom cell factory for wrapping text in the table cells
        this.QuizesQuizTitle.setCellFactory(column -> new WrappingTableCell<>());
        this.QuizezQuizDescribtion.setCellFactory(column -> new WrappingTableCell<>());
        this.QuizesQuizTheme.setCellFactory(column -> new WrappingTableCell<>());
        this.QuizesQuizLink.setCellFactory(column -> new WrappingTableCell<>());

        ViewQuiz.addQuizList2 = addQuizList; // Assign the quiz list to the static variable
        QuizTable.setItems(addQuizList); // Set the table items
    }

    // Method to filter the quiz list based on selected themes
    private void filterQuizList() {
        List<String> selectedThemes = new ArrayList<>();

        // Add selected themes to the list
        if (ScienceTickBox.isSelected()) {
            selectedThemes.add("Science");
        }
        if (TechnologyTickBox.isSelected()) {
            selectedThemes.add("Technology");
        }
        if (EngineeringTickBox.isSelected()) {
            selectedThemes.add("Engineering");
        }
        if (MathematicTickBox.isSelected()) {
            selectedThemes.add("Mathematics");
        }

        // Filter the quiz list based on the selected themes
        if (selectedThemes.isEmpty()) {
            QuizTable.setItems(addQuizList); // Show all quizzes if no theme is selected
        } else {
            List<QuizInfo> filteredList = addQuizList.stream()
                    .filter(quiz -> selectedThemes.contains(quiz.getQuiz_Theme()))
                    .collect(Collectors.toList());
            QuizTable.setItems(FXCollections.observableArrayList(filteredList)); // Show filtered quizzes
        }
    }

    // Method to get the titles of all quizzes
    public List<String> getQuizTitles() {
        return addQuizList2.stream()
                .map(QuizInfo::getQuiz_Title)
                .collect(Collectors.toList());
    }

    // Method to get a quiz by its title
    public QuizInfo getQuizByTitle(String title) {
        for (QuizInfo quiz : addQuizList2) {
            if (quiz.getQuiz_Title().equals(title)) {
                return quiz;
            }
        }
        return null;
    }

    // Method to get the titles of available quizzes (those that are pending)
    protected List<String> getAvailableQuizTitles() {
        return addQuizList2.stream()
                .filter(quiz -> "Pending".equals(quiz.getQuiz_status()))
                .map(QuizInfo::getQuiz_Title)
                .collect(Collectors.toList());
    }
}
