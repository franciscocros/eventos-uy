package main.java.presentacion;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.toedter.calendar.JDateChooser;

import main.java.excepciones.FechaNacimientoPosteriorExcepcion;
import main.java.excepciones.NoExisteUsuarioExcepcion;
import main.java.logica.DTAsistente;
import main.java.logica.DTInstitucion;
import main.java.logica.DTOrganizador;
import main.java.logica.DTUsuario;
import main.java.logica.Fabrica;
import main.java.logica.IControladorUsuario;

public class ModificarDatosDeUsuario extends JInternalFrame {
	private JComboBox<String> comboUsuarios;
	private JComboBox<String> comboInst;
	private Map<String, DTUsuario> usuarios;
	private IControladorUsuario ICU;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblFechaDeNacimiento;
	private JDateChooser dateChooser;
	private JPanel panel;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	private JLabel lblInstitucion;



	private void cargarInterfaces(){
		Fabrica f = Fabrica.getInstance();
		ICU = f.getIControladorUsuario();
	}

	public ModificarDatosDeUsuario() {

		this.setTitle("Consulta de Usuario");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

        cargarInterfaces();

        GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{73, 20, 0, 456, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		comboUsuarios = new JComboBox<>();

        cargarUsuarios();
        cargarUsuario();

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 5;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		getContentPane().add(comboUsuarios, gbc_comboBox);

		lblNewLabel = new JLabel("Nickname:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(30, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 3;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(30, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 3;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		lblNewLabel_1 = new JLabel("Correo:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(10, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 5;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(10, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 5;
		getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		lblNewLabel_2 = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(10, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 7;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(10, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 7;
		getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		lblNewLabel_3 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(10, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 9;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(10, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 9;
		getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);

		lblNewLabel_4 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(10, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 11;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);

		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(10, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 11;
		getContentPane().add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);

		lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaDeNacimiento.setVisible(false);
		GridBagConstraints gbc_lblFechaDeNacimiento = new GridBagConstraints();
		gbc_lblFechaDeNacimiento.anchor = GridBagConstraints.WEST;
		gbc_lblFechaDeNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeNacimiento.gridx = 1;
		gbc_lblFechaDeNacimiento.gridy = 13;
		getContentPane().add(lblFechaDeNacimiento, gbc_lblFechaDeNacimiento);

		dateChooser = new JDateChooser();
		dateChooser.setVisible(false);
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 3;
		gbc_dateChooser.gridy = 13;
		getContentPane().add(dateChooser, gbc_dateChooser);

		lblInstitucion = new JLabel("Institucion:");
		GridBagConstraints gbc_lblInstitucion = new GridBagConstraints();
		gbc_lblInstitucion.anchor = GridBagConstraints.WEST;
		gbc_lblInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_lblInstitucion.gridx = 1;
		gbc_lblInstitucion.gridy = 14;
		getContentPane().add(lblInstitucion, gbc_lblInstitucion);

		comboInst = new JComboBox<>();
		GridBagConstraints gbc_comboBox1 = new GridBagConstraints();
		gbc_comboBox1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox1.gridx = 3;
		gbc_comboBox1.gridy = 14;
		getContentPane().add(comboInst, gbc_comboBox1);

		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 15;
		getContentPane().add(panel, gbc_panel);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modificarDatos();
				} catch (NoExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnConfirmar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				terminarCaso();
			}
		});
		panel.add(btnCancelar);

        todoApagado();

		comboUsuarios.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				limpiarCampos();
				cargarUsuario();
			}
		});
	}

	private void todoApagado() {
		textField.setVisible(false);
		textField_1.setVisible(false);
		textField_2.setVisible(false);
		textField_3.setVisible(false);
		textField_4.setVisible(false);

		lblNewLabel.setVisible(false);
		lblNewLabel_1.setVisible(false);
		lblNewLabel_2.setVisible(false);
		lblNewLabel_3.setVisible(false);
		lblNewLabel_4.setVisible(false);
	}

	private void cargarUsuario() {
		if(this.comboUsuarios != null) {
			if(!this.comboUsuarios.getSelectedItem().equals("seleccionar")) {
				String [] nick = this.comboUsuarios.getSelectedItem().toString().split(" - ");
				String nickName = nick[0];

				DTUsuario usu = this.usuarios.get(nickName);
				if(usu != null) {
					textField.setVisible(true);
					textField_1.setVisible(true);
					textField_2.setVisible(true);
					textField_3.setVisible(true);

					lblNewLabel.setVisible(true);
					lblNewLabel_1.setVisible(true);
					lblNewLabel_2.setVisible(true);
					lblNewLabel_3.setVisible(true);

					textField.setText(usu.getNickName());
					textField_1.setText(usu.getCorreo());
					textField_2.setText(usu.getNombre());

					if(usu.getClass() == DTAsistente.class) {

						lblNewLabel_3.setText("Apellido:");
						textField_3.setText(((DTAsistente) usu).getApellido());

						lblNewLabel_4.setVisible(false);
						textField_4.setVisible(false);
						dateChooser.setVisible(true);
						lblFechaDeNacimiento.setVisible(true);

						Map<String, DTInstitucion> instituciones = ICU.listarInstituciones();
						comboInst.addItem("ninguna");
						comboInst.setSelectedItem("ninguna");
						for(DTInstitucion inst : instituciones.values()) {
							comboInst.addItem(inst.getNombre());
						}
						if(((DTAsistente) usu).getInstitucion() != "") {
							comboInst.setSelectedItem(((DTAsistente) usu).getInstitucion());
						}

						LocalDate fecha = ((DTAsistente) usu).getFechaNacimiento();

						java.util.Date date = java.util.Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
						dateChooser.setDate(date);

					}else {
						lblNewLabel_3.setText("Descripcion:");
						lblNewLabel_4.setText("pagina Web:");
						textField_4.setVisible(true);
						textField_3.setText(((DTOrganizador) usu).getDescripcion());
						textField_4.setText(((DTOrganizador) usu).getPaginaWeb());
						dateChooser.setVisible(false);
						lblFechaDeNacimiento.setVisible(false);
					}
				}
			}
		}
	}


	boolean sonValidosDatos(String usuario) {
		if(usuario.equals("asistente")) {
			if(dateChooser.getDate() == null || textField_3.getText().equals("") ||textField_3.getText().equals("")) {
				return false;
			}
		}else {
			if(textField_2.getText().equals("") || textField_3.getText().equals("")) {
				return false;
			}
		}
		return true;
	}

	private void modificarDatos() throws NoExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		if(this.comboUsuarios != null) {
			if(!this.comboUsuarios.getSelectedItem().equals("seleccionar")) {
				String [] nick = this.comboUsuarios.getSelectedItem().toString().split(" - ");
				String nickName = nick[0];

				DTUsuario usu = this.usuarios.get(nickName);
				if(usu != null) {

					String nickname = usu.getNickName();
					String correo = usu.getCorreo();
					String nombre = textField_2.getText();
					LocalDate localFecha = null;
					java.util.Date fecha = dateChooser.getDate();
					if(fecha != null) {
						localFecha = LocalDate.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
					}

					if(usu.getClass() == DTAsistente.class) {
						String apellido =  textField_3.getText().toString();

						String institucion = (comboInst.getSelectedItem().toString().equals("ninguna")) ? "" : comboInst.getSelectedItem().toString();
						try {
							if(sonValidosDatos("asistente")) {
								ICU.modificarAsistente(nombre, nickname, correo,apellido, localFecha);
								ICU.setInstitucionAsistente(nickname, institucion);
								JOptionPane.showMessageDialog(this,"Usuario modificado con exito", "Modificar usuario",JOptionPane.INFORMATION_MESSAGE);
								terminarCaso();
							}else {
								JOptionPane.showMessageDialog(this,"No pueden haber campos vacios", "Modificar usuario",JOptionPane.WARNING_MESSAGE);
							}
						} catch(FechaNacimientoPosteriorExcepcion e1) {
							JOptionPane.showMessageDialog(this,"La fecha de nacimiento debe ser posterior a la de hoy", "Modificar usuario",JOptionPane.WARNING_MESSAGE);
						}
					}else {

						String descripcion = textField_3.getText();
						String paginaWeb   = textField_4.getText();

						if(sonValidosDatos("organizador")) {
							ICU.modificarOrganizador(nombre, nickname, correo, descripcion, paginaWeb);
							JOptionPane.showMessageDialog(this,"Usuario modificado con exito", "Modificar usuario",JOptionPane.INFORMATION_MESSAGE);
							terminarCaso();
						}else {
							JOptionPane.showMessageDialog(this,"No pueden haber campos vacios", "Modificar usuario",JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		}
	}

	private void terminarCaso() {
		limpiarCampos();
		this.setVisible(false);
	}

	private void limpiarCampos() {
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textField_4.setText("");
		if(comboInst != null) {
			comboInst.removeAllItems();
		}

	}

	private void cargarUsuarios() {
		this.usuarios = ICU.listarUsuarios();
		this.comboUsuarios.addItem("seleccionar");
		Set<String> us = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		if(this.usuarios != null) {
			for (DTUsuario usu : this.usuarios.values()) {
				String aux = usu.getNickName() + " - " + usu.getNombre() + " - " + usu.getCorreo();
				us.add(aux);
			}
			for(String usu: us) {
				this.comboUsuarios.addItem(usu);
			}

		}
	}

}
