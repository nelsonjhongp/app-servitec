
package modelo;

import java.sql.Date;

/* 🚀 Developed by NelsonJGP */
public class Trabajador {
    private int id;
    private String nombre;
    private int servicioId;
    private Date fechaCreacion;
    private String usuario;
    
    public Trabajador() {
    }

    public Trabajador(String nombre, int servicioId, Date fechaCreacion, String usuario) {
        this.nombre = nombre;
        this.servicioId = servicioId;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
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

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
}
