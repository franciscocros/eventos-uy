package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.http.Part;

import webservice.DtEdicion;
import webservice.DtEvento;
import webservice.WebServiceEvento;
import webservice.WebServiceEventoService;
import webservice.WebServiceUsuario;
import webservice.WebServiceUsuarioService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;

import excepciones.ExisteEventoExcepcion;
import excepciones.ExisteUsuarioExcepcion;
import excepciones.FechaNacimientoPosteriorExcepcion;


@WebServlet("/SrvEvento")
@jakarta.servlet.annotation.MultipartConfig
public class SrvEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebServiceUsuarioService servicioUsuario = new WebServiceUsuarioService();
	private WebServiceUsuario ICUw = servicioUsuario.getWebServiceUsuarioPort();
	private WebServiceEventoService servicioEvento = new WebServiceEventoService();
	private WebServiceEvento ICEw = servicioEvento.getWebServiceEventoPort();

    public SrvEvento() {
        super();
    }

	public boolean CargarSesion(HttpServletRequest request, HttpServletResponse response, WebServiceUsuario ICU,String ruta) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("EstadoDeSesion") == null) {
			session.setAttribute("EstadoDeSesion", true);
			//ICU.cargarDatos("Servlet",ruta);
			//cargarEventos(request,response);
	    //	cargarCategorias(request,response,"WEB-INF/index.jsp");
			return true;
		}
		return false;
	}
    
	public void cargarCategorias(HttpServletRequest request, HttpServletResponse response, String ruta) throws ServletException, IOException {
		List<String> categorias = ICEw.listarCategorias().getItem();
		if(categorias != null) {
			TreeSet<String> categoriasOrdenadas = new TreeSet<String>(categorias);
			request.setAttribute("listaCategorias", categoriasOrdenadas);
	        RequestDispatcher dispatcher = request.getRequestDispatcher(ruta);
	        dispatcher.forward(request, response);
		}
	}
	
	

	private void cargarEventos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<DtEvento> eventosList = ICEw.listarEventos().getItem();
		eventosList.sort(Comparator.comparing(DtEvento::getNombre));
		request.setAttribute("eventos", eventosList);
    	cargarCategorias(request,response,"WEB-INF/views/listaEventos.jsp");
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CargarSesion(request,response,this.ICUw,"");
		} catch (Exception e) {
		}
    	String destino = request.getParameter("destino");
    	
    	if(destino != null) {
    		if(destino.equals("index")) {
    			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/index.jsp");
    	        dispatcher.forward(request, response);
    		}else if(destino.equals("detalleEvento")){
    			String ev = request.getParameter("nomEvento");
    			DtEvento dtEv = ICEw.getEvento(ev);
    			request.setAttribute("datosEvento", dtEv);
    			List<DtEdicion> edicionesList = ICEw.listarEdicionesConfirmadas(dtEv.getNombre()).getItem();
    			edicionesList.sort(Comparator.comparing(DtEdicion::getNombre));
    			request.setAttribute("edsConfiEvento", edicionesList);
    			
    			cargarCategorias(request,response,"WEB-INF/views/consultaDeEvento.jsp");
    		}else if(destino.equals("indexLogeado")) {
    			cargarEventos(request,response);
    		}else if(destino.equals("altaEvento")) {
    			cargarCategorias(request,response,"WEB-INF/views/altaEvento.jsp");
    		}else if(destino.equals("listarEventosCategoria")) {
    			String cat = request.getParameter("categoria");
    			List<DtEvento> eventosList = ICEw.listarEventosDeCategoria(cat).getItem();
    			eventosList.sort(Comparator.comparing(DtEvento::getNombre));
    			request.setAttribute("eventos", eventosList);
    	    	cargarCategorias(request,response,"WEB-INF/index.jsp");
    		}else {
    	    	request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
    		}
    	}else {
	    	request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destino = request.getParameter("destino");
		if(destino != null) {
					doGet(request, response);
		}else {
			doGet(request, response);
		}
	}
	
	

}
