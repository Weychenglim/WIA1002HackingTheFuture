package Assignment;

import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            LearnMoreForm.setVisible(false);
            ;
            FriendGraphForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
            obj.initialize(MakeBookingDateMenuButton, MakeBookingDestinationMenuButton, MakeBookingTimeSlotPane, MakeBookingUsernameButton);
            obj.initializeMenuButtons();
            obj.confirmBooking();
            obj.clearMenuButtonPane();
            obj.initialize(MakeBookingDateMenuButton, MakeBookingDestinationMenuButton, MakeBookingTimeSlotPane, MakeBookingUsernameButton);
            obj.initializeMenuButtons();
        } else if (event.getSource() == MakeBookingCancelButton) {
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
        } else if (event.getSource() == UserProfileButton) {
            UserProfile obj = new UserProfile(username, role);
            if (role.equals("EDUCATOR")) {
                UserProfileEducatorAchievement.setVisible(true);
                UserProfileParentAchievementPane.setVisible(false);
                UserProfileStudentAchievementPane.setVisible(false);
            } else if (role.equals("PARENT")) {
                UserProfileEducatorAchievement.setVisible(false);
                UserProfileParentAchievementPane.setVisible(true);
                UserProfileStudentAchievementPane.setVisible(false);
                ParentUsername.setVisible(false);
                ChildUsername.setVisible(true);
            } else {
                UserProfileEducatorAchievement.setVisible(false);
                UserProfileParentAchievementPane.setVisible(false);
                UserProfileStudentAchievementPane.setVisible(true);
                ParentUsername.setVisible(true);
                ChildUsername.setVisible(false);
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
            AddRelationshipsPane.setVisible(false);
            MakeBookingForm.setVisible(false);
            UserProfileForm.setVisible(true);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
            obj.initialize(UserProfileEducatorAchievementNumberOfQuizesCreatedPane, UserProfileEmailPane, UserProfileLocationCoordinatePane, UserProfileParentAchiementNumberOfEventsCreatedPane, UserProfileParentAchiementPastBookingMadePane, UserProfileRolePane, UserProfileStudentAchievementFriendsPane, UserProfileStudentAchievementTotalPointsPane, UserProfileUsernamePane, UserProfileParentChildPane);
        } else if (event.getSource() == UserProfileBacktoHomeButton) {
            UserProfile obj = new UserProfile(username, role);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
        } else if (event.getSource() == ConnectionButton) {
            SearchFriend<?> obj;
            if (role.equals("STUDENT")) {
                obj = new SearchFriend<YoungStudents>();
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
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(true);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
        } else if (event.getSource() == SearchFriendSearchButton) {
            SearchFriend<?> obj;
            if (role.equals("STUDENT")) {
                obj = new SearchFriend<YoungStudents>(username);
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
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(true);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
            obj.initialize(FriendsProfileEmailPane, FriendsProfileLocationCoordinatePane, FriendsProfileTotalPointsPane, FriendsProfileUsernamePane, SearchFriendUsernameTextField);
            obj.searchAndDisplayFriendProfile();
        } else if (event.getSource() == SearchFriendBacktoHomeButoon) {
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
        } else if (event.getSource() == NotificationButton) {
            SearchFriend<?> obj;
            if (role.equals("STUDENT")) {
                obj = new SearchFriend<YoungStudents>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Students can access !");
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
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(true);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
            loadFriendRequests();
        } else if (event.getSource() == SearchFriendSendFriendRequestButton) {
            SearchFriend<?> obj;
            if (role.equals("STUDENT")) {
                obj = new SearchFriend<YoungStudents>(username);
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
            RegisterEventsForm.setVisible(false);
            SubmitStatusForm.setVisible(false);
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(true);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
            obj.initialize(FriendsProfileEmailPane, FriendsProfileLocationCoordinatePane, FriendsProfileTotalPointsPane, FriendsProfileUsernamePane, SearchFriendUsernameTextField);
            obj.sendFriendRequest();
        } else if (event.getSource() == FriendGraphButton) {
            GenerateFriendGraph<?> obj;
            if (role.equals("STUDENT")) {
                obj = new GenerateFriendGraph<>(username);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Only Students can access !");
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
            MakeBookingForm.setVisible(false);
            AddRelationshipsPane.setVisible(false);
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(true);
            LearnMoreForm.setVisible(false);
            obj.initialize(FriendGraphPane);
        } else if (event.getSource() == learnmorebutton) {
            homeform.setVisible(false);
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(true);
        } else if (event.getSource() == LearnMoreHomeButton)  {
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
            UserProfileForm.setVisible(false);
            SearchFriendForm.setVisible(false);
            Notificationform.setVisible(false);
            FriendGraphForm.setVisible(false);
            LearnMoreForm.setVisible(false);
        }
    }


    @FXML
    private Button FriendGraphButton;

    @FXML
    private AnchorPane FriendGraphForm;

    @FXML
    private Pane FriendGraphPane;

    @FXML
    private Button SearchFriendSendFriendRequestButton;

    @FXML
    private AnchorPane Notificationform;

    @FXML
    private Button SearchFriendBacktoHomeButoon;

    @FXML
    private Button SearchFriendSearchButton;

    @FXML
    private TextField SearchFriendUsernameTextField;

    @FXML
    private Button ConnectionButton;

    @FXML
    private Label ParentUsername;

    @FXML
    private Label ChildUsername;

    @FXML
    private Pane UserProfileParentChildPane;

    @FXML
    private AnchorPane UserProfileEducatorAchievement;

    @FXML
    private AnchorPane SearchFriendForm;

    @FXML
    private Pane UserProfileEducatorAchievementNumberOfQuizesCreatedPane;

    @FXML
    private Pane UserProfileEmailPane;

    @FXML
    private Pane UserProfileLocationCoordinatePane;

    @FXML
    private Pane UserProfileParentAchiementNumberOfEventsCreatedPane;

    @FXML
    private Pane UserProfileParentAchiementPastBookingMadePane;

    @FXML
    private AnchorPane UserProfileParentAchievementPane;

    @FXML
    private Pane UserProfileRolePane;

    @FXML
    private Pane UserProfileStudentAchievementFriendsPane;

    @FXML
    private AnchorPane UserProfileStudentAchievementPane;

    @FXML
    private Pane UserProfileStudentAchievementTotalPointsPane;

    @FXML
    private Pane UserProfileUsernamePane;


    @FXML
    private Pane FriendsProfileEmailPane;

    @FXML
    private Pane FriendsProfileLocationCoordinatePane;

    @FXML
    private Pane FriendsProfileTotalPointsPane;

    @FXML
    private Pane FriendsProfileUsernamePane;

    @FXML
    private AnchorPane FriendProfileForm;

    @FXML
    private Button FriendRequestAcceptButton1;

    @FXML
    private Button FriendRequestAcceptButton2;

    @FXML
    private Button FriendRequestAcceptButton3;

    @FXML
    private Button FriendRequestAcceptButton4;

    @FXML
    private Button FriendRequestAcceptButton5;

    @FXML
    private Button FriendRequestAcceptButton6;

    @FXML
    private Button FriendRequestAcceptButton7;

    @FXML
    private Button FriendRequestAcceptButton8;

    @FXML
    private HBox FriendRequestHBOX1;

    @FXML
    private HBox FriendRequestHBOX2;

    @FXML
    private HBox FriendRequestHBOX3;

    @FXML
    private HBox FriendRequestHBOX4;

    @FXML
    private HBox FriendRequestHBOX5;

    @FXML
    private HBox FriendRequestHBOX6;

    @FXML
    private HBox FriendRequestHBOX7;

    @FXML
    private HBox FriendRequestHBOX8;

    @FXML
    private Button FriendRequestAcceptButton10;

    @FXML
    private Button FriendRequestAcceptButton11;

    @FXML
    private Button FriendRequestAcceptButton12;

    @FXML
    private Button FriendRequestAcceptButton13;

    @FXML
    private Button FriendRequestAcceptButton14;

    @FXML
    private Button FriendRequestAcceptButton15;
    @FXML
    private Button FriendRequestAcceptButton9;

    @FXML
    private HBox FriendRequestHBOX10;

    @FXML
    private HBox FriendRequestHBOX11;

    @FXML
    private HBox FriendRequestHBOX12;

    @FXML
    private HBox FriendRequestHBOX13;

    @FXML
    private HBox FriendRequestHBOX14;

    @FXML
    private HBox FriendRequestHBOX15;

    @FXML
    private HBox FriendRequestHBOX9;
    @FXML
    private BottomNavigationButton FriendRequestRejectButton10;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton11;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton12;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton13;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton14;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton15;
    @FXML
    private BottomNavigationButton FriendRequestRejectButton9;


    @FXML
    private Pane FriendRequestUsernamePane10;

    @FXML
    private Pane FriendRequestUsernamePane11;

    @FXML
    private Pane FriendRequestUsernamePane12;

    @FXML
    private Pane FriendRequestUsernamePane13;

    @FXML
    private Pane FriendRequestUsernamePane14;

    @FXML
    private Pane FriendRequestUsernamePane15;

    @FXML
    private Pane FriendRequestUsernamePane9;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton1;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton2;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton3;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton4;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton5;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton6;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton7;

    @FXML
    private BottomNavigationButton FriendRequestRejectButton8;

    @FXML
    private Pane FriendRequestUsernamePane1;

    @FXML
    private Pane FriendRequestUsernamePane2;

    @FXML
    private Pane FriendRequestUsernamePane3;

    @FXML
    private Pane FriendRequestUsernamePane4;

    @FXML
    private Pane FriendRequestUsernamePane5;

    @FXML
    private Pane FriendRequestUsernamePane6;

    @FXML
    private Pane FriendRequestUsernamePane7;

    @FXML
    private Pane FriendRequestUsernamePane8;

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
    private TableColumn<LeaderboardInfo, Integer> LeaderboardNumber;

    @FXML
    private TableColumn<LeaderboardInfo, Integer> LeaderboardPoints;

    @FXML
    private TableColumn<LeaderboardInfo, String> LeaderboardStudentName;

    @FXML
    private TableColumn<LeaderboardInfo, String> LeaderboardTime;

    @FXML
    private Button NotificationButton;

    @FXML
    private Button QuizzesButton;

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
    private TableColumn<EventInfo, String> AddEventTime;

    @FXML
    private TableColumn<EventInfo, String> AddEventTitle;

    @FXML
    private TableColumn<EventInfo, String> AddEventVenue;

    @FXML
    private TableColumn<EventInfo, String> AddEventDate;

    @FXML
    private TableColumn<EventInfo, String> AddEventDescription;

    @FXML
    private TableColumn<EventInfo, String> AddEventNumber;

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
    private AnchorPane LearnMoreForm;

    @FXML
    private Button LearnMoreHomeButton;

    @FXML
    private Button learnmorebutton;

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    private AddDiscussionBoard discussionBoard;
    private static String username;
    private static String role;

    public DashBoardController(String username, String role) {
        DashBoardController.username = username;
        DashBoardController.role = role;
    }

    public DashBoardController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDiscussionBoard(DashBoardController.username, DashBoardController.role);
    }

    @FXML
    private void initializeDiscussionBoard(String username, String role) {
        System.out.println(username + role);
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

    // Method to set visibility of friend request elements
    private void setFriendRequestVisibility(int numberOfRequests) {
        HBox[] requestHBoxes = {FriendRequestHBOX1, FriendRequestHBOX2, FriendRequestHBOX3, FriendRequestHBOX4, FriendRequestHBOX5, FriendRequestHBOX6, FriendRequestHBOX7, FriendRequestHBOX8, FriendRequestHBOX9, FriendRequestHBOX10, FriendRequestHBOX11, FriendRequestHBOX12, FriendRequestHBOX13, FriendRequestHBOX14, FriendRequestHBOX15};
        Button[] acceptButtons = {FriendRequestAcceptButton1, FriendRequestAcceptButton2, FriendRequestAcceptButton3, FriendRequestAcceptButton4, FriendRequestAcceptButton5, FriendRequestAcceptButton6, FriendRequestAcceptButton7, FriendRequestAcceptButton8, FriendRequestAcceptButton9, FriendRequestAcceptButton10, FriendRequestAcceptButton11, FriendRequestAcceptButton12, FriendRequestAcceptButton13, FriendRequestAcceptButton14, FriendRequestAcceptButton15};
        Button[] rejectButtons = {FriendRequestRejectButton1, FriendRequestRejectButton2, FriendRequestRejectButton3, FriendRequestRejectButton4, FriendRequestRejectButton5, FriendRequestRejectButton6, FriendRequestRejectButton7, FriendRequestRejectButton8, FriendRequestRejectButton9, FriendRequestRejectButton10, FriendRequestRejectButton11, FriendRequestRejectButton12, FriendRequestRejectButton13, FriendRequestRejectButton14, FriendRequestRejectButton15};
        Pane[] usernamePanes = {FriendRequestUsernamePane1, FriendRequestUsernamePane2, FriendRequestUsernamePane3, FriendRequestUsernamePane4, FriendRequestUsernamePane5, FriendRequestUsernamePane6, FriendRequestUsernamePane7, FriendRequestUsernamePane8, FriendRequestUsernamePane9, FriendRequestUsernamePane10, FriendRequestUsernamePane11, FriendRequestUsernamePane12, FriendRequestUsernamePane13, FriendRequestUsernamePane14, FriendRequestUsernamePane15};
        for (int i = 0; i < requestHBoxes.length; i++) {
            boolean visible = i < numberOfRequests;
            requestHBoxes[i].setVisible(visible);
            acceptButtons[i].setVisible(visible);
            rejectButtons[i].setVisible(visible);
            usernamePanes[i].setVisible(visible);
        }
        // Initialize pane with friend username

        File file = new File(username + "_request.csv");
        if (!file.exists()) {
            return;
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(username + "_request.csv"))) {
                String line = br.readLine(); // Read the first line only
                int i = 0;
                while (i<numberOfRequests){
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        Label label = new Label(parts[0]); // Assuming friend username is in the first column
                        usernamePanes[i].getChildren().add(label);
                        i++;
                    }
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while reading friend request file.");
            }
        }
    }



    // General method for accepting friend request
    @FXML
    private void acceptFriendRequest(ActionEvent event) {
        Button button = (Button) event.getSource();
        String friendUsername = (String) button.getUserData();
        try {
            // Add friend to friendlist table
            addFriendToFriendlist(friendUsername);
            // Remove the request from CSV file
            removeFriendRequestFromFile(friendUsername);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Friend request accepted from: " + friendUsername);
            loadFriendRequests(); // Refresh the friend requests after accepting
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while accepting friend request.");
        }
    }

    // General method for rejecting friend request
    @FXML
    private void rejectFriendRequest(ActionEvent event) {
        Button button = (Button) event.getSource();
        String friendUsername = (String) button.getUserData();
        try {
            // Remove the request from CSV file
            removeFriendRequestFromFile(friendUsername);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Friend request rejected from: " + friendUsername);
            loadFriendRequests(); // Refresh the friend requests after rejecting
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while rejecting friend request.");
        }
    }

    private void removeFriendRequestFromFile(String friendUsername) throws IOException {
        File inputFile = new File(username + "_request.csv");
        File tempFile = new File(username + "_request_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(friendUsername + "," + username)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        // Delete the original file and rename the temp file to the original file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }


    // Method to get the number of friend requests from CSV file
    private int getFriendRequestCount() {
        File file = new File(username + "_request.csv");
        if (!file.exists()) {
            return 0;
        }

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while reading friend request file.");
        }
        return count;
    }

    // Method to load friend requests and set visibility
    @FXML
    public void loadFriendRequests() {
        clearPanes();
        HBox[] requestHBoxes = {FriendRequestHBOX1, FriendRequestHBOX2, FriendRequestHBOX3, FriendRequestHBOX4, FriendRequestHBOX5, FriendRequestHBOX6, FriendRequestHBOX7, FriendRequestHBOX8, FriendRequestHBOX9, FriendRequestHBOX10, FriendRequestHBOX11, FriendRequestHBOX12, FriendRequestHBOX13, FriendRequestHBOX14, FriendRequestHBOX15};
        Button[] acceptButtons = {FriendRequestAcceptButton1, FriendRequestAcceptButton2, FriendRequestAcceptButton3, FriendRequestAcceptButton4, FriendRequestAcceptButton5, FriendRequestAcceptButton6, FriendRequestAcceptButton7, FriendRequestAcceptButton8, FriendRequestAcceptButton9, FriendRequestAcceptButton10, FriendRequestAcceptButton11, FriendRequestAcceptButton12, FriendRequestAcceptButton13, FriendRequestAcceptButton14, FriendRequestAcceptButton15};
        Button[] rejectButtons = {FriendRequestRejectButton1, FriendRequestRejectButton2, FriendRequestRejectButton3, FriendRequestRejectButton4, FriendRequestRejectButton5, FriendRequestRejectButton6, FriendRequestRejectButton7, FriendRequestRejectButton8, FriendRequestRejectButton9, FriendRequestRejectButton10, FriendRequestRejectButton11, FriendRequestRejectButton12, FriendRequestRejectButton13, FriendRequestRejectButton14, FriendRequestRejectButton15};
        Pane[] usernamePanes = {FriendRequestUsernamePane1, FriendRequestUsernamePane2, FriendRequestUsernamePane3, FriendRequestUsernamePane4, FriendRequestUsernamePane5, FriendRequestUsernamePane6, FriendRequestUsernamePane7, FriendRequestUsernamePane8, FriendRequestUsernamePane9, FriendRequestUsernamePane10, FriendRequestUsernamePane11, FriendRequestUsernamePane12, FriendRequestUsernamePane13, FriendRequestUsernamePane14, FriendRequestUsernamePane15};
        int requestCount = getFriendRequestCount();
        System.out.println(requestCount);
        List<String> friendUsernames = readFriendUsernamesFromFile(); // Assuming this method reads friend usernames from file

        // Set visibility and initialize buttons with friend usernames
        for (int i = 0; i < requestCount; i++) {
            String friendUsername = friendUsernames.get(i);
            Button acceptButton = acceptButtons[i] ; // Assuming acceptButtons is an array of buttons
            acceptButton.setUserData(friendUsername);
            Button rejectButton = rejectButtons[i];
            rejectButton.setUserData(friendUsername);
            // Similarly, set the userData property for rejectButtons or any other buttons
        }
        setFriendRequestVisibility(requestCount);
    }

    private void addFriendToFriendlist(String friendUsername) throws SQLException {
        // Add friendUsername to the current user's friend list
        String currentUserSql = "SELECT Friends FROM user.friendlist WHERE STUDENT_username = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement currentUserStmt = connection.prepareStatement(currentUserSql)) {
            currentUserStmt.setString(1, username);
            ResultSet currentUserResultSet = currentUserStmt.executeQuery();

            if (currentUserResultSet.next()) {
                String currentUserFriendsJson = currentUserResultSet.getString("Friends");
                // Assuming currentUserFriendsJson is a JSON array of friend usernames
                if (!currentUserFriendsJson.contains(friendUsername)) {
                    currentUserFriendsJson = currentUserFriendsJson.substring(0, currentUserFriendsJson.length() - 1) + (currentUserFriendsJson.length() > 2 ? "," : "") + "\"" + friendUsername + "\"]";
                    String currentUserUpdateSql = "UPDATE user.friendlist SET Friends = ? WHERE STUDENT_username = ?";
                    try (PreparedStatement currentUserUpdateStmt = connection.prepareStatement(currentUserUpdateSql)) {
                        currentUserUpdateStmt.setString(1, currentUserFriendsJson);
                        currentUserUpdateStmt.setString(2, username);
                        currentUserUpdateStmt.executeUpdate();
                    }
                }
            } else {
                // Add friendUsername to the current user's friend list if not present
                String currentUserInsertSql = "INSERT INTO user.friendlist (STUDENT_username, Friends) VALUES (?, ?)";
                try (PreparedStatement currentUserInsertStmt = connection.prepareStatement(currentUserInsertSql)) {
                    currentUserInsertStmt.setString(1, username);
                    currentUserInsertStmt.setString(2, "[\"" + friendUsername + "\"]");
                    currentUserInsertStmt.executeUpdate();
                }
            }
        }

        // Add the current user to friendUsername's friend list
        String friendUserSql = "SELECT Friends FROM user.friendlist WHERE STUDENT_username = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement friendUserStmt = connection.prepareStatement(friendUserSql)) {
            friendUserStmt.setString(1, friendUsername);
            ResultSet friendUserResultSet = friendUserStmt.executeQuery();

            if (friendUserResultSet.next()) {
                String friendUserFriendsJson = friendUserResultSet.getString("Friends");
                // Assuming friendUserFriendsJson is a JSON array of friend usernames
                if (!friendUserFriendsJson.contains(username)) {
                    friendUserFriendsJson = friendUserFriendsJson.substring(0, friendUserFriendsJson.length() - 1) + (friendUserFriendsJson.length() > 2 ? "," : "") + "\"" + username + "\"]";
                    String friendUserUpdateSql = "UPDATE user.friendlist SET Friends = ? WHERE STUDENT_username = ?";
                    try (PreparedStatement friendUserUpdateStmt = connection.prepareStatement(friendUserUpdateSql)) {
                        friendUserUpdateStmt.setString(1, friendUserFriendsJson);
                        friendUserUpdateStmt.setString(2, friendUsername);
                        friendUserUpdateStmt.executeUpdate();
                    }
                }
            } else {
                // Add the current user to friendUsername's friend list if not present
                String friendUserInsertSql = "INSERT INTO user.friendlist (STUDENT_username, Friends) VALUES (?, ?)";
                try (PreparedStatement friendUserInsertStmt = connection.prepareStatement(friendUserInsertSql)) {
                    friendUserInsertStmt.setString(1, friendUsername);
                    friendUserInsertStmt.setString(2, "[\"" + username + "\"]");
                    friendUserInsertStmt.executeUpdate();
                }
            }
        }
    }


    private List<String> readFriendUsernamesFromFile() {
        List<String> friendUsernames = new ArrayList<>();
        File file = new File(username + "_request.csv");
        if (!file.exists()) {
            return friendUsernames; // Return an empty list if the file doesn't exist
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format where values are separated by commas
                if (parts.length > 0) {
                    String friendUsername = parts[0]; // Assuming friend username is in the first column
                    friendUsernames.add(friendUsername);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while reading friend request file.");
        }
        return friendUsernames;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearPanes() {
        Pane[] usernamePanes = {FriendRequestUsernamePane1, FriendRequestUsernamePane2, FriendRequestUsernamePane3, FriendRequestUsernamePane4, FriendRequestUsernamePane5, FriendRequestUsernamePane6, FriendRequestUsernamePane7, FriendRequestUsernamePane8, FriendRequestUsernamePane9, FriendRequestUsernamePane10, FriendRequestUsernamePane11, FriendRequestUsernamePane12, FriendRequestUsernamePane13, FriendRequestUsernamePane14, FriendRequestUsernamePane15};
        for (Pane pane : usernamePanes) {
            pane.getChildren().clear();
        }
    }

}
