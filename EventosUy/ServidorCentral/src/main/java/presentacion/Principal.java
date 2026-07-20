package main.java.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import main.java.excepciones.ExisteCategoriaExcepcion;
import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.ExisteInstitucionExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.excepciones.NoEstanLosDatos;
import main.java.excepciones.NoExisteEdicionExcepcion;
import main.java.excepciones.NoExisteEvento;
import main.java.excepciones.NoExisteInstitucion;
import main.java.excepciones.NoExisteTipoRegistro;
import main.java.excepciones.SuperaCantidadGratuitos;
import main.java.excepciones.YatienePatrocinioException;
import main.java.logica.DTInstitucion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;
import main.java.resources.Config;
import main.java.webservice.WebServiceEvento;
import main.java.webservice.WebServiceUsuario;

public class Principal {

	//Main frame y menu

	private Scanner sc;
	private JFrame mainFrame;
	private JMenuBar mainMenuBar;
	private JMenu menuUsuarios;
	private JMenu menuEventos;
	private JMenu menuRegistros;
	private JMenu menuInistituciones;
	private JMenu menuCategorias;
	private JMenuItem miAltaDeUsuario;
	private JMenuItem miConsultaDeUsuario;
	private JMenuItem miModificarDatosDeUsuario;
	private JMenuItem miAltaDeEvento;
	private JMenuItem miConsultaDeEvento;
	private JMenuItem miAltaEdicionDeEvento;
	private JMenuItem miConsultaEdicionDeEvento;
	private JMenuItem miAltaTipoDeRegistro;
	private JMenuItem miConsultaTipoDeRegistro;
	private JMenuItem miRegistroAEdicionDeEvento;
	private JMenuItem miConsultaDeregistro;
	private JMenuItem miAltaDeInstitucion;
	private JMenuItem miAltaDePatrocinio;
	private JMenuItem miConsultaDePatrocinio;
	private JMenuItem miAltaDeCategoria;

	//Internal Frames
	private AltaDeUsuario altaDeUsuarioIf;
	private ConsultaDeUsuario consultaDeUsuarioIf;
	private ModificarDatosDeUsuario modificarDatosDeUsuarioIf;
	private AltaDeEvento altaDeEventoIf;
	private ConsultaDeEvento consultaDeEventoIf;
	private AltaEdicionDeEvento altaEdicionDeEventoIf;
	private ConsultaEdicionDeEvento consultaEdicionDeEventoIf;
	private AltaTipoDeRegistro altaTipoDeRegistroIf;
	private ConsultaTipoDeRegistro consultaTipoDeRegistroIf;
	private RegistroAEdicionDeEvento registroAEdicionDeEventoIf;
	private ConsultaDeRegistro consultaDeregistroIf;
	private AltaDeInstitucion altaDeInstitucionIf;
	private AltaDePatrocinio altaDePatrocinioIf;
	private ConsultaDePatrocinio consultaDePatrocinioIf;
	private DetalleEdicionDeEvento detalleEdicionDeEventoIf;
	private AceptarRechazarEdicion aceptarRechazarEdicion;


	//Interfaces
	private Fabrica fabrica;
	private IControladorUsuario ICU;
	private IControladorEvento ICE;
	private JMenu mnDatos;
	private JMenuItem mntmCargardatos;

	private boolean datosCargados;
	private JMenuItem mntmAceptarrechazarEdicion;



	public static void main(String[] args) {
		WebServiceUsuario wsu = new WebServiceUsuario();
		WebServiceEvento eve = new WebServiceEvento();

		String urlUsuario = Config.buildUrl(Config.get("WS_PORT_USUARIO"), "usuario");
		String urlEvento = Config.buildUrl(Config.get("WS_PORT_EVENTO"), "evento");
		System.out.println(urlUsuario + "--------" + urlEvento);
		try { 
			wsu.publicar(urlUsuario); 
			eve.publicar(urlEvento); 
			System.out.println("WebServices publicados correctamente."); 
		} catch (Exception e) { 
			System.err.println("Error al publicar WebServices: " + e.getMessage()); e.printStackTrace(); }
		

		new Principal();
	}

	private Principal() {
		iniciarInterfaces();
		cargarMainFrame();
		cargarInternalFrames();
		cargarExtras();
	}

	private void cargarExtras() {
		this.datosCargados = false;
		sc = new Scanner(System.in);
	}

	private void iniciarInterfaces() {
		fabrica = Fabrica.getInstance();
		ICU = fabrica.getIControladorUsuario();
		ICE = fabrica.getIControladorEvento();
	}

