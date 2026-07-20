package main.java.webservice;
import java.time.LocalDate;
import java.util.Set;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.ws.Endpoint;
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
import main.java.logica.Asistente;
import main.java.logica.Compra;
import main.java.logica.DTAsistente;
import main.java.logica.DTCompra;
import main.java.logica.DTCompraWeb;
import main.java.logica.DTEdicion;
import main.java.logica.DTInstitucion;
import main.java.logica.DTOrganizador;
import main.java.logica.DTPatrocinio;
import main.java.logica.DTUsuario;
import main.java.logica.ENivelPatrocinio;
import main.java.logica.Fabrica;
import main.java.logica.IControladorUsuario;
import main.java.logica.Usuario;
import main.java.resources.Config;


@XmlSeeAlso({
    DTCompraWeb.class,
    DTAsistente.class,
    DTOrganizador.class,
    StringArrayWrapper.class,
    StringArrayWrapper.class 
    
})
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServiceUsuario {
	private Fabrica F = Fabrica.getInstance();
	private IControladorUsuario icu = F.getIControladorUsuario();
	 private Endpoint endpoint   = null;
	 
	    //Constructor
	    public WebServiceUsuario(){}
	    
		@WebMethod(exclude = true)
		public void publicar(String url) {
		    endpoint = Endpoint.publish(url, this);
		    System.out.println("WebService publicado en: " + url);
		}
	    @WebMethod(exclude = true)
	    public Endpoint getEndpoint() {
	            return endpoint;
	    }

	    @WebMethod(exclude = true)
	    public void stop() {
	        if (endpoint != null) {
	            endpoint.stop();
	            System.out.println("Endpoint detenido");
	        }
	    }
	    
	    @WebMethod
	    public @XmlJavaTypeAdapter(StringArrayAdapter.class) String[] obtenerMensajes(String nombre) {
	        return new String[] { "Hola " + nombre, "Bienvenido al servicio", "Esto es un String[]" };
	    }
	    
	    @WebMethod
	    public String procesarMensajes( @XmlJavaTypeAdapter(StringArrayAdapter.class) String[] mensajes) {
	        if (mensajes == null) return "Sin mensajes";
	        return String.join(", ", mensajes);
	    }
	    @WebMethod
		public void crearAsistente(String nombre, String nickname, String correo, String apellido, String fechaNacimiento) throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
	    	icu.crearAsistente(nombre, nickname, correo, apellido, LocalDate.parse(fechaNacimiento));
	    }
		@WebMethod
		public void modificarAsistente(String nombre, String nickname, String correo, String apellido, String fechaNacimiento) throws NoExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion{
			icu.modificarAsistente(nombre, nickname, correo, apellido, LocalDate.parse(fechaNacimiento));
		}
		@WebMethod
		public void crearOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) throws ExisteUsuarioExcepcion{
			icu.crearOrganizador(nombre, nickname, correo, descripcion, paginaWeb);
		}

		@WebMethod
		public void modificarOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) throws NoExisteUsuarioExcepcion{
			icu.modificarOrganizador(nombre, nickname, correo, descripcion, paginaWeb);
		}
		@WebMethod public void crearInstitucion(String nombre, String descripcion, String paginaWeb) throws ExisteInstitucionExcepcion{
			icu.crearInstitucion(nombre, descripcion, paginaWeb);
		}
		 @WebMethod
		public DTInstitucion[] listarInstituciones() {
			 DTInstitucion[] resultado = new DTInstitucion[0] ;
	    	if (icu.listarInstituciones().values()!= null){
	    		resultado=icu.listarInstituciones().values().toArray(new DTInstitucion[0]);
	    	}

	    	return resultado;
		}

		@WebMethod
		public DTUsuario[] listarUsuarios() {
			DTUsuario[] resultado = new DTUsuario[0] ;
	    	if (icu.listarUsuarios().values() != null){
	    		resultado=icu.listarUsuarios().values().toArray(new DTUsuario[0]);
	    	}

	    	return resultado;
		}
		
		@WebMethod
		public DTUsuario[] listarSeguidores(String nick) {
			DTUsuario[] resultado = new DTUsuario[0];
			if (icu.listarSeguidores(nick) != null) {
				resultado = icu.listarSeguidores(nick).toArray(new DTUsuario[0]);
			}
			return resultado;
		}
			
		
		@WebMethod
		public DTUsuario[] listarSeguidos(String nick) {
			DTUsuario[] resultado = new DTUsuario[0];
			if (icu.listarSeguidos(nick) != null) {
				resultado = icu.listarSeguidos(nick).toArray(new DTUsuario[0]);
			}
			return resultado;
		}
		
		@WebMethod
		public void addSeguidor(String seguido, String seguidor) {
			icu.addSeguido(seguido, seguidor);
		}
		
		@WebMethod
		public void removeSeguido(String seguido, String seguidor) {
			icu.removeSeguido(seguido, seguidor);;
		}
		
			@WebMethod
		public DTUsuario getDTUsuario(String usuario) {
			return icu.getDTUsuario(usuario);

		}
		@WebMethod
		public void crearPatrocinio(float aporteEcon, String codigo, String fecha, int cantGratis, String enu, String tiporeg, String evento, String edicion, String institution) throws NoExisteInstitucion, NoExisteEvento, NoExisteEdicionExcepcion, NoExisteTipoRegistro, YatienePatrocinioException, SuperaCantidadGratuitos, Exception{
			icu.crearPatrocinio(aporteEcon, codigo, LocalDate.parse(fecha), cantGratis, ENivelPatrocinio.valueOf(enu), tiporeg, evento, edicion, institution);
		}
		@WebMethod
		public DTPatrocinio[] listarPatrociniosregistro(String edicion, String registro){
			Set<DTPatrocinio> dtps = icu.listarPatrociniosregistro(edicion, registro);
			DTPatrocinio[] resultado = new DTPatrocinio[0] ;
			if (dtps!= null) {
				resultado = dtps.toArray(new DTPatrocinio[0]);
			}
				
			return  resultado;

		}
		@WebMethod
		public DTPatrocinio[] listarPatrociniosAlt(String event, String edi){
			//ArrayList<DTPatrocinio> myArrayList = new ArrayList<>( );
			Set<DTPatrocinio> lista =  icu.listarPatrociniosAlt(event, edi);
			DTPatrocinio[] resultado = new DTPatrocinio[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTPatrocinio[0]);
	    	}

	    	return resultado;

		}
		@WebMethod
		public void setInstitucionAsistente(String asistente, String institucion) {
			icu.setInstitucionAsistente(asistente, institucion);
		}
		@WebMethod
		public DTCompra getDTCompra(String usuario, String idetificador) {
			return icu.getDTCompra(usuario, idetificador);

		}
		@WebMethod
		public boolean esAsistente(String usuario) {
			return icu.esAsistente(usuario);

		}
		@WebMethod
		public DTEdicion[] getSetEdiciones(String edi){
			//ArrayList<DTEdicion> myArrayList = new ArrayList<>( icu.getSetEdiciones( edi));
			Set<DTEdicion> lista =  icu.getSetEdiciones( edi);
			DTEdicion[] resultado = new DTEdicion[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTEdicion[0]);
	    	}

	    	return resultado;
		}
		@WebMethod
		public String getInstitucionAsistente(String asistente) {
			return asistente;

		}
		@WebMethod
		public String findEvento(String nickName, String nombre) {
			return icu.findEvento(nickName, nombre);

		}
		@WebMethod
		public boolean existeUsuario(String dato) {
			return icu.existeUsuario(dato);

		}
		@WebMethod
		public void setImagenUsuario(String usu, String img) {
			icu.setImagenUsuario(usu, img);
		}
		@WebMethod
		public void setContraseniaUsuario(String usu, String contra) {
			icu.setContraseniaUsuario(usu, contra);
		}
		@WebMethod
		public void setImagenInstitucion(String instit, String img) {
			icu.setImagenInstitucion(instit, img);
		}
		@WebMethod
		public boolean login(String nombre, String contrasenia) {
			return icu.login(nombre, contrasenia);

		}
		@WebMethod
		public DTCompraWeb[] listarComprasDeUsuarioWeb(String nickname) throws NoExisteUsuarioExcepcion{
			//ArrayList<DTCompraWeb> myArrayList = new ArrayList<>(  icu.listarComprasDeUsuarioWeb(nickname));
			Set<DTCompraWeb> lista = icu.listarComprasDeUsuarioWeb(nickname);
			DTCompraWeb[] resultado = new DTCompraWeb[0] ;
	    	if (lista != null){
	    		resultado=lista.toArray(new DTCompraWeb[0]);
	    	}

	    	return resultado;
		}
		
		@WebMethod
		 public void aceptarCompraWeb(String nickAsis, String compraID) {     // PRECONDICIÓN: la compra existe en el sistema
			 icu.aceptarCompraWeb(nickAsis, compraID);
		 }

		@WebMethod
		 public boolean loginAsistente(String nombre, String contrasenia) {
			 return icu.loginAsistente(nombre, contrasenia);
		 }
}
