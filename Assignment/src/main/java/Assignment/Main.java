package Assignment;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Create a new undirected graph
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Add vertices (persons) to the graph
        String person1 = "Alice";
        String person2 = "Bob";
        String person3 = "Charlie";
        String person4 = "David";
        String person5 = "Eve";

        graph.addVertex(person1);
        graph.addVertex(person2);
        graph.addVertex(person3);
        graph.addVertex(person4);
        graph.addVertex(person5);

        // Add edges (friendships) to the graph
        graph.addEdge(person1, person2);
        graph.addEdge(person2, person3);
        graph.addEdge(person3, person4);
        graph.addEdge(person4, person5);
        graph.addEdge(person5, person1);

        // Visualize the graph
        visualizeGraph(graph);
    }

    private static void visualizeGraph(Graph<String, DefaultEdge> graph) {
        // Create a JComponent to display the graph
        JComponent jGraphTComponent = new JGraphTComponent<>(graph);

        // Create a JFrame to display the graph
        JFrame frame = new JFrame();
        frame.getContentPane().add(jGraphTComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    static class JGraphTComponent<V, E> extends JComponent {
        private Graph<V, E> graph;
        private Map<V, Point> vertexPositions;

        public JGraphTComponent(Graph<V, E> graph) {
            this.graph = graph;
            this.vertexPositions = new HashMap<>();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Layout vertices
            layoutVertices();

            // Draw edges
            for (E edge : graph.edgeSet()) {
                V source = graph.getEdgeSource(edge);
                V target = graph.getEdgeTarget(edge);
                Point sourcePoint = vertexPositions.get(source);
                Point targetPoint = vertexPositions.get(target);
                g.drawLine((int) sourcePoint.x, (int) sourcePoint.y, (int) targetPoint.x, (int) targetPoint.y);
            }

            // Draw vertices
            for (V vertex : graph.vertexSet()) {
                Point vertexPoint = vertexPositions.get(vertex);
                g.setColor(Color.BLACK);
                g.fillOval((int) (vertexPoint.x - 10), (int) (vertexPoint.y - 10), 20, 20);
                g.setColor(Color.RED);
                g.drawString(vertex.toString(), (int) (vertexPoint.x - 5), (int) (vertexPoint.y + 5));
            }
        }

        private void layoutVertices() {
            int vertexCount = graph.vertexSet().size();
            int radius = 150;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            double angleIncrement = 2 * Math.PI / vertexCount;
            double angle = 0;

            for (V vertex : graph.vertexSet()) {
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));
                vertexPositions.put(vertex, new Point(x, y));
                angle += angleIncrement;
            }
        }
    }
}
