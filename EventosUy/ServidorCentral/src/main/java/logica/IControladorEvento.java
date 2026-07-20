package main.java.logica;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import main.java.excepciones.ExisteCategoriaExcepcion;
import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.NoTieneCupo;
import main.java.excepciones.YaTieneCompra;
import main.java.excepciones.existeTipoRegistro;
public interface IControladorEvento {
	public abstract void addTipoRegistro(String nombEvento, String nombEdicion, String nombre, String desc, float costo, int cupo) throws existeTipoRegistro ;
	public abstract Set<DTTipoRegistro> listarTipoRegistro(String nombEvento, String nombEdicion);
	public abstract Set<String> listarCategorias();
	public abstract Set<DTEvento> listarEventos();
	public abstract DTEvento getEvento(String nombreevento) ;
	public abstract void addEvento(String nombre, String descripcion, String sigla, LocalDate fechaAlta, Set<String> categorias) throws ExisteEventoExcepcion;
	public abstract Set<DTEdicion> listarEdiciones(String nombEvento);
	public abstract Set<DTEdicion> listarEdicionesConfirmadas(String nombEvento);
	public abstract DTEdicion getEdicion(String nomEvent, String nomEdicion);
	public abstract DTTipoRegistro getTipoRegistro(String nomEvent, String nomEdicion, String nomTipo);
	public abstract void altaEdicion(String nomEvento, String nickOrganizador, String nombre , String ciudad, String sigla , LocalDate fechaAlta, LocalDate fechaFin, LocalDate fechaInicion )  throws ExisteEdicion ;
	public abstract void altaCategoria(String cat) throws ExisteCategoriaExcepcion;
	public abstract Map<String, Ciudad> listarCiudades();
	public abstract void altaCiudad(String ciudad, String pais);
	public void altaDeRegistro(String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, LocalDate fechaReg) throws NoTieneCupo, YaTieneCompra, Exception;
	public void altaDeRegistro(String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, LocalDate fechaReg , float costo ) throws NoTieneCupo, YaTieneCompra, Exception;
	public boolean validoCupon( String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, String cupon) throws NoTieneCupo, YaTieneCompra, Exception;
	public abstract void liberarManejadorEvento();
	public abstract void setImagenEvento(String evento, String img);
	public abstract void setImagenEdicion(String edicion, String img);
	public abstract void setEstadoEdicion(String edicion, EEstadoEdicion est);
	public abstract Set<DTEdicion> listarEdicionesSinProcesar(String nombEvento);
	public abstract boolean existeEvento(String nombre);
	public abstract Set<DTEvento> listarEventosDeCategoria(String cat);
	public abstract Set<DTEdicion> listarEdicionesOrganizadorEvento(String organizador, String evento);
	public abstract DTTipoRegistro getTipoRegistroEdicion(String nomEdicion, String nomTipo);
	public abstract DTEdicion getEdicionB(String edicion);
	public abstract void setFinalidado(String edicion);
	public abstract void visitar(String evento);
	public abstract int getVisitas(String evento);
	public abstract void setVideo(String edi,String video);
	public abstract String getVideoEdicion(String edi);
}