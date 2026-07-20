package main.java.presentacion;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
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

import main.java.excepciones.ExisteEdicion;
import main.java.logica.Ciudad;
import main.java.logica.DTEvento;
import main.java.logica.DTOrganizador;
import main.java.logica.DTUsuario;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;



public class AltaEdicionDeEvento extends JInternalFrame {


    private JComboBox<String> comboEventos;
    private JComboBox<String> comboOrganizadores;
    private JComboBox<String> comboCiudad;
    private JTextField txtNombre;
    private JTextField txtSigla;
    private JDateChooser dcInicio;
    private JDateChooser dcFin;
    private JDateChooser dcAlta;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private IControladorEvento ICE;
	private IControladorUsuario ICU;
	private JTextField txtNewCiudad;
	private JTextField txtNewPais;
	private JLabel lblNewCiudad;
	private JLabel lblNewPais;

	public AltaEdicionDeEvento(IControladorEvento ICE, IControladorUsuario ICU) {

		this.setTitle("Alta edicion de evento");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);
        this.ICE = ICE;
        this.ICU = ICU;
        //setSize(680, 520);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Evento
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblEvento = new JLabel();
        lblEvento.setText("Evento:");
        add(lblEvento, gbc);
        comboEventos = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        add(comboEventos, gbc);

        //  Organizador
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Organizador:"), gbc);
        comboOrganizadores = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        add(comboOrganizadores, gbc);

        // Ciudad
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        add(new JLabel("Ciudad:"), gbc);
        comboCiudad = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        add(comboCiudad, gbc);

        // Nueva Ciudad
        gbc.gridx = 5; gbc.gridy = 4; gbc.gridwidth = 1;
        lblNewCiudad = new JLabel();

