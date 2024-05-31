package Assignment;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddDiscussionBoard{

    private ScrollPane discussionBoardDisplay;
    private TextField discussionBoardTextField;
    private Button discussionBoardSendButton;
    private String username;
    private String role;
    private final String filePath = "C:\\Users\\User\\Documents\\SEM2_WIA1002\\WIA1002_HackingTheFuture\\Assignment\\src\\main\\java\\Assignment\\Discussion.txt";

    // Initialize varaible
    public AddDiscussionBoard(ScrollPane discussionBoardDisplay, TextField discussionBoardTextField, Button discussionBoardSendButton, String username, String role) {
        this.discussionBoardDisplay = discussionBoardDisplay;
        this.discussionBoardTextField = discussionBoardTextField;
        this.discussionBoardSendButton = discussionBoardSendButton;
        this.username = username;
        this.role = role;
        System.out.println(username+role);
    }

    // Display Discussion from txt file
    public void displayDiscussion() {
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            Text text = new Text(content.toString());
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(200);
            anchorPane.setPrefHeight(200);
            anchorPane.getChildren().add(text);
            discussionBoardDisplay.setContent(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read from txt file and display it in the format of username(role) : message
    public void postMessage() {
        String message = discussionBoardTextField.getText();
        if (!message.isEmpty()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                writer.write("\n" + username + " (" + role + "): " + message);
                writer.close();
                displayDiscussion();
                discussionBoardTextField.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
