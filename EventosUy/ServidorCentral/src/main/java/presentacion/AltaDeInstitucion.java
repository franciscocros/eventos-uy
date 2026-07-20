package main.java.presentacion;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import main.java.excepciones.ExisteInstitucionExcepcion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorUsuario;

public class AltaDeInstitucion extends JInternalFrame {
	private JTextField NombreTextField;
	private JTextField PaginaWebTextField;
	private JTextArea DescripcionTextArea;
	private IControladorUsuario ICU;

	public AltaDeInstitucion() {
		Fabrica F = Fabrica.getInstance();
		ICU = F.getIControladorUsuario();

		this.setTitle("Alta de institucion");
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setClosable(true);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{58, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{23, 26, 16, 0, 19, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		getContentPane().add(lblNombre, gbc_lblNombre);

		NombreTextField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		getContentPane().add(NombreTextField, gbc_textField);
		NombreTextField.setColumns(10);

		JLabel lblPaginaWeb = new JLabel("Pagina web:");
		GridBagConstraints gbc_lblPaginaWeb = new GridBagConstraints();
		gbc_lblPaginaWeb.anchor = GridBagConstraints.WEST;
		gbc_lblPaginaWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaginaWeb.gridx = 1;
		gbc_lblPaginaWeb.gridy = 3;
		getContentPane().add(lblPaginaWeb, gbc_lblPaginaWeb);

		PaginaWebTextField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		getContentPane().add(PaginaWebTextField, gbc_textField_1);
		PaginaWebTextField.setColumns(10);

		JLabel lblDescripcion = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.WEST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 5;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);

		DescripcionTextArea = new JTextArea();
		DescripcionTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 2;
		gbc_textArea.gridy = 5;
		getContentPane().add(DescripcionTextArea, gbc_textArea);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 7;
		getContentPane().add(panel, gbc_panel);

		JButton btnConfirmar = new JButton("confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					realizarAlta();
				} catch (ExisteInstitucionExcepcion e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnConfirmar);

		JButton btnCancelar = new JButton("cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				terminarCaso();
			}
		});
		panel.add(btnCancelar);
	}

	private boolean sonValidosDatos() {
		if(!this.NombreTextField.getText().equals("") && !this.PaginaWebTextField.getText().equals("") && !this.DescripcionTextArea.getText().equals("")) {
			return true;
		}
		return false;
	}

	private void realizarAlta() throws ExisteInstitucionExcepcion {
		if(sonValidosDatos()) {
			String nombre = this.NombreTextField.getText();
			String paginaWeb = this.PaginaWebTextField.getText();
			String descripcion = this.DescripcionTextArea.getText();

			try {
				ICU.crearInstitucion(nombre,descripcion,paginaWeb);
				JOptionPane.showMessageDialog(this,"Institucion creada  con exito", "Registrar Institucion",JOptionPane.INFORMATION_MESSAGE);
				terminarCaso();
			}catch(ExisteInstitucionExcepcion e) {
		        int opcion = JOptionPane.showConfirmDialog(this, e.getMessage(),"Alta de Institucion", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);

		        if(opcion == 1) {
		        	terminarCaso();
		        }else {
		        	this.NombreTextField.setText("");
		        }
			}
		}
	}

	private void terminarCaso() {
		this.NombreTextField.setText("");
		this.PaginaWebTextField.setText("");
		this.DescripcionTextArea.setText("");

		this.setVisible(false);
	}

}
