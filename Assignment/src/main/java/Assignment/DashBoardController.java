package Assignment;

import com.gluonhq.charm.glisten.control.DropdownButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author kianx
 */
public class DashBoardController extends SixteenDashboard implements Initializable {

    @FXML
    public Button LogOutButton;

    @FXML
    private AnchorPane homeform;

    @FXML
    private AnchorPane Quizzesform;

    @FXML
    private AnchorPane EventsForm;

    @FXML
    private AnchorPane RegisterEventsForm;

    @FXML
    private Pane RegisterEventDatePane;

    @FXML
    private Pane RegisterEventDescriptionPane;

    @FXML
    private Pane RegisterEventTimePane;

    @FXML
    private Pane RegisterEventVenusPane;

    @FXML
    private AnchorPane BookingForm;

    @FXML
    private AnchorPane LeaderBoardForm;

    public void switchForm(ActionEvent event){

        if(event.getSource() == HomeButton){
            homeform.setVisible(true);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
        }else if(event.getSource()== QuizzesButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(true);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
        }else if(event.getSource()==EventsButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(true);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
        }else if(event.getSource()==BookingButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(true);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
        }else if(event.getSource()==LeaderBoardButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(true);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
        }else if(event.getSource() == AddQuizButton){
            AddNewQuiz<?> obj;
            if (role.equals("EDUCATOR")) {
                obj = new AddNewQuiz<Educators>();
            } else{
                System.out.println("a");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Educators can accessed !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(true);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            obj.initializeMenuButton(engineeringMenuItem,mathematicsMenuItem,scienceMenuItem,technologyMenuItem);
            obj.addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle,ViewQuizDescription,ViewQuizTheme,ViewQuizContent,AddQuizTable);
            obj.setQuizField(AddQuizTitleTextField,AddQuizDescriptionTextField,AddQuizContentTextField, AddMenuButton);

        }else if(event.getSource() == AddEventsButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(true);
            RegisterEventsForm.setVisible(false);
        }
        else if(event.getSource() == EventsRegisterButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(true);
            addChoice();
        }else if(event.getSource() == AddQuizAddButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(true);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            AddNewQuiz obj = new AddNewQuiz();
            obj.initializeMenuButton(engineeringMenuItem,mathematicsMenuItem,scienceMenuItem,technologyMenuItem);
            obj.addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle,ViewQuizDescription,ViewQuizTheme,ViewQuizContent,AddQuizTable);
            obj.setQuizField(AddQuizTitleTextField,AddQuizDescriptionTextField,AddQuizContentTextField, AddMenuButton);
            obj.addNewQuiz();
        }
        else if(event.getSource() == AddQuizDeleteButton){
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(true);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            AddNewQuiz obj = new AddNewQuiz();
            obj.initializeMenuButton(engineeringMenuItem,mathematicsMenuItem,scienceMenuItem,technologyMenuItem);
            obj.addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle,ViewQuizDescription,ViewQuizTheme,ViewQuizContent,AddQuizTable);
            obj.setQuizField(AddQuizTitleTextField,AddQuizDescriptionTextField,AddQuizContentTextField, AddMenuButton);
            obj.addQuizDelete();
        }


    }

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label Booking;

    @FXML
    private Button BookingButton;

    @FXML
    private TableView<?> BookingTable;

    @FXML
    private Label DisscussionBoardLabel;

    @FXML
    private Label Events;

    @FXML
    private Button EventsButton;

    @FXML
    private Button EventsRegisterButton;

    @FXML
    private TableView<?> EventsTable;

    @FXML
    private DropdownButton RegisterEventsEventTitleDropDown;
    private String[] sexList = {"Male", "Female"};

    public void addChoice() {
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

        for (String data : sexList) {
            MenuItem menuItem = new MenuItem(data);
            menuItem.setOnAction(event -> {
                // Get the source of the event, which is the clicked MenuItem
                MenuItem clickedItem = (MenuItem) event.getSource();
                // Set the selected item of the DropdownButton
                RegisterEventsEventTitleDropDown.setSelectedItem(clickedItem);
            });
            menuItems.add(menuItem);
        }

        // Set the items of the dropdown button
        RegisterEventsEventTitleDropDown.getItems().setAll(menuItems);
    }



    @FXML
    private Button HomeButton;

    @FXML
    private Label LeaderBoard;

    @FXML
    private Button LeaderBoardButton;

    @FXML
    private TableView<?> LeaderBoardTable;

    @FXML
    private Button NotificationButton;

    @FXML
    private Button QuizzesButton;


    @FXML
    private TableColumn<?, ?> QuizesDone;

    @FXML
    private TableColumn<?, ?> QuizesNumber;

    @FXML
    private TableColumn<?, ?> QuizesQuizLink;

    @FXML
    private TableColumn<?, ?> QuizesQuizTheme;

    @FXML
    private TableColumn<?, ?> QuizesQuizTitle;

    @FXML
    private TableColumn<?, ?> QuizezQuizDescribtion;

    @FXML
    private CheckBox ScienceTickBox;

    @FXML
    private CheckBox TechnologyTickBox;

    @FXML
    private Button AddEventsAddButton;

    @FXML
    private Button AddEventsButton;

    @FXML
    private TextField AddEventsDateTextField;

    @FXML
    private Button AddEventsDeleteButton;

    @FXML
    private TextField AddEventsDescriptionTextField;

    @FXML
    private AnchorPane AddEventsForm;

    @FXML
    private TextField AddEventsTimeTextField;

    @FXML
    private TextField AddEventsTitleTextField;

    @FXML
    private TextField AddEventsVenusTextField;

    @FXML
    private Button AddQuizAddButton;

    @FXML
    private ScrollPane DiscussionBoardDisplay;

    @FXML
    private CheckBox EngineeringTickBox;

    @FXML
    private TableColumn<?, ?> EventDate;

    @FXML
    private TableColumn<?, ?> EventTime;

    @FXML
    private Button DiscussionBoardSendButton;

    @FXML
    private TextField DiscussionBoardTextField;

    @FXML
    private Button AddQuizButton;

    @FXML
    private TextField AddQuizContentTextField;

    @FXML
    private Button AddQuizDeleteButton;

    @FXML
    private TextField AddQuizDescriptionTextField;


    @FXML
    private AnchorPane AddQuizForm;

    @FXML
    private TextField AddQuizTitleTextField;

    @FXML
    private Button AddRelationshipButton;

    @FXML
    private TableColumn<?, ?> SearchTable;

    @FXML
    private TextField SearchTextField;

    @FXML
    private Button UserProfileButton;

    @FXML
    private Button closebutton;

    @FXML
    private Button mininimizebutton;

    @FXML
    private TableView<?> AddEventTable;

    @FXML
    private TableColumn<?, ?> AddEventTime;

    @FXML
    private TableColumn<?, ?> AddEventTitle;

    @FXML
    private TableColumn<?, ?> AddEventVenue;

    @FXML
    private TableColumn<?, ?> AddEventDate;

    @FXML
    private TableColumn<?, ?> AddEventDescription;

    @FXML
    private TableColumn<?, ?> AddEventNumber;

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
    private MenuItem engineeringMenuItem;

    @FXML
    private MenuItem mathematicsMenuItem;

    @FXML
    private MenuItem scienceMenuItem;

    @FXML
    private MenuItem technologyMenuItem;

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    private AddDiscussionBoard discussionBoard;
    private static String username;
    private static String role;

    public DashBoardController(String username, String role){
        DashBoardController.username = username;
        DashBoardController.role = role;
    }

    public DashBoardController(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDiscussionBoard(DashBoardController.username , DashBoardController.role);
    }

    @FXML
    private void initializeDiscussionBoard(String username, String role) {
        System.out.println(username+role);
        discussionBoard = new AddDiscussionBoard(DiscussionBoardDisplay, DiscussionBoardTextField, DiscussionBoardSendButton, username, role);
        discussionBoard.displayDiscussion();
        DiscussionBoardSendButton.setOnAction(event -> discussionBoard.postMessage());

    }

    public void LogOut(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // Close the current stage
        stage.close();
        LoginAndSignUp.main(new String[0]);
    }

}
