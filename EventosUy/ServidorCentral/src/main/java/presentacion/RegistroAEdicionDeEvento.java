package main.java.presentacion;
import java.awt.Component;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.toedter.calendar.JDateChooser;

import main.java.excepciones.NoTieneCupo;
import main.java.excepciones.YaTieneCompra;
import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.DTTipoRegistro;
import main.java.logica.DTUsuario;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;

public class RegistroAEdicionDeEvento extends JInternalFrame {
	private JTextField TFCosto;

	private IControladorUsuario ICU;
	private IControladorEvento ICE;

	private JComboBox<String> CBAsistente;
	private JComboBox<String> CBEvento;
	private JComboBox<String> CBEdicion;
	private JComboBox<String> CBTipoRegistro;

	private JDateChooser dateChooser;

	private Map<String, DTUsuario> asistentes;
	private Set<DTEvento> eventos;

	private void llamarInterfaces() {
		Fabrica F = Fabrica.getInstance();
		this.ICU = F.getIControladorUsuario();
		this.ICE = F.getIControladorEvento();
	}

	public RegistroAEdicionDeEvento(Map<String, DTUsuario> map) {
		llamarInterfaces();

		this.asistentes = map;
		this.eventos = ICE.listarEventos();

		CBAsistente = new JComboBox<>();
		resetearComboBox(CBAsistente, "Seleccione un asistente:");
		CBEdicion = new JComboBox<>();
		resetearComboBox(CBEdicion, "Seleccione una edicion:");
		CBEvento = new JComboBox<>();
		resetearComboBox(CBEvento, "Seleccione un evento:");
		CBTipoRegistro = new JComboBox<>();
		resetearComboBox(CBTipoRegistro, "Seleccione un tipo de registro:");

		this.setTitle("Registro a edicion de evento");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

        GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 93, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.25, 1.0, 0.0, 1.0, 0.25};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblAsistente = new JLabel("Asistente:");
		GridBagConstraints gbc_lblAsistente = new GridBagConstraints();
		gbc_lblAsistente.insets = new Insets(0, 0, 5, 5);
		gbc_lblAsistente.gridx = 1;
		gbc_lblAsistente.gridy = 2;
		getContentPane().add(lblAsistente, gbc_lblAsistente);

		cargarAsistentes();
		GridBagConstraints gbc_CBAsistente = new GridBagConstraints();
		gbc_CBAsistente.insets = new Insets(0, 0, 5, 5);
		gbc_CBAsistente.fill = GridBagConstraints.BOTH;
		gbc_CBAsistente.gridx = 3;
		gbc_CBAsistente.gridy = 2;
		getContentPane().add(CBAsistente, gbc_CBAsistente);

		JLabel lblEvento = new JLabel("Evento:");
		GridBagConstraints gbc_lblEvento = new GridBagConstraints();
		gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblEvento.gridx = 1;
		gbc_lblEvento.gridy = 3;
		getContentPane().add(lblEvento, gbc_lblEvento);

