package Assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Optional;

public class AddNewQuiz<T extends Educators> {

    @FXML
    private TableColumn<QuizInfo, String> ViewQuizContent;

    @FXML
    private TableColumn<QuizInfo, String> ViewQuizDescription;

    @FXML
    private TableColumn<QuizInfo, String> ViewQuizNumber;

    @FXML
    private TableColumn<QuizInfo, String> ViewQuizTheme;

    @FXML
    private TableColumn<QuizInfo, String> ViewQuizTitle;

    @FXML
    private TableView<QuizInfo> AddQuizTable;

    @FXML
    private MenuButton AddMenuButton;

    @FXML
    private TextField AddQuizContentTextField;

    @FXML
    private Button AddQuizDeleteButton;

    @FXML
    private TextField AddQuizDescriptionTextField;

    @FXML
    private TextField AddQuizTitleTextField;

    @FXML
    private MenuItem engineeringMenuItem;

    @FXML
    private MenuItem mathematicsMenuItem;

    @FXML
    private MenuItem scienceMenuItem;

    @FXML
    private MenuItem technologyMenuItem;

    private static String username;


    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public AddNewQuiz() {

    }

    public AddNewQuiz(String username){
        AddNewQuiz.username=username;
    }

    public void updateNumQuiz(){
        String sql = "UPDATE user.educator SET NUM_QUIZZES_CREATED = NUM_QUIZZES_CREATED + 1 WHERE EDUCATOR_USERNAME = ?";

        try {
            this.connection = DatabaseConnector.getConnection();
            this.preparedStatement = connection.prepareStatement(sql);
            System.out.println(username);
            preparedStatement.setString(1, username);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "INFO Message", "Number of quizzes created updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Failed to update the number of quizzes created.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while updating the number of quizzes created.");
        }
    }

    public void initializeMenuButton(MenuItem engineeringMenuItem, MenuItem mathematicsMenuItem, MenuItem scienceMenuItem,MenuItem technologyMenuItem){
        this.engineeringMenuItem = engineeringMenuItem;
        this.mathematicsMenuItem = mathematicsMenuItem;
        this.scienceMenuItem = scienceMenuItem;
        this.technologyMenuItem = technologyMenuItem;

        scienceMenuItem.setOnAction(e -> AddMenuButton.setText(scienceMenuItem.getText()));
        technologyMenuItem.setOnAction(e -> AddMenuButton.setText(technologyMenuItem.getText()));
        engineeringMenuItem.setOnAction(e -> AddMenuButton.setText(engineeringMenuItem.getText()));
        mathematicsMenuItem.setOnAction(e -> AddMenuButton.setText(mathematicsMenuItem.getText()));
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

    public void addQuizShowQuizInfo(TableColumn<QuizInfo, String> ViewQuizNumber, TableColumn<QuizInfo, String> ViewQuizTitle, TableColumn<QuizInfo, String> ViewQuizDescription, TableColumn<QuizInfo, String> ViewQuizTheme, TableColumn<QuizInfo, String> ViewQuizContent, TableView<QuizInfo> AddQuizTable) {
        this.ViewQuizNumber = ViewQuizNumber;
        this.ViewQuizTitle = ViewQuizTitle;
        this.ViewQuizDescription = ViewQuizDescription;
        this.ViewQuizTheme = ViewQuizTheme;
        this.ViewQuizContent = ViewQuizContent;
        this.AddQuizTable = AddQuizTable;

        addQuizList = addQuizInfo();
        this.ViewQuizNumber.setCellValueFactory(new PropertyValueFactory<>("quiz_ID"));
        this.ViewQuizTitle.setCellValueFactory(new PropertyValueFactory<>("quiz_Title"));
        this.ViewQuizDescription.setCellValueFactory(new PropertyValueFactory<>("quiz_Description"));
        this.ViewQuizTheme.setCellValueFactory(new PropertyValueFactory<>("quiz_Theme"));
        this.ViewQuizContent.setCellValueFactory(new PropertyValueFactory<>("quiz_content"));

        // Set custom cell factory
        this.ViewQuizContent.setCellFactory(column -> new WrappingTableCell<>());
        this.ViewQuizDescription.setCellFactory(column -> new WrappingTableCell<>());
        this.ViewQuizTitle.setCellFactory(column -> new WrappingTableCell<>());
        this.ViewQuizTheme.setCellFactory(column -> new WrappingTableCell<>());

        AddQuizTable.setItems(addQuizList);

    }

    public void setQuizField(TextField AddQuizTitleTextField, TextField AddQuizDescriptionTextField, TextField AddQuizContentTextField, MenuButton AddMenuButton) {
        this.AddQuizTitleTextField = AddQuizTitleTextField;
        this.AddQuizDescriptionTextField = AddQuizDescriptionTextField;
        this.AddQuizContentTextField = AddQuizContentTextField;
        this.AddMenuButton = AddMenuButton;

        QuizInfo quizD = AddQuizTable.getSelectionModel().getSelectedItem();
        int num = AddQuizTable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        this.AddQuizTitleTextField.setText(quizD.getQuiz_Title());
        this.AddQuizDescriptionTextField.setText(quizD.getQuiz_Description());
        this.AddQuizContentTextField.setText(quizD.getQuiz_content());
    }

    public void addNewQuiz() {
        String sql = "INSERT INTO user.quiz (quiz_id, title, description, theme, quizizz_link) VALUES(?,?,?,?,?)";
        try {
            this.connection = DatabaseConnector.getConnection();

            Alert alert;
            if (AddQuizContentTextField.getText().isEmpty() || AddQuizDescriptionTextField.getText().isEmpty() || AddQuizTitleTextField.getText().isEmpty() || AddMenuButton.getText().equals("Select Category")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT quizizz_link FROM user.quiz WHERE quizizz_link = ?";
                preparedStatement = connection.prepareStatement(checkData);
                preparedStatement.setString(1, AddQuizContentTextField.getText());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Quizizz link already exists!");
                    alert.showAndWait();
                } else {
                    int newQuizId = getNextQuizId();
                    this.preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, newQuizId);
                    preparedStatement.setString(2, AddQuizTitleTextField.getText());
                    preparedStatement.setString(3, AddQuizDescriptionTextField.getText());
                    preparedStatement.setString(4, AddMenuButton.getText());
                    preparedStatement.setString(5, AddQuizContentTextField.getText());
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFO Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    updateNumQuiz();

                    addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle, ViewQuizDescription, ViewQuizTheme, ViewQuizContent, AddQuizTable);
                    addQuizClear();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int getNextQuizId() throws SQLException {
        String sql = "SELECT MAX(quiz_id) AS max_id FROM user.quiz";
        this.statement = connection.createStatement();
        this.resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            return resultSet.getInt("max_id") + 1;
        } else {
            return 1; // If there are no records, start with ID 1
        }
    }

    public void addQuizClear() {
        AddQuizTitleTextField.setText("");
        AddQuizDescriptionTextField.setText("");
        AddMenuButton.setText("Select Category");
        AddQuizContentTextField.setText("");
    }

    public void addQuizDelete() {
            // Get the selected quizizz link
            String selectedQuizizzLink = AddQuizContentTextField.getText();

            // Check if a quizizz link is selected
            if (selectedQuizizzLink.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please select a quizizz link to delete.");
                return;
            }

            // Confirmation dialog to confirm deletion
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Delete Quizizz Link");
            confirmation.setContentText("Are you sure you want to delete the selected quizizz link?");
            Optional<ButtonType> result = confirmation.showAndWait();

            // If user confirms deletion, proceed with the delete operation
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Prepare the SQL delete statement
                    String sql = "DELETE FROM user.quiz WHERE quizizz_link = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, selectedQuizizzLink);

                    // Execute the delete statement
                    int rowsAffected = preparedStatement.executeUpdate();

                    // Show success message if deletion is successful
                    if (rowsAffected > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "INFO Message", "Quizizz link deleted successfully.");
                        addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle, ViewQuizDescription, ViewQuizTheme, ViewQuizContent, AddQuizTable);
                        addQuizClear();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error Message", "Failed to delete the quizizz link.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while deleting the quizizz link.");
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

