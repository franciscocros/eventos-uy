package main.java.presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import main.java.excepciones.NoExisteUsuarioExcepcion;
import main.java.logica.DTAsistente;
import main.java.logica.DTCompra;
import main.java.logica.DTEdicion;
import main.java.logica.DTOrganizador;
import main.java.logica.DTUsuario;
import main.java.logica.Fabrica;
import main.java.logica.IControladorUsuario;

public class ConsultaDeUsuario extends JInternalFrame {

	Fabrica f = Fabrica.getInstance();
	IControladorUsuario ICU = f.getIControladorUsuario();

	private JTextField nickname;
	private JTextField nombre;
	private JTextField email;
	private JTextField alt01;
	private JTextField alt02;

	private JLabel lbl02;
	private JLabel lbl01;
	private JLabel lblPerteneceAInstitucion;
	private JButton btnCancelar;
	private JButton btnConfirmar;

	private JComboBox<String> comboBox_Usuarios;
	private JLabel lbl03;
	private JList<String> list;
	private DefaultListModel<String> modelo;
	private Principal mainFrame;
	private JTextField textField;

	public ConsultaDeUsuario(Principal main) {
		//this.instituciones = instituciones;
		this.mainFrame = main;
		this.setTitle("Consulta de Usuario");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

        GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 93, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);


		comboBox_Usuarios = new JComboBox<>();
		GridBagConstraints gbc_comboBox_Usuarios = new GridBagConstraints();
		gbc_comboBox_Usuarios.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_Usuarios.gridwidth = 3;
		gbc_comboBox_Usuarios.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_Usuarios.gridx = 1;
		gbc_comboBox_Usuarios.gridy = 1;
		getContentPane().add(comboBox_Usuarios, gbc_comboBox_Usuarios);
		cargaUsuarios();

		iniciarInstituciones();

		JLabel lblNickName = new JLabel("NickName:");
		lblNickName.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNickName = new GridBagConstraints();
		gbc_lblNickName.ipadx = 50;
		gbc_lblNickName.insets = new Insets(10, 50, 10, 5);
		gbc_lblNickName.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNickName.gridx = 1;
		gbc_lblNickName.gridy = 4;
		getContentPane().add(lblNickName, gbc_lblNickName);

		nickname = new JTextField();
		nickname.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 4;
		getContentPane().add(nickname, gbc_textField);
		nickname.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(10, 50, 10, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 6;
		getContentPane().add(lblNombre, gbc_lblNombre);

		nombre = new JTextField();
		nombre.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 6;
		getContentPane().add(nombre, gbc_textField_1);
		nombre.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();

		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(10, 50, 10, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 8;
		getContentPane().add(lblEmail, gbc_lblEmail);

		email = new JTextField();
		email.setEditable(false);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 8;
		getContentPane().add(email, gbc_textField_2);
		email.setColumns(10);

		lbl01= new JLabel("Apellido:");
		GridBagConstraints gbc_lblAlt = new GridBagConstraints();
		gbc_lblAlt.insets = new Insets(10, 50, 10, 5);
		gbc_lblAlt.anchor = GridBagConstraints.EAST;
		gbc_lblAlt.gridx = 1;
		gbc_lblAlt.gridy = 10;
		getContentPane().add(lbl01, gbc_lblAlt);

		alt01 = new JTextField();
		alt01.setEditable(false);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 10;
		getContentPane().add(alt01, gbc_textField_3);
		alt01.setColumns(10);

		lbl02 = new JLabel("fecha Nacimiento:");
		GridBagConstraints gbc_lblAlt_1 = new GridBagConstraints();
		gbc_lblAlt_1.insets = new Insets(10, 50, 10, 5);
		gbc_lblAlt_1.anchor = GridBagConstraints.EAST;
		gbc_lblAlt_1.gridx = 1;
		gbc_lblAlt_1.gridy = 12;
		getContentPane().add(lbl02, gbc_lblAlt_1);

		alt02 = new JTextField();
		alt02.setEditable(false);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 12;
		getContentPane().add(alt02, gbc_textField_4);
		alt02.setColumns(10);
		btnConfirmar = new JButton("confirmar");

		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.insets = new Insets(0, 0, 5, 5);
		gbc_textField1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField1.gridx = 3;
		gbc_textField1.gridy = 13;
		getContentPane().add(textField, gbc_textField1);
		textField.setColumns(10);


		lbl03 = new JLabel("Compras:");
		GridBagConstraints gbc_lbl03 = new GridBagConstraints();
		gbc_lbl03.anchor = GridBagConstraints.NORTHEAST;
		gbc_lbl03.insets = new Insets(0, 0, 5, 5);
		gbc_lbl03.gridx = 1;
		gbc_lbl03.gridy = 14;
		getContentPane().add(lbl03, gbc_lbl03);



        modelo = new DefaultListModel<>();
		list = new JList<>(modelo);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 3;
		gbc_list.gridy = 14;
		getContentPane().add(list, gbc_list);


		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
		gbc_btnConfirmar.insets = new Insets(30, 0, 5, 5);
		gbc_btnConfirmar.gridx = 1;
		gbc_btnConfirmar.gridy = 15;
		getContentPane().add(btnConfirmar, gbc_btnConfirmar);

		btnCancelar = new JButton("cancelar");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.EAST;
		gbc_btnCancelar.insets = new Insets(30, 0, 5, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 15;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

		// Eventos vv
				btnCancelar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						cancelarCasoDeUso();
					}
				});

