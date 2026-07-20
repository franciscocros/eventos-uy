package main.java.presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import com.toedter.calendar.JDateChooser;

import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.excepciones.FechaNacimientoPosteriorExcepcion;
import main.java.logica.DTInstitucion;
import main.java.logica.IControladorUsuario;

public class AltaDeUsuario extends JInternalFrame {
	private JTextField nickname;
	private JTextField nombre;
	private JTextField email;
	private JTextArea alt01;
	private JTextField alt02;

	private JLabel lbl02;
	private JLabel lbl01;
	private JComboBox<String> comboBox;
	private JLabel lblPerteneceAInstitucion;
	private JButton btnCancelar;
	private JButton btnConfirmar;

	private JRadioButton AsistenteRadioButton;
	private JRadioButton OrganizadorRadioButton;

	private IControladorUsuario ICU;

	private Map<String, DTInstitucion> instituciones;
	private JDateChooser dateChooser;
	private JLabel lblSitioWeb;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JPasswordField pwd1;
	private JPasswordField pwd2;

	public AltaDeUsuario(IControladorUsuario ICU, Map<String, DTInstitucion> instituciones) {
		this.ICU = ICU;
		this.instituciones = instituciones;

		setTitle("Alta de Usuario");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		iniciarInstituciones();

		AsistenteRadioButton = new JRadioButton("Asistente");
		AsistenteRadioButton.setSelected(true);
		AsistenteRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evento) {
				setAsistente();
			}
		});
		OrganizadorRadioButton = new JRadioButton("Organizador");
		OrganizadorRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evento) {
				setOrganizador();
			}
		});

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(AsistenteRadioButton);
		grupo.add(OrganizadorRadioButton);

		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(20, 0, 20, 5);
		gbc_rdbtnNewRadioButton.gridx = 3;
		gbc_rdbtnNewRadioButton.gridy = 1;
		getContentPane().add(AsistenteRadioButton, gbc_rdbtnNewRadioButton);

		GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_1.ipady = 2;
		gbc_rdbtnNewRadioButton_1.ipadx = 1;
		gbc_rdbtnNewRadioButton_1.insets = new Insets(20, 0, 20, 5);
		gbc_rdbtnNewRadioButton_1.gridx = 6;
		gbc_rdbtnNewRadioButton_1.gridy = 1;
		getContentPane().add(OrganizadorRadioButton, gbc_rdbtnNewRadioButton_1);

		JLabel lblNickName = new JLabel("NickName:");
		GridBagConstraints gbc_lblNickName = new GridBagConstraints();
		gbc_lblNickName.ipadx = 50;
		gbc_lblNickName.insets = new Insets(10, 50, 10, 5);
		gbc_lblNickName.anchor = GridBagConstraints.WEST;
		gbc_lblNickName.gridx = 3;
		gbc_lblNickName.gridy = 3;
		getContentPane().add(lblNickName, gbc_lblNickName);

		nickname = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 6;
		gbc_textField.gridy = 3;
		getContentPane().add(nickname, gbc_textField);
		nickname.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(10, 50, 10, 5);
		gbc_lblNombre.gridx = 3;
		gbc_lblNombre.gridy = 5;
		getContentPane().add(lblNombre, gbc_lblNombre);

		nombre = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 6;
		gbc_textField_1.gridy = 5;
		getContentPane().add(nombre, gbc_textField_1);
		nombre.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();

		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(10, 50, 10, 5);
		gbc_lblEmail.gridx = 3;
		gbc_lblEmail.gridy = 7;
		getContentPane().add(lblEmail, gbc_lblEmail);

		email = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 6;
		gbc_textField_2.gridy = 7;
		getContentPane().add(email, gbc_textField_2);
		email.setColumns(10);

		lbl01= new JLabel("Apellido:");
		GridBagConstraints gbc_lblAlt = new GridBagConstraints();
		gbc_lblAlt.insets = new Insets(10, 50, 10, 5);
		gbc_lblAlt.anchor = GridBagConstraints.WEST;
		gbc_lblAlt.gridx = 3;
		gbc_lblAlt.gridy = 10;
		getContentPane().add(lbl01, gbc_lblAlt);

		alt01 = new JTextArea();
		alt01.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.gridx = 6;
		gbc_textField_3.gridy = 10;
		getContentPane().add(alt01, gbc_textField_3);
		alt01.setColumns(10);

		alt02 = new JTextField();
		alt02.setVisible(false);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.gridx = 6;
		gbc_textField_4.gridy = 12;
		getContentPane().add(alt02, gbc_textField_4);
		alt02.setColumns(10);

		btnConfirmar = new JButton("confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(	ActionEvent e) {
				try {
					confirmarAltaDeUsuario();
				} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e1) {
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
		gbc_btnConfirmar.insets = new Insets(30, 0, 5, 5);
		gbc_btnConfirmar.gridx = 3;
		gbc_btnConfirmar.gridy = 15;
		getContentPane().add(btnConfirmar, gbc_btnConfirmar);

		btnCancelar = new JButton("cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cancelarCasoDeUso();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.EAST;
		gbc_btnCancelar.insets = new Insets(30, 0, 5, 5);
		gbc_btnCancelar.gridx = 6;
		gbc_btnCancelar.gridy = 15;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

	private void iniciarInstituciones() {

		lblNewLabel = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 8;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		pwd1 = new JPasswordField();
		GridBagConstraints gbc_pwd1 = new GridBagConstraints();
		gbc_pwd1.insets = new Insets(0, 0, 5, 5);
		gbc_pwd1.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwd1.gridx = 6;
		gbc_pwd1.gridy = 8;
		getContentPane().add(pwd1, gbc_pwd1);

		lblNewLabel_1 = new JLabel("Reingrese la contraseña:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 9;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		pwd2 = new JPasswordField();
		GridBagConstraints gbc_pwd2 = new GridBagConstraints();
		gbc_pwd2.insets = new Insets(0, 0, 5, 5);
		gbc_pwd2.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwd2.gridx = 6;
		gbc_pwd2.gridy = 9;
		getContentPane().add(pwd2, gbc_pwd2);

		lbl02 = new JLabel("fecha Nacimiento:");
		GridBagConstraints gbc_lblAlt_1 = new GridBagConstraints();
		gbc_lblAlt_1.insets = new Insets(10, 50, 10, 5);
		gbc_lblAlt_1.anchor = GridBagConstraints.WEST;
		gbc_lblAlt_1.gridx = 3;
		gbc_lblAlt_1.gridy = 11;
		getContentPane().add(lbl02, gbc_lblAlt_1);

		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.fill = GridBagConstraints.BOTH;
		gbc_dateChooser_1.gridx = 6;
		gbc_dateChooser_1.gridy = 11;
		getContentPane().add(dateChooser, gbc_dateChooser_1);

		lblSitioWeb = new JLabel("Sitio Web:");
		lblSitioWeb.setVisible(false);
		GridBagConstraints gbc_lblSitioWeb = new GridBagConstraints();
		gbc_lblSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblSitioWeb.gridx = 3;
		gbc_lblSitioWeb.gridy = 12;
		getContentPane().add(lblSitioWeb, gbc_lblSitioWeb);

		lblPerteneceAInstitucion = new JLabel("Institucion:");

		GridBagConstraints gbc_lblPerteneceAInstitucion = new GridBagConstraints();
		gbc_lblPerteneceAInstitucion.insets = new Insets(20, 0, 20, 5);
		gbc_lblPerteneceAInstitucion.gridx = 3;
		gbc_lblPerteneceAInstitucion.gridy = 13;
		getContentPane().add(lblPerteneceAInstitucion, gbc_lblPerteneceAInstitucion);

		comboBox = new JComboBox<>();
		cargarComboBox();

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 6;
		gbc_comboBox.gridy = 13;
		getContentPane().add(comboBox, gbc_comboBox);
	}

	private void ocultarInstituciones() {
		lblPerteneceAInstitucion.setVisible(false);
		comboBox.setVisible(false);
	}

	private void mostrarInstituciones() {
		lblPerteneceAInstitucion.setVisible(true);
		comboBox.setVisible(true);
	}

	//Validacion de datos
	boolean sonValidosDatos() {

		//Campos vacios
		if(this.nombre.getText().equals("") || this.email.getText().equals("") || this.nickname.getText().equals("")){
			JOptionPane.showMessageDialog(this, "No puede haber campos vacios", "Crear Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(this.pwd1.getText().equals(this.pwd2.getText())) {
			JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Crear Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		//Asistente, se valida apellido y  fecha de nacimiento
		if(this.AsistenteRadioButton.isSelected() && (this.alt01.getText().equals("") || this.dateChooser.getDate() == null)) {
			JOptionPane.showMessageDialog(this, "No puede haber campos vacios", "Crear Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(this.OrganizadorRadioButton.isSelected() &&  this.alt01.getText().equals("")) {//Organizador, se valida descripcion general, la web es opcional
			JOptionPane.showMessageDialog(this, "No puede haber campos vacios", "Crear Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if( !esValidoEmail(this.email.getText())) {
			JOptionPane.showMessageDialog(this, "Debe ingresar un correo valido ", "Crear Usuario",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private boolean esValidoEmail(String email) {
		return email.contains("@");
	}


	private void  confirmarAltaDeUsuario() throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		if(sonValidosDatos()) {
			try {
				if(this.AsistenteRadioButton.isSelected()) {
					Date fecha = this.dateChooser.getDate();
					LocalDate localFecha = LocalDate.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
					ICU.crearAsistente(this.nombre.getText(), this.nickname.getText(),this.email.getText(),this.alt01.getText(),localFecha);
					if(this.comboBox.getSelectedItem()!= "seleccionar") {
						ICU.setInstitucionAsistente(this.nickname.getText(),this.comboBox.getSelectedItem().toString());
						ICU.setContraseniaUsuario(this.nickname.getText(),this.pwd1.getText());
					}
				}else {
					ICU.crearOrganizador(this.nombre.getText(), this.nickname.getText(),this.email.getText(),this.alt01.getText(),this.alt02.getText());
					ICU.setContraseniaUsuario(this.nickname.getText(),this.pwd1.getText());
				}
				JOptionPane.showMessageDialog(this,"Usuario registrado con exito", "Registrar usuario",JOptionPane.INFORMATION_MESSAGE);
				limpiarVariables();
				this.setVisible(false);
			}catch(ExisteUsuarioExcepcion e) {
				int opcion = 0;
				if(ICU.existeUsuario(this.nickname.getText())){
					opcion = JOptionPane.showConfirmDialog(this, "Existe nickname","Alta de Usuario", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
				}else {
			        opcion = JOptionPane.showConfirmDialog(this, "Existe email","Alta de Usuario", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
				}
		        if(opcion == 1) {
		        	cancelarCasoDeUso();
		        }else if(opcion == 0){
		        	if(ICU.existeUsuario(this.nickname.getText())){
		        		this.nickname.setText("");
					}else {
						this.email.setText("");
					}

		        }
			}catch(FechaNacimientoPosteriorExcepcion ex) {
				JOptionPane.showMessageDialog(this,"Fecha invalida", "Registrar usuario",JOptionPane.INFORMATION_MESSAGE);
			}
		}
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
		this.dateChooser.setDate(null);
	}

	private void setAsistente() {
		mostrarInstituciones();
		lbl01.setText("Apellido:");
		lbl02.setVisible(true);
		lblSitioWeb.setVisible(false);
		if(dateChooser != null) {
			dateChooser.setVisible(true);
		}
		this.alt02.setVisible(false);
	}

	private void setOrganizador() {
		ocultarInstituciones();
		lblSitioWeb.setVisible(true);
		lbl01.setText("Descripcion:");
		lbl02.setVisible(false);
		if(dateChooser != null) {
			dateChooser.setVisible(false);
		}
		this.alt02.setVisible(true);
	}

	private void cargarComboBox() {
		this.comboBox.addItem("seleccionar");
		Map<String, DTInstitucion> instituciones = ICU.listarInstituciones();
		Set<String> insts = new HashSet<>();
		for(DTInstitucion dt:instituciones.values()) {
			insts.add(dt.getNombre());
		}
		Set<String> instsORd = new TreeSet<>(insts);


		for(String inst:  instsORd) {
			this.comboBox.addItem(inst);
		}

	}

}
