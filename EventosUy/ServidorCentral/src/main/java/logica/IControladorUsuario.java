package main.java.logica;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import main.java.excepciones.ExisteInstitucionExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.excepciones.FechaNacimientoPosteriorExcepcion;
import main.java.excepciones.NoExisteEdicionExcepcion;
import main.java.excepciones.NoExisteEvento;
import main.java.excepciones.NoExisteInstitucion;
import main.java.excepciones.NoExisteTipoRegistro;
import main.java.excepciones.NoExisteUsuarioExcepcion;
import main.java.excepciones.SuperaCantidadGratuitos;
import main.java.excepciones.YatienePatrocinioException;

public interface IControladorUsuario {
	public abstract void crearAsistente(String nombre, String nickname, String correo, String apellido, LocalDate fechaNacimiento) throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion;
	public abstract void modificarAsistente(String nombre, String nickname, String correo, String apellido, LocalDate fechaNacimiento) throws NoExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion;
	public abstract void crearOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) throws ExisteUsuarioExcepcion;
	public abstract void modificarOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) throws NoExisteUsuarioExcepcion;
	public abstract void crearInstitucion(String nombre, String descripcion, String paginaWeb) throws ExisteInstitucionExcepcion;
	public abstract Map<String, DTInstitucion> listarInstituciones();
	public abstract Map<String, DTUsuario> listarUsuarios();
	public abstract DTUsuario getDTUsuario(String usuario);
	public abstract void crearPatrocinio(float aporteEcon, String codigo, LocalDate fecha, int cantGratis, ENivelPatrocinio enu, String tiporeg, String evento, String edicion, String institution) throws NoExisteInstitucion, NoExisteEvento, NoExisteEdicionExcepcion, NoExisteTipoRegistro, YatienePatrocinioException, SuperaCantidadGratuitos, Exception;
	public abstract Set<DTPatrocinio> listarPatrocinios(String event, String edi);
	public abstract Set<DTPatrocinio> listarPatrociniosregistro(String edicion, String registro);
	public abstract Set<DTPatrocinio> listarPatrociniosAlt(String event, String edi);
	public abstract void setInstitucionAsistente(String asistente, String institucion);
	public abstract Map<String, DTCompra> listarComprasDeUsuario(String nickname) throws NoExisteUsuarioExcepcion;
	public abstract void liberarManejadorUsuario();
	public abstract DTCompra getDTCompra(String usuario, String idetificador);
	public abstract boolean esAsistente(String usuario);
	public abstract Set<DTEdicion> getSetEdiciones(String string);
	public abstract Map<String, DTUsuario> listarAsistentes();
	public abstract boolean tieneInstitucion(String asistente);
	public abstract String getInstitucionAsistente(String asistente);
	public abstract String findEvento(String nickName, String nombre);
	public abstract boolean existeUsuario(String dato);
	public abstract void cargarDatos(String ruta, String origen) throws Exception;
	public abstract void setImagenUsuario(String usu, String img);
	public abstract void setContraseniaUsuario(String usu, String contra);
	public abstract void setImagenInstitucion(String instit, String img);
	public abstract boolean login(String nombre, String contrasenia);
	public abstract boolean loginAsistente(String nombre, String contrasenia);
	public abstract Set<DTCompraWeb> listarComprasDeUsuarioWeb(String nickname) throws NoExisteUsuarioExcepcion;
	public abstract void aceptarCompraWeb(String nickAsis, String compra);      // PRECONDICIÓN: la compra existe en el sistema
	public abstract void addSeguido(String seguido, String seguidor);
	public abstract void removeSeguidor(String seguido, String seguidor);
	public abstract void removeSeguido(String seguidonick, String seguidornick);
	public abstract Set<DTUsuario> listarSeguidos(String nick);
	public abstract Set<DTUsuario> listarSeguidores(String nick);
}
