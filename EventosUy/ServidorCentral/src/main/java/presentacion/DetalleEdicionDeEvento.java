package main.java.presentacion;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import main.java.logica.DTCompra;
import main.java.logica.DTEdicion;
import main.java.logica.DTPatrocinio;
// Ajustá a tus paquetes reales:
import main.java.logica.IControladorEvento;

@SuppressWarnings("serial")
public class DetalleEdicionDeEvento extends JInternalFrame {

    private IControladorEvento ICE;
    private String nombreEdicion;
    private String nombreEvento;
    private Principal mainFrame;

    private final JLabel lblNombreVal = new JLabel("-");
    private final JLabel lblOrgVal    = new JLabel("-");
    private final JLabel lblCiudadVal = new JLabel("-");
    private final JLabel lblPaisVal   = new JLabel("-");
    private final JLabel lblEstadoVal = new JLabel("-"); // NUEVO
    private final JLabel lblInicioVal = new JLabel("-");
    private final JLabel lblFinVal    = new JLabel("-");
    private final JLabel lblAltaVal   = new JLabel("-");

    private final JLabel lblTiposRes  = new JLabel("0 ítems");
    private final JLabel lblComprasRes= new JLabel("0 compras");
    private final JLabel lblPatrosRes = new JLabel("0 patrocinios");

    private final JButton btnTipos    = new JButton("Ver tipos…");
    private final JButton btnCompras  = new JButton("Ver compras…");
    private final JButton btnPatros   = new JButton("Ver patrocinios…");

    private List<String> tipos;
    private List<String> compras;
    private List<DTPatrocinio> patrocinios;

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DetalleEdicionDeEvento(Principal main) {
    	super("Detalle de edición " , true, true, true, true);
    	this.nombreEdicion = "";
    	this.nombreEvento= "";
    	this.mainFrame = main;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addDataRow("Nombre:", lblNombreVal, row, gbc);
        row = addDataRow("Organizador:", lblOrgVal, row, gbc);
        row = addDataRow("Ciudad:", lblCiudadVal, row, gbc);
        row = addDataRow("País:", lblPaisVal, row, gbc);
        row = addDataRow("Estado:", lblEstadoVal, row, gbc); // NUEVO
        row = addDataRow("Fecha inicio:", lblInicioVal, row, gbc);
        row = addDataRow("Fecha fin:", lblFinVal, row, gbc);
        row = addDataRow("Fecha alta:", lblAltaVal, row, gbc);

        row = addSummaryRow("Tipos de registro:", lblTiposRes, btnTipos, row, gbc);
        row = addSummaryRow("Compras:", lblComprasRes, btnCompras, row, gbc);
        row = addSummaryRow("Patrocinios:", lblPatrosRes, btnPatros, row, gbc);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnCerrar = new JButton("Cerrar");
        p.add(btnCerrar);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        add(p, gbc);

        btnCerrar.addActionListener(e -> dispose());
        btnTipos.addActionListener(e -> mostrarLista("Tipos de registro", tipos));
        btnCompras.addActionListener(e -> mostrarLista("Compras", compras));
        btnPatros.addActionListener(e -> mostrarLista("Patrocinios", patrocinios));

        pack();
    }

    private int addDataRow(String label, JComponent valueComp, int row, GridBagConstraints gbc) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 1;
        add(valueComp, gbc);
        return row + 1;
    }

    private int addSummaryRow(String label, JLabel summary, JButton viewButton, int row, GridBagConstraints gbc) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(label), gbc);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.add(summary);
        viewButton.setEnabled(false);
        p.add(viewButton);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 1;
        add(p, gbc);
        return row + 1;
    }

    public void cargarDetalle(IControladorEvento ICE, String nombreEvento, String nombreEdicion) {
         this.ICE = ICE;
         this.nombreEdicion = nombreEdicion;
         this.nombreEvento = nombreEvento;
    	 try {
            DTEdicion dt = ICE.getEdicion(nombreEvento, nombreEdicion);
            if (dt == null) {
                JOptionPane.showMessageDialog(this, "No se encontraron datos de la edición.", "Detalle", JOptionPane.WARNING_MESSAGE);
                return;
            }

            lblNombreVal.setText(noVacio(dt.getNombre()));
            lblOrgVal.setText(noVacio(dt.getOrganizador()));
            lblCiudadVal.setText(noVacio(dt.getCiudad()));
            lblPaisVal.setText(noVacio(dt.getPais()));
            lblEstadoVal.setText(noVacio(dt.getEstado().toString())); // NUEVO
            lblInicioVal.setText(dt.getFechaini()!=null ? dt.getFechaini().format(fmt) : "-");
            lblFinVal.setText(dt.getFechaFin()!=null ? dt.getFechaFin().format(fmt) : "-");
            lblAltaVal.setText(dt.getFechaAlta()!=null ? dt.getFechaAlta().format(fmt) : "-");

            tipos = dt.getTiposRegistros();
            compras = new ArrayList<>();
            List<DTCompra> setDTCompras = dt.getDatosCompras();
            if(setDTCompras != null) {
            	for(DTCompra comp : setDTCompras) {
                	this.compras.add(comp.getAsistente() + "   -   " + comp.getCosto() + "   -   " + comp.getTipoRegistro());
                }
            }

            patrocinios = dt.getPatrocinios();

            int nTipos = tipos==null?0:tipos.size();
            int nComp  = compras==null?0:compras.size();
            int nPat   = patrocinios==null?0:patrocinios.size();

            lblTiposRes.setText(nTipos + (nTipos==1 ? " ítem" : " ítems"));
            lblComprasRes.setText(nComp  + (nComp==1  ? " compra" : " compras"));
            lblPatrosRes.setText(nPat   + (nPat==1   ? " patrocinio" : " patrocinios"));

            btnTipos.setEnabled(nTipos>0);
            btnCompras.setEnabled(nComp>0);
            btnPatros.setEnabled(nPat>0);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos: " + ex.getMessage(), "Detalle", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarLista(String titulo, List<?> items) {
        String[] data;
        if (items == null || items.isEmpty()) {
            data = new String[0];
        } else {
            data = items.stream()
                        .map(o -> o==null ? "(null)" : o.toString())
                        .sorted()
                        .toArray(String[]::new);
        }
        JList<String> list = new JList<>(data);
        JScrollPane sp = new JScrollPane(list);
        sp.setPreferredSize(new Dimension(420, 260));
        JOptionPane optionPane = new JOptionPane(sp, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = optionPane.createDialog(this, titulo);
        list.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                	int index = list.locationToIndex(e.getPoint());
                    String treg = list.getModel().getElementAt(index);
                    if(titulo.equals("Tipos de registro")) {
                    	cambio(treg);
                    	dialog.dispose();
                    }
                  }
          });
        dialog.setVisible(true);
    }

    private void cambio (String reg) {
    	mainFrame.DetEdicionTOConsTRegis(nombreEvento, nombreEdicion, reg);
    }

    private String noVacio(String s) {
        return (s == null || s.equals("")) ? "-" : s;
    }
}
