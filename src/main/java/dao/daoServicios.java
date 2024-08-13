
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DetalleTrabajador;
import modelo.Servicios;
import util.MySQLConexion;

/* 🚀 Developed by NelsonJGP */
public class daoServicios {
    Connection con = MySQLConexion.getConexion();
    public List<Servicios> obtenerServicios() {
        List<Servicios> servicios = new ArrayList<>();

        // Reemplaza MySQLConexion con tu clase de conexión a la base de datos

        String sql = "SELECT id, nombre_servicio, descripcion FROM servicios";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String nombreServicio = rs.getString("nombre_servicio");
                String descripcion = rs.getString("descripcion");
                Servicios sv = new Servicios(nombreServicio, descripcion);
                sv.setId(Integer.parseInt(id));
                
                servicios.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicios;
    }
        
    public boolean CrearServicio(Servicios servicio) {
        // Reemplaza MySQLConexion con tu clase de conexión a la base de datos

        String sql = "INSERT INTO servicios (nombre_servicio, descripcion) VALUES (?, ?)";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, servicio.getNombre_servicio());
            st.setString(2, servicio.getDescripcion());
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                return true; // El servicio se creó con éxito
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Hubo un error al crear el servicio
    }
    public int contarTrabajadoresPorServicio(int servicioId) {
        // Reemplaza MySQLConexion con tu clase de conexión a la base de datos

        String sql = "SELECT COUNT(*) FROM trabajador WHERE servicio_id = ?";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, servicioId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1); // Devuelve la cantidad de trabajadores asociados al servicio
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Si hubo un error o no se encontraron trabajadores, devuelve 0
    }
    
    public List<DetalleTrabajador> obtenerDetallesTrabajadoresPorServicio(int idServicio) {
        List<DetalleTrabajador> detalles = new ArrayList<>();

        String sql = "SELECT t.id AS trabajador_id, t.nombre AS nombre_trabajador, c.calificacion " +
                     "FROM trabajador t " +
                     "LEFT JOIN calificacion c ON t.id = c.trabajador_id " +
                     "WHERE t.servicio_id = ?";

        try (             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, idServicio);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int trabajadorId = rs.getInt("trabajador_id");
                String nombreTrabajador = rs.getString("nombre_trabajador");
                int calificacion = rs.getInt("calificacion");

                DetalleTrabajador detalle = new DetalleTrabajador(trabajadorId, nombreTrabajador, calificacion);
                detalles.add(detalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }
    
    public String obtenerNombreServicioPorId(int servicioId) {
        String nombreServicio = null;

        String sql = "SELECT nombre_servicio FROM servicios WHERE id = ?";

        try (             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, servicioId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                nombreServicio = rs.getString("nombre_servicio");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreServicio;
    }
    
    public int obtenerIdServicioPorNombre(String nombreServicio) {
        int servicioId = -1; // Se puede usar un valor predeterminado según tus necesidades

        String sql = "SELECT id FROM servicios WHERE nombre_servicio = ?";

        try (             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, nombreServicio);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                servicioId = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicioId;
    }

    
    public void iniciartabla(DefaultTableModel model) {
        Connection con = MySQLConexion.getConexion(); 
        try {
            String sql = "SELECT * FROM servicios"; 
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String servicio = rs.getString("nombre_servicio");
                String descripcion = rs.getString("descripcion");

                model.addRow(new Object[]{id, servicio, descripcion});
            }
        } catch (Exception e) {
            e.printStackTrace(); 
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
    public int verid() {
            Connection con = MySQLConexion.getConexion(); 

        try {
            String sql = "SELECT MAX(id) FROM servicios";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int maxID = rs.getInt(1);
                int nextID = maxID + 1;
                return nextID;
            }
        } catch (Exception e) {
            e.printStackTrace();

           } finally {
            try {
                if (con != null) {
                    con.close(); // Cierra la conexión en el bloque finally
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
        public boolean validarservicio(String servicio){
            Connection con = MySQLConexion.getConexion(); 

            try {
                String sql="Select nombre_servicio FROM servicios where nombre_servicio=?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1, servicio);
                ResultSet rs=ps.executeQuery();
                boolean validar=rs.next();
                return validar;
            } catch (Exception e) {
                return false;
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
    public void actualizarservicio(int id,String servicio, String descripcion) {
           Connection con = MySQLConexion.getConexion(); 
        String sql = "UPDATE servicios SET nombre_servicio=?, descripcion=? WHERE id=?";
            try {
                PreparedStatement ps=con.prepareStatement(sql);
                    ps.setString(1, servicio);
                    ps.setString(2, descripcion);
                    ps.setInt(3, id);
                    ps.executeUpdate();
                  JOptionPane.showMessageDialog(null, "SERVICIO ACTUALIZADO");

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
    public void eliminarservicio(int id){
        Connection con=MySQLConexion.getConexion();
        try {
            String sql="DELETE from servicios WHERE id=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "SERVICIO ELIMINADO");

        } catch (Exception e) {

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
    public int idservicio(String nombreServicio) {
    try {
        String sql = "SELECT id FROM servicios WHERE nombre_servicio=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombreServicio);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            
            int id = rs.getInt("id");
            return id;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
   
    return -1;
}
}
