package Assignment;

import java.util.*;

class Graph {
    private Map<String, List<String>> adjList = new HashMap<>();
    private Map<String, Point> coordinates = new HashMap<>();

    public void addStudent(String student) {
        adjList.putIfAbsent(student, new ArrayList<>());
    }

    public void addFriendship(String student1, String student2) {
        adjList.get(student1).add(student2);
        adjList.get(student2).add(student1);
    }

    public Map<String, List<String>> getAdjList() {
        return adjList;
    }

    public Map<String, Point> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String student, double x, double y) {
        coordinates.put(student, new Point(x, y));
    }
}

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
