package Assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

/**
 *
 * @author kianx
 */
public class SixteenDashboard extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws Exception {
        // Specify the file path to your FXML file
        String fxmlFilePath = "C:\\Users\\User\\Documents\\SEM2_WIA1002\\WIA1002_HackingTheFuture\\Assignment\\src\\main\\java\\Assignment\\FXMLDocument.fxml";

        // Load the FXML file using the specified file path
        File fxmlFile = new File(fxmlFilePath);
        if (!fxmlFile.exists()) {
            System.err.println("FXML file not found: " + fxmlFilePath);
            System.exit(1);
        }

        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getSceneX() - x);
            stage.setY(event.getSceneY() - y);

            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch();
    }

    public static void launchFX(String username, String role) throws Exception {
        DashBoardController obj = new DashBoardController(username, role);
        launch();
    }



}
