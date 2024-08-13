
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Trabajador;
import util.MySQLConexion;

/* 🚀 Developed by NelsonJGP */
public class daoTrabajador {
    Connection con = MySQLConexion.getConexion();
    public List<Trabajador> obtenerTrabajadores() {
        List<Trabajador> trabajadores = new ArrayList<>();

         // Reemplaza MySQLConexion con tu clase de conexión a la base de datos

        String sql = "SELECT id, nombre, servicio_id, fecha_creacion, usuario FROM trabajador";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int servicioId = rs.getInt("servicio_id");
                Date fechaCreacion = rs.getDate("fecha_creacion");
                String usuario = rs.getString("usuario");
                    
                Trabajador trabajador = new Trabajador(nombre, servicioId, fechaCreacion, usuario);
                trabajador.setId(id);
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trabajadores;
    }

    public List<Trabajador> obtenerTrabajadoresPorServicio(int idServicio) {
        List<Trabajador> trabajadores = new ArrayList<>();

        String sql = "SELECT id, nombre, servicio_id, fecha_creacion, usuario FROM trabajador WHERE servicio_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, idServicio);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Date fechaCreacion = rs.getDate("fecha_creacion");
                String usuario = rs.getString("usuario");

                Trabajador trabajador = new Trabajador(nombre, idServicio, fechaCreacion, usuario);
                trabajador.setId(id);
                trabajadores.add(trabajador);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trabajadores;
    }
    
    public double obtenerPromedioCalificaciones(int idTrabajador) {
        double promedio = 0.0;
        int totalCalificaciones = 0;

        String sql = "SELECT calificacion FROM calificacion WHERE trabajador_id = ?";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, idTrabajador);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int calificacion = rs.getInt("calificacion");
                promedio += calificacion;
                totalCalificaciones++;
            }

            if (totalCalificaciones > 0) {
                promedio /= totalCalificaciones;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promedio;
    }
    public void actualizartrabajador(int id,String nombre,int servicio_id,Date fecha_creacion,String usuario){
               Connection con = MySQLConexion.getConexion(); 
        String sql = "UPDATE trabajador SET nombre=?, servicio_id=?,fecha_creacion=?,usuario=? WHERE id=?"; 
        
                   try {
                PreparedStatement ps=con.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.setInt(2, servicio_id);
                    ps.setDate(3, fecha_creacion);
                    ps.setString(4, usuario);
                    ps.setInt(5, id);
                    ps.executeUpdate();

            } catch (Exception e) {
                System.out.println("error"+e);
          } finally {
            try {
                if (con != null) {
                    con.close(); // Cierra la conexión en el bloque finally
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 
        
        
    }
}
