package main.java.presentacion;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import main.java.excepciones.NoExisteUsuarioExcepcion;
import main.java.logica.DTCompra;
import main.java.logica.DTUsuario;
import main.java.logica.Fabrica;
import main.java.logica.IControladorUsuario;

public class ConsultaDeRegistro extends JInternalFrame {

	Fabrica f = Fabrica.getInstance();
	IControladorUsuario ICU = f.getIControladorUsuario();

	private JTextField TFEvento;
	private JTextField TFEdicion;
	private JTextField TFTipoRegistro;
	private JTextField TFFecha;
	private JTextField TFCosto;
	private JComboBox<String> CBRegistro;
	private JComboBox<String> CBUsuario;
	private JButton btnCancelar;

	public ConsultaDeRegistro() {

		this.setTitle("Consulta de Registro");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

        GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 93, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.25, 1.0, 0.0, 4.0, 0.25};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblUsuario = new JLabel("Usuario:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 4;
		getContentPane().add(lblUsuario, gbc_lblUsuario);

		CBUsuario = new JComboBox<>();
		resetearComboBox(CBUsuario, "Seleccione un usuario:");
		listarUsuarios();
		CBUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CBRegistro.setEnabled(false);
				if (CBUsuario.getSelectedIndex() != -1){
					if (ICU.esAsistente((String) CBUsuario.getSelectedItem())) {
						CBRegistro.setEnabled(true);
					}
					try {
						listarRegistros((String) CBUsuario.getSelectedItem());
					} catch (NoExisteUsuarioExcepcion e1) { // no pasa nunca este error porque se selecciona usuario desde combobox
						e1.printStackTrace();
					}
				}
				mostrarRegistro(null,null);
			}
		});
		GridBagConstraints gbc_CBUsuario = new GridBagConstraints();
		gbc_CBUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_CBUsuario.fill = GridBagConstraints.BOTH;
		gbc_CBUsuario.gridx = 3;
		gbc_CBUsuario.gridy = 4;
		getContentPane().add(CBUsuario, gbc_CBUsuario);

		JLabel lblRegistro = new JLabel("Registro:");
		GridBagConstraints gbc_lblRegistro = new GridBagConstraints();
		gbc_lblRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistro.gridx = 1;
		gbc_lblRegistro.gridy = 5;
		getContentPane().add(lblRegistro, gbc_lblRegistro);

		CBRegistro = new JComboBox<>();
		resetearComboBox(CBRegistro, "Seleccione un registro:");
		CBRegistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nomEve =  (String) CBRegistro.getSelectedItem();
				if (nomEve != null) {
					nomEve = nomEve.split(" - ")[0];
					mostrarRegistro((String) CBUsuario.getSelectedItem(),nomEve);
				}
			}
		});
		CBRegistro.setEnabled(false);
		GridBagConstraints gbc_CBRegistro = new GridBagConstraints();
		gbc_CBRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_CBRegistro.fill = GridBagConstraints.BOTH;
		gbc_CBRegistro.gridx = 3;
		gbc_CBRegistro.gridy = 5;
		getContentPane().add(CBRegistro, gbc_CBRegistro);

		JLabel lblEvento = new JLabel("Evento:");
		GridBagConstraints gbc_lblEvento = new GridBagConstraints();
		gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblEvento.gridx = 1;
		gbc_lblEvento.gridy = 7;
		getContentPane().add(lblEvento, gbc_lblEvento);

		TFEvento = new JTextField();
		TFEvento.setEditable(false);
		GridBagConstraints gbc_tFEvento = new GridBagConstraints();
		gbc_tFEvento.insets = new Insets(0, 0, 5, 5);
		gbc_tFEvento.fill = GridBagConstraints.BOTH;
		gbc_tFEvento.gridx = 3;
		gbc_tFEvento.gridy = 7;
		getContentPane().add(TFEvento, gbc_tFEvento);
		TFEvento.setColumns(10);

		JLabel lblEdicion = new JLabel("Edicion:");
		GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
		gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicion.gridx = 1;
		gbc_lblEdicion.gridy = 8;
		getContentPane().add(lblEdicion, gbc_lblEdicion);

		TFEdicion = new JTextField();
		TFEdicion.setEditable(false);
		GridBagConstraints gbc_tFEdicion = new GridBagConstraints();
		gbc_tFEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_tFEdicion.fill = GridBagConstraints.BOTH;
		gbc_tFEdicion.gridx = 3;
		gbc_tFEdicion.gridy = 8;
		getContentPane().add(TFEdicion, gbc_tFEdicion);
		TFEdicion.setColumns(10);

		JLabel lblTipoRegistro = new JLabel("Tipo de registro:");
		GridBagConstraints gbc_lblTipoRegistro = new GridBagConstraints();
		gbc_lblTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoRegistro.gridx = 1;
		gbc_lblTipoRegistro.gridy = 9;
		getContentPane().add(lblTipoRegistro, gbc_lblTipoRegistro);

		TFTipoRegistro = new JTextField();
		TFTipoRegistro.setEditable(false);
		GridBagConstraints gbc_tFTipoRegistro = new GridBagConstraints();
		gbc_tFTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_tFTipoRegistro.fill = GridBagConstraints.BOTH;
		gbc_tFTipoRegistro.gridx = 3;
		gbc_tFTipoRegistro.gridy = 9;
		getContentPane().add(TFTipoRegistro, gbc_tFTipoRegistro);
		TFTipoRegistro.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha de registro:");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 10;
		getContentPane().add(lblFecha, gbc_lblFecha);

		TFFecha = new JTextField();
		TFFecha.setEditable(false);
		GridBagConstraints gbc_tFFecha = new GridBagConstraints();
		gbc_tFFecha.insets = new Insets(0, 0, 5, 5);
		gbc_tFFecha.fill = GridBagConstraints.BOTH;
		gbc_tFFecha.gridx = 3;
		gbc_tFFecha.gridy = 10;
		getContentPane().add(TFFecha, gbc_tFFecha);
		TFFecha.setColumns(10);

		JLabel lblCosto = new JLabel("Costo:");
		GridBagConstraints gbc_lblCosto = new GridBagConstraints();
		gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
		gbc_lblCosto.gridx = 1;
		gbc_lblCosto.gridy = 11;
		getContentPane().add(lblCosto, gbc_lblCosto);

		TFCosto = new JTextField();
		TFCosto.setEditable(false);
		GridBagConstraints gbc_tFCosto = new GridBagConstraints();
		gbc_tFCosto.insets = new Insets(0, 0, 5, 5);
		gbc_tFCosto.fill = GridBagConstraints.BOTH;
		gbc_tFCosto.gridx = 3;
		gbc_tFCosto.gridy = 11;
		getContentPane().add(TFCosto, gbc_tFCosto);
		TFCosto.setColumns(10);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelarCasoDeUso();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 12;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

	private void cancelarCasoDeUso() {
		limpiarVariables();
		this.setVisible(false);
	}

	public void limpiarVariables(){
		this.TFEvento.setText("");
		this.TFEdicion.setText("");
		this.TFTipoRegistro.setText("");
		this.TFFecha.setText("");
		this.TFCosto.setText("");
		resetearComboBox(CBUsuario, "Seleccione un usuario:");
		resetearComboBox(CBRegistro, "Seleccione un registro:");
		CBRegistro.setEnabled(false);
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
		Set<String> datosORdenados = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		datosORdenados.addAll(datos);
        for (String item : datosORdenados) {
            modelo.addElement(item);
        }
        cb.setModel(modelo);
		cb.setSelectedIndex(-1);
    }

	private void cargarDatosRegistros(JComboBox<String> cb, Collection<DTCompra> collection) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (DTCompra item : collection) {
        	String view = item.getEdicion() + " - " + item.getTipoRegistro();
            modelo.addElement(view);
        }
        cb.setModel(modelo);
		cb.setSelectedIndex(-1);
    }

	public void listarUsuarios(){
		Map<String, DTUsuario> usuarios =ICU.listarUsuarios();
		Set<String> nickUsuarios = new HashSet<>();
		for(DTUsuario usr : usuarios.values()) {
			nickUsuarios.add(usr.getNickName());
		}

		cargarDatos(CBUsuario,nickUsuarios);
		resetearComboBox(CBUsuario, "Seleccione un usuario:");
	}

	public void listarRegistros(String usuario) throws NoExisteUsuarioExcepcion{
		cargarDatosRegistros(CBRegistro, ICU.listarComprasDeUsuario(usuario).values());
		resetearComboBox(CBRegistro, "Seleccione un registro:");
	}

	public void mostrarRegistro(String usuario, String id){
		DTCompra registro = ICU.getDTCompra(usuario, id);
		if(registro != null) {
			TFEvento.setText(registro.getEvento());
			TFEdicion.setText(registro.getEdicion());
			TFTipoRegistro.setText(registro.getTipoRegistro());
			TFFecha.setText(registro.getFecha().toString());
			TFCosto.setText(Float.toString(registro.getCosto()));
		}
		else {
			TFEvento.setText("");
			TFEdicion.setText("");
			TFTipoRegistro.setText("");
			TFFecha.setText("");
			TFCosto.setText("");
		}
	}

}
