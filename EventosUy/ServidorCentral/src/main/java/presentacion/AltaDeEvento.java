package main.java.presentacion;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import com.toedter.calendar.JDateChooser;

import main.java.excepciones.ExisteEventoExcepcion;
import main.java.logica.IControladorEvento;

public class AltaDeEvento extends JInternalFrame {
	private JTextField               nombre;
	private JTextField               sigla;
	private JTextArea                desc;
	private JDateChooser             dateChooser;

	private DefaultComboBoxModel<String>     modeloCombo;

	private JComboBox<String>        cats;
	private JList<String>            listaCats;
	private IControladorEvento       ICE;
	private DefaultListModel<String>         modeloLista;


	public AltaDeEvento(IControladorEvento iCE) {
		this.ICE = iCE;

		this.setTitle("Alta de Evento");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{-35, 10, 0, 0, 0, 0, -127, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

		getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.ipady = 10;
		gbc_lblNewLabel.ipadx = 10;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(50, 50, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 0;

		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		nombre = new JTextField();
		GridBagConstraints gbc_nombre = new GridBagConstraints();
		gbc_nombre.gridwidth = 3;
		gbc_nombre.insets = new Insets(50, 0, 5, 5);
		gbc_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombre.gridx = 3;
		gbc_nombre.gridy = 0;
		getContentPane().add(nombre, gbc_nombre);
		this.nombre.setColumns(10);

		modeloCombo = new DefaultComboBoxModel<>();

        cargarComboCats();

		modeloLista = new DefaultListModel<>();


		JLabel lblNewLabel_2 = new JLabel("Sigla:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(10, 50, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 1;

		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		sigla = new JTextField();
		GridBagConstraints gbc_sigla = new GridBagConstraints();
		gbc_sigla.gridwidth = 3;
		gbc_sigla.insets = new Insets(10, 0, 5, 5);
		gbc_sigla.fill = GridBagConstraints.HORIZONTAL;
		gbc_sigla.gridx = 3;
		gbc_sigla.gridy = 1;
		getContentPane().add(sigla, gbc_sigla);
		this.sigla.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(20, 50, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 2;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		desc = new JTextArea();
		desc.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_desc = new GridBagConstraints();
		gbc_desc.ipady = 40;
		gbc_desc.ipadx = 40;
		gbc_desc.gridheight = 2;
		gbc_desc.gridwidth = 3;
		gbc_desc.insets = new Insets(20, 0, 5, 5);
		gbc_desc.fill = GridBagConstraints.BOTH;
		gbc_desc.gridx = 3;
		gbc_desc.gridy = 2;
		getContentPane().add(desc, gbc_desc);

		JLabel lblNewLabel_4 = new JLabel("Fecha de alta:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 2;
		gbc_lblNewLabel_4.gridy = 4;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);

		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.gridwidth = 3;
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.fill = GridBagConstraints.BOTH;
		gbc_dateChooser_1.gridx = 3;
		gbc_dateChooser_1.gridy = 4;
		getContentPane().add(dateChooser, gbc_dateChooser_1);


		JLabel lblNewLabel_3 = new JLabel("Categorías:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(20, 50, 5, 5);
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 5;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

	    cats = new JComboBox<>(modeloCombo);
		GridBagConstraints gbc_cats = new GridBagConstraints();
		gbc_cats.gridwidth = 3;
		gbc_cats.insets = new Insets(20, 0, 5, 5);
		gbc_cats.fill = GridBagConstraints.HORIZONTAL;
		gbc_cats.gridx = 3;
		gbc_cats.gridy = 5;
		getContentPane().add(cats, gbc_cats);


		cats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cats.getSelectedItem() != null && e.getActionCommand().equals("comboBoxChanged")) {
					String seleccion = cats.getSelectedItem().toString();
					modeloLista.addElement(seleccion);
					modeloCombo.removeElement(seleccion);
				}
			}
		});
		JLabel lblSeleccionadas = new JLabel("Seleccionadas:");
		GridBagConstraints gbc_lblSeleccionadas = new GridBagConstraints();
		gbc_lblSeleccionadas.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSeleccionadas.insets = new Insets(30, 50, 5, 5);
		gbc_lblSeleccionadas.gridx = 2;
		gbc_lblSeleccionadas.gridy = 6;
		getContentPane().add(lblSeleccionadas, gbc_lblSeleccionadas);
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				darAltaEvento();
			}
		});

				listaCats = new JList<>(modeloLista);
				listaCats.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				GridBagConstraints gbc_list = new GridBagConstraints();
				gbc_list.ipady = 30;
				gbc_list.gridwidth = 3;
				gbc_list.insets = new Insets(30, 0, 5, 5);

						gbc_list.fill = GridBagConstraints.BOTH;
						gbc_list.gridx = 3;
						gbc_list.gridy = 6;
						getContentPane().add(listaCats, gbc_list);
						listaCats.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic para devolver
                    String seleccionadoLista = listaCats.getSelectedValue();
                    if (seleccionadoLista != null) {
                        modeloCombo.addElement(seleccionadoLista);
                        modeloLista.removeElement(seleccionadoLista);
                    }
                }
            }
        });
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 9;
		getContentPane().add(btnNewButton, gbc_btnNewButton);


		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelarCaso();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 5;
		gbc_btnNewButton_1.gridy = 9;

		getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
	}

	private void cancelarCaso() {
		limpiarVariables();
		this.setVisible(false);
	}

	private void limpiarVariables(){
		this.nombre.setText("");
		this.sigla.setText("");
		this.desc.setText("");
		this.cats.removeAllItems();
		this.modeloCombo.removeAllElements();
		this.listaCats.removeAll();
		this.modeloLista.removeAllElements();
		this.dateChooser.setDate(null);
	}

	private boolean sonDatosValidos() {
		if(this.nombre.getText().isEmpty() || this.sigla.getText().isEmpty() || this.desc.getText().isEmpty() || this.dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(this, "No puede haber campos vacios", "Alta de Evento",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(this.modeloLista.getSize() == 0) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una categoria", "Alta de Evento",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void darAltaEvento() {
		if(sonDatosValidos()) {

			Date fecha = this.dateChooser.getDate();
			LocalDate fechaAlta = LocalDate.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
			try {
				Set<String> categorias = new HashSet<>();
				for(int iter = 0; iter < this.modeloLista.size(); iter++) {
					categorias.add(this.modeloLista.elementAt(iter));
				}
				this.ICE.addEvento(this.nombre.getText(), this.desc.getText(), this.sigla.getText(), fechaAlta, categorias);
				JOptionPane.showMessageDialog(this,"Evento dado de alta con exito", "Alta de Evento",JOptionPane.INFORMATION_MESSAGE);
				limpiarVariables();
				this.setVisible(false);
			} catch (ExisteEventoExcepcion e) {
				int opcion = JOptionPane.showConfirmDialog(this, e.getMessage(),"Alta de Evento", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);

		        if(opcion == 1) {
		        	cancelarCaso();
		        }else {
					this.nombre.setText("");
		        }
			}

		}
	}

	private void cargarComboCats() {
		//Cargo el ComboBox con las categorias del sistema:
		Set<String> setCategorias = ICE.listarCategorias();
		if(setCategorias != null) {
			Set<String> ordenadas = new TreeSet<>(setCategorias);
			for(String iter : ordenadas) {
				this.modeloCombo.addElement(iter);
			}
		}
	}
}
