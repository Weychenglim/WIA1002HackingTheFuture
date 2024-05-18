package Assignment;

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

    public void switchForm(ActionEvent event) {

        if (event.getSource() == HomeButton) {
            homeform.setVisible(true);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
        } else if (event.getSource() == QuizzesButton) {
            ViewQuiz<?> obj;
            if (role.equals("STUDENT")) {
                obj = new ViewQuiz<YoungStudents>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Students can access !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(true);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            obj.initializeTickBox(ScienceTickBox, TechnologyTickBox, EngineeringTickBox, MathematicTickBox);
            obj.ShowQuizInfo(QuizesNumber, QuizesQuizTitle, QuizezQuizDescribtion, QuizesQuizTheme, QuizesQuizLink, QuizTable, QuizesStatus);
        } else if (event.getSource() == EventsButton) {
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(true);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            ViewEvent obj = new ViewEvent(username);
            obj.ShowEventInfo(EventNumber, EventTitle, EventDescription, EventsVenue, EventDate, EventTime, EventTable, EventsStatus);
        } else if (event.getSource() == BookingButton) {
            ViewBooking<?> obj;
            if (role.equals("PARENT")) {
                obj = new ViewBooking<Parents>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Parents can access !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(true);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            obj.initialize(BookingDistance, BookingNumber, BookingSlot, BookingTable, BookingVenue);
        } else if (event.getSource() == LeaderBoardButton) {
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(true);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            LeaderboardRanking obj = new LeaderboardRanking();
            obj.initialize(LeaderBoardTable, LeaderboardNumber, LeaderboardPoints, LeaderboardStudentName, LeaderboardTime);
        } else if (event.getSource() == AddQuizButton) {
            AddNewQuiz<?> obj;
            if (role.equals("EDUCATOR")) {
                obj = new AddNewQuiz<Educators>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Educators can access !!");
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
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            obj.initializeMenuButton(engineeringMenuItem, mathematicsMenuItem, scienceMenuItem, technologyMenuItem);
            obj.addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle, ViewQuizDescription, ViewQuizTheme, ViewQuizContent, AddQuizTable);
            obj.setQuizField(AddQuizTitleTextField, AddQuizDescriptionTextField, AddQuizContentTextField, AddMenuButton);
        } else if (event.getSource() == EventsRegisterButton) {
            RegisterEvent<?> obj;
            if (role.equals("STUDENT")) {
                obj = new RegisterEvent<YoungStudents>();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Students can access !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(true);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            obj.initialize(RegisterEventDatePane, RegisterEventDescriptionPane, RegisterEventTimePane, RegisterEventVenusPane, MenuButtonRegisterEvent);
        } else if (event.getSource() == AddQuizAddButton) {
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(true);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            AddNewQuiz obj = new AddNewQuiz();
            obj.initializeMenuButton(engineeringMenuItem, mathematicsMenuItem, scienceMenuItem, technologyMenuItem);
            obj.addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle, ViewQuizDescription, ViewQuizTheme, ViewQuizContent, AddQuizTable);
            obj.setQuizField(AddQuizTitleTextField, AddQuizDescriptionTextField, AddQuizContentTextField, AddMenuButton);
            obj.addNewQuiz();
        } else if (event.getSource() == AddQuizDeleteButton) {
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(true);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            AddNewQuiz obj = new AddNewQuiz();
            obj.initializeMenuButton(engineeringMenuItem, mathematicsMenuItem, scienceMenuItem, technologyMenuItem);
            obj.addQuizShowQuizInfo(ViewQuizNumber, ViewQuizTitle, ViewQuizDescription, ViewQuizTheme, ViewQuizContent, AddQuizTable);
            obj.setQuizField(AddQuizTitleTextField, AddQuizDescriptionTextField, AddQuizContentTextField, AddMenuButton);
            obj.addQuizDelete();
        } else if (event.getSource() == AddEventsButton) {
            AddNewEvent<?> obj;
            if (role.equals("EDUCATOR")) {
                obj = new AddNewEvent<Educators>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Educators can access !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(true);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            obj.addEventShowEventInfo(AddEventNumber, AddEventTitle, AddEventDescription, AddEventVenue, AddEventDate, AddEventTime, AddEventTable);
            obj.setEventField(AddEventsTitleTextField, AddEventsDescriptionTextField, AddEventsVenusTextField, AddEventsTimeTextField, AddEventsDateTextField);
        } else if (event.getSource() == AddEventsAddButton) {
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(true);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            AddNewEvent obj = new AddNewEvent();
            obj.addEventShowEventInfo(AddEventNumber, AddEventTitle, AddEventDescription, AddEventVenue, AddEventDate, AddEventTime, AddEventTable);
            obj.setEventField(AddEventsTitleTextField, AddEventsDescriptionTextField, AddEventsVenusTextField, AddEventsTimeTextField, AddEventsDateTextField);
            obj.addNewEvent();
        } else if (event.getSource() == AddEventsDeleteButton) {
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(true);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            AddNewEvent obj = new AddNewEvent();
            obj.addEventShowEventInfo(AddEventNumber, AddEventTitle, AddEventDescription, AddEventVenue, AddEventDate, AddEventTime, AddEventTable);
            obj.setEventField(AddEventsTitleTextField, AddEventsDescriptionTextField, AddEventsVenusTextField, AddEventsTimeTextField, AddEventsDateTextField);
            obj.deleteEvent();
        } else if (event.getSource() == RegisterEventsComfirmButton) {
            RegisterEvent obj = new RegisterEvent(username);
            obj.confirmRegistration();
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(true);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            obj.initialize(RegisterEventDatePane, RegisterEventDescriptionPane, RegisterEventTimePane, RegisterEventVenusPane, MenuButtonRegisterEvent);
            obj.clearMenuButtonPane();
            obj.initialize(RegisterEventDatePane, RegisterEventDescriptionPane, RegisterEventTimePane, RegisterEventVenusPane, MenuButtonRegisterEvent);
        } else if (event.getSource() == QuizesSubmitStatus) {
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(true);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            SubmitStatusQuiz obj = new SubmitStatusQuiz(username);
            obj.initialize(SubmitStatusQuizDescriptionPane, SubmitStatusQuizThemePane, MenuButtonQuiz);
        } else if (event.getSource() == SubmitStatusConfirmButton) {
            SubmitStatusQuiz obj = new SubmitStatusQuiz(username);
            obj.confirmRegistration();
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(true);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            obj.initialize(SubmitStatusQuizDescriptionPane, SubmitStatusQuizThemePane, MenuButtonQuiz);
            obj.clearMenuButtonPane();
            obj.initialize(SubmitStatusQuizDescriptionPane, SubmitStatusQuizThemePane, MenuButtonQuiz);
        } else if (event.getSource() == AddRelationshipButton) {
            AddRelationship<?> obj;
            if (role.equals("PARENT")) {
                obj = new AddRelationship<Parents>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Parent can access !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            AddRelationshipsPane.setVisible(true);
            MakeBookingForm.setVisible(false);
            obj.initialize(AddRelationshipsChildrenUserNameTextField);
        } else if (event.getSource() == AddRelationshipsAddButton) {
            AddRelationship obj = new AddRelationship(username);
            obj.initialize(AddRelationshipsChildrenUserNameTextField);
            obj.handleAddRelationship();
            obj.clear();
            obj.initialize(AddRelationshipsChildrenUserNameTextField);
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            AddRelationshipsPane.setVisible(true);
            MakeBookingForm.setVisible(false);
            Parents.printParentChildMap();
            YoungStudents.printChildParentMap();
        } else if (event.getSource() == AddRelationshipsCancelButton) {
            AddRelationship obj = new AddRelationship(username);
            obj.initialize(AddRelationshipsChildrenUserNameTextField);
            obj.clear();
            homeform.setVisible(true);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            MakeBookingForm.setVisible(false);
        } else if (event.getSource() == MakeBookingButton) {
            MakeBooking<?> obj;
            if (role.equals("PARENT")) {
                obj = new MakeBooking<Parents>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Parents can access !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(true);
            obj.initialize(MakeBookingDateMenuButton, MakeBookingDestinationMenuButton, MakeBookingTimeSlotPane, MakeBookingUsernameButton);
            obj.initializeMenuButtons();
        } else if (event.getSource() == MakeBookingConfirmButton) {
            MakeBooking<?> obj;
            if (role.equals("PARENT")) {
                obj = new MakeBooking<Parents>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Parents can access !!");
                alert.showAndWait();
                return;
            }
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(false);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(true);
            obj.initialize(MakeBookingDateMenuButton, MakeBookingDestinationMenuButton, MakeBookingTimeSlotPane, MakeBookingUsernameButton);
            obj.initializeMenuButtons();
            obj.confirmBooking();
            obj.clearMenuButtonPane();
            obj.initialize(MakeBookingDateMenuButton, MakeBookingDestinationMenuButton, MakeBookingTimeSlotPane, MakeBookingUsernameButton);
            obj.initializeMenuButtons();
        }else if (event.getSource() == MakeBookingCancelButton) {
            MakeBooking<?> obj;
            if (role.equals("PARENT")) {
                obj = new MakeBooking<Parents>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Parents can access !!");
                alert.showAndWait();
                return;
            }
            obj.initialize(MakeBookingDateMenuButton, MakeBookingDestinationMenuButton, MakeBookingTimeSlotPane, MakeBookingUsernameButton);
            obj.initializeMenuButtons();
            obj.clearMenuButtonPane();
            homeform.setVisible(false);
            Quizzesform.setVisible(false);
            EventsForm.setVisible(false);
            BookingForm.setVisible(true);
            LeaderBoardForm.setVisible(false);
            AddQuizForm.setVisible(false);
            AddEventsForm.setVisible(false);
            RegisterEventsForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
        }
    }

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

    @FXML
    private Button MakeBookingButton;

    @FXML
    private Button MakeBookingCancelButton;

    @FXML
    private Button MakeBookingConfirmButton;

    @FXML
    private MenuButton MakeBookingUsernameButton;

    @FXML
    private Pane MakeBookingTimeSlotPane;

    @FXML
    private MenuButton MakeBookingDateMenuButton;

    @FXML
    private MenuButton MakeBookingDestinationMenuButton;

    @FXML
    private AnchorPane MakeBookingForm;

    @FXML
    private MenuButton MakeBookingTimeSlotMenuButton;

    @FXML
    private Button AddRelationshipsAddButton;

    @FXML
    private Button AddRelationshipsCancelButton;

    @FXML
    private TextField AddRelationshipsChildrenUserNameTextField;

    @FXML
    private AnchorPane AddRelationshipsPane;

    @FXML
    private Button QuizesSubmitStatus;

    @FXML
    private Button RegisterEventsComfirmButton;

    @FXML
    private MenuButton MenuButtonRegisterEvent;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label Booking;

    @FXML
    private Button BookingButton;

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
    private Button HomeButton;

    @FXML
    private Label LeaderBoard;

    @FXML
    private Button LeaderBoardButton;

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

    @FXML
    private Button NotificationButton;

    @FXML
    private Button QuizzesButton;

    @FXML
    private TableView<QuizInfo> QuizTable;

    @FXML
    private TableColumn<QuizInfo,String> QuizesDone;

    @FXML
    private TableColumn<QuizInfo,Integer> QuizesNumber;

    @FXML
    private TableColumn<QuizInfo,String> QuizesQuizLink;

    @FXML
    private TableColumn<QuizInfo,String> QuizesQuizTheme;

    @FXML
    private TableColumn<QuizInfo,String> QuizesQuizTitle;

    @FXML
    private TableColumn<QuizInfo,String> QuizesStatus;

    @FXML
    private TableColumn<QuizInfo,String> QuizezQuizDescribtion;

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
    private CheckBox MathematicTickBox;

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
    private TableView<EventInfo> AddEventTable;

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

    @FXML
    private Button SubmitStatusConfirmButton;

    @FXML
    private AnchorPane SubmitStatusForm;

    @FXML
    private Pane SubmitStatusQuizDescriptionPane;

    @FXML
    private Pane SubmitStatusQuizThemePane;

    @FXML
    private MenuButton MenuButtonQuiz;

    @FXML
    private Button UserProfileBacktoHomeButton;

    @FXML
    private AnchorPane UserProfileForm;

    @FXML
    private Pane UserProfilePane;

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
