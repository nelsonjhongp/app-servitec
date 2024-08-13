
package modelo;

import modelo.Usuario;

/* 🚀 Developed by NelsonJGP */
public class Autenticacion {
    private boolean usuarioValido;
    private boolean contraseñaValida;
    private Usuario usuario; // El objeto Usuario autenticado

    public Autenticacion(boolean usuarioValido, boolean contraseñaValida, Usuario usuario) {
        this.usuarioValido = usuarioValido;
        this.contraseñaValida = contraseñaValida;
        this.usuario = usuario;
    }

    public boolean isUsuarioValido() {
        return usuarioValido;
    }

    public boolean isContraseñaValida() {
        return contraseñaValida;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}