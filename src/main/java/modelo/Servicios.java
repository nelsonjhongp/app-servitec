
package modelo;

/* 🚀 Developed by NelsonJGP */
public class Servicios {
    private int id;
    private String nombre_servicio;
    private String descripcion;

    public Servicios(String nombre_servicio, String descripcion) {
        this.nombre_servicio = nombre_servicio;
        this.descripcion = descripcion;
    }

    public Servicios() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) {
        this.nombre_servicio = nombre_servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
