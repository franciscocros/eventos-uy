package main.java.presentacion;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.DTPatrocinio;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;

public class ConsultaDePatrocinio extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	private JComboBox<String> EventosCB;
	private JComboBox<String> EdicionCB;
	private JComboBox<String> PatrociniosCB;

	private Set<DTEdicion> ediciones;

	private Set<DTPatrocinio> patrocinios;

	private IControladorEvento ICE;
	private IControladorUsuario ICU;

	private void iniciarInterfaces() {
		Fabrica fabrica = Fabrica.getInstance();
		ICE = fabrica.getIControladorEvento();
		ICU = fabrica.getIControladorUsuario();
	}

	public ConsultaDePatrocinio() {
		this.setTitle("Alta de patrocinio");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

        iniciarInterfaces();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{60, 0, 0, 79, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblEventos = new JLabel("Eventos:");
		GridBagConstraints gbc_lblEventos = new GridBagConstraints();
		gbc_lblEventos.anchor = GridBagConstraints.WEST;
		gbc_lblEventos.insets = new Insets(0, 0, 5, 5);
		gbc_lblEventos.gridx = 1;
		gbc_lblEventos.gridy = 1;
		getContentPane().add(lblEventos, gbc_lblEventos);

		EventosCB = new JComboBox<>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.ipadx = 70;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		getContentPane().add(EventosCB, gbc_comboBox);

		JLabel lblAporteEconomico = new JLabel("Aporte economico:");
		GridBagConstraints gbc_lblAporteEconomico = new GridBagConstraints();
		gbc_lblAporteEconomico.anchor = GridBagConstraints.WEST;
		gbc_lblAporteEconomico.insets = new Insets(0, 20, 5, 5);
		gbc_lblAporteEconomico.gridx = 4;
		gbc_lblAporteEconomico.gridy = 1;
		getContentPane().add(lblAporteEconomico, gbc_lblAporteEconomico);

		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 1;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblCodigo = new JLabel("Codigo:");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.WEST;
		gbc_lblCodigo.insets = new Insets(25, 20, 5, 5);
		gbc_lblCodigo.gridx = 4;
		gbc_lblCodigo.gridy = 2;
		getContentPane().add(lblCodigo, gbc_lblCodigo);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(25, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 5;
		gbc_textField_1.gridy = 2;
		getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JLabel lblEdiciones = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblEdiciones = new GridBagConstraints();
		gbc_lblEdiciones.anchor = GridBagConstraints.WEST;
		gbc_lblEdiciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdiciones.gridx = 1;
		gbc_lblEdiciones.gridy = 3;
		getContentPane().add(lblEdiciones, gbc_lblEdiciones);

		EdicionCB = new JComboBox<>();
		EdicionCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(EventosCB != null && EdicionCB != null && EventosCB.getSelectedItem() != null && EdicionCB.getSelectedItem() != null) {
					if(PatrociniosCB != null) {
						PatrociniosCB.removeAllItems();
					}
					limpiarCampos();
					cargarPatrocinios();
				}

			}
		});
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 3;
		getContentPane().add(EdicionCB, gbc_comboBox_1);

		JLabel lblFecha = new JLabel("Fecha:");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.WEST;
		gbc_lblFecha.insets = new Insets(25, 20, 5, 5);
		gbc_lblFecha.gridx = 4;
		gbc_lblFecha.gridy = 3;
		getContentPane().add(lblFecha, gbc_lblFecha);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets= new Insets(25, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 5;
		gbc_textField_2.gridy = 3;
		getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		JLabel lblCantidadGratis = new JLabel("Cantidad Gratis:");
		GridBagConstraints gbc_lblCantidadGratis = new GridBagConstraints();
		gbc_lblCantidadGratis.anchor = GridBagConstraints.WEST;
		gbc_lblCantidadGratis.insets = new Insets(25, 20, 5, 5);
		gbc_lblCantidadGratis.gridx = 4;
		gbc_lblCantidadGratis.gridy = 4;
		getContentPane().add(lblCantidadGratis, gbc_lblCantidadGratis);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(25, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 5;
		gbc_textField_3.gridy = 4;
		getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);

		JLabel lblPatrocinios = new JLabel("Patrocinios:");
		GridBagConstraints gbc_lblPatrocinios = new GridBagConstraints();
		gbc_lblPatrocinios.anchor = GridBagConstraints.WEST;
		gbc_lblPatrocinios.insets = new Insets(0, 0, 5, 5);
		gbc_lblPatrocinios.gridx = 1;
		gbc_lblPatrocinios.gridy = 5;
		getContentPane().add(lblPatrocinios, gbc_lblPatrocinios);

		PatrociniosCB = new JComboBox<>();
		PatrociniosCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				limpiarCampos();
				mostrarPatrocinio();
			}
		});
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 2;
		gbc_comboBox_2.gridy = 5;
		getContentPane().add(PatrociniosCB, gbc_comboBox_2);

		JLabel lblNivel = new JLabel("Nivel:");
		GridBagConstraints gbc_lblNivel = new GridBagConstraints();
		gbc_lblNivel.anchor = GridBagConstraints.WEST;
		gbc_lblNivel.insets = new Insets(25, 20, 5, 5);
		gbc_lblNivel.gridx = 4;
		gbc_lblNivel.gridy = 5;
		getContentPane().add(lblNivel, gbc_lblNivel);

		textField_4 = new JTextField();
		textField_4.setEditable(false);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(25, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 5;
		gbc_textField_4.gridy = 5;
		getContentPane().add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);

		JButton btnCerrar = new JButton("cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TerminarCaso();
			}
		});
		GridBagConstraints gbc_btnCerrar = new GridBagConstraints();
		gbc_btnCerrar.insets = new Insets(30, 0, 5, 0);
		gbc_btnCerrar.anchor = GridBagConstraints.EAST;
		gbc_btnCerrar.gridx = 5;
		gbc_btnCerrar.gridy = 6;
		getContentPane().add(btnCerrar, gbc_btnCerrar);

		EventosCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(EventosCB!= null && !EventosCB.getSelectedItem().equals("seleccionar")) {
					EdicionCB.removeAllItems();
					limpiarCampos();
					cargarEdiciones();
				}
			}
		});

        cargarComboBox();
	}

	private void cargarEventos() {
		Set<DTEvento> eventos = ICE.listarEventos();
		eventos = eventos.stream().sorted(Comparator.comparing(DTEvento::getNombre)).collect(Collectors.toCollection(LinkedHashSet::new));
		for(DTEvento evento: eventos) {
			this.EventosCB.addItem(evento.getNombre());
		}
	}


	private void cargarEdiciones() {
		ediciones = ICE.listarEdiciones(this.EventosCB.getSelectedItem().toString());
		for(DTEdicion edicion: ediciones) {
			EdicionCB.addItem(edicion.getNombre());
		}

	}

	private void cargarPatrocinios() {
		String evento  = EventosCB.getSelectedItem().toString();
		String edicion = EdicionCB.getSelectedItem().toString();
		if(!evento.equals("") && !edicion.equals("")) {
			patrocinios = ICU.listarPatrociniosAlt(evento, edicion);
			if(patrocinios != null) {
				for(DTPatrocinio pat : patrocinios) {
					PatrociniosCB.addItem(pat.getCodigo());
				}
			}
		}
	}

	private void mostrarPatrocinio() {
		DTPatrocinio ptr = null;
		for(DTPatrocinio pt : this.patrocinios) {
			if(PatrociniosCB.getSelectedItem() != null &&  pt.getCodigo().equals(PatrociniosCB.getSelectedItem().toString())) {
				ptr = pt;
				break;
			}
		}
		if(ptr != null) {
			this.textField.setText(String.valueOf(ptr.getAporte()));
			this.textField_1.setText(ptr.getCodigo());
			this.textField_2.setText(ptr.getDTFecha().toString());
			this.textField_3.setText(String.valueOf(ptr.getCantGratis()));
			this.textField_4.setText(ptr.getNivelPatrocinio().toString());
		}
	}

	private void limpiarCampos() {
		this.textField.setText("");
		this.textField_1.setText("");
		this.textField_2.setText("");
		this.textField_3.setText("");
		this.textField_4.setText("");
	}

	private void cargarComboBox() {
		limpiarCampos();
		this.EdicionCB.addItem("seleccionar");
		this.EventosCB.addItem("seleccionar");
		cargarEventos();
	}


	private void TerminarCaso() {
		this.setVisible(false);
	}

}
