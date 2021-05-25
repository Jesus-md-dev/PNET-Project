package es.uca.espaciometronomo;

import java.util.Calendar;

public class Booking {
    private int id;
    private String name;
    private Calendar date;

    public Booking(int id, String name, Calendar date){
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
