
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import util.MySQLConexion;

/* 🚀 Developed by NelsonJGP */
public class daoUsuario {
    Connection con = MySQLConexion.getConexion();
    
    public void crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (tipo, nombre, apellido, usuario, password, correo,numero) VALUES (?,?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, usuario.getTipo());
            st.setString(2, usuario.getNombre());
            st.setString(3, usuario.getApellido());
            st.setString(4, usuario.getUsuario());
            st.setString(5, usuario.getContrasena());
            st.setString(6, usuario.getCorreo());
            st.setString(7, usuario.getNumero());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void crearTrabajador(Trabajador trabajador) {
        String sql = "INSERT INTO trabajador (nombre, servicio_id, fecha_creacion, usuario) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, trabajador.getNombre());
            st.setInt(2, trabajador.getServicioId());
            st.setDate(3, new java.sql.Date(trabajador.getFechaCreacion().getTime()));
            st.setString(4,trabajador.getUsuario());
            // Agrega otros campos según sea necesario
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para iniciar sesión con detalles de autenticación
    public Autenticacion autenticar(String usuario, String contraseña) {
        String sql = "SELECT id FROM usuario WHERE usuario = ?";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, usuario);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                // Usuario existe
                int idUsuario = rs.getInt("id");

                // Comprobar si la contraseña coincide
                if (validarContraseña(idUsuario, contraseña)) {
                    // Obtener los datos adicionales del usuario
                    Usuario usuarioAutenticado = obtenerUsuario(idUsuario);
                    return new Autenticacion(true, true, usuarioAutenticado); // Usuario y contraseña válidos
                } else {
                    return new Autenticacion(true, false, null); // Contraseña incorrecta
                }
            } else {
                return new Autenticacion(false, false, null); // Usuario incorrecto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Error
        return new Autenticacion(false, false, null);
    }
    
    public boolean validarContraseña(int idUsuario, String contraseña) {
        String sql = "SELECT password FROM usuario WHERE id = ?";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, idUsuario);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String contraseñaDB = rs.getString("password");
                return contraseña.equals(contraseñaDB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Error o contraseña incorrecta
    }
    
    public Usuario obtenerUsuario(int idUsuario) {
        String sql = "SELECT id, tipo, nombre, apellido, correo,usuario,numero FROM usuario WHERE id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, idUsuario);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setTipo(rs.getInt("tipo"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setNumero(rs.getString("numero"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Devuelve null si no se encuentra el usuario
    }
    private String obtenerNombreServicio(int idServicio) {
        Connection con = MySQLConexion.getConexion();
        String nombreServicio = "";

        try {
            String sql = "SELECT nombre_servicio FROM servicios WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idServicio);

            ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            nombreServicio = rs.getString("nombre_servicio");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nombreServicio;
    }
        public void iniciartabla(DefaultTableModel model) {
        Connection con = MySQLConexion.getConexion(); 
        try {
            String sql = "SELECT u.id, u.tipo, u.nombre, u.apellido, u.usuario, u.password, u.correo, u.numero, t.servicio_id FROM usuario u LEFT JOIN trabajador t ON u.usuario = t.usuario ORDER BY u.id ASC";
            
            PreparedStatement ps = con.prepareStatement(sql);
            String tipocuenta;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                int id = rs.getInt("id");
                 int tipo = rs.getInt("tipo");
                 if(tipo==2){
                      tipocuenta="CLIENTE";
                 }else if(tipo==3){
                     tipocuenta="TRABAJADOR";
                 }else{
                     tipocuenta="ADMIN";
                 }
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String usuario = rs.getString("usuario");
                String password = rs.getString("password");
                String correo = rs.getString("correo");
                String numero = rs.getString("numero");
                String    value = numero.substring(1);

                 int idServicio = rs.getInt("servicio_id");

                String nombreServicio = obtenerNombreServicio(idServicio);
                if (nombreServicio.equals("")) {
                    nombreServicio="------";
                }else{
                    
                }
                          model.addRow(new Object[]{id,tipocuenta, usuario, nombreServicio,nombre,apellido,correo,password,value});

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
            String sql = "SELECT MAX(id) FROM usuario";
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
      public boolean usuarioverificar(String nombreuser){
      
         try {
           String sql="SELECT * FROM usuario WHERE usuario = ?";
         PreparedStatement ps = con.prepareStatement(sql);
         ps.setString(1, nombreuser);
         ResultSet rs = ps.executeQuery();
         boolean usuarioverificado = rs.next();
        rs.close();
        ps.close();

        return usuarioverificado;
        } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }
      public void  actualizarusuario(int id,int tipo,String nombre,String apellido,String usuario,String contra,String correo,String numero){
                 Connection con = MySQLConexion.getConexion(); 
        String sql = "UPDATE usuario SET tipo=?, nombre=?, apellido=?, usuario=?, password=?, correo=?, numero=? WHERE id=?";
            try {
                PreparedStatement ps=con.prepareStatement(sql);
                    ps.setInt(1, tipo);
                    ps.setString(2, nombre);
                    ps.setString(3, apellido);
                    ps.setString(4, usuario);
                    ps.setString(5, contra);
                    ps.setString(6, correo);
                    String valor="51"+numero;
                    ps.setString(7, valor);
                    ps.setInt(8, id);
                    ps.executeUpdate();
                  JOptionPane.showMessageDialog(null, "USUARIO ACTUALIZADO");

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
          public void eliminarusuario(int id){
        Connection con=MySQLConexion.getConexion();
        try {
            String sql="DELETE from usuario WHERE id=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "USUARIO ELIMINADO");

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
}
