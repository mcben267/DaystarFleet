package Model;

public class TaskDetails {
    int taskId;
    String title, status, details, timestamp;

    public TaskDetails(int taskId, String title, String status, String details, String timestamp) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.details = details;
        this.timestamp = timestamp;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