	/**
	 * Carga el Frame principal y menu* **/
	private void cargarMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 800, 600);
		mainFrame.setTitle("Principal");
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainMenuBar = new JMenuBar();
		mainFrame.setJMenuBar(mainMenuBar);

		menuUsuarios = new JMenu("Usuarios");
		mainMenuBar.add(menuUsuarios);

		miAltaDeUsuario = new JMenuItem("Alta de Usuario");
		miAltaDeUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evento) {
				limpiarVentanas();
				inicializarAltaDeUsuario();
				altaDeUsuarioIf.setVisible(true);
				mainFrame.getContentPane().add(altaDeUsuarioIf);
			}
		});
		menuUsuarios.add(miAltaDeUsuario);

		miConsultaDeUsuario = new JMenuItem("Consulta de Usuario");
		miConsultaDeUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				consultaDeUsuarioIf.cargaUsuarios();
				consultaDeUsuarioIf.setVisible(true);
				mainFrame.getContentPane().add(consultaDeUsuarioIf);
			}
		});
		menuUsuarios.add(miConsultaDeUsuario);

		miModificarDatosDeUsuario = new JMenuItem("Modificar datos de Usuario");
		miModificarDatosDeUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				iniciarModificarDatosDeUsuario();
				modificarDatosDeUsuarioIf.setVisible(true);
				mainFrame.getContentPane().add(modificarDatosDeUsuarioIf);
			}
		});
		menuUsuarios.add(miModificarDatosDeUsuario);

		menuEventos = new JMenu("Eventos");
		mainMenuBar.add(menuEventos);

		miAltaDeEvento = new JMenuItem("Alta de evento");
		miAltaDeEvento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				altaDeEventoIf = new AltaDeEvento(ICE);
				altaDeEventoIf.setVisible(true);
				mainFrame.getContentPane().add(altaDeEventoIf);
			}
		});
		menuEventos.add(miAltaDeEvento);

		miConsultaDeEvento = new JMenuItem("Consulta de evento");
		miConsultaDeEvento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				consultaDeEventoIf.cargarEventos();
				consultaDeEventoIf.setVisible(true);
				mainFrame.getContentPane().add(consultaDeEventoIf);
			}
		});
		menuEventos.add(miConsultaDeEvento);

		miAltaEdicionDeEvento = new JMenuItem("Alta edicion de evento");
		miAltaEdicionDeEvento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				altaEdicionDeEventoIf = new AltaEdicionDeEvento(ICE, ICU);
				altaEdicionDeEventoIf.setVisible(true);
				mainFrame.getContentPane().add(altaEdicionDeEventoIf);
			}
		});
		menuEventos.add(miAltaEdicionDeEvento);

		miConsultaEdicionDeEvento = new JMenuItem("Consulta edicion de evento");
		miConsultaEdicionDeEvento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				consultaEdicionDeEventoIf.cargarEventos() ;
				consultaEdicionDeEventoIf.setVisible(true);
				mainFrame.getContentPane().add(consultaEdicionDeEventoIf);
			}
		});
		menuEventos.add(miConsultaEdicionDeEvento);

		menuRegistros = new JMenu("Registros");
		mainMenuBar.add(menuRegistros);

		miAltaTipoDeRegistro = new JMenuItem("Alta tipo de registro");
		miAltaTipoDeRegistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				altaTipoDeRegistroIf.limpiarCampos();
				altaTipoDeRegistroIf.cargaDeEventos();
				altaTipoDeRegistroIf.setVisible(true);
				mainFrame.getContentPane().add(altaTipoDeRegistroIf);
			}
		});
		menuRegistros.add(miAltaTipoDeRegistro);

		miConsultaTipoDeRegistro = new JMenuItem("Consulta tipo de registro");
		miConsultaTipoDeRegistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				consultaTipoDeRegistroIf.limpiar();
				consultaTipoDeRegistroIf.cargaDeEventos();
				consultaTipoDeRegistroIf.setVisible(true);
				mainFrame.getContentPane().add(consultaTipoDeRegistroIf);
			}
		});
		menuRegistros.add(miConsultaTipoDeRegistro);

		miRegistroAEdicionDeEvento = new JMenuItem("Registro a edicion de evento");
		miRegistroAEdicionDeEvento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				inicializarRegistroAEdicion();
				registroAEdicionDeEventoIf.setVisible(true);
				mainFrame.getContentPane().add(registroAEdicionDeEventoIf);
			}
		});
		menuRegistros.add(miRegistroAEdicionDeEvento);

		miConsultaDeregistro = new JMenuItem("Consulta de registro");
		miConsultaDeregistro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				consultaDeregistroIf.limpiarVariables();
				consultaDeregistroIf.listarUsuarios();
				consultaDeregistroIf.setVisible(true);
				mainFrame.getContentPane().add(consultaDeregistroIf);
			}
		});
		menuRegistros.add(miConsultaDeregistro);

		menuInistituciones = new JMenu("Instituciones y Patrocinios");
		mainMenuBar.add(menuInistituciones);

		miAltaDeInstitucion = new JMenuItem("Alta de institucion");
		miAltaDeInstitucion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				altaDeInstitucionIf.setVisible(true);
				mainFrame.getContentPane().add(altaDeInstitucionIf);
			}
		});
		menuInistituciones.add(miAltaDeInstitucion);

		miAltaDePatrocinio = new JMenuItem("Alta de patrocinio");
		miAltaDePatrocinio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				inicarAltaDePatrocinio();
				altaDePatrocinioIf.setVisible(true);
				mainFrame.getContentPane().add(altaDePatrocinioIf);
			}
		});
		menuInistituciones.add(miAltaDePatrocinio);

		miConsultaDePatrocinio = new JMenuItem("Consulta de patrocinio");
		miConsultaDePatrocinio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				iniciarConsultaDePatrocinio();
				consultaDePatrocinioIf.setVisible(true);
				mainFrame.getContentPane().add(consultaDePatrocinioIf);
			}
		});
		menuInistituciones.add(miConsultaDePatrocinio);

		mntmAceptarrechazarEdicion = new JMenuItem("Aceptar/Rechazar Edicion");
		mntmAceptarrechazarEdicion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarVentanas();
				iniciarAceptarRechazarEdicion();
				aceptarRechazarEdicion.setVisible(true);
				mainFrame.getContentPane().add(aceptarRechazarEdicion);
			}
		});
		menuInistituciones.add(mntmAceptarrechazarEdicion);
		mainFrame.setVisible(true);


	    menuCategorias = new JMenu("Categorias");
	    mainMenuBar.add(menuCategorias);

	    miAltaDeCategoria = new JMenuItem("Alta de categoria");
		miAltaDeCategoria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AltaDeCategoria cat = new AltaDeCategoria(sc);
				cat.darAltaCategoria(ICE);
	     	}
		});

	    menuCategorias.add(miAltaDeCategoria);

	    mnDatos = new JMenu("Datos");
	    mainMenuBar.add(mnDatos);

	    mntmCargardatos = new JMenuItem("CargarDatos");
	    mntmCargardatos.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		try {
	    			if(!datosCargados) {
						cargarDatos();
	    			}
				} catch (ExisteInstitucionExcepcion | NoEstanLosDatos | IOException | ExisteUsuarioExcepcion | ExisteCategoriaExcepcion | ExisteEventoExcepcion | ExisteEdicion | NumberFormatException | NoExisteInstitucion | NoExisteEvento | NoExisteEdicionExcepcion | NoExisteTipoRegistro | YatienePatrocinioException | SuperaCantidadGratuitos e1) {
					e1.printStackTrace();
				}
	    	}
	    });
	    mnDatos.add(mntmCargardatos);
	}

	private void cargarDatos() throws ExisteInstitucionExcepcion, NoEstanLosDatos, FileNotFoundException, IOException, ExisteUsuarioExcepcion, ExisteCategoriaExcepcion, ExisteEventoExcepcion, ExisteEdicion, NumberFormatException, NoExisteInstitucion, NoExisteEvento, NoExisteEdicionExcepcion, NoExisteTipoRegistro, YatienePatrocinioException, SuperaCantidadGratuitos {
			try {
				ICU.cargarDatos("", "Presentacion");
				limpiarVentanas();
				this.datosCargados = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private void inicializarAltaDeUsuario() {
		Map<String, DTInstitucion> instituciones = ICU.listarInstituciones();
		altaDeUsuarioIf = new AltaDeUsuario(ICU,instituciones);
		altaDeUsuarioIf.setVisible(false);
	}

	private void inicializarRegistroAEdicion() {
		registroAEdicionDeEventoIf = new RegistroAEdicionDeEvento(ICU.listarAsistentes());
		registroAEdicionDeEventoIf.setVisible(false);
	}

	private void inicarAltaDePatrocinio() {
		altaDePatrocinioIf = new AltaDePatrocinio();
		altaDePatrocinioIf .setVisible(false);
	}

	private void iniciarConsultaDePatrocinio() {
		consultaDePatrocinioIf = new ConsultaDePatrocinio();
		consultaDePatrocinioIf.setVisible(false);
	}

	private void iniciarModificarDatosDeUsuario() {
		modificarDatosDeUsuarioIf  = new ModificarDatosDeUsuario();
		modificarDatosDeUsuarioIf.setVisible(false);
	}

	private void iniciarAceptarRechazarEdicion() {
		aceptarRechazarEdicion = new AceptarRechazarEdicion();
		aceptarRechazarEdicion.setVisible(false);
	}


	/**
	 * Carga Los internal Frames.Uno por caso de uso*/
	 void cargarInternalFrames(){

		inicializarAltaDeUsuario();

		consultaDeUsuarioIf = new ConsultaDeUsuario(this);
		consultaDeUsuarioIf.setVisible(false);

		iniciarModificarDatosDeUsuario();


		altaDeEventoIf = new AltaDeEvento(ICE);
		altaDeEventoIf.setVisible(false);

		consultaDeEventoIf = new ConsultaDeEvento(ICE, this);
		consultaDeEventoIf.setVisible(false);

		altaEdicionDeEventoIf = new AltaEdicionDeEvento(ICE, ICU);
		altaEdicionDeEventoIf.setVisible(false);

	    consultaEdicionDeEventoIf = new ConsultaEdicionDeEvento(ICE, this);
	    consultaEdicionDeEventoIf.setVisible(false);

		altaTipoDeRegistroIf = new AltaTipoDeRegistro();
		altaTipoDeRegistroIf.setVisible(false);

		consultaTipoDeRegistroIf = new ConsultaTipoDeRegistro();
		consultaTipoDeRegistroIf.setVisible(false);

		inicializarRegistroAEdicion();

		consultaDeregistroIf = new ConsultaDeRegistro();
		consultaDeregistroIf.setVisible(false);

		altaDeInstitucionIf = new AltaDeInstitucion();
		altaDeInstitucionIf.setVisible(false);

		inicarAltaDePatrocinio();

		iniciarConsultaDePatrocinio();

		detalleEdicionDeEventoIf = new DetalleEdicionDeEvento(this);
		detalleEdicionDeEventoIf.setVisible(false);

		iniciarAceptarRechazarEdicion();
	}

	void DetalleEdicion(String evento ,String edicion) {
		limpiarVentanas();
		//DetalleEdicionDeEvento detalleDesdeEventoIf = new DetalleEdicionDeEvento(ICE, evento, edicion);
		detalleEdicionDeEventoIf.cargarDetalle(ICE, evento, edicion);
		detalleEdicionDeEventoIf.setVisible(true);
		mainFrame.getContentPane().add(detalleEdicionDeEventoIf);
	}


	void DetEdicionTOConsTRegis(String evento, String edicion, String registro){
		limpiarVentanas();
		consultaTipoDeRegistroIf.cargaCBOX(evento, edicion, registro);
		consultaTipoDeRegistroIf.cargaDatos();
		consultaTipoDeRegistroIf.setVisible(true);
		mainFrame.getContentPane().add(consultaTipoDeRegistroIf);
	 }

	void ConEventoTOConEdicion(String evento, String edicion) {
		limpiarVentanas();
		detalleEdicionDeEventoIf.cargarDetalle(ICE, evento, edicion);
		detalleEdicionDeEventoIf.setVisible(true);
		mainFrame.getContentPane().add(detalleEdicionDeEventoIf);
	}

	/***
	 * Deja los internal frames no visibles.
	 */
	private void limpiarVentanas() {
		altaDeUsuarioIf.setVisible(false);
		consultaDeUsuarioIf.setVisible(false);
		modificarDatosDeUsuarioIf.setVisible(false);
		altaDeEventoIf.setVisible(false);
		consultaDeEventoIf.setVisible(false);
		altaEdicionDeEventoIf.setVisible(false);
		consultaEdicionDeEventoIf.setVisible(false);
		altaTipoDeRegistroIf.setVisible(false);
		consultaTipoDeRegistroIf.setVisible(false);
		registroAEdicionDeEventoIf.setVisible(false);
		consultaDeregistroIf.setVisible(false);
		altaDeInstitucionIf.setVisible(false);
		altaDePatrocinioIf .setVisible(false);
		consultaDePatrocinioIf.setVisible(false);
		detalleEdicionDeEventoIf.setVisible(false);
		aceptarRechazarEdicion.setVisible(false);
	}

	public void DetalleCompra(String nickname, String edicion) {
		// TODO Auto-generated method stub
		consultaDeregistroIf.mostrarRegistro(nickname, edicion);
		consultaDeregistroIf.setVisible(true);
		mainFrame.getContentPane().add(consultaDeregistroIf);
	}

}
