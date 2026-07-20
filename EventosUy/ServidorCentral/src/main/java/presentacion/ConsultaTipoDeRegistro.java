package main.java.presentacion;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.DTTipoRegistro;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;

public class ConsultaTipoDeRegistro extends JInternalFrame {

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
	private Fabrica f = Fabrica.getInstance();
	private IControladorEvento ICE = f.getIControladorEvento();
	private JLabel lblTipoRegistro;
	private JComboBox<String> cboxTipoRegistros;

	public ConsultaTipoDeRegistro() {
		this.setTitle("Consulta tipo de registro");
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JLabel lblEvento = new JLabel("Evento: ");
        GridBagConstraints gbc_lblEvento = new GridBagConstraints();
        gbc_lblEvento.anchor = GridBagConstraints.EAST;
        gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
        gbc_lblEvento.gridx = 1;
        gbc_lblEvento.gridy = 1;
        getContentPane().add(lblEvento, gbc_lblEvento);

        cboxEvento = new JComboBox<>();
        GridBagConstraints gbc_cboxEvento = new GridBagConstraints();
        gbc_cboxEvento.gridwidth = 3;
        gbc_cboxEvento.insets = new Insets(0, 0, 5, 5);
        gbc_cboxEvento.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboxEvento.gridx = 2;
        gbc_cboxEvento.gridy = 1;
        getContentPane().add(cboxEvento, gbc_cboxEvento);

        JLabel lblEdicion = new JLabel("Edicion: ");
        GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
        gbc_lblEdicion.anchor = GridBagConstraints.EAST;
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

        lblTipoRegistro = new JLabel("Tipo de Registro:");
        GridBagConstraints gbc_lblTipoRegistro = new GridBagConstraints();
        gbc_lblTipoRegistro.anchor = GridBagConstraints.EAST;
        gbc_lblTipoRegistro.insets = new Insets(0, 0, 5, 5);
        gbc_lblTipoRegistro.gridx = 1;
        gbc_lblTipoRegistro.gridy = 5;
        getContentPane().add(lblTipoRegistro, gbc_lblTipoRegistro);

        cboxTipoRegistros = new JComboBox();
        GridBagConstraints gbc_cboxTipoRegistross = new GridBagConstraints();
        gbc_cboxTipoRegistross.gridwidth = 3;
        gbc_cboxTipoRegistross.insets = new Insets(0, 0, 5, 5);
        gbc_cboxTipoRegistross.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboxTipoRegistross.gridx = 2;
        gbc_cboxTipoRegistross.gridy = 5;
        getContentPane().add(cboxTipoRegistros, gbc_cboxTipoRegistross);



        JLabel lblNombre = new JLabel("Nombre:");
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.EAST;
        gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombre.gridx = 1;
        gbc_lblNombre.gridy = 7;
        getContentPane().add(lblNombre, gbc_lblNombre);

        textNombre = new JTextField();
        textNombre.setEditable(false);
        GridBagConstraints gbc_textNombre = new GridBagConstraints();
        gbc_textNombre.gridwidth = 3;
        gbc_textNombre.insets = new Insets(0, 0, 5, 5);
        gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textNombre.gridx = 2;
        gbc_textNombre.gridy = 7;
        getContentPane().add(textNombre, gbc_textNombre);
        textNombre.setColumns(10);

        JLabel lblCupos = new JLabel("Cantidad de cupos:");
        GridBagConstraints gbc_lblCupos = new GridBagConstraints();
        gbc_lblCupos.anchor = GridBagConstraints.EAST;
        gbc_lblCupos.insets = new Insets(0, 0, 5, 5);
        gbc_lblCupos.gridx = 1;
        gbc_lblCupos.gridy = 9;
        getContentPane().add(lblCupos, gbc_lblCupos);

        textCupo = new JFormattedTextField(formatoEntero);
        textCupo.setEditable(false);
        GridBagConstraints gbc_textCupo = new GridBagConstraints();
        gbc_textCupo.gridwidth = 3;
        gbc_textCupo.insets = new Insets(0, 0, 5, 5);
        gbc_textCupo.fill = GridBagConstraints.HORIZONTAL;
        gbc_textCupo.gridx = 2;
        gbc_textCupo.gridy = 9;
        getContentPane().add(textCupo, gbc_textCupo);

        JLabel lblCosto = new JLabel("Costo: ");
        GridBagConstraints gbc_lblCosto = new GridBagConstraints();
        gbc_lblCosto.anchor = GridBagConstraints.EAST;
        gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
        gbc_lblCosto.gridx = 1;
        gbc_lblCosto.gridy = 11;
        getContentPane().add(lblCosto, gbc_lblCosto);

        textCosto = new JFormattedTextField(formatoDecimal);
        textCosto.setEditable(false);
        GridBagConstraints gbc_textCosto = new GridBagConstraints();
        gbc_textCosto.gridwidth = 3;
        gbc_textCosto.insets = new Insets(0, 0, 5, 5);
        gbc_textCosto.fill = GridBagConstraints.HORIZONTAL;
        gbc_textCosto.gridx = 2;
        gbc_textCosto.gridy = 11;
        getContentPane().add(textCosto, gbc_textCosto);

        JLabel lblDescripcion = new JLabel("Descirpcion: ");
        GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
        gbc_lblDescripcion.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripcion.gridx = 1;
        gbc_lblDescripcion.gridy = 13;
        getContentPane().add(lblDescripcion, gbc_lblDescripcion);

        textDescripcion = new JTextArea();
        GridBagConstraints gbc_textDescripcion = new GridBagConstraints();
        gbc_textDescripcion.gridwidth = 3;
        gbc_textDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_textDescripcion.fill = GridBagConstraints.BOTH;
        gbc_textDescripcion.gridx = 2;
        gbc_textDescripcion.gridy = 13;
        getContentPane().add(textDescripcion, gbc_textDescripcion);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        	}
        });

        btnCancelar = new JButton("Salir");
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.gridx = 4;
        gbc_btnCancelar.gridy = 15;
        getContentPane().add(btnCancelar, gbc_btnCancelar);



        // ______________________ EVENTOS __________________________________________

        cboxEvento.addItemListener(new ItemListener() {
		    @Override
			public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		        	cargaDeEdiciones();
		        }
		    }
		});
        cboxEdicion.addItemListener(new ItemListener() {
		    @Override
			public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		        	cargaDeTipoRegistro();
		        }
		    }
		});
        cboxTipoRegistros.addItemListener(new ItemListener() {
		    @Override
			public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		        	cargaDatos();
		        }
		    }
		});

        btnCancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		limpiar();
        		setVisible(false);
        	}
        });

	}

	public void cargaDeEventos() {

		Set<DTEvento> dteventos = ICE.listarEventos();
        Set<String> eventos = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (DTEvento dte : dteventos) {
        	eventos.add(dte.getNombre());
        }
         for (String nom : eventos) {
        	 cboxEvento.addItem(nom);
        }
    }

	void cargaDeEdiciones() {
		cboxEdicion.removeAllItems();
		cboxEdicion.addItem("");
		if (cboxEvento.getSelectedItem() != null) {
			String eve = cboxEvento.getSelectedItem().toString();
			Set<DTEdicion> edis = ICE.listarEdiciones(eve);

			Set<String> us = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			for(DTEdicion e:edis) {
				us.add(e.getNombre());
			}

			for(String edi : us) {
				cboxEdicion.addItem(edi);
			}
		}
    }
	void cargaDeTipoRegistro() {
		cboxTipoRegistros.removeAllItems();
		cboxTipoRegistros.addItem("");
		if (cboxEvento.getSelectedItem() != null && cboxEdicion.getSelectedItem() != null) {
			String eve = cboxEvento.getSelectedItem().toString();
			String edi = cboxEdicion.getSelectedItem().toString();
			Set<DTTipoRegistro> tps = ICE.listarTipoRegistro(eve,edi);
			if (tps != null) {
				for(DTTipoRegistro tipo : tps) {
					cboxTipoRegistros.addItem(tipo.getNombre());
				}
			}
		}
    }
	void cargaDatos(){
		 if(cboxEvento.getSelectedItem() != null | cboxEdicion.getSelectedItem() != null | cboxTipoRegistros.getSelectedItem() != null) {
			 String eve = cboxEvento.getSelectedItem().toString();
			 String edi = cboxEdicion.getSelectedItem().toString();
			 String tp = cboxTipoRegistros.getSelectedItem().toString();
			 DTTipoRegistro dttp = ICE.getTipoRegistro(eve, edi, tp);
			 if (dttp != null){
			 textNombre.setText(dttp.getNombre());
			textCupo.setValue(dttp.getCupos());
			textCosto.setValue(dttp.getCosto());
			textDescripcion.setText(dttp.getDescripcion());
			 }
			 else {
				 textNombre.setText("");
					textCupo.setText("");
					textCosto.setText("");
					textDescripcion.setText("");
			 }
		 }else {
			textNombre.setText("");
			textCupo.setText("");
			textCosto.setText("");
			textDescripcion.setText("");
		 }

	}
	void cargaCBOX(String eve, String edi, String tp) {
		cboxEvento.addItem(eve);
		cboxEvento.setSelectedItem(eve);
		cboxEdicion.addItem(edi);
		cboxEdicion.setSelectedItem(edi);
		cboxTipoRegistros.addItem(tp);
		cboxTipoRegistros.setSelectedItem(tp);
	}
	void limpiar() {
		cboxEvento.removeAllItems();
		cboxEdicion.removeAllItems();
		cboxTipoRegistros.removeAllItems();
		textNombre.setText("");
		textCupo.setText("");
		textCosto.setText("");
		textDescripcion.setText("");
	}
}
