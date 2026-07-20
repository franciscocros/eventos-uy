//	        /home/matias/git/tpgr32/EventosUy/ServidorCentral/src/main/resources/datos
package main.java.logica;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;

import main.java.excepciones.ExisteCategoriaExcepcion;
import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.ExisteInstitucionExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.excepciones.FechaNacimientoPosteriorExcepcion;
import main.java.excepciones.NoEstanLosDatos;
import main.java.excepciones.existeTipoRegistro;
import main.java.resources.Config;
public class CargarDatos {
	private IControladorUsuario iusu;
	private IControladorEvento ice;

	private Map<String, String[]> datosUsuarios;
	private Map<String, String[]> datosUsuariosAsistentes;
	private Map<String, String[]> datosUsuariosOrganizadores;
	private Map<String, String[]> datosCategorias;
	private Map<String, String[]> datosEventos;
	private Map<String, String[]> datosEdicionesDeEventos;
	private Map<String, String[]> datosTiposDeRegistros;
	private Map<String, String[]> datosInstituciones;
	private Map<String, String[]> datosPatrocinios;
	private Map<String, String[]> datosCompras;

	private String usuarios;
	private String asistentes;
	private String organizadores;
	private String categorias;
	private String eventos;
	private String edicionesDeEventos;
	private String tiposDeRegistros;
	private String instituciones;
	private String patrocinios;
	private String compras;

	public String todos(String ruta , String origen) throws NoEstanLosDatos {
	    if (GraphicsEnvironment.isHeadless()) {
	        // Estamos en servidor, usar ruta por defecto o configurada
	        return ruta;
	    }

	    // Solo si se ejecuta en entorno gráfico (ej: pruebas locales)
	    String rutaAbsoluta = new File("").getAbsolutePath() + "/src/pruebasNuevas";
	    JFileChooser fileChooser = new JFileChooser(rutaAbsoluta);
	    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	   fileChooser.showOpenDialog(null);
	    if (fileChooser.getSelectedFile() == null) {
	        throw new NoEstanLosDatos("No seleccionó ninguna carpeta");
	    }

	    return fileChooser.getSelectedFile().getAbsolutePath();
	}


	public CargarDatos(String ruta, String origen) throws Exception {
		Fabrica Fabric = Fabrica.getInstance();
		iusu = Fabric.getIControladorUsuario();
		ice = Fabric.getIControladorEvento();

		String ubi = Config.get("RUTA_DATOS");
		
        //Ruta de los archivos
	   	this.usuarios = ubi +"/2025Usuarios.csv";
	   	this.asistentes = ubi +"/2025Usuarios-Asistentes.csv";
	   	this.organizadores = ubi +"/2025Usuarios-Organizadores.csv";
	   	this.categorias = ubi +"/2025Categorias.csv";
	   	this.eventos = ubi +"/2025Eventos.csv";
	   	this.edicionesDeEventos = ubi +"/2025EdicionesEventos.csv";
	   	this.tiposDeRegistros = ubi +"/2025TipoRegistro.csv";
	   	this.instituciones = ubi +"/2025Instituciones.csv";
	   	this.patrocinios = ubi +"/2025Patrocinios.csv";
	   	this.compras = ubi +"/2025Registros.csv";

		datosUsuarios = leerInfoDeArchivos(usuarios);
		datosUsuariosAsistentes = leerInfoDeArchivos(asistentes) ;
		datosUsuariosOrganizadores = leerInfoDeArchivos(organizadores) ;
		datosCategorias= leerInfoDeArchivos(categorias) ;
		datosEventos= leerInfoDeArchivos(eventos) ;
		datosEdicionesDeEventos= leerInfoDeArchivos(edicionesDeEventos) ;
		datosTiposDeRegistros= leerInfoDeArchivos(tiposDeRegistros) ;
		datosInstituciones= leerInfoDeArchivos(instituciones) ;
		datosPatrocinios= leerInfoDeArchivos(patrocinios);
		datosCompras= leerInfoDeArchivos(compras);

		datosUsuarios.remove("Ref");
		datosUsuariosAsistentes.remove("Ref");
		datosUsuariosOrganizadores.remove("Ref");
		datosCategorias.remove("Ref");
		datosEventos.remove("Ref");
		datosEdicionesDeEventos.remove("Ref");

		datosTiposDeRegistros.remove("Ref");
		datosInstituciones.remove("Ref");
		datosPatrocinios.remove("Ref");
		datosCompras.remove("Ref");

		cargarDatosInstituciones(datosInstituciones);
		cargarDatosDeUsuarios(datosUsuarios, datosUsuariosAsistentes, datosUsuariosOrganizadores, datosInstituciones);
		cargarDatosCategorias(datosCategorias);
		cargarDatosEventos(datosEventos, datosCategorias);
		cargarDatosEdicionesDeEventos(datosEdicionesDeEventos, datosUsuarios, datosEventos);
		cargarDatosTiposDeRegistros(datosTiposDeRegistros, datosEdicionesDeEventos, datosEventos);
		cargarDatosPatrocinios(datosPatrocinios, datosEdicionesDeEventos, datosInstituciones, datosTiposDeRegistros, datosEventos);
		cargarDatosCompras(datosCompras, datosUsuarios, datosEdicionesDeEventos, datosEventos, datosTiposDeRegistros);
	}

