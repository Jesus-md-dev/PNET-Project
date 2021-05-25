package es.uca.espaciometronomo;


import java.io.Serializable;
import java.util.Calendar;

public class Booking implements Serializable {
    private String id, name, dni, phone, email;
    private Calendar date, startHour, endHour;
    private int roomType, reason;

    public Booking() {}

    public Booking(String id, String name, String dni, String phone, String email,
                   Calendar date, Calendar startHour, Calendar endHour,
                   int reason, int roomType){
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.phone = phone;
        this.email = email;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.reason = reason;
        this.roomType = roomType;
    }

    public Booking(String id, String name, String dni, String phone, String email,
                   Calendar date, Calendar startHour, Calendar endHour,
                   String reason, String roomType){
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.phone = phone;
        this.email = email;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.reason = BookingAdapter.reasonStringToInt(reason);
        this.roomType = BookingAdapter.roomTypeStringToInt(roomType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) { this.dni = dni; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDateString() {
        return BookingAdapter.dateCalendarToString(date);
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = BookingAdapter.dateStringToCalendar(date);
    }

    public Calendar getStartHour() { return startHour; }

    public String getStartHourString() { return BookingAdapter.hourCalendarToString(startHour); }

    public void setStartHour(Calendar startHour) { this.startHour = startHour; }

    public void setStartHour(String startHour) {
        this.startHour = BookingAdapter.hourStringToCalendar(startHour);
    }

    public Calendar getEndHour() { return endHour; }

    public String getEndHourString() { return BookingAdapter.hourCalendarToString(endHour); }

    public void setEndHour(Calendar endHour) { this.endHour = endHour; }

    public void setEndHour(String endHour) {
        this.endHour = BookingAdapter.hourStringToCalendar(endHour);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReason() {
        return reason;
    }

    public String getRoomTypeString() { return BookingAdapter.roomTypeIntToString(roomType); }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public void setReason(String reason) {
        this.reason = BookingAdapter.reasonStringToInt(reason);
    }

    public String getReasonString() { return BookingAdapter.reasonIntToString(reason); }

    public int getRoomType() { return roomType; }

    public void setRoomType(int roomType) { this.roomType = roomType; }

    public void setRoomType(String roomType) {
        this.roomType = BookingAdapter.roomTypeStringToInt(roomType);
    }
}
