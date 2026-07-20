package main.java.presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.IControladorEvento;

public class ConsultaDeEvento extends JInternalFrame {
	private JTextField                      textSigla;
	private JTextArea                       textCats;
	private JTextField                      textFechaAlta;
	private JTextField                      textNombre;
	private JButton                         botonCancelar;
	private DefaultComboBoxModel<String>    modeloCombo;
	private JComboBox<String>               comboEventos;
	private DefaultListModel<String>        modeloLista;
	private JList<String>                   listaEdiciones;
	private IControladorEvento              ICE;
	private JTextArea                       textDesc;
	private Principal                       mainFrame;
	private JLabel lblEstado;
	private JTextField txtEstado;

	public ConsultaDeEvento(IControladorEvento iCE, Principal main) {
		this.ICE = iCE;
		this.mainFrame = main;

		this.setTitle("Consulta de Evento");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Evento:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 3;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		modeloCombo = new DefaultComboBoxModel<>();
		comboEventos = new JComboBox<>(modeloCombo);
		cargarEventos();
		GridBagConstraints gbc_comboEventos = new GridBagConstraints();
		gbc_comboEventos.insets = new Insets(0, 0, 5, 5);
		gbc_comboEventos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEventos.gridx = 5;
		gbc_comboEventos.gridy = 3;
		getContentPane().add(comboEventos, gbc_comboEventos);
		this.comboEventos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listaEdiciones.removeAll();
				modeloLista.removeAllElements();
				textCats.setText("");
				mostrarDatosEvento();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 5;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		textNombre = new JTextField();
		textNombre.setEditable(false);
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridx = 5;
		gbc_textNombre.gridy = 5;
		getContentPane().add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Sigla:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 4;
		gbc_lblNewLabel_2.gridy = 7;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		textSigla = new JTextField();
		textSigla.setEditable(false);
		GridBagConstraints gbc_textSigla = new GridBagConstraints();
		gbc_textSigla.insets = new Insets(0, 0, 5, 5);
		gbc_textSigla.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSigla.gridx = 5;
		gbc_textSigla.gridy = 7;
		getContentPane().add(textSigla, gbc_textSigla);
		textSigla.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Fecha de alta:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 7;
		gbc_lblNewLabel_4.gridy = 7;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);