		CBEvento.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if(CBEvento.getSelectedItem() != null && !CBEvento.getSelectedItem().toString().equals("seleccionar")) {
					cargarEdiciones(CBEvento.getSelectedItem().toString());
				}
			}
		});

		cargarEventos();
		GridBagConstraints gbc_CBEvento = new GridBagConstraints();
		gbc_CBEvento.insets = new Insets(0, 0, 5, 5);
		gbc_CBEvento.fill = GridBagConstraints.BOTH;
		gbc_CBEvento.gridx = 3;
		gbc_CBEvento.gridy = 3;
		getContentPane().add(CBEvento, gbc_CBEvento);

		JLabel lblEdicion = new JLabel("Edicion:");
		GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
		gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicion.gridx = 1;
		gbc_lblEdicion.gridy = 4;
		getContentPane().add(lblEdicion, gbc_lblEdicion);

		CBEdicion.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(CBEdicion.getSelectedItem()!= null && CBEvento.getSelectedItem() != null) {
					if(!CBEdicion.getSelectedItem().toString().equals("seleccionar") && !CBEvento.getSelectedItem().toString().equals("seleccionar")) {
						cargarTiposDeRegistro(CBEdicion.getSelectedItem().toString(),CBEvento.getSelectedItem().toString());
					}
				}
			}
		});

		GridBagConstraints gbc_CBEdicion = new GridBagConstraints();
		gbc_CBEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_CBEdicion.fill = GridBagConstraints.BOTH;
		gbc_CBEdicion.gridx = 3;
		gbc_CBEdicion.gridy = 4;
		getContentPane().add(CBEdicion, gbc_CBEdicion);

		JLabel lblTipoRegistro = new JLabel("Tipo de registro:");
		GridBagConstraints gbc_lblTipoRegistro = new GridBagConstraints();
		gbc_lblTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoRegistro.gridx = 1;
		gbc_lblTipoRegistro.gridy = 5;
		getContentPane().add(lblTipoRegistro, gbc_lblTipoRegistro);

		CBTipoRegistro.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(CBEdicion.getSelectedItem()!= null && CBEvento.getSelectedItem() != null && CBTipoRegistro.getSelectedItem() != null) {
					if(!CBEdicion.getSelectedItem().toString().equals("seleccionar") && !CBEvento.getSelectedItem().toString().equals("seleccionar") && !CBTipoRegistro.getSelectedItem().toString().equals("seleccionar")) {
						cargarCosto(CBTipoRegistro.getSelectedItem().toString(),CBEdicion.getSelectedItem().toString(),CBEvento.getSelectedItem().toString() );
					}
				}
			}
		});

		GridBagConstraints gbc_CBTipoRegistro = new GridBagConstraints();
		gbc_CBTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_CBTipoRegistro.fill = GridBagConstraints.BOTH;
		gbc_CBTipoRegistro.gridx = 3;
		gbc_CBTipoRegistro.gridy = 5;
		getContentPane().add(CBTipoRegistro, gbc_CBTipoRegistro);

		JLabel lblCosto = new JLabel("Costo:");
		GridBagConstraints gbc_lblCosto = new GridBagConstraints();
		gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
		gbc_lblCosto.gridx = 1;
		gbc_lblCosto.gridy = 6;
		getContentPane().add(lblCosto, gbc_lblCosto);

		TFCosto = new JTextField();
		TFCosto.setEditable(false);
		GridBagConstraints gbc_tFCosto = new GridBagConstraints();
		gbc_tFCosto.insets = new Insets(0, 0, 5, 5);
		gbc_tFCosto.fill = GridBagConstraints.BOTH;
		gbc_tFCosto.gridx = 3;
		gbc_tFCosto.gridy = 6;
		getContentPane().add(TFCosto, gbc_tFCosto);
		TFCosto.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha:");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 7;
		getContentPane().add(lblFecha, gbc_lblFecha);

		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 3;
		gbc_dateChooser.gridy = 7;
		getContentPane().add(dateChooser, gbc_dateChooser);

		JPanel panBotones = new JPanel();
		GridBagConstraints gbc_panBotones = new GridBagConstraints();
		gbc_panBotones.insets = new Insets(0, 0, 5, 5);
		gbc_panBotones.fill = GridBagConstraints.BOTH;
		gbc_panBotones.gridx = 3;
		gbc_panBotones.gridy = 8;
		getContentPane().add(panBotones, gbc_panBotones);
		panBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnConfirmar = new JButton("Confirmar");
		panBotones.add(btnConfirmar);

		JButton btnCancelar = new JButton("Cancelar");
		panBotones.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				terminarCaso();
			}
		});
		btnConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					altaDeCompra();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void cargarAsistentes() {
		Set<String> result = new HashSet<>();
		if (this.asistentes != null) {
			for(DTUsuario inst: this.asistentes.values()) {
				result.add(inst.getNickName());
			}
		}
		Set<String> asistORd = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		asistORd.addAll(result);
		cargarDatos(CBAsistente,asistORd);
		resetearComboBox(CBAsistente, "Seleccione un asistente:");
	}

	private void cargarEventos() {
		Set<String> result = new HashSet<>();
		if (this.eventos != null) {
			for(DTEvento dte: this.eventos ) {
				result.add(dte.getNombre());
			}

		}
		Set<String> events = new TreeSet<>(result);
		cargarDatos(CBEvento,events);
		resetearComboBox(CBEvento, "Seleccione un evento:");
	}

	private void cargarEdiciones(String evento) {
		Set<DTEdicion> ediciones = ICE.listarEdiciones(evento);
		Set<String> result = new HashSet<>();
		if (ediciones != null) {
			for(DTEdicion edi:ediciones) {
				result.add(edi.getNombre());
			}
		}
		Set<String> edis = new TreeSet<>(result);
		cargarDatos(CBEdicion,edis);
		resetearComboBox(CBEdicion, "Seleccione una edicion:");
	}

	private void cargarTiposDeRegistro(String edicion, String evento) {
		Set<DTTipoRegistro> registros = ICE.listarTipoRegistro(evento,edicion);
		Set<String> result = new HashSet<>();
		if(registros != null) {
			for(DTTipoRegistro dtr : registros) {
				result.add(dtr.getNombre());
			}
		}
		Set<String> regs = new TreeSet<>(result);
		cargarDatos(CBTipoRegistro,regs);
		resetearComboBox(CBTipoRegistro, "Seleccione un tipo de registro:");
	}

	private void cargarCosto(String tipo,String edicion, String evento) {
		TFCosto.setText(String.valueOf(ICE.getTipoRegistro(evento, edicion, tipo).getCosto()));
	}

	private void terminarCaso() {
		TFCosto.setText("");
		resetearComboBox(CBAsistente, "Seleccione un asistente:");
		resetearComboBox(CBEdicion, "Seleccione una edicion:");
		resetearComboBox(CBEvento, "Seleccione un evento:");
		resetearComboBox(CBTipoRegistro, "Seleccione un tipo de registro:");
		this.setVisible(false);
	}

	boolean sonValidosDatos() {
		if(CBAsistente == null || CBEdicion== null || CBEvento == null || CBTipoRegistro == null ||  this.dateChooser.getDate() == null) {
			return false;
		}
		if(!CBAsistente.getSelectedItem().equals("Seleccione un asistente:") && !CBEdicion.getSelectedItem().equals("Seleccione una edicion:") && !CBEvento.getSelectedItem().equals("Seleccione un evento:") && !CBTipoRegistro.getSelectedItem().equals("Seleccione un tipo de registro:") && this.dateChooser.getDate() != null) {
			return true;
		}
		return  false;
	}

	private void altaDeCompra() throws NoTieneCupo, YaTieneCompra {
		if(sonValidosDatos()) {
			try {
				Date fecha = this.dateChooser.getDate();
				LocalDate localFecha = LocalDate.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
				String asistente = CBAsistente.getSelectedItem().toString();

				String edicion = CBEdicion.getSelectedItem().toString();
				String evento =  CBEvento.getSelectedItem().toString();
				String registro = CBTipoRegistro.getSelectedItem().toString();

				ICE.altaDeRegistro(asistente, evento, edicion, registro, localFecha);
				JOptionPane.showMessageDialog(this,"Compra realizada con exito", "Registro a edicion",JOptionPane.INFORMATION_MESSAGE);
				terminarCaso();
			}catch(YaTieneCompra e) {
		        int opcion = JOptionPane.showConfirmDialog(this, e.getMessage(),"Registro a edicion", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);

		        if(opcion == 1) {
		        	terminarCaso();
		        }else {

		        }
			}catch(NoTieneCupo e) {
		        int opcion = JOptionPane.showConfirmDialog(this, e.getMessage(),"Registro a edicion", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);

		        if(opcion == 1) {
		        	terminarCaso();
		        }else {

		        }
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Realizar Compra",JOptionPane.ERROR_MESSAGE);
			}



		}else {
			JOptionPane.showMessageDialog(this, "No puede haber campos vacios", "Realizar Compra",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void resetearComboBox(JComboBox<String> cb, String texto){
		cb.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public Component getListCellRendererComponent(
	                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            if (index == -1 && value == null) {
	                label.setText(texto);
	            }
	            return label;
	        }
	    });
		cb.setSelectedIndex(-1);
	}

	private void cargarDatos(JComboBox<String> cb, Set<String> datos) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (String item : datos) {
            modelo.addElement(item);
        }
        cb.setModel(modelo);
		cb.setSelectedIndex(-1);
    }

}




