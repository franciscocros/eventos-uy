package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import webservice.*;
import webservice.Exception;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import excepciones.NoExisteEdicionExcepcion;
import excepciones.NoExisteEvento;
import excepciones.NoExisteInstitucion;
import excepciones.NoExisteTipoRegistro;
import excepciones.SuperaCantidadGratuitos;
import excepciones.YatienePatrocinioException;

/**
 * Servlet implementation class SrvPatrocinio
 */
@WebServlet("/SrvPatrocinio")
public class SrvPatrocinio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebServiceUsuarioService servicioUsuario = new WebServiceUsuarioService();
	private WebServiceUsuario ICUw = servicioUsuario.getWebServiceUsuarioPort();
	private WebServiceEventoService servicioEvento = new WebServiceEventoService();
	private WebServiceEvento ICEw = servicioEvento.getWebServiceEventoPort();
	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SrvPatrocinio() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String destino = (String) request.getParameter("destino");
		String formulario = (String) request.getParameter("formulario");
		if(destino != null) {
			if(destino.equals("consultaPatrocinio")) {
				consultapatrocinio(request,response);
			}else if(destino.equals("altaPatrocinio")) {
				altapatrocinio(request,response);
			}
		}else if(formulario != null) {
			try {
				procesaroFormulario(request,response);
			} catch (ServletException | IOException | NoExisteEvento | NoExisteEdicionExcepcion | NoExisteTipoRegistro
					| YatienePatrocinioException | SuperaCantidadGratuitos e) {
			}
		}else {
	    	request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
		}
	}
	
	private void procesaroFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoExisteEvento, NoExisteEdicionExcepcion, NoExisteTipoRegistro, YatienePatrocinioException, SuperaCantidadGratuitos {
		String evento = (String) request.getParameter("evento");
		String edicion = (String) request.getParameter("edicion");
		String tiporeg = (String) request.getParameter("tiporeg");
		String instit = (String) request.getParameter("inst");
		String nivel =(String) request.getParameter("nivel").toUpperCase();
		String cantreg = request.getParameter("cantreg");
		
		String  aporte = request.getParameter("aporte");
		String codigo = (String) request.getParameter("codigo");
		LocalDate fecha = LocalDate.now();

		try {
			ICUw.crearPatrocinio( Float.parseFloat(aporte), codigo, fecha.toString(),Integer.parseInt(cantreg) , nivel, tiporeg, evento,edicion, instit);
			request.getRequestDispatcher("SrvEvento?destino=index").forward(request, response);
		}catch( Exception_Exception error) {
			
			request.setAttribute("aporte_old", aporte);
			request.setAttribute("codigo_old", codigo);
			request.setAttribute("cantreg_old", cantreg);
			request.setAttribute("nivel_old", nivel);
			request.setAttribute("tiporeg_old", tiporeg);
			request.setAttribute("evento_old", evento);
			request.setAttribute("edicion-old", edicion);
			request.setAttribute("instit_old", instit);
			

			if(error.getMessage() != null) {
				System.out.println("mensaje: "+error);
				request.setAttribute("error", error.getMessage());
		    	altapatrocinio(request,response);
			}else {
				request.getRequestDispatcher("SrvEvento?destino=index").forward(request, response);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", "El numero ingresado es mas grande de lo permitido");
			altapatrocinio(request,response);
		} catch (NoExisteEdicionExcepcion_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", e.getMessage());
			altapatrocinio(request,response);
		} catch (NoExisteEvento_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", e.getMessage());
			altapatrocinio(request,response);
		} catch (NoExisteInstitucion_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("err", true);
			request.setAttribute("msgerr", e.getMessage());
			altapatrocinio(request,response);
		} catch (NoExisteTipoRegistro_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", e.getMessage());
			altapatrocinio(request,response);
		} catch (SuperaCantidadGratuitos_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", e.getMessage());
			altapatrocinio(request,response);
		} catch (YatienePatrocinioException_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", e.getMessage());
			altapatrocinio(request,response);
		}
	}

	public void cargarInstituciones(HttpServletRequest request) {
		List<DtInstitucion> instit = (List<DtInstitucion>) ICUw.listarInstituciones().getItem();
		List<String> instituciones =  new ArrayList<>();
		for (DtInstitucion dti : instit) {
			instituciones.add(dti.getNombre());
		}
		   
		request.setAttribute("instituciones", instituciones);
	}
    
	private void altapatrocinio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cargarInstituciones(request);
		HttpSession session = request.getSession();
		List<DtEvento> eventos = (List<DtEvento>) ICEw.listarEventos().getItem();
		DtUsuario usuario = (DtUsuario) session.getAttribute("datosUsuario");
		List<DtEdicion> ediciones = ICUw.getSetEdiciones(usuario.getNickname()).getItem();
		
		List<DtEvento> eventosOrden = new ArrayList<>();
		
		if (eventos != null) {
			eventosOrden.addAll(eventos);
			eventosOrden.sort(Comparator.comparing(DtEvento::getNombre));
		}
		
		request.setAttribute("eventos", eventos);
		request.setAttribute("ediciones", ediciones);
		
    	request.getRequestDispatcher("WEB-INF/views/altaPatrocinio.jsp").forward(request, response);
	}

	private void consultapatrocinio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String aporte = request.getParameter("aporte");
    	String codigo = request.getParameter("codigo");
    	String fecha = request.getParameter("fecha");
    	String gratis = request.getParameter("gratis");
    	String nivel = request.getParameter("nivel"); 
    	String imagen = request.getParameter("imagen");
    	
    	request.setAttribute("aporte", aporte);
    	request.setAttribute("codigo",codigo );
    	request.setAttribute("fecha", fecha);
    	request.setAttribute("gratis",gratis );
    	request.setAttribute("nivel",nivel );
    	request.setAttribute("imagen",imagen );

		SrvEvento srv = new SrvEvento();
		srv.cargarCategorias(request, response,"WEB-INF/views/detallePatrocinio.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