        lblNewCiudad.setText("Ciudad:");
        lblNewCiudad.setVisible(false);
        add(lblNewCiudad, gbc);
        txtNewCiudad = new JTextField();
        gbc.gridx = 7; gbc.gridy = 4; gbc.gridwidth = 3;
        txtNewCiudad.setVisible(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(txtNewCiudad, gbc);


        // Nuevo Pais
        gbc.gridx = 5; gbc.gridy = 5; gbc.gridwidth = 1;
        lblNewPais = new JLabel();
        lblNewPais.setText("Pais:");
        lblNewPais.setVisible(false);
        add(lblNewPais, gbc);

        txtNewPais = new JTextField();
        txtNewPais.setVisible(false);
        gbc.gridx = 7; gbc.gridy = 5; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(txtNewPais, gbc);



        // Nombre
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        add(txtNombre, gbc);

        // Sigla
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        add(new JLabel("Sigla:"), gbc);
        txtSigla = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        add(txtSigla, gbc);



        // Fecha inicio
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        add(new JLabel("Fecha de inicio:"), gbc);
        dcInicio = new JDateChooser();
        dcInicio.setDateFormatString("dd/MM/yyyy");
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2;
        add(dcInicio, gbc);

        // Fecha fin
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 1;
        add(new JLabel("Fecha de fin:"), gbc);
        dcFin = new JDateChooser();
        dcFin.setDateFormatString("dd/MM/yyyy");
        gbc.gridx = 1; gbc.gridy = 7; gbc.gridwidth = 2;
        add(dcFin, gbc);

        // Fecha alta
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 1;
        add(new JLabel("Fecha de alta:"), gbc);
        dcAlta = new JDateChooser();
        dcAlta.setDateFormatString("dd/MM/yyyy");
        gbc.gridx = 1; gbc.gridy = 8; gbc.gridwidth = 2;
        add(dcAlta, gbc);

        //  Botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");
        pnlBotones.add(btnConfirmar);
        pnlBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        add(pnlBotones, gbc);

        // Cargar datos iniciales
        cargarListas();

        // Listeners
        btnCancelar.addActionListener(e -> cerrar());
        btnConfirmar.addActionListener(e -> onConfirmar());
        this.comboCiudad.addActionListener(e -> nuevaCiudad());
        comboCiudad.addItemListener(new ItemListener() {
		    @Override
			public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		        	 nuevaCiudad();
		        }
		    }
		});
        nuevaCiudad();

    }

	private Object nuevaCiudad() {
		if (comboCiudad.getSelectedItem().toString() == "Otra") {
			lblNewCiudad.setVisible(true);
			lblNewPais.setVisible(true);
			txtNewCiudad.setVisible(true);
			txtNewPais.setVisible(true);

		}else {
			lblNewCiudad.setVisible(false);
			lblNewPais.setVisible(false);
			txtNewCiudad.setVisible(false);
			txtNewPais.setVisible(false);
		}
		return null;
	}

	private void cargarListas() {

        	//cargo combobox eventos
            Set<DTEvento> dteventos = ICE.listarEventos();
            Set<String> eventos = new HashSet<>();
            for (DTEvento dte : dteventos) {
            	eventos.add(dte.getNombre());
            }
            Set<String> events = new TreeSet<>(eventos);
             for (String nom : events) {
                comboEventos.addItem(nom);
            }

             //cargo combobox organizadores

            Map<String, DTUsuario> mapusu = ICU.listarUsuarios();
            Set<String> usuarios = new HashSet<>();
            for (DTUsuario dtu : mapusu.values()) {
            	if (dtu instanceof DTOrganizador) {
					usuarios.add(dtu.getNickName());
				}
            }
            Set<String> organizadores = new TreeSet<>(usuarios);
            for (String nick : organizadores) {
                comboOrganizadores.addItem(nick);
            }

            //cargo combobox ciudad
            Map<String, Ciudad> mapciu = ICE.listarCiudades();
            Set<String> ciudades = mapciu.keySet();
            Set<String> cds = new TreeSet<>(ciudades);
            for (String nom : cds) {
              comboCiudad.addItem(nom);
            }
            comboCiudad.addItem("Otra");
	  }

    private void onConfirmar() {
        //  Validaciones básicas
        String evento = (String) comboEventos.getSelectedItem();
        String organizador = (String) comboOrganizadores.getSelectedItem();
        String ciudad = (String) comboCiudad.getSelectedItem();
        String nombre = txtNombre.getText().trim();
        String sigla = txtSigla.getText().trim();
        Date fInicio = dcInicio.getDate();
        Date fFin = dcFin.getDate();
        Date fAlta = dcAlta.getDate();
        String nuevaCiudad =  txtNewCiudad.getText().trim();
        String nuevaPais =  txtNewPais.getText().trim();


        if (evento == null || evento.isEmpty()
                || organizador == null || organizador.isEmpty()
                || nombre.isEmpty() || sigla.isEmpty()
                || fInicio == null || fFin == null || fAlta == null) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ciudad == "Otra" && (nuevaCiudad.isEmpty() || nuevaPais.isEmpty()) ) {
        	JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Coherencia de fechas
        if (!fFin.after(fInicio) && !mismoDia(fInicio, fFin)) {
            JOptionPane.showMessageDialog(this,
                    "La fecha de fin debe ser posterior o igual a la fecha de inicio.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }


        //  Alta
        else {
        	LocalDate localfInicio = LocalDate.ofInstant(fInicio.toInstant(), ZoneId.systemDefault());
            LocalDate localfFin = LocalDate.ofInstant(fFin.toInstant(), ZoneId.systemDefault());
            LocalDate localfAlta = LocalDate.ofInstant(fAlta.toInstant(), ZoneId.systemDefault());
            try {
        	if (ciudad == "Otra") {
        		ICE.altaCiudad(nuevaCiudad, nuevaPais);
        		ciudad = nuevaCiudad;
        	}
            ICE.altaEdicion(evento, organizador, nombre, sigla, ciudad, localfAlta, localfFin,  localfInicio);

            JOptionPane.showMessageDialog(this,"La edición se dio de alta correctamente.","Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
            this.setVisible(false);

        } catch(ExisteEdicion e) {
	        int opcion = JOptionPane.showConfirmDialog(this, e.getMessage(),"Alta de Edicion", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);

	        if(opcion == 1) {
	        	cerrar();
	        }else {
				this.txtNombre.setText("");
	        }
	     }
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtSigla.setText("");
        dcInicio.setDate(null);
        dcFin.setDate(null);
        dcAlta.setDate(new Date());
        txtNewCiudad.setText("");
        txtNewPais.setText("");
        if (comboEventos.getItemCount() > 0) {
			comboEventos.setSelectedIndex(0);
		}
        if (comboOrganizadores.getItemCount() > 0) {
			comboOrganizadores.setSelectedIndex(0);
		}
    }

    private void cerrar() {
        int op = JOptionPane.showConfirmDialog(this,
                "¿Seguro que querés cancelar el alta?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private boolean mismoDia(Date a, Date b) {

        return trunc(a) == trunc(b);
    }

    private long trunc(Date d) {

        return d.getTime() / (24L * 60L * 60L * 1000L);
    }
}
