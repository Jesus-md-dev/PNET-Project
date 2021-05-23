package es.uca.espaciometronomo;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Calendar;

import static android.content.res.Resources.*;

public class Book implements Serializable {
    private String name, dni, phone, email, reason;
    private Calendar date, startHour, endHour;
    private int roomType;

    public Book(String name, String dni, String phone, String email,
                Calendar date, Calendar startHour, Calendar endHour,
                String reason, int roomType){
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

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

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Calendar getStartHour() { return startHour; }

    public void setStartHour(Calendar startHour) { this.startHour = startHour; }

    public Calendar getEndHour() { return endHour; }

    public void setEndHour(Calendar endHour) { this.endHour = endHour; }

    public int getRoomType() { return roomType; }

    public void setRoomType(int roomType) { this.roomType = roomType; }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", reason='" + reason + '\'' +
                ", date=" + BookAdapter.calendarDateToString(date) +
                ", startHour=" + BookAdapter.calendarHourToString(startHour) +
                ", endHour=" + BookAdapter.calendarHourToString(endHour) +
                ", roomType=" + roomType +
                '}';
    }
}