	private LocalDate strALocalDate(String fecha) {
		String[] split = fecha.split("/");
		return  LocalDate.of(Integer.valueOf(split[2]), Integer.valueOf(split[1]), Integer.valueOf(split[0]));
	}

	private void cargarDatosInstituciones(Map<String, String[]> inst) throws ExisteInstitucionExcepcion {
		for (String[] iter : inst.values()) {
			iusu.crearInstitucion(iter[1], iter[2], iter[3]);
			//System.out.println(iter.toString());
		}
	}

	private void cargarDatosDeUsuarios(Map<String, String[]> usuarios, Map<String, String[]> asistentes, Map<String, String[]> organizadores, Map<String, String[]> inst) throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		for (String[] iter : usuarios.values()) {
			if (iter[1].equals("A")) {
				iusu.crearAsistente(iter[3], iter[2], iter[4], asistentes.get(iter[0])[1], strALocalDate(asistentes.get(iter[0])[2]) );
				if (asistentes.get(iter[0]).length==4) {
					iusu.setInstitucionAsistente(iter[2], inst.get(asistentes.get(iter[0])[3])[1]);
				}
			}else if (iter[1].equals("O")) {
				iusu.crearOrganizador(iter[3], iter[2], iter[4], organizadores.get(iter[0])[1], organizadores.get(iter[0])[2]);
			}
			if (!iter[6].equals("-")) {
				iusu.setImagenUsuario(iter[2], iter[6]);
			}
			iusu.setContraseniaUsuario(iter[2], iter[5]);
			//System.out.println(iter.toString());
		}
	}
	private void cargarDatosCategorias(Map<String, String[]> cates) throws ExisteCategoriaExcepcion {
		for (String[] iter : cates.values()) {
			ice.altaCategoria(iter[1]);
			//System.out.println(iter.toString());
		}
	}

	private void cargarDatosEventos(Map<String, String[]> eventos,  Map<String, String[]> cates) throws ExisteEventoExcepcion {
		for (String[] iter : eventos.values()) {
			String[] split = iter[5].split(", ");
			Set<String> cats = new HashSet<>();
			for (String it:split) {
				cats.add(cates.get(it)[1]);
			}
			ice.addEvento(iter[1], iter[2], iter[3], strALocalDate(iter[4]), cats);
			if (!iter[6].equals("-")) {
				ice.setImagenEvento(iter[1], iter[6]);
			}
			if(iter[7].equals("Si")) {
				ice.setFinalidado(iter[1]);
			}
			//visitas
			String visitasString = iter[8];
			int visitas = Integer.parseInt(visitasString);
			for(int i = 0;i<visitas;i++) {
				ice.visitar(iter[1]);
			}
			//System.out.println(iter.toString());
			}

		
	}
	private void cargarDatosEdicionesDeEventos(Map<String, String[]> edi, Map<String, String[]> Ordes, Map<String, String[]> eventos) throws ExisteEdicion {
		for (String[] iter : edi.values()) {
			ice.altaCiudad(iter[5], iter[6]);
			ice.altaEdicion(eventos.get(iter[1])[1], Ordes.get(iter[2])[2] , iter[3], iter[4], iter[5], strALocalDate(iter[9]), strALocalDate(iter[8]), strALocalDate(iter[7]));

			if (!iter[10].equals("Ingresada")) {

				String estado = iter[10];
				if (estado.equals("Aceptada")) {
					estado = "CONFIRMADA";
				}
				if (estado.equals("Rechazada")) {
					estado = "RECHAZADA";
				}
				if (estado.equals("Ingresada")) {
					estado = "INGRESADA";
				}
				ice.setEstadoEdicion(iter[3], EEstadoEdicion.valueOf(estado));
			}

			if (!iter[11].equals("-")) {
				ice.setImagenEdicion(iter[3], iter[11]);
		
			}
			if (!iter[12].equals("-")) {
				ice.setVideo(iter[3], iter[12]);
			}
		}
	}

	private void cargarDatosTiposDeRegistros(Map<String, String[]> dreg, Map<String, String[]> edi, Map<String, String[]> eventos) {
		for (String[] iter : dreg.values()) {
			try {
				ice.addTipoRegistro(eventos.get(edi.get(iter[1])[1])[1], edi.get(iter[1])[3], iter[2], iter[3], Float.parseFloat(iter[4]), Integer.parseInt(iter[5]));
			} catch (existeTipoRegistro e) {
				e.printStackTrace();
			}
			//System.out.println(iter.toString());
		}
	}

	private void cargarDatosPatrocinios(Map<String, String[]> patrocinios, Map<String, String[]> edi, Map<String, String[]> inst, Map<String, String[]> dreg, Map<String, String[]> eventos) throws Exception {
		for (String[] iter : patrocinios.values()) {

			iusu.crearPatrocinio(Float.parseFloat(iter[5]), iter[8], strALocalDate(iter[6]) , Integer.parseInt(iter[7]), ENivelPatrocinio.valueOf(iter[3].toUpperCase()), dreg.get(iter[4])[2], eventos.get(edi.get(iter[1])[1])[1] , edi.get(iter[1])[3], inst.get(iter[2])[1]);
			//System.out.println(iter.toString());
		}
		
	}

	private void cargarDatosCompras(Map<String, String[]> compras, Map<String, String[]> asistentes, Map<String, String[]> ediciones, Map<String, String[]> eventos, Map<String, String[]> registros) throws Exception {
		for (String[] iter : compras.values()) {
			ice.altaDeRegistro(asistentes.get(iter[1])[2], eventos.get(ediciones.get(iter[2])[1])[1], ediciones.get(iter[2])[3], registros.get(iter[3])[2], strALocalDate(iter[4]));
			if(iter[7].equals("S")) {
				iusu.aceptarCompraWeb(asistentes.get(iter[1])[2], ediciones.get(iter[2])[3]);
			}
		}
	}
	private Map<String, String[]> leerInfoDeArchivos(String ruta) throws IOException {
	    Map<String, String[]> infoMap = new HashMap<>();

	    File archivo = new File(ruta);

	    try (FileReader filer = new FileReader(archivo);
	         BufferedReader bufferr = new BufferedReader(filer)) {

	        String linea;
	        while ((linea = bufferr.readLine()) != null) {
	            String[] datos = linea.split(";");
	            if (datos.length > 0) {
	                infoMap.put(datos[0], datos);
	            }
	        }
	    }

	    return infoMap;
	}

}