		textFechaAlta = new JTextField();
		textFechaAlta.setEditable(false);
		GridBagConstraints gbc_textFechaAlta = new GridBagConstraints();
		gbc_textFechaAlta.gridwidth = 2;
		gbc_textFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_textFechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFechaAlta.gridx = 8;
		gbc_textFechaAlta.gridy = 7;
		getContentPane().add(textFechaAlta, gbc_textFechaAlta);
		textFechaAlta.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Categorías:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 4;
		gbc_lblNewLabel_5.gridy = 9;
		getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);

		textCats = new JTextArea();
		textCats.setEditable(false);
		GridBagConstraints gbc_textCats = new GridBagConstraints();
		gbc_textCats.insets = new Insets(0, 0, 5, 5);
		gbc_textCats.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCats.gridx = 5;
		gbc_textCats.gridy = 9;
		getContentPane().add(textCats, gbc_textCats);
		textCats.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 7;
		gbc_lblNewLabel_3.gridy = 9;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

		textDesc = new JTextArea();
		textDesc.setEditable(false);
		GridBagConstraints gbc_textDesc = new GridBagConstraints();
		gbc_textDesc.gridwidth = 2;
		gbc_textDesc.insets = new Insets(0, 0, 5, 5);
		gbc_textDesc.fill = GridBagConstraints.BOTH;
		gbc_textDesc.gridx = 8;
		gbc_textDesc.gridy = 9;
		getContentPane().add(textDesc, gbc_textDesc);

		modeloLista = new DefaultListModel<>();
				
				lblEstado = new JLabel("Estado:");
				GridBagConstraints gbc_lblEstado = new GridBagConstraints();
				gbc_lblEstado.anchor = GridBagConstraints.EAST;
				gbc_lblEstado.insets = new Insets(0, 0, 5, 5);
				gbc_lblEstado.gridx = 4;
				gbc_lblEstado.gridy = 11;
				getContentPane().add(lblEstado, gbc_lblEstado);
				
				txtEstado = new JTextField();
				GridBagConstraints gbc_txtEstado = new GridBagConstraints();
				gbc_txtEstado.insets = new Insets(0, 0, 5, 5);
				gbc_txtEstado.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtEstado.gridx = 5;
				gbc_txtEstado.gridy = 11;
				getContentPane().add(txtEstado, gbc_txtEstado);
				txtEstado.setColumns(10);
		
				JLabel lblNewLabel_6 = new JLabel("Ediciones:");
				GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
				gbc_lblNewLabel_6.anchor = GridBagConstraints.NORTH;
				gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_6.gridx = 4;
				gbc_lblNewLabel_6.gridy = 13;
				getContentPane().add(lblNewLabel_6, gbc_lblNewLabel_6);
		listaEdiciones = new JList<>(modeloLista);
		GridBagConstraints gbc_listaEdiciones = new GridBagConstraints();
		gbc_listaEdiciones.insets = new Insets(0, 0, 5, 5);
		gbc_listaEdiciones.fill = GridBagConstraints.BOTH;
		gbc_listaEdiciones.gridx = 5;
		gbc_listaEdiciones.gridy = 13;
		getContentPane().add(listaEdiciones, gbc_listaEdiciones);
		listaEdiciones.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                	int index = listaEdiciones.locationToIndex(e.getPoint());
                    String valor = listaEdiciones.getModel().getElementAt(index);
                    cambio(valor);
                }
            }
        });

		botonCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_BotonCancelar = new GridBagConstraints();
		gbc_BotonCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_BotonCancelar.gridx = 9;
		gbc_BotonCancelar.gridy = 13;
		getContentPane().add(botonCancelar, gbc_BotonCancelar);
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelarCaso();
			}
		});
	}

	public void cargarEventos() {
		if(ICE.listarEventos() != null) {
			Set<DTEvento> listaEventos=ICE.listarEventos();
			List<DTEvento> listaEventosOrdenada = listaEventos.stream().sorted(Comparator.comparing(DTEvento::getNombre)).collect(Collectors.toList());
			this.modeloCombo.removeAllElements();
			for(DTEvento event : listaEventosOrdenada) {
				this.modeloCombo.addElement(event.getNombre());
			}
		}
	}


	private void mostrarDatosEvento() {
		if(this.modeloCombo.getSelectedItem() != null) {
			DTEvento eventoSelec = this.ICE.getEvento((String) this.modeloCombo.getSelectedItem());
			this.textNombre.setText(eventoSelec.getNombre());
			this.textSigla.setText(eventoSelec.getSigla());
			this.textDesc.setText(eventoSelec.getDescipcion());
			this.textFechaAlta.setText(eventoSelec.getFechaAlta().getYear() + "-" + eventoSelec.getFechaAlta().getMonthValue() + "-" + eventoSelec.getFechaAlta().getDayOfMonth());
			//ahora cargo las ediciones del evento a la lista:
			for(DTEdicion edi : eventoSelec.getEdiciones()) {
				this.modeloLista.addElement(edi.getNombre());
			}
			String estado = "";
			if (!eventoSelec.getFinalizado()) {
				estado = "Activo";
			}else {
				estado = "Finalizado";
			}
			this.txtEstado.setText(estado);
			//cargo las categorías:
			if(eventoSelec.getCategorias() != null) {
				for(String cat : eventoSelec.getCategorias()) {
					this.textCats.setText(this.textCats.getText() + cat + "\n");
				}
			}
		}
	}

	private void cancelarCaso() {
		limpiarVariables();
		this.setVisible(false);
	}

	private void cambio(String main) {
		mainFrame.DetalleEdicion((String) this.modeloCombo.getSelectedItem(), this.listaEdiciones.getSelectedValue());
		cancelarCaso();
	}

	private void limpiarVariables(){
		this.textNombre.setText("");
		this.textSigla.setText("");
		this.textDesc.setText("");
		this.textCats.setText("");
		this.comboEventos.removeAllItems();
		this.modeloCombo.removeAllElements();
		this.listaEdiciones.removeAll();
		this.modeloLista.removeAllElements();
	}


}
