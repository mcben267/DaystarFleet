package Model;

public class BookingDetails {

    String booking_id, name, mobile, email, department, note, booking_date, timestamp;

    public BookingDetails(String booking_id, String name, String mobile, String email,
                          String department, String note, String date, String timestamp) {

        this.booking_id = booking_id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.department = department;
        this.note = note;
        this.booking_date = date;
        this.timestamp = timestamp;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getNote() {
        return note;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
