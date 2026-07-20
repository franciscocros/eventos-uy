package main.java.presentacion;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.NumberFormatter;

import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;

public class AltaTipoDeRegistro extends JInternalFrame {
	private JTextField textNombre;
	private JComboBox<String> cboxEvento;
	private  JComboBox<String> cboxEdicion;
	private JFormattedTextField textCupo;
	private JFormattedTextField textCosto;
	private JTextArea textDescripcion;
	JButton btnAceptar;
	JButton btnCancelar;
	NumberFormat formatoDecimal = NumberFormat.getNumberInstance();
	NumberFormat formatoEntero = NumberFormat.getIntegerInstance();
	Fabrica f = Fabrica.getInstance();
	IControladorEvento ICE = f.getIControladorEvento();


	public AltaTipoDeRegistro() {

		formatoDecimal.setGroupingUsed(false);

		NumberFormatter naturales = new NumberFormatter(formatoEntero);
		naturales.setValueClass(Integer.class);
		naturales.setAllowsInvalid(false);
		naturales.setMinimum(0);
		naturales.setMaximum(Integer.MAX_VALUE);

		NumberFormatter absFloat = new NumberFormatter(formatoDecimal);
		absFloat.setValueClass(Double.class);
		absFloat.setMinimum(0.0d);
		absFloat.setMaximum(Double.MAX_VALUE);

        this.setTitle("Alta tipo de Registro");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{66, 0, 0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JLabel lblEvento = new JLabel("Evento: ");
        GridBagConstraints gbc_lblEvento = new GridBagConstraints();
        gbc_lblEvento.anchor = GridBagConstraints.WEST;
        gbc_lblEvento.insets = new Insets(50, 0, 5, 5);
        gbc_lblEvento.gridx = 1;
        gbc_lblEvento.gridy = 1;
        getContentPane().add(lblEvento, gbc_lblEvento);

        cboxEvento = new JComboBox<>();
        GridBagConstraints gbc_cboxEvento = new GridBagConstraints();
        gbc_cboxEvento.gridwidth = 3;
        gbc_cboxEvento.insets = new Insets(50, 0, 5, 5);
        gbc_cboxEvento.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboxEvento.gridx = 2;
        gbc_cboxEvento.gridy = 1;
        getContentPane().add(cboxEvento, gbc_cboxEvento);

        JLabel lblEdicion = new JLabel("Edicion: ");
        GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
        gbc_lblEdicion.anchor = GridBagConstraints.WEST;
        gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
        gbc_lblEdicion.gridx = 1;
        gbc_lblEdicion.gridy = 3;
        getContentPane().add(lblEdicion, gbc_lblEdicion);

         cboxEdicion = new JComboBox();
        GridBagConstraints gbc_cboxEdicion = new GridBagConstraints();
        gbc_cboxEdicion.gridwidth = 3;
        gbc_cboxEdicion.insets = new Insets(0, 0, 5, 5);
        gbc_cboxEdicion.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboxEdicion.gridx = 2;
        gbc_cboxEdicion.gridy = 3;
        getContentPane().add(cboxEdicion, gbc_cboxEdicion);

        JLabel lblNombre = new JLabel("Nombre:");
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.WEST;
        gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombre.gridx = 1;
        gbc_lblNombre.gridy = 5;
        getContentPane().add(lblNombre, gbc_lblNombre);

        textNombre = new JTextField();
        GridBagConstraints gbc_textNombre = new GridBagConstraints();
        gbc_textNombre.gridwidth = 3;
        gbc_textNombre.insets = new Insets(0, 0, 5, 5);
        gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textNombre.gridx = 2;
        gbc_textNombre.gridy = 5;
        getContentPane().add(textNombre, gbc_textNombre);
        textNombre.setColumns(10);

        JLabel lblCupos = new JLabel("Cantidad de cupos:");
        GridBagConstraints gbc_lblCupos = new GridBagConstraints();
        gbc_lblCupos.anchor = GridBagConstraints.WEST;
        gbc_lblCupos.insets = new Insets(0, 0, 5, 5);
        gbc_lblCupos.gridx = 1;
        gbc_lblCupos.gridy = 7;
        getContentPane().add(lblCupos, gbc_lblCupos);

        textCupo = new JFormattedTextField(naturales);
        GridBagConstraints gbc_textCupo = new GridBagConstraints();
        gbc_textCupo.gridwidth = 3;
        gbc_textCupo.insets = new Insets(0, 0, 5, 5);
        gbc_textCupo.fill = GridBagConstraints.HORIZONTAL;
        gbc_textCupo.gridx = 2;
        gbc_textCupo.gridy = 7;
        getContentPane().add(textCupo, gbc_textCupo);

        JLabel lblCosto = new JLabel("Costo: ");
        GridBagConstraints gbc_lblCosto = new GridBagConstraints();
        gbc_lblCosto.anchor = GridBagConstraints.WEST;
        gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
        gbc_lblCosto.gridx = 1;
        gbc_lblCosto.gridy = 9;
        getContentPane().add(lblCosto, gbc_lblCosto);

        textCosto = new JFormattedTextField(absFloat);
        GridBagConstraints gbc_textCosto = new GridBagConstraints();
        gbc_textCosto.gridwidth = 3;
        gbc_textCosto.insets = new Insets(0, 0, 5, 5);
        gbc_textCosto.fill = GridBagConstraints.HORIZONTAL;
        gbc_textCosto.gridx = 2;
        gbc_textCosto.gridy = 9;
        getContentPane().add(textCosto, gbc_textCosto);

        JLabel lblDescripcion = new JLabel("Descripcion: ");
        GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
        gbc_lblDescripcion.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripcion.gridx = 1;
        gbc_lblDescripcion.gridy = 11;
        getContentPane().add(lblDescripcion, gbc_lblDescripcion);

        textDescripcion = new JTextArea();
        textDescripcion.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        GridBagConstraints gbc_textDescripcion = new GridBagConstraints();
        gbc_textDescripcion.gridwidth = 3;
        gbc_textDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_textDescripcion.fill = GridBagConstraints.BOTH;
        gbc_textDescripcion.gridx = 2;
        gbc_textDescripcion.gridy = 11;
        getContentPane().add(textDescripcion, gbc_textDescripcion);

        btnAceptar = new JButton("Aceptar");
        
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
        gbc_btnAceptar.gridx = 2;
        gbc_btnAceptar.gridy = 13;
        getContentPane().add(btnAceptar, gbc_btnAceptar);

        btnCancelar = new JButton("Salir");


        // ______________________ EVENTOS __________________________________________

        cboxEvento.addItemListener(new ItemListener() {
		    @Override
			public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		        	cargaDeEdiciones();
		        }
		    }
		});

