package main.java.presentacion;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.NumberFormatter;

import com.toedter.calendar.JDateChooser;

import main.java.excepciones.NoExisteEdicionExcepcion;
import main.java.excepciones.NoExisteEvento;
import main.java.excepciones.NoExisteInstitucion;
import main.java.excepciones.NoExisteTipoRegistro;
import main.java.excepciones.SuperaCantidadGratuitos;
import main.java.excepciones.YatienePatrocinioException;
import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.DTInstitucion;
import main.java.logica.DTTipoRegistro;
import main.java.logica.ENivelPatrocinio;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;

public class AltaDePatrocinio extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	private JComboBox<String> EventosCB;
	private JComboBox<String> EdicionCB;
	private JComboBox<String> InstitucionesCB;
	private JComboBox<ENivelPatrocinio> NivelCB;
	private JComboBox<String> TipoRegCB;

	private NumberFormatter naturales;
	private NumberFormatter absFloat;
	private NumberFormat formatoDecimal;
	private NumberFormat formatoEntero;

	private JDateChooser dateChooser;

	private IControladorEvento ICE;
	private IControladorUsuario ICU;


	private void iniciarComboBox() {
		this.EventosCB  = new JComboBox<>();
		this.EdicionCB = new JComboBox<>();
		this.InstitucionesCB = new JComboBox<>();
		this.TipoRegCB = new JComboBox<>();
		this.NivelCB = new JComboBox<>();
	}

	private void iniciarInterfaces() {
		Fabrica fabrica = Fabrica.getInstance();
		ICE = fabrica.getIControladorEvento();
		ICU = fabrica.getIControladorUsuario();
	}

	public AltaDePatrocinio() {
		this.setTitle("Alta de Patrocinio");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

    	formatoDecimal = NumberFormat.getNumberInstance();
    	formatoDecimal.setMinimumFractionDigits(2); // Ensure at least 2 decimal places
    	formatoDecimal.setMaximumFractionDigits(2);

    	formatoEntero = NumberFormat.getIntegerInstance();

        iniciarInterfaces();
        iniciarComboBox();
        cargarComboBox();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{67, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 18, 0, 16, 18, -19, 0, 10, 0, 6, 0, 11, 0, 2, 0, -4, 48, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblEventos = new JLabel("Eventos:");
		GridBagConstraints gbc_lblEventos = new GridBagConstraints();
		gbc_lblEventos.insets = new Insets(50, 0, 5, 5);
		gbc_lblEventos.anchor = GridBagConstraints.WEST;
		gbc_lblEventos.gridx = 1;
		gbc_lblEventos.gridy = 1;
		getContentPane().add(lblEventos, gbc_lblEventos);

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(50, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		getContentPane().add(EventosCB, gbc_comboBox);

		JLabel lblEdiciones = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblEdiciones = new GridBagConstraints();
		gbc_lblEdiciones.anchor = GridBagConstraints.WEST;
		gbc_lblEdiciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdiciones.gridx = 1;
		gbc_lblEdiciones.gridy = 3;
		getContentPane().add(lblEdiciones, gbc_lblEdiciones);

		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 3;
		getContentPane().add(EdicionCB, gbc_comboBox_1);

		JLabel lblInstituciones = new JLabel("Instituciones:");
		GridBagConstraints gbc_lblInstituciones = new GridBagConstraints();
		gbc_lblInstituciones.anchor = GridBagConstraints.WEST;
		gbc_lblInstituciones.insets = new Insets(0, 0, 10, 5);
		gbc_lblInstituciones.gridx = 1;
		gbc_lblInstituciones.gridy = 5;
		getContentPane().add(lblInstituciones, gbc_lblInstituciones);

		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 10, 0);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 2;
		gbc_comboBox_2.gridy = 5;
		getContentPane().add(InstitucionesCB, gbc_comboBox_2);

		JLabel lblNivel = new JLabel("Nivel:");
		GridBagConstraints gbc_lblNivel = new GridBagConstraints();
		gbc_lblNivel.anchor = GridBagConstraints.WEST;
		gbc_lblNivel.insets = new Insets(10, 0, 5, 5);
		gbc_lblNivel.gridx = 1;
		gbc_lblNivel.gridy = 7;
		getContentPane().add(lblNivel, gbc_lblNivel);

		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.insets = new Insets(10, 0, 5, 0);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 2;
		gbc_comboBox_3.gridy = 7;
		getContentPane().add(NivelCB, gbc_comboBox_3);

		JLabel lblTipoDeRegistro = new JLabel("Tipo de registro:");
		GridBagConstraints gbc_lblTipoDeRegistro = new GridBagConstraints();
		gbc_lblTipoDeRegistro.anchor = GridBagConstraints.WEST;
		gbc_lblTipoDeRegistro.insets = new Insets(10, 0, 5, 5);
		gbc_lblTipoDeRegistro.gridx = 1;
		gbc_lblTipoDeRegistro.gridy = 9;
		getContentPane().add(lblTipoDeRegistro, gbc_lblTipoDeRegistro);


		GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
		gbc_comboBox_4.insets = new Insets(10, 0, 5, 0);
		gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_4.gridx = 2;
		gbc_comboBox_4.gridy = 9;
		getContentPane().add(TipoRegCB, gbc_comboBox_4);

		JLabel lblAporteEconomico = new JLabel("Aporte economico:");
		GridBagConstraints gbc_lblAporteEconomico = new GridBagConstraints();
		gbc_lblAporteEconomico.anchor = GridBagConstraints.WEST;
		gbc_lblAporteEconomico.insets = new Insets(10, 0, 10, 5);
		gbc_lblAporteEconomico.gridx = 1;
		gbc_lblAporteEconomico.gridy = 11;
		getContentPane().add(lblAporteEconomico, gbc_lblAporteEconomico);

		validarIngresarNumeros();
		textField = new JFormattedTextField(absFloat);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(10, 0, 10, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 11;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblCantidadAOtorgar = new JLabel("Cantidad a otorgar:");
		GridBagConstraints gbc_lblCantidadAOtorgar = new GridBagConstraints();
		gbc_lblCantidadAOtorgar.anchor = GridBagConstraints.WEST;
		gbc_lblCantidadAOtorgar.insets = new Insets(0, 0, 10, 5);
		gbc_lblCantidadAOtorgar.gridx = 1;
		gbc_lblCantidadAOtorgar.gridy = 13;
		getContentPane().add(lblCantidadAOtorgar, gbc_lblCantidadAOtorgar);

		textField_1 = new JFormattedTextField(naturales);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 10, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 13;
		getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha:");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.WEST;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 14;
		getContentPane().add(lblFecha, gbc_lblFecha);

		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 14;
		getContentPane().add(dateChooser, gbc_dateChooser);

		JLabel lblCodigo = new JLabel("Codigo:");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.WEST;
		gbc_lblCodigo.insets = new Insets(10, 0, 5, 5);
		gbc_lblCodigo.gridx = 1;
		gbc_lblCodigo.gridy = 15;
		getContentPane().add(lblCodigo, gbc_lblCodigo);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(10, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 15;
		getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 17;
		getContentPane().add(panel, gbc_panel);

		JButton btnConfirmar = new JButton("confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					darAltaDePatrocinio();
				} catch (NoExisteInstitucion | NoExisteEvento | NoExisteEdicionExcepcion | NoExisteTipoRegistro e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnConfirmar);

		JButton btnCancelar = new JButton("cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TerminarCaso();
			}

		});
		panel.add(btnCancelar);

		EventosCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(EventosCB!= null && !EventosCB.getSelectedItem().equals("seleccionar")) {
					EdicionCB.removeAllItems();
					TipoRegCB.removeAllItems();
					cargarEdiciones();
				}
			}
		});

		EdicionCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(EventosCB!= null && EdicionCB != null ) {
					TipoRegCB.removeAllItems();
					cargarRegistros();
				}
			}
		});
	}

	private void cargarComboBox() {
		this.EdicionCB.addItem("seleccionar");
		this.InstitucionesCB.addItem("seleccionar");
		this.EventosCB.addItem("seleccionar");
		this.TipoRegCB.addItem("seleccionar");
		cargarEventos();
		cargarInsituciones();
		cargarNivel();
	}

	private void cargarEventos() {
		Set<DTEvento> eventos = ICE.listarEventos();
		List<DTEvento> evs = eventos.stream().sorted(Comparator.comparing(DTEvento::getNombre)).collect(Collectors.toList());
		for(DTEvento evento: evs) {
			this.EventosCB.addItem(evento.getNombre());
		}
	}

	private void cargarEdiciones() {
		Set<DTEdicion> ediciones = ICE.listarEdiciones(this.EventosCB.getSelectedItem().toString());
		List<DTEdicion> eds = ediciones.stream().sorted(Comparator.comparing(DTEdicion::getNombre)).collect(Collectors.toList());
		for(DTEdicion edicion: eds) {
			EdicionCB.addItem(edicion.getNombre());
		}
	}

	private void cargarInsituciones() {
		Map<String, DTInstitucion> instituciones = ICU.listarInstituciones();
		Set<String> insts = new HashSet<>();
		for(DTInstitucion dt:instituciones.values()) {
			insts.add(dt.getNombre());
		}
		Set<String> instsORd = new TreeSet<>(insts);


		for(String inst:  instsORd) {
			InstitucionesCB.addItem(inst);
		}
	}

	private void cargarNivel() {
		ENivelPatrocinio[] nivel = ENivelPatrocinio.values();
		for(ENivelPatrocinio n: nivel) {
			this.NivelCB.addItem(n);
		}
	}

	private void cargarRegistros() {
		if(EventosCB != null && EdicionCB!= null && EventosCB.getSelectedItem()!= null && EdicionCB.getSelectedItem()!= null) {
			if(!EventosCB.getSelectedItem().toString().equals("seleccionar") && !EdicionCB.getSelectedItem().toString().equals("seleccionar")) {
				String evento = EventosCB.getSelectedItem().toString();
				String edicion = EdicionCB.getSelectedItem().toString();
				Set<DTTipoRegistro> registros = ICE.listarTipoRegistro(evento,edicion);
				List<DTTipoRegistro> regs = registros.stream().sorted(Comparator.comparing(DTTipoRegistro::getNombre)).collect(Collectors.toList());
				for(DTTipoRegistro registro:regs) {
					TipoRegCB.addItem(registro.getNombre());
				}
			}
		}
	}

	private boolean SonValidosDatos() {
		if(EventosCB.getSelectedItem() != null && EdicionCB.getSelectedItem()  != null && EdicionCB.getSelectedItem() != null && TipoRegCB.getSelectedItem() != null ) {
			Date fecha 			=  dateChooser.getDate();
			String evento 		=  EventosCB.getSelectedItem().toString();
			String edicion		=  EdicionCB.getSelectedItem().toString();
			String institucion  =  InstitucionesCB.getSelectedItem().toString();
			String nivel		=  NivelCB.getSelectedItem().toString();
			String tiporeg		=  TipoRegCB.getSelectedItem().toString();

			if(!evento.equals("seleccionar") && !edicion.equals("seleccionar") && !institucion.equals("seleccionar") && !tiporeg.equals("seleccionar") && fecha != null) {
				if(!textField.getText().equals("") && !textField_1.getText().equals("") && !textField_2.getText().equals("")) {
					return true;
				}
			}
		}
		JOptionPane.showMessageDialog(this, "No puede haber campos vacios", "Alta de patrocinio",JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private void validarIngresarNumeros() {
		naturales = new NumberFormatter(formatoEntero);
		naturales.setValueClass(Integer.class);
		naturales.setAllowsInvalid(false);
		naturales.setMinimum(0);
		naturales.setMaximum(Integer.MAX_VALUE);

		absFloat = new NumberFormatter(formatoDecimal);
		absFloat.setValueClass(Float.class);
		absFloat.setAllowsInvalid(false);
		absFloat.setMinimum(Float.MIN_VALUE);
		absFloat.setMaximum(Float.MAX_VALUE);
	}

	private void darAltaDePatrocinio() throws NoExisteInstitucion, NoExisteEvento, NoExisteEdicionExcepcion, NoExisteTipoRegistro {
		if(SonValidosDatos()) {
			String evento 		   =  EventosCB.getSelectedItem().toString();
			String edicion		   =  EdicionCB.getSelectedItem().toString();
			String institucion     =  InstitucionesCB.getSelectedItem().toString();
			ENivelPatrocinio nivel =  ENivelPatrocinio.valueOf(NivelCB.getSelectedItem().toString()) ;
			String tiporeg		   =  TipoRegCB.getSelectedItem().toString();
			String aports  	   =  textField.getText().toString();
			String aportes = aports.substring(0, aports.length() - 2).replace(",","").replace(".", "");

			Float aporte 		   = Float.parseFloat(aportes);
			String dec       	   = aports.substring(aports.length() - 2, aports.length());
			Float decimal		   = Float.parseFloat(dec);
			Float aporteCompleto   = aporte+decimal/100;

			Integer cantidad	   =  Integer.parseInt(textField_1.getText().toString().replace(".",""));
			String codigo		   =  textField_2.getText();
			LocalDate fecha        = LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault());
			try {
				ICU.crearPatrocinio(aporteCompleto,codigo, fecha, cantidad, nivel, tiporeg, evento, edicion,institucion);
				JOptionPane.showMessageDialog(this,"Patrocinio registrado con exito", "Alta de Patrocinio",JOptionPane.INFORMATION_MESSAGE);
				TerminarCaso();
			}catch(YatienePatrocinioException ex) {//cambiar institucion
		        int opcion = JOptionPane.showConfirmDialog(this, ex.getMessage(),"Alta de Patrocinio", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);

		        if(opcion == 1) {
		        	TerminarCaso();
		        }else {

		        }
			}catch( SuperaCantidadGratuitos ex) {//costo de registros gratuitos supera el 20 % del aporte
		        int opcion = JOptionPane.showConfirmDialog(this, ex.getMessage(),"Alta de Patrocinio", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
		        if(opcion == 1) {
		        	TerminarCaso();
		        }else {
		    		textField_1.setText("0");
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {

		}
	}

	private void TerminarCaso() {
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		this.setVisible(false);
	}
}



