package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Function;

import excepciones.NoExisteUsuarioExcepcion;
import excepciones.NoTieneCupo;
import excepciones.YaTieneCompra;
import webservice.DtAsistente;
import webservice.DtCompra;
import webservice.DtCompraWeb;
import webservice.DtEdicion;
import webservice.DtEvento;
import webservice.DtUsuario;
import webservice.NoExisteUsuarioExcepcion_Exception;
import webservice.WebServiceEvento;
import webservice.WebServiceEventoService;
import webservice.WebServiceUsuario;
import webservice.WebServiceUsuarioService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Servlet implementation class srvCompra
 */
@WebServlet({"/srvCompra","/detalleCompra","/listaRegistros","/asistenciaCompra"})
public class srvCompra extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebServiceUsuarioService servicioUsuario = new WebServiceUsuarioService();
	private WebServiceUsuario ICUw = servicioUsuario.getWebServiceUsuarioPort();
	private WebServiceEventoService servicioEvento = new WebServiceEventoService();
	private WebServiceEvento ICEw = servicioEvento.getWebServiceEventoPort();
    public srvCompra() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public static <T, U extends Comparable<? super U>> List<T> ordenarSetPorCampo(
            Set<T> conjunto, Function<T, U> claveExtractor) {
    	   // Si el set es null → retorna null
        if (conjunto == null) {
            return null;
        }

        List<T> lista = new ArrayList<>(conjunto);

        // Si está vacío, devolvemos lista vacía (no null)
        if (lista.isEmpty()) {
            return lista;
        }
        lista.sort(Comparator.comparing(claveExtractor));
        return lista;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*String destino = request.getParameter("destino");
		if(destino != null && destino.equals("edicionesConRegistro")) { 
        	HttpSession session = request.getSession(false);
        	DTUsuario dtUsu = (DTUsuario) session.getAttribute("datosUsuario");
        	String nickAsist = dtUsu.getNickName();
        	
        	Set<DTCompraWeb> comprasAsist;
			try {
				comprasAsist = ICU.listarComprasDeUsuarioWeb(nickAsist);
				List<DTCompraWeb> compList = new ArrayList<>(comprasAsist);
				compList.sort(Comparator.comparing(DTCompraWeb::getId));

				request.setAttribute("compras", compList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/listaRegistros.jsp");
				dispatcher.forward(request, response);
			} catch (NoExisteUsuarioExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
	     } */
		  if(request.getServletPath().equals("/detalleCompra")){
			 doGETDetalleCompra(request,response);
		 }		
		 else if(request.getServletPath().equals("/asistenciaCompra")){
			 try {
				doGETAsistenciaCompra(request,response);
			 } catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (NoExisteUsuarioExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		 }
		 else if(request.getServletPath().equals("/listaRegistros")){
			 try {
				doGETListaRegistros(request,response);
			 } catch (NoExisteUsuarioExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (NoExisteUsuarioExcepcion_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 if (request.getServletPath().equals("/srvCompra")) {
			 doGet(request,response);			 
		 }
	}
	
	private void doGETAsistenciaCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoExisteUsuarioExcepcion {
		HttpSession session = request.getSession(true);
		DtUsuario datosAsist = (DtUsuario) session.getAttribute("datosUsuario");
		String nickAsist = datosAsist.getNickname();
		String ediCompra = (String) request.getParameter("ediCompra");	

		ICUw.aceptarCompraWeb(nickAsist, ediCompra);
				
		request.setAttribute("nickname", nickAsist);
		request.setAttribute("edicion", ediCompra);

		doGETDetalleCompra(request, response);
	}
	
	private  void doGETDetalleCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String edicion = (String) request.getParameter("edicion");
		String nickname = (String) request.getParameter("nickname");
		if(edicion == null && nickname == null) {
			edicion = (String) request.getAttribute("edicion");
			nickname = (String) request.getAttribute("nickname");
		}
		DtCompra dtc = ICUw.getDTCompra(nickname,edicion);
		DtAsistente dtu = (DtAsistente) ICUw.getDTUsuario(nickname);
		
		DtEdicion dte = ICEw.getEdicion(dtc.getEvento(), edicion);
		String nombrecompleto = dtu.getNombre() +" "+ dtu.getApellido();
		float costo = dtc.getCosto();
		LocalDate fecha = LocalDate.parse( dtc.getFechaCompra());
		String TRregistro = dtc.getTipoRegistro();
		String idCompra = dtc.getIdentificador();
		String esPatrocinado ;
		String evento = dtc.getEvento();
		if(costo == 0) {
			esPatrocinado = "Si";
		}else {
			esPatrocinado = "No";
		}		
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaInicioEdicion = LocalDate.parse(dte.getFechaInicio());
		boolean comenzada = fechaActual.isAfter(fechaInicioEdicion);
		
		request.setAttribute("ediCompra", edicion);
		request.setAttribute("evento", evento);
		request.setAttribute("ediciones", edicion);
		request.setAttribute("nickname", nickname);
		request.setAttribute("nombre", nombrecompleto);
		request.setAttribute("costo", costo);
		request.setAttribute("comenzada", comenzada);
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		request.setAttribute("fecha", fecha.format(customFormatter));
		request.setAttribute("fechaInicioEdicion", fechaInicioEdicion.format(customFormatter));
		request.setAttribute("TRregistro", TRregistro);
		request.setAttribute("esPatrocinado", esPatrocinado);
		request.setAttribute("confirmada", dtc.isAceptada());
		request.setAttribute("imgusu", (dtu.getImagen().equals(""))?"defaultUser.png":dtu.getImagen());
		request.setAttribute("imgedi", (dte.getImagen().equals(""))?"defaultUser.png":dte.getImagen());
		SrvEvento srv = new SrvEvento();
		srv.cargarCategorias(request, response,"WEB-INF/views/detalleCompra.jsp");
		
	}
	
	
	
	private void doGETListaRegistros(HttpServletRequest request, HttpServletResponse response) throws NoExisteUsuarioExcepcion, ServletException, IOException, NoExisteUsuarioExcepcion_Exception{
			HttpSession session = request.getSession();
			LocalDate fechaActual = LocalDate.now();
			DtUsuario usu = (DtUsuario) session.getAttribute("datosUsuario");
			List<DtCompraWeb> compras = ICUw.listarComprasDeUsuarioWeb(usu.getNickname()).getItem();
			List<DtCompraWeb> comprasAConfirmar = new ArrayList<DtCompraWeb>();
			for (DtCompraWeb comp : compras) {
				DtEdicion ediComp = ICEw.getEdicionB(comp.getEdicion());
				if (!comp.isAceptada() && fechaActual.isAfter(LocalDate.parse(ediComp.getFechaInicio()))) {
					comprasAConfirmar.add(comp);
				}
			}
			String soloNoConfirmadas = request.getParameter("soloNoConfirmadas");
			request.setAttribute("soloNoConfirmadas", soloNoConfirmadas);
			request.setAttribute("compras", compras);
			request.setAttribute("comprasAConfirmar", comprasAConfirmar);
			request.setAttribute("nickname", usu.getNickname());
			SrvEvento srv = new SrvEvento();
			srv.cargarCategorias(request, response,"WEB-INF/views/listaRegistros.jsp");
	}
	
	}
	

