package main.java.presentacion;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.IControladorEvento;

@SuppressWarnings("serial")
public class ConsultaEdicionDeEvento extends JInternalFrame {

    private final IControladorEvento ICE;
    private final Principal mainF;

    private final JComboBox<String> comboEvento = new JComboBox<>();
    private final JComboBox<String> comboEdicion = new JComboBox<>();

  //  private boolean cargandoEventos = false;
  //  private boolean cargandoEdiciones = false;

    public ConsultaEdicionDeEvento(IControladorEvento ICE, Principal main) {
        super("Eventos y Ediciones", true, true, true, true);
        this.ICE = ICE;
        this.mainF = main;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Evento
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Evento:"), gbc);

        comboEvento.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        add(comboEvento, gbc);

        // Edición
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Edición:"), gbc);

        comboEdicion.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        add(comboEdicion, gbc);

        // Botón Cerrar
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnCerrar = new JButton("Cerrar");
        p.add(btnCerrar);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        add(p, gbc);

        cargarEventos();

        // Listeners
        comboEvento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ev = (String) comboEvento.getSelectedItem();
	        	if (ev != null) {
	               if (ev != null && !ev.isEmpty()) {
					cargarEdiciones(ev);
				   }
	               }
			}
		});

        comboEdicion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ed = (String) comboEdicion.getSelectedItem();
	            if (ed != null && !ed.isEmpty()) {
					abrirDetalleSeleccionado(ed);
				}
			}
		});

        btnCerrar.addActionListener(e -> {
        	comboEvento.removeAllItems();
        	this.setVisible(false);
        });


        comboEdicion.setEnabled(false);


       /*
        pack();
        setVisible(true);*/
    }

    public void cargarEventos() {
        try {

        	comboEvento.removeAllItems();

            Set<DTEvento> eventos = ICE.listarEventos();

           if (eventos == null || eventos.isEmpty()) {
                comboEdicion.removeAllItems();
                comboEdicion.setEnabled(false);
                return;
            }
            comboEvento.addItem("");
			List<DTEvento> listaEventosOrdenada = eventos.stream().sorted(Comparator.comparing(DTEvento::getNombre)).collect(Collectors.toList());
            for (DTEvento dte : listaEventosOrdenada) {
                if (dte != null && dte.getNombre() != null) {
                    comboEvento.addItem(dte.getNombre());
                }
            }

                 comboEvento.setSelectedIndex(0);


        } catch (Exception ex) {
            System.err.println("Error listar eventos: " + ex.getMessage());
          }

        // Cargar ediciones del seleccionado (si hay)
      //  String seleccionado = (String) comboEvento.getSelectedItem();
      //  cargarEdiciones(seleccionado);
    }

    private void cargarEdiciones(String nombreEvento) {
        try {
         //   cargandoEdiciones = true;
            comboEdicion.removeAllItems();

            if (nombreEvento == null || nombreEvento.equals("")) {
                comboEdicion.setEnabled(false);
                return;
            }

            Set<DTEdicion> ediciones = ICE.listarEdiciones(nombreEvento);

            if (ediciones == null || ediciones.isEmpty()) {
                comboEdicion.setEnabled(false);
                return;
            }
            comboEdicion.addItem("");
			List<DTEdicion> eds = ediciones.stream().sorted(Comparator.comparing(DTEdicion::getNombre)).collect(Collectors.toList());
            for (DTEdicion dte : eds) {
                if (dte != null && dte.getNombre() != null) {
                    comboEdicion.addItem(dte.getNombre());
                }
            }

            boolean hayItems = comboEdicion.getItemCount() > 1;
            comboEdicion.setEnabled(hayItems);
            if (hayItems) {
				comboEdicion.setSelectedIndex(0);
			}

        } catch (Exception ex) {
        	System.err.println("Error listar ediciones: " + ex.getMessage());
            //comboEdicion.setEnabled(false);
        }


    }

    private void abrirDetalleSeleccionado(String edi) {
        String ev = (String) comboEvento.getSelectedItem();
        if (edi == null || edi.equals("") || ev == null || ev.equals("")) {
			return;
		}
        this.mainF.DetalleEdicion(ev, edi);
        this.setVisible(false);


    }


}
