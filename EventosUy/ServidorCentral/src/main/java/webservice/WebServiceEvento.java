package main.java.webservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.ws.Endpoint;
import main.java.excepciones.ExisteCategoriaExcepcion;
import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.NoTieneCupo;
import main.java.excepciones.YaTieneCompra;
import main.java.excepciones.existeTipoRegistro;
import main.java.logica.Ciudad;
import main.java.logica.DTAsistente;
import main.java.logica.DTCiudad;
import main.java.logica.DTCompraWeb;
import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.DTOrganizador;
import main.java.logica.DTPatrocinio;
import main.java.logica.DTTipoRegistro;
import main.java.logica.EEstadoEdicion;
import main.java.logica.Evento;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.resources.Config;

import jakarta.jws.WebService;
import java.util.List;
@XmlSeeAlso({
    DTCompraWeb.class,
    DTAsistente.class,
    DTOrganizador.class,
    StringArrayWrapper.class 
    
})
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServiceEvento {
	private Fabrica F = Fabrica.getInstance();
	private IControladorEvento ice = F.getIControladorEvento();
	 private Endpoint endpoint = null;
	    //Constructor
	    public WebServiceEvento(){}//
	    @WebMethod(exclude = true)
	    public void publicar(String url) {
	        endpoint = Endpoint.publish(url, this);
	        System.out.println("WebService publicado en: " + url);
	    }
	    @WebMethod(exclude = true)
	    public void stop() {
	        if (endpoint != null) {
	            endpoint.stop();
	            System.out.println("Endpoint detenido");
	        }
	    }

	    @WebMethod(exclude = true)
	    public Endpoint getEndpoint() {
	            return endpoint;
	    }

	    @WebMethod
	    public void addTipoRegistro(String nombEvento, String nombEdicion, String nombre, String desc, float costo, int cupo) throws existeTipoRegistro{
	    	ice.addTipoRegistro(nombEvento, nombEdicion, nombre, desc, costo, cupo);
	    }
	    @WebMethod
	    public DTTipoRegistro[] listarTipoRegistro(String nombEvento, String nombEdicion){
	    	Set<DTTipoRegistro> lista = ice.listarTipoRegistro(nombEvento, nombEdicion);
	    	DTTipoRegistro[] resultado = new DTTipoRegistro[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTTipoRegistro[0]);
	    	}

	    	return resultado;
	    }
	   
	    @WebMethod
	    public  @XmlJavaTypeAdapter(StringArrayAdapter.class) String[] listarCategorias(){
	    	Set<String> lista = ice.listarCategorias();
	    	String[] resultado = new String[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new String[0]);
	    	}

	    	return resultado;
	    }

	   @WebMethod
	    public DTEvento[] listarEventos(){
	    	List<DTEvento> lista =  ice.listarEventos().stream().toList();
	    	List<DTEvento> aux = new ArrayList<>();
			for (DTEvento entry :lista) {
				 if (!entry.getFinalizado()) {
					 aux.add(entry);
				 }
			}
	    	DTEvento[] resultado = new DTEvento[0] ;
	    	
	    	if (lista != null){
	    		resultado=aux.toArray(new DTEvento[0]);
	    	}

	    	return resultado;
	    }

	    @WebMethod
	    public DTEvento getEvento(String nombreevento) {
	    	return ice.getEvento(nombreevento);
	    }

	    @WebMethod
	    public void addEvento(String nombre, String descripcion, String sigla, String fechaAlta, @XmlJavaTypeAdapter(StringArrayAdapter.class) String[] categorias) throws ExisteEventoExcepcion {
	        LocalDate fecha = LocalDate.parse(fechaAlta);
	
	        Set<String> categoriasSet = new HashSet<>();
	        if (categorias != null) {
	            for (String cat : categorias) {
	                categoriasSet.add(cat);
	            }
	        }

	        ice.addEvento(nombre, descripcion, sigla, fecha, categoriasSet);
	    }


	    @WebMethod
	    public DTEdicion[] listarEdiciones(String nombEvento){
	    	Set<DTEdicion> lista =  ice.listarEdiciones(nombEvento);
	    	DTEdicion[] resultado = new DTEdicion[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTEdicion[0]);
	    	}

	    	return resultado;
	    }

	    @WebMethod
	    public DTEdicion[] listarEdicionesConfirmadas(String nombEvento){

	    	Set<DTEdicion> lista =  ice.listarEdicionesConfirmadas(nombEvento);
	    	DTEdicion[] resultado = new DTEdicion[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTEdicion[0]);
	    	}

	    	return resultado;
	    }

	    @WebMethod
	    public DTEdicion getEdicion(String nomEvent, String nomEdicion) {
	    	return ice.getEdicion(nomEvent, nomEdicion);
	    }

	    @WebMethod
	    public DTTipoRegistro getTipoRegistro(String nomEvent, String nomEdicion, String nomTipo) {
	    	return ice.getTipoRegistro(nomEvent, nomEdicion, nomTipo);
	    }

	    @WebMethod
	    public void altaEdicion(String nomEvento, String nickOrganizador, String nombre , String ciudad, String sigla , String fechaAlta, String fechaFin, String fechaInicion )  throws ExisteEdicion{
	    	ice.altaEdicion(nomEvento, nickOrganizador, nombre, ciudad, sigla, LocalDate.parse(fechaAlta), LocalDate.parse(fechaFin), LocalDate.parse(fechaInicion));
	    }

	    @WebMethod
	    public void altaCategoria(String cat) throws ExisteCategoriaExcepcion{
	    	ice.altaCategoria(cat);
	    }

	    @WebMethod
	    public void altaCiudad(String ciudad, String pais) {
	    	ice.altaCiudad(ciudad, pais);
	    }

	    @WebMethod
	    public DTCiudad[] listarCiudades() {
	       // Map<String, Ciudad> mapa = ; // tu lógica interna
	        ArrayList<DTCiudad> lista = new ArrayList<>();

	        for (Map.Entry<String, Ciudad> entry : ice.listarCiudades().entrySet()) {
	            lista.add(new DTCiudad(entry.getValue().getNombre(), entry.getValue().getPais()));
	        }
	        return lista.toArray(new DTCiudad[0]);
	    }

	    @WebMethod
	    public void altaDeRegistro(String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, String fechaReg) throws NoTieneCupo, YaTieneCompra, Exception{
	    	ice.altaDeRegistro(nickAsistente, nombreEento, nombreEdicion, nombreTipoReg, LocalDate.parse(fechaReg));
	    }

	    @WebMethod
	    public void altaDeRegistroB(String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, String fechaReg , float costo ) throws NoTieneCupo, YaTieneCompra, Exception{
	    	LocalDate fecha = LocalDate.parse(fechaReg);
	    	ice.altaDeRegistro(nickAsistente, nombreEento, nombreEdicion, nombreTipoReg, fecha, costo);
	    }

	    @WebMethod
	    public boolean validoCupon( String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, String cupon) throws NoTieneCupo, YaTieneCompra, Exception{
	    	return ice.validoCupon(nickAsistente, nombreEento, nombreEdicion, nombreTipoReg, cupon);
	    }

	    @WebMethod
	    public void liberarManejadorEvento() {
	    	ice.liberarManejadorEvento();
	    }

	    @WebMethod
	    public  void setImagenEvento(String evento, String img) {
	    	ice.setImagenEvento(evento, img);
	    }

	    @WebMethod
	    public void setImagenEdicion(String edicion, String img) {
	    	ice.setImagenEdicion(edicion, img);
	    }

	    @WebMethod
	    public void setEstadoEdicion(String edicion, EEstadoEdicion est) {
	    	ice.setEstadoEdicion(edicion, est);
	    }

	    @WebMethod
	    public DTEdicion[] listarEdicionesSinProcesar(String nombEvento){
	 
	    	Set<DTEdicion> lista =  ice.listarEdiciones(nombEvento);
	    	DTEdicion[] resultado = new DTEdicion[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTEdicion[0]);
	    	}

	    	return resultado;
	    }

	    @WebMethod
	    public boolean existeEvento(String nombre) {
	    	return ice.existeEvento(nombre);
	    }

	    @WebMethod
	    public DTEvento[] listarEventosDeCategoria(String cat){
	    	Set<DTEvento> lista =  ice.listarEventosDeCategoria(cat);
	    	DTEvento[] resultado = new DTEvento[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTEvento[0]);
	    	}

	    	return resultado;
	    }

	    @WebMethod
	    public DTEdicion[] listarEdicionesOrganizadorEvento(String organizador, String evento){

	    	Set<DTEdicion> lista =  ice.listarEdicionesOrganizadorEvento(organizador, evento);
	    	DTEdicion[] resultado = new DTEdicion[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTEdicion[0]);
	    	}

	    	return resultado;
	    }

	    @WebMethod
	    public DTTipoRegistro getTipoRegistroEdicion(String nomEdicion, String nomTipo) {
	    	return ice.getTipoRegistroEdicion(nomEdicion, nomTipo);
	    }

	    @WebMethod
	    public DTEdicion getEdicionB(String edicion) {
	    	return ice.getEdicionB(edicion);
	    }
	    @WebMethod
	    public void setFinalizado(String edicion) {
	    	ice.setFinalidado(edicion);
	    }

}
