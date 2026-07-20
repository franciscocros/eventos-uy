package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.time.LocalDate;
import java.util.function.Function;

import excepciones.NoExisteUsuarioExcepcion;
import excepciones.NoTieneCupo;
import excepciones.YaTieneCompra;
import webservice.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Servlet implementation class srvCompra
 */
@WebServlet({"/srvCompra","/detalleCompra","/listaRegistros"})
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
		 if (request.getServletPath().equals("/srvCompra")) {
			 doGETtNuevaCompra(request,response);
			 
		 }
		 else if(request.getServletPath().equals("/detalleCompra")){
			 doGETDetalleCompra(request,response);
		 }
		 else if(request.getServletPath().equals("/listaRegistros")){
			 try {
				try {
					doGETListaRegistros(request,response);
				} catch (ServletException | IOException | NoExisteUsuarioExcepcion_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 } catch (NoExisteUsuarioExcepcion e) {
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
			 doPOSTNuevaCompra(request,response);
			 
		 }
	}
	private  void doGETDetalleCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String edicion = (String) request.getParameter("edicion");
		String nickname = (String) request.getParameter("nickname");
		
		DtCompra dtc = ICUw.getDTCompra(nickname,edicion);
		//System.out.println(dtc.getEdicion());
		DtAsistente dtu = (DtAsistente) ICUw.getDTUsuario(nickname);
		
		DtEdicion dte = ICEw.getEdicion(dtc.getEvento(), edicion);
		String nombrecompleto = dtu.getNombre() +" "+ dtu.getApellido();
		float costo = dtc.getCosto();
		String fecha = dtc.getFechaCompra();
		String TRregistro = dtc.getTipoRegistro();
		String esPatrocinado ;
		String evento = dtc.getEvento();
		if(costo == 0) {
			esPatrocinado = "Si";
		}else {
			esPatrocinado = "No";		
		}
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaFinEdicion = LocalDate.parse(dte.getFechaFin());
		boolean finalizada = fechaActual.isAfter(fechaFinEdicion);
		
		request.setAttribute("evento", evento);
		request.setAttribute("ediciones", edicion);
		request.setAttribute("nickname", nickname);
		request.setAttribute("nombre", nombrecompleto);
		request.setAttribute("costo", costo);
		request.setAttribute("finalizada", finalizada);
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		request.setAttribute("fecha", fecha);
		request.setAttribute("TRregistro", TRregistro);
		request.setAttribute("esPatrocinado", esPatrocinado);
		request.setAttribute("confirmada", dtc.isAceptada());
		request.setAttribute("imgusu", (dtu.getImagen().equals(""))?"defaultUser.png":dtu.getImagen());
		request.setAttribute("imgedi", (dte.getImagen().equals(""))?"defaultUser.png":dte.getImagen());
		SrvEvento srv = new SrvEvento();
		srv.cargarCategorias(request, response,"WEB-INF/views/detalleCompra.jsp");
		
	}
	private  void doGETtNuevaCompra(HttpServletRequest request, HttpServletResponse response) {
		List<DtEvento> eventos = ICEw.listarEventos().getItem();
		// List<DTEvento> eventos = ordenarSetPorCampo(ICE.listarEventos(), DTEvento::getNombre);
		request.setAttribute("eventos", eventos);
		String eventoSeleccionado = (String) request.getParameter("eventoSelect");
		if (eventoSeleccionado != null){
			request.setAttribute("eventoSel", eventoSeleccionado);
			
		}else{
			
		}
		Map<String,List<DtTipoRegistro> > tregistros = new HashMap();
		if (eventoSeleccionado != null) {
			List<DtEdicion> ediciones = ICEw.listarEdicionesConfirmadas(eventoSeleccionado).getItem();
			//List<DTEdicion> ediciones = ordenarSetPorCampo(ICE.listarEdicionesConfirmadas(eventoSeleccionado), DTEdicion::getNombre); ;
			request.setAttribute("ediciones", ediciones);
			if (ediciones != null) {
				
				for (DtEdicion dtedi : ediciones  ) {
					List<DtTipoRegistro> registros = ICEw.listarTipoRegistro(eventoSeleccionado,dtedi.getNombre()).getItem();
					tregistros.put( dtedi.getNombre(),registros);
				}
					
				
								
			}
			
		}	
		request.setAttribute("tregistros", tregistros);
		try {
			request.getRequestDispatcher("WEB-INF/views/compra.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	

	

	
	private  void doPOSTNuevaCompra(HttpServletRequest request, HttpServletResponse response) {
 		jakarta.servlet.http.HttpSession objSesion = request.getSession();
		
		DtUsuario dtu = (DtUsuario) objSesion.getAttribute("datosUsuario");
		String nick="";
		if(dtu != null) {
			nick = dtu.getNickname();
		}else{
			request.setAttribute("Error", "No ha iniciado sesion");
		}

		String eventoSeleccionado = (String) request.getParameter("evento");
		String edicion= (String) request.getParameter("aceptar").split(";")[0];
		String id = (String) request.getParameter("aceptar").split(";")[1];
		String registro= (String) request.getParameter("tipoRegistro".concat(id)).split(";")[2];
		String costostr= (String) request.getParameter("tipoRegistro".concat(id)).split(";")[1];
		float costo = Float.parseFloat(costostr);
		String cupon = (String) request.getParameter("cupon"); 
		
		
		//System.out.print(eventoSeleccionado + " | "+edicion + " | "+id + " | "+registro + " | "+cupon + " | "+hoy + " | ");
		try {
			if(cupon != "" ) {
				if(ICEw.validoCupon(nick,eventoSeleccionado, edicion, registro, cupon)) {
					costo = 0;
				
					ICEw.altaDeRegistroB(nick, eventoSeleccionado, edicion,registro,LocalDate.now().toString(),  costo);
					request.setAttribute("ok","La compra se realizo correctamete con costo" + costo);
				}else {
					request.setAttribute("Error", "El codigo no es valido");
				}
		
			}else {
				ICEw.altaDeRegistroB(nick, eventoSeleccionado, edicion, registro, LocalDate.now().toString(), costo);
				request.setAttribute("ok","La compra se realizo correctamete con costo $" + costo);
			}
			
				} catch (NoTieneCupo_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("Error", e.getMessage());
		} catch (YaTieneCompra_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("Error", e.getMessage());
		} catch (Exception_Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("Error", e.getMessage());
		}
		try {
			doGet(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void doGETListaRegistros(HttpServletRequest request, HttpServletResponse response) throws NoExisteUsuarioExcepcion, ServletException, IOException, NoExisteUsuarioExcepcion_Exception{
			HttpSession session = request.getSession();
			DtUsuario usu = (DtUsuario) session.getAttribute("datosUsuario");
			//List<DtCompraWeb> compras = ICUw.listarComprasDeUsuarioWeb("sofirod").getItem();
			List<DtCompraWeb> compras = ICUw.listarComprasDeUsuarioWeb(usu.getNickname()).getItem();
			request.setAttribute("comprasUsuario", compras);
			request.setAttribute("nickname", usu.getNickname());
			//request.setAttribute("nickname", "sofirod");
			SrvEvento srv = new SrvEvento();
			srv.cargarCategorias(request, response,"WEB-INF/views/listaRegistros.jsp");
	}
	
	}
	