				comboBox_Usuarios.addItemListener(new ItemListener() {
				    @Override
					public void itemStateChanged(ItemEvent e) {
				        if (e.getStateChange() == ItemEvent.SELECTED) {
				        	String [] nickname = comboBox_Usuarios.getSelectedItem().toString().split(" - ");
				        	DTUsuario dtu = ICU.getDTUsuario(nickname[0]);
				        	if (dtu instanceof  DTAsistente) {
				        		try {
									cargaDatosAsistente(dtu);
								} catch (NoExisteUsuarioExcepcion e1) {
									e1.printStackTrace();
								}
				        	}if ( dtu instanceof DTOrganizador) {
				        		Set<DTEdicion> edis = ICU.getSetEdiciones(nickname[0]);
				        		cargaDatosOrganizador(dtu,edis);				        	}

				        }
				    }
				});
				list.addMouseListener(new MouseAdapter() {
	                @Override
					public void mouseClicked(MouseEvent e) {
	                    if (e.getClickCount() == 2) { // doble clic
	                    	int index = list.locationToIndex(e.getPoint());
	                        String valor = list.getModel().getElementAt(index);
	                        cambio(valor);
	                    }
	                }
	            });

	}

	private void cambio(String dato) {
		String [] aux = dato.split(" - ");
		if(aux.length == 1) {
			mainFrame.DetalleCompra(this.nickname.getText(), aux[0]);
		}else {
			mainFrame.DetalleEdicion(aux[1],aux[0]);
		}
		cancelarCasoDeUso();

	}
	private void iniciarInstituciones() {

		lblPerteneceAInstitucion = new JLabel("Institucion:");

		GridBagConstraints gbc_lblPerteneceAInstitucion = new GridBagConstraints();
		gbc_lblPerteneceAInstitucion.anchor = GridBagConstraints.EAST;
		gbc_lblPerteneceAInstitucion.insets = new Insets(20, 0, 20, 5);
		gbc_lblPerteneceAInstitucion.gridx = 1;
		gbc_lblPerteneceAInstitucion.gridy = 13;
		getContentPane().add(lblPerteneceAInstitucion, gbc_lblPerteneceAInstitucion);
	}

	private void ocultarInstituciones() {
		lblPerteneceAInstitucion.setVisible(false);
		textField.setVisible(false);
	}

	private void mostrarInstituciones() {
		textField.setText("");
		lblPerteneceAInstitucion.setVisible(true);
		textField.setVisible(true);
	}

	private void cancelarCasoDeUso() {
		limpiarVariables();
		this.setVisible(false);
	}

	private void limpiarVariables(){
		this.nombre.setText("");
		this.nickname.setText("");
		this.email.setText("");
		this.alt01.setText("");
		this.alt02.setText("");
	}

	private void cargaDatosOrganizador(DTUsuario usu, Set<DTEdicion> edis) {
		ocultarInstituciones();
		lbl01.setText("Descripcion:");
		lbl02.setText("Sitio web:");
		lbl03.setText("Ediciones");
		this.nickname.setText(usu.getNickName());
		this.nombre.setText(usu.getNombre());
		this.email.setText(usu.getCorreo());
		this.alt01.setText(((DTOrganizador) usu).getDescripcion());
		this.alt02.setText(((DTOrganizador) usu).getPaginaWeb());
		this.modelo.removeAllElements();
		for(DTEdicion comp: edis) {
			String eve = ICU.findEvento(usu.getNickName(), comp.getNombre() );
			String formato = comp.getNombre() +" - "+ eve + " - "+ comp.getFechaini() +  " - "+ comp.getFechaFin() + " - "+ comp.getCiudad() + " - " +comp.getPais() ;
			this.modelo.addElement(formato);
		}
}

	private void cargaDatosAsistente(DTUsuario usu) throws NoExisteUsuarioExcepcion {
		this.modelo.clear();
		mostrarInstituciones();
		lbl01.setText("Apellido:");
		lbl02.setText("Fecha Nacimiento:");
		lbl03.setText("Compras");
		this.nickname.setText(usu.getNickName());
		this.nombre.setText(usu.getNombre());
		this.email.setText(usu.getCorreo());
		this.alt01.setText(((DTAsistente) usu).getApellido());
		this.alt02.setText(((DTAsistente) usu).getFechaNacimiento().toString());


		if(ICU.tieneInstitucion(usu.getNickName())) {
			String institucion = ICU.getInstitucionAsistente(usu.getNickName());
			textField.setText(institucion);
		}

		Map<String, DTCompra> comprasUsuario = ICU.listarComprasDeUsuario(usu.getNickName());
		for(DTCompra compra : comprasUsuario.values()) {
			this.modelo.addElement(compra.getEdicion());
		}
	}

	public void cargaUsuarios() {
		Map<String,DTUsuario> usuarios = ICU.listarUsuarios();
		this.comboBox_Usuarios.removeAllItems();
		Map<String,DTUsuario> ordenado = new TreeMap<>(usuarios);

		Set<String> usrs = new HashSet<>();
		for (DTUsuario usu : ordenado.values()) {
			String aux = usu.getNickName() + " - " + usu.getNombre() + " - " + usu.getCorreo();
			usrs.add(aux);
		}
		Set<String> us = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		us.addAll(usrs);

		for (String u : us) {
			this.comboBox_Usuarios.addItem(u);
		}
	}

}
