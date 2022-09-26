package ua.com.foxminded.andriysalnikov.university.dto;

public class EventDTO {

    private final String id;
    private final String dayOfEvent;
    private final String startTime;
    private final String endTime;
    private final String classRoom;
    private final String course;

    public EventDTO(String id, String dayOfEvent, String startTime, String endTime,
                    String classRoom, String course) {
        this.id = id;
        this.dayOfEvent = dayOfEvent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classRoom = classRoom;
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public String getDayOfEvent() {
        return dayOfEvent;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "id='" + id + '\'' +
                ", dayOfEvent='" + dayOfEvent + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
