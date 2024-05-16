package Assignment;

public class LeaderboardInfo {
    private int ranking;
    private String username;
    private int points;
    private String time;

    public LeaderboardInfo(int ranking, String username, int points, String time) {
        this.ranking = ranking;
        this.username = username;
        this.points = points;
        this.time = time;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
