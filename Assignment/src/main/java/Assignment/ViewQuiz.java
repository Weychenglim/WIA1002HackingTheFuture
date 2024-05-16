package Assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewQuiz<T extends YoungStudents> {
    private static String username;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @FXML
    private TableView<QuizInfo> QuizTable;

    @FXML
    private TableColumn<QuizInfo, String> QuizesDone;

    @FXML
    private TableColumn<QuizInfo, Integer> QuizesNumber;

    @FXML
    private TableColumn<QuizInfo, String> QuizesQuizLink;

    @FXML
    private TableColumn<QuizInfo, String> QuizesQuizTheme;

    @FXML
    private TableColumn<QuizInfo, String> QuizesQuizTitle;

    @FXML
    private TableColumn<QuizInfo, String> QuizesStatus;

    @FXML
    private TableColumn<QuizInfo, String> QuizezQuizDescribtion;

    @FXML
    private CheckBox ScienceTickBox;

    @FXML
    private CheckBox TechnologyTickBox;

    @FXML
    private CheckBox EngineeringTickBox;

    @FXML
    private CheckBox MathematicTickBox;

    public void initializeTickBox(CheckBox ScienceTickBox, CheckBox TechnologyTickBox, CheckBox EngineeringTickBox, CheckBox MathematicTickBox) {
        this.ScienceTickBox = ScienceTickBox;
        this.TechnologyTickBox = TechnologyTickBox;
        this.EngineeringTickBox = EngineeringTickBox;
        this.MathematicTickBox = MathematicTickBox;

        // Add listeners to the checkboxes
        this.ScienceTickBox.setOnAction(event -> filterQuizList());
        this.TechnologyTickBox.setOnAction(event -> filterQuizList());
        this.EngineeringTickBox.setOnAction(event -> filterQuizList());
        this.MathematicTickBox.setOnAction(event -> filterQuizList());
    }

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
                quizInfo = new QuizInfo(quizId, title, description, theme, quizizzLink);
                listData.add(quizInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<QuizInfo> addQuizList;

    public void ShowQuizInfo(TableColumn<QuizInfo, Integer> QuizesNumber, TableColumn<QuizInfo, String> QuizesTitle, TableColumn<QuizInfo, String> QuizesDescription, TableColumn<QuizInfo, String> QuizesTheme, TableColumn<QuizInfo, String> QuizesContent, TableView<QuizInfo> QuizesTable) {
        this.QuizesNumber = QuizesNumber;
        this.QuizesQuizTitle = QuizesTitle;
        this.QuizezQuizDescribtion = QuizesDescription;
        this.QuizesQuizTheme = QuizesTheme;
        this.QuizesQuizLink = QuizesContent;
        this.QuizTable = QuizesTable;
        addQuizList = addQuizInfo();

        this.QuizesNumber.setCellValueFactory(new PropertyValueFactory<>("quiz_ID"));
        this.QuizesQuizTitle.setCellValueFactory(new PropertyValueFactory<>("quiz_Title"));
        this.QuizezQuizDescribtion.setCellValueFactory(new PropertyValueFactory<>("quiz_Description"));
        this.QuizesQuizTheme.setCellValueFactory(new PropertyValueFactory<>("quiz_Theme"));
        this.QuizesQuizLink.setCellValueFactory(new PropertyValueFactory<>("quiz_content"));

        // Set custom cell factory
        this.QuizesQuizTitle.setCellFactory(column -> new WrappingTableCell<>());
        this.QuizezQuizDescribtion.setCellFactory(column -> new WrappingTableCell<>());
        this.QuizesQuizTheme.setCellFactory(column -> new WrappingTableCell<>());
        this.QuizesQuizLink.setCellFactory(column -> new WrappingTableCell<>());

        QuizTable.setItems(addQuizList);
    }

    private void filterQuizList() {
        List<String> selectedThemes = new ArrayList<>();

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

        if (selectedThemes.isEmpty()) {
            QuizTable.setItems(addQuizList);
        } else {
            List<QuizInfo> filteredList = addQuizList.stream()
                    .filter(quiz -> selectedThemes.contains(quiz.getQuiz_Theme()))
                    .collect(Collectors.toList());
            QuizTable.setItems(FXCollections.observableArrayList(filteredList));
        }
    }
}
