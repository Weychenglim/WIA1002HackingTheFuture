package Assignment;

public class EventInfo {
    private int event_id;
    private String event_title;
    private String event_description;
    private String event_venue;
    private String event_date;
    private String event_time;
    private String event_status;

    public EventInfo(int event_id, String event_title, String event_description, String event_venue, String event_date, String event_time,String event_status){
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_description = event_description;
        this.event_venue = event_venue;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_status = event_status;
    }

    public EventInfo(int event_id, String event_title, String event_description, String event_venue, String event_date, String event_time){
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_description = event_description;
        this.event_venue = event_venue;
        this.event_date = event_date;
        this.event_time = event_time;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public void setEvent_status(String event_status) {this.event_status = event_status;}

    public String getEvent_status() {return event_status;}
}
