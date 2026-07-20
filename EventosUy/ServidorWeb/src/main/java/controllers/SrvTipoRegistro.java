package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservice.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import excepciones.existeTipoRegistro;

@WebServlet("/SrvTipoRegistro")
public class SrvTipoRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebServiceUsuarioService servicioUsuario = new WebServiceUsuarioService();
	private WebServiceUsuario ICUw = servicioUsuario.getWebServiceUsuarioPort();
	private WebServiceEventoService servicioEvento = new WebServiceEventoService();
	private WebServiceEvento ICEw = servicioEvento.getWebServiceEventoPort();


	
    public SrvTipoRegistro() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String destino = request.getParameter("destino");
		String formulario = request.getParameter("formulario");
		//http://localhost:8080/EventosUy-0.0.1-SNAPSHOT/SrvTipoRegistro?destino=detalleTipoRegistro
		//http://localhost:8080/EventosUy-0.0.1-SNAPSHOT/SrvTipoRegistro?destino=altaTipoRegistro
		if(destino != null || request.getAttribute("desdePost") != null) {
			if(destino.equals("altaTipoRegistro") || request.getAttribute("desdePost") != null && request.getAttribute("desdePost").equals("true")) {
				request.setAttribute("desdePost", "false");
				HttpSession session = request.getSession();//El organizador debe estar logeado
				DtUsuario organizador = (DtUsuario)session.getAttribute("datosUsuario");									
				
				List<DtEvento> eventosList = ICEw.listarEventos().getItem();
    			eventosList.sort(Comparator.comparing(DtEvento::getNombre));
    			request.setAttribute("eventos", eventosList);
    			
    			String eventoSelect = (String) request.getParameter("eventoSelect");   
    			if (eventoSelect != null) {    				
    				List<DtEdicion> ediciones =ICEw.listarEdicionesOrganizadorEvento(organizador.getNickname(), eventoSelect).getItem();
    				ediciones.sort(Comparator.comparing(DtEdicion::getNombre));
    				
    				request.setAttribute("eventoSel", eventoSelect);
        			request.setAttribute("ediciones", ediciones);
    			}
    			else {
    				request.setAttribute("eventoSel", null);
        			request.setAttribute("ediciones", null);
    			}
				request.getRequestDispatcher("WEB-INF/views/altaTipoRegistro.jsp").forward(request, response);
				
			}else if(destino.equals("detalleTipoRegistro")) {
				detalleTipoRegistro(request,response);
			}
		}else if(formulario != null) {
			if(formulario.equals("altaTipoRegistro")) {
				altatipoRegistroForm(request,response);
			}
		}else {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("SrvEvento?destino=index");
			dispatcher.forward(request, response);
		}
	}

	private void altatipoRegistroForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("SrvEvento?destino=index");
		dispatcher.forward(request, response);
	}

	private void detalleTipoRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = (String) request.getParameter("treg");
		String edicion = (String)request.getParameter("edi");
		String imagen = (String)request.getParameter("imagen");
		System.out.println(tipo +" -- "+ edicion+" -- "+ imagen );
		List<DtPatrocinio> patrocinios = ICUw.listarPatrociniosregistro(edicion, tipo).getItem();
		request.setAttribute("patrocinios", patrocinios);
		
		DtTipoRegistro dtreg = ICEw.getTipoRegistroEdicion(edicion, tipo);
		request.setAttribute("imagen", imagen);
		request.setAttribute("dtreg", dtreg);
		SrvEvento srv = new SrvEvento();
		srv.cargarCategorias(request, response,"WEB-INF/views/consultaTipoRegistro.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("____________________________________________________________________________________");
		String destino = request.getParameter("destino");
		if(destino != null) {
			if (destino.equals("darAltaTipoRegistro")) {
					String eventoSelec = (String) request.getParameter("evento");
					String edicionSelec = (String) request.getParameter("edicion");
					String nombreTipo = (String) request.getParameter("nombreTipo");
					String descriTipo = (String) request.getParameter("descripcion");
					String costoStr = (String) request.getParameter("costo");
					float  costo = Float.parseFloat(costoStr);
					String cupoStr = (String) request.getParameter("cupo");
					int    cupo = Integer.parseInt(cupoStr);
					
					try {
						ICEw.addTipoRegistro(eventoSelec, edicionSelec, nombreTipo, descriTipo, costo, cupo);
						request.setAttribute("alta", "El tipo de registro se dio de alta con éxito");
					} catch (ExisteTipoRegistro_Exception e) {
						// TODO Auto-generated catch block
						request.setAttribute("Error", e.getMessage());
					}
			}
			request.setAttribute("desdePost", "true");
			doGet(request, response);
		}
	}
}
