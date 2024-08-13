package vista;

import com.formdev.flatlaf.FlatLightLaf;
import dao.daoServicios;
import dao.daoTrabajador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import modelo.Servicios;
import modelo.Trabajador;

/**
 *
 * @author Nelson
 */
public class clienteOfertas extends javax.swing.JPanel {
        daoTrabajador daoTra = new daoTrabajador();
        daoServicios daoServ = new daoServicios();
    /**
     * Creates new form clienteServicios
     */
    public clienteOfertas() {
        FlatLightLaf.setup();
        initComponents();
        mostrarTrabajadoresEnScrollPanel("General");
        comboServiciosListar();
    }

    public void comboServiciosListar(){
        List<Servicios> listaServicios = daoServ.obtenerServicios();
        for (Servicios servicio : listaServicios) {
            cmbServicios.addItem(servicio.getNombre_servicio());
        }
    }
    
    public void mostrarTrabajadoresEnScrollPanel(String Opcion) {
        // Limpiar el panel antes de generar nuevos componentes
        contentServ.removeAll();
        contentServ.revalidate();
        contentServ.repaint();
        
        List<Trabajador> trabajadores;
        if (Opcion.equals("General")) {
            trabajadores = daoTra.obtenerTrabajadores();
        } else {
            trabajadores = daoTra.obtenerTrabajadoresPorServicio(daoServ.obtenerIdServicioPorNombre(Opcion));
        }

        int numColumnas = 3;
        int velocidadScroll = 16;

        // Agrega un espaciado entre paneles en el GridLayout
        int hgap = 20; // Espaciado horizontal
        int vgap = 20; // Espaciado vertical
        contentServ.setLayout(new GridLayout(0, numColumnas, hgap, vgap));
        
        for (Trabajador trabajador : trabajadores) {
            DropShadowPanel trabPanel = new DropShadowPanel(5); // Cambia el valor según el tamaño de sombra que desees
            trabPanel.setLayout(new BorderLayout());
            trabPanel.setPreferredSize(new Dimension(200, 320));

            DropShadowPanel cuadroPanel = new DropShadowPanel(5); // Cambia el valor según el tamaño de sombra que desees
            cuadroPanel.setLayout(new BoxLayout(cuadroPanel, BoxLayout.Y_AXIS));
            cuadroPanel.setPreferredSize(new Dimension(200, 320));

            cuadroPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

            JPanel contenidoPanel = new JPanel();
            contenidoPanel.setLayout(new BoxLayout(contenidoPanel, BoxLayout.Y_AXIS));
            contenidoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Mostrar el título del trabajador (nombre)
            JLabel nameLabel = new JLabel(trabajador.getNombre());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            contenidoPanel.add(nameLabel);

            contenidoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Mostrar la imagen del trabajador
            String imagePath = "/images/user/trabajadores/" + trabajador.getUsuario() + ".png";
            URL imageURL = getClass().getResource(imagePath);
            ImageIcon icon;

            if (imageURL != null) {
                icon = new ImageIcon(imageURL);
            } else {
                // Si la imagen no existe, utiliza la imagen predeterminada.
                icon = new ImageIcon(getClass().getResource("/images/user/trabajadores/defaultTrabajador.png"));
            }

            Image image = icon.getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenidoPanel.add(imageLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mostrar el ID del servicio y la fecha de creación de la cuenta del trabajador

        infoPanel.add(new JLabel("Servicio: " + daoServ.obtenerNombreServicioPorId(trabajador.getServicioId())));
        infoPanel.add(new JLabel("Fecha de Creación: " + trabajador.getFechaCreacion()));

        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Mostrar la calificación y el botón "Contactar" y "Más Detalles"
        double promedioCalificaciones = daoTra.obtenerPromedioCalificaciones(trabajador.getId());
        infoPanel.add(new JLabel("Calificación Promedio: " + promedioCalificaciones));

        contenidoPanel.add(infoPanel);

        contenidoPanel.add(Box.createRigidArea(new Dimension(0, 10)));


            JButton contactarButton = new JButton("Contactar");
            JButton detallesButton = new JButton("Más Detalles");

            // Agregar ActionListeners para manejar los clics en los botones
            contactarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lógica para contactar al trabajador
                }
            });

            detallesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lógica para ver más detalles del trabajador
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(contactarButton);
            buttonPanel.add(detallesButton);

            contenidoPanel.add(buttonPanel);

            JPanel descPanel = new JPanel();
            descPanel.setLayout(new BorderLayout());

            contenidoPanel.add(descPanel);

            cuadroPanel.add(Box.createVerticalGlue());
            cuadroPanel.add(contenidoPanel);

            trabPanel.add(Box.createHorizontalGlue(), BorderLayout.CENTER);
            trabPanel.add(cuadroPanel, BorderLayout.CENTER);

            contentServ.add(trabPanel);
        }

        contentServ.revalidate();
        Scroll.getVerticalScrollBar().setUnitIncrement(velocidadScroll);
    }
    
    public class DropShadowPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public int pixels;

        public DropShadowPanel(int pix) {
            this.pixels = pix;
            Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
            this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
            this.setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            int shade = 0;
            int topOpacity = 80;
            for (int i = 0; i < pixels; i++) {
                g.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
                g.drawRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1));
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Scroll = new guiRecursos.GuiJScrollPane();
        contentServ = new javax.swing.JPanel();
        cmbServicios = new javax.swing.JComboBox<>();

        Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Ofertas");
        Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 400, 40));

        Scroll.setBorder(null);

        javax.swing.GroupLayout contentServLayout = new javax.swing.GroupLayout(contentServ);
        contentServ.setLayout(contentServLayout);
        contentServLayout.setHorizontalGroup(
            contentServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 890, Short.MAX_VALUE)
        );
        contentServLayout.setVerticalGroup(
            contentServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );

        Scroll.setViewportView(contentServ);

        Panel.add(Scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 890, 510));

        cmbServicios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "General" }));
        cmbServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbServiciosActionPerformed(evt);
            }
        });
        Panel.add(cmbServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 200, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbServiciosActionPerformed
        mostrarTrabajadoresEnScrollPanel(cmbServicios.getSelectedItem().toString());
    }//GEN-LAST:event_cmbServiciosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private guiRecursos.GuiJScrollPane Scroll;
    private javax.swing.JComboBox<String> cmbServicios;
    private javax.swing.JPanel contentServ;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
