
package modelo;

/* 🚀 Developed by NelsonJGP */
public class DetalleTrabajador {
    private int IdTrabajador;
    private String NombreTrabajador;
    private int Calificacion;

    public DetalleTrabajador() {
    }

    public DetalleTrabajador(int IdTrabajador, String NombreTrabajador, int Calificacion) {
        this.IdTrabajador = IdTrabajador;
        this.NombreTrabajador = NombreTrabajador;
        this.Calificacion = Calificacion;
    }

    public int getIdTrabajador() {
        return IdTrabajador;
    }

    public void setIdTrabajador(int IdTrabajador) {
        this.IdTrabajador = IdTrabajador;
    }

    public String getNombreTrabajador() {
        return NombreTrabajador;
    }

    public void setNombreTrabajador(String NombreTrabajador) {
        this.NombreTrabajador = NombreTrabajador;
    }

    public int getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(int Calificacion) {
        this.Calificacion = Calificacion;
    }

    
}
