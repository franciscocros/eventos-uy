package main.java.presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.EEstadoEdicion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;

public class AceptarRechazarEdicion extends JInternalFrame {
	private JComboBox<String> EvComboBox;
	private JComboBox<String> EdComboBox;
	private IControladorEvento ICE;
	private Set<DTEvento> eventos;
	private Set<DTEdicion> ediciones;

	public AceptarRechazarEdicion() {
		Fabrica F = Fabrica.getInstance();
		ICE = F.getIControladorEvento();
		eventos = new HashSet<>();
		ediciones = new HashSet<>();

		this.setTitle("Aceptar/rechazar Edicion");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 301, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JLabel lblEventos = new JLabel("Eventos:");
        GridBagConstraints gbc_lblEventos = new GridBagConstraints();
        gbc_lblEventos.anchor = GridBagConstraints.WEST;
        gbc_lblEventos.insets = new Insets(50, 30, 20, 5);
        gbc_lblEventos.gridx = 1;
        gbc_lblEventos.gridy = 2;
        getContentPane().add(lblEventos, gbc_lblEventos);

        EvComboBox = new JComboBox<>();
        EvComboBox.addItemListener(new ItemListener() {
        	@Override
			public void itemStateChanged(ItemEvent e) {
        		if(EvComboBox !=null &&  !EvComboBox.getSelectedItem().equals("seleccionar")) {
        			cargarEdiciones();
        		}
        	}
        });
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(50, 0, 20, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 2;
        getContentPane().add(EvComboBox, gbc_comboBox);

        JLabel lblEdiciones = new JLabel("Ediciones:");
        GridBagConstraints gbc_lblEdiciones = new GridBagConstraints();
        gbc_lblEdiciones.anchor = GridBagConstraints.WEST;
        gbc_lblEdiciones.insets = new Insets(0, 30, 5, 5);
        gbc_lblEdiciones.gridx = 1;
        gbc_lblEdiciones.gridy = 3;
        getContentPane().add(lblEdiciones, gbc_lblEdiciones);

        EdComboBox = new JComboBox<>();
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 2;
        gbc_comboBox_1.gridy = 3;
        getContentPane().add(EdComboBox, gbc_comboBox_1);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 0, 5);
        gbc_panel.anchor = GridBagConstraints.SOUTHEAST;
        gbc_panel.gridx = 2;
        gbc_panel.gridy = 4;
        getContentPane().add(panel, gbc_panel);

        JButton rechazarbtn = new JButton("Rechazar");
        rechazarbtn.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if(DatosSeleccionados()) {
            		ICE.setEstadoEdicion(EdComboBox.getSelectedItem().toString(),EEstadoEdicion.RECHAZADA);
            		cargarEdiciones();
        		}
        	}
        });
        panel.add(rechazarbtn);

        JButton confirmarbtn = new JButton("Confirmar");
        confirmarbtn.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if(DatosSeleccionados()) {
            		ICE.setEstadoEdicion(EdComboBox.getSelectedItem().toString(),EEstadoEdicion.CONFIRMADA);
            		cargarEdiciones();
        		}
        	}
        });
        panel.add(confirmarbtn);

        cargarEventos();
	}

	private void cargarEventos() {
		this.eventos = ICE.listarEventos();
		if(this.eventos != null) {
			for(DTEvento ev: this.eventos) {
				this.EvComboBox.addItem(ev.getNombre());
			}
		}
	}

	private boolean DatosSeleccionados() {
		if(this.EdComboBox != null && this.EvComboBox != null ) {
			if((this.EdComboBox.getSelectedItem() != null) && this.EvComboBox.getSelectedItem() != null) {
				return true;
			}
		}
		return false;
	}

	private void cargarEdiciones() {
		this.ediciones = ICE.listarEdicionesSinProcesar(this.EvComboBox.getSelectedItem().toString());
		this.EdComboBox.removeAllItems();
		for(DTEdicion ed : this.ediciones) {
			this.EdComboBox.addItem(ed.getNombre());
		}
	}

}
