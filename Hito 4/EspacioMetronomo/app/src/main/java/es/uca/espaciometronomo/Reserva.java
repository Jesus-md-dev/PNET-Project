package es.uca.espaciometronomo;

import java.util.Calendar;

public class Reserva {
    private int id;
    private String nombre;
    private Calendar fecha;

    public Reserva(int id, String nombre, Calendar fecha){
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
}