        btnAceptar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if (chequeoDeDatos()) {
        			float costo = Float.parseFloat( textCosto.getText());
        			int cupo = Integer.parseInt( textCupo.getText());
        			try {
						ICE.addTipoRegistro(cboxEvento.getSelectedItem().toString(), cboxEdicion.getSelectedItem().toString(), textNombre.getText(), textDescripcion.getText() , costo,cupo);
						limpiarVariables();
						JOptionPane.showMessageDialog(null, "El tipo de registro fue ingresado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
						cancelarCasoDeUso();
        			} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
        		}

        	}
        });
        btnCancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		limpiarCampos();
        		setVisible(false);
        	}
        });
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.gridx = 4;
        gbc_btnCancelar.gridy = 13;
        getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

	void cargaDeEventos() {

		Set<DTEvento> dteventos = ICE.listarEventos();
        Set<String> eventos = new HashSet<>();
        for (DTEvento dte : dteventos) {
        	eventos.add(dte.getNombre());
        }
        Set<String> events = new TreeSet<>(eventos);
         for (String nom : events) {
        	 cboxEvento.addItem(nom);
        }
    }

	void cargaDeEdiciones() {
		cboxEdicion.removeAllItems();
		cboxEdicion.addItem("");
		if (cboxEvento.getSelectedItem() != null) {
			String eve = cboxEvento.getSelectedItem().toString();
			Set<DTEdicion> edis = ICE.listarEdiciones(eve);
			for(DTEdicion edi : edis) {
				cboxEdicion.addItem(edi.getNombre());
			}
		}
    }
	boolean chequeoDeDatos() {
		if (textNombre.getText().equals("")) {
		    JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
		    return false;
		}
		if(textCupo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "El campo Cupo no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
		    return false;
		}
		if(textCosto.getText().equals("") || (textCosto.getText() == "")) {
			JOptionPane.showMessageDialog(null, "El campo Costo no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
		    return false;
		}
		if(textDescripcion.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "El campo Descipcion no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
		    return false;
		}
		if(cboxEvento.getSelectedItem() == null || cboxEvento.getSelectedItem().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "El campo Evento no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
		    return false;
		}
		if(cboxEdicion.getSelectedItem() == null || cboxEdicion.getSelectedItem().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "El campo Edicion no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
		    return false;
		}

		return true;
	}

	private void cancelarCasoDeUso() {
		limpiarVariables();
		this.setVisible(false);
	}
	private void limpiarVariables() {
		textNombre.setText("");
		textCupo.setText("");
		textCosto.setText("");
		textDescripcion.setText("");
	}
	void limpiarCampos(){
		cboxEvento.removeAllItems();
		cboxEdicion.removeAllItems();
		limpiarVariables();
	}
}
