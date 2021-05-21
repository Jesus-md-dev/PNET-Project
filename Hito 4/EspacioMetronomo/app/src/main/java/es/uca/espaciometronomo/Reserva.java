package es.uca.espaciometronomo;

import java.util.Date;

public class Reserva {
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private Date fecha;
    private String motivo;

    public Reserva(String nombre, String dni, String telefono, String email, Date fecha,
                   String motivo)
    {
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public String getNombre() { return nombre; }
    public String getDNI() { return dni; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public Date getFecha() { return fecha; }
    public String getMotivo() { return motivo; }
}
