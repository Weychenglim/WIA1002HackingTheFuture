package Assignment;

public class BookingInfo {
    private String id;
    private String destination;
    private String distance;
    private String slots;

    public BookingInfo(String id, String destination, String distance, String slots) {
        this.id = id;
        this.destination = destination;
        this.distance = distance;
        this.slots = slots;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
