package Assignment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class FriendshipVisualizer extends Application {

    @Override
    public void start(Stage primaryStage) {
        Graph graph = createGraph();
        Pane root = new Pane();
        drawGraph(root, graph);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Friendship Assignment.Graph Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Graph createGraph() {
        Graph graph = new Graph();
        // Adding students with coordinates for visualization 
        graph.addStudent("Alice", 100, 150);
        graph.addStudent("Bob", 300, 150);
        graph.addStudent("Charlie", 500, 150);
        graph.addStudent("David", 200, 350);
        graph.addStudent("Eve", 400, 350);

        graph.addFriendship("Alice", "Bob");
        graph.addFriendship("Bob", "Charlie");
        graph.addFriendship("Charlie", "David");
        graph.addFriendship("David", "Eve");
        graph.addFriendship("Eve", "Alice");

        return graph;
    }

    private void drawGraph(Pane root, Graph graph) {
        for (String student : graph.getAdjList().keySet()) {
            List<String> friends = graph.getAdjList().get(student);
            Point p1 = graph.getCoordinates().get(student);
            for (String friend : friends) {
                Point p2 = graph.getCoordinates().get(friend);
                Line line = new Line(p1.x, p1.y, p2.x, p2.y);
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(2);
                root.getChildren().add(line); // Lines first so they're below circles 
            }
        }

        for (String student : graph.getAdjList().keySet()) {
            Point p = graph.getCoordinates().get(student);
            Circle circle = new Circle(p.x, p.y, 20, Color.LIGHTBLUE);
            circle.setStroke(Color.DARKBLUE);
            circle.setStrokeWidth(2);
            root.getChildren().add(circle);

            Text text = new Text(p.x - 10, p.y + 4, student);
            root.getChildren().add(text);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}