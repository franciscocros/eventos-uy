package controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import excepciones.ExisteEdicion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;

import webservice.DtEvento;
import webservice.DtOrganizador;
import webservice.DtUsuario;
import webservice.ExisteEdicion_Exception;
import webservice.WebServiceEvento;
import webservice.WebServiceEventoService;
import webservice.WebServiceUsuario;
import webservice.WebServiceUsuarioService;


import controllers.SrvEvento;

@WebServlet("/SrvEdicion")
@MultipartConfig
public class SrvEdicion extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
	private WebServiceUsuarioService servicioUsuario = new WebServiceUsuarioService();
	private WebServiceUsuario ICUw = servicioUsuario.getWebServiceUsuarioPort();
	private WebServiceEventoService servicioEvento = new WebServiceEventoService();
	private WebServiceEvento ICEw = servicioEvento.getWebServiceEventoPort();


    private void cargarEventos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	List<DtEvento> eventos = ICEw.listarEventos().getItem();
    	
    	if (eventos == null) {
    	    eventos = new ArrayList<>();
    	}
        eventos.sort(Comparator.comparing(DtEvento::getNombre));
        req.setAttribute("eventos", eventos);
        List<webservice.Ciudad> ciudades = ICEw.listarCiudades().getItem();
        req.setAttribute("ciudades", ciudades);
        req.getRequestDispatcher("WEB-INF/views/altaEdicion.jsp").forward(req, resp);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String destino = req.getParameter("destino");
        if ("altaEdicion".equals(destino)) {
        	cargarEventos(req,resp);

        } else if ("detalleEdiciono".equals(destino)) {

            String edicion = req.getParameter("edicion");
            String evento = req.getParameter("evento"); //esto al final no es necesario, no se usa para nada.
            
            webservice.DtEdicion dte = ICEw.getEdicionB(edicion);
            Set<String> dtregi = new HashSet<>(dte.getTiposRegistros());
            String org = dte.getOrganizador();
            webservice.DtUsuario dtu = ICUw.getDTUsuario(org);           
            Set<webservice.DtCompra> dtcompra = new HashSet<>(dte.getDatosCompras());
            Set<webservice.DtPatrocinio> dtpat = new HashSet<>(dte.getPatrocinios());
            
			boolean soyOrganizador= false;
            boolean tengoCompra   = false;
            String  logueado     = null;
			
			HttpSession sesion = req.getSession(false);
            if (sesion != null) {

            	webservice.DtUsuario dtulogin = (webservice.DtUsuario) sesion.getAttribute("datosUsuario");
                if (dtulogin != null) {
				   logueado = dtulogin.getNickname(); 		
                   soyOrganizador = (org != null && org.equals(logueado));
             
                    if (dtulogin instanceof webservice.DtAsistente && dtcompra != null){
                   		for (webservice.DtCompra comp : dtcompra){
                            boolean mismaEdicion = edicion.equals(comp.getEdicion());
                            boolean esMiCompra = logueado.equals(comp.getAsistente()); 							   
                            if (mismaEdicion && esMiCompra){
								tengoCompra = true;
                      			break;					
                            }
						}
				    }
				}
			}
     
            req.setAttribute("login", logueado);
            req.setAttribute("soyOrganizador",soyOrganizador);
            req.setAttribute("tengoCompra",tengoCompra);
            req.setAttribute("edicion", dte);
            req.setAttribute("tiposReg", dtregi);
            req.setAttribute("pats", dtpat);
            req.setAttribute("evento", evento); 
            req.setAttribute("organizador", dtu);
            req.setAttribute("compras", dtcompra);

            SrvEvento sve = new SrvEvento();
			sve.cargarCategorias(req,resp,"WEB-INF/views/consultaDeEdicion.jsp");
            
        } else if(destino.equals("misEdiciones")) {
			String nickOrg = req.getParameter("nickOrg");
			webservice.DtOrganizador dtOrg = (DtOrganizador) ICUw.getDTUsuario(nickOrg);
			List<webservice.DtEdicion> ediList = new ArrayList<>(dtOrg.getEdiciones());
			ediList.sort(Comparator.comparing(webservice.DtEdicion::getNombre));
			req.setAttribute("ediciones", ediList);
			
			SrvEvento sve = new SrvEvento();
			sve.cargarCategorias(req,resp,"WEB-INF/views/listaEdiciones.jsp");
		}
        else {
            req.getRequestDispatcher("WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String destino = req.getParameter("destino");
        String evento       = req.getParameter("evento");
        String edicion     = req.getParameter("nombreEdicion");

        if ("ingresarEdicion".equals(destino)) {
            
            String sigla        = req.getParameter("sigla");
            String ciudad       = req.getParameter("ciudad");
            LocalDate fechaIni  = LocalDate.parse(req.getParameter("fechaInicio"));
            LocalDate fechaFin  = LocalDate.parse(req.getParameter("fechaFin"));	
            LocalDate fechaAlta = LocalDate.now();
            Part filePart      = req.getPart("imagen");
            HttpSession sesion = req.getSession(false);
            
            DtUsuario usuario = (DtUsuario) sesion.getAttribute("datosUsuario");
            String nickusu = usuario.getNickname();
            
            
        	req.setAttribute("old_evento",evento);
        	req.setAttribute("old_sigla",sigla);
        	req.setAttribute("old_ciudad",ciudad);
        	req.setAttribute("old_inicio",fechaIni);
        	req.setAttribute("old_fin",fechaFin);
        	
        	
            
            try {
            	String fechaIniStr = fechaIni.toString();
            	String fechaFinStr = fechaFin.toString();
            	String fechaAltaStr = fechaAlta.toString();
				ICEw.altaEdicion(evento, nickusu, edicion, sigla, ciudad, fechaAltaStr, fechaFinStr, fechaIniStr);
			} catch (ExisteEdicion_Exception e) {
				req.setAttribute("error", e.getMessage());
                RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/views/altaEdicion.jsp");
                cargarEventos(req,resp);
                dispatcher.forward(req, resp);
				return;
			}
            boolean imagen     = (!Paths.get(filePart.getSubmittedFileName()).getFileName().toString().isEmpty());
	        String rutaImg	   =  ""; 
            if(imagen) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                File vuelo = new File(System.getProperty("user.dir")+"/git/tpgr32/Laboratorio/tarea2.2/tarea2.2/src/main/webapp/media/ediciones");
                File file = new File(vuelo, fileName);

                File imgBorrar = new File(file.toString());
                if(imgBorrar.exists()) {
                    imgBorrar.delete();
                }
                Files.copy(fileContent, file.toPath());
                rutaImg = fileName;           
            }
               
              ICEw.setImagenEdicion(edicion, rutaImg);
            }


            webservice.DtEdicion dte = ICEw.getEdicion(evento, edicion);
            String org = dte.getOrganizador();
            webservice.DtUsuario dtu = ICUw.getDTUsuario(org);
       
            req.setAttribute("edicion", dte);
            req.setAttribute("evento", evento); 
            req.setAttribute("organizador", dtu);

        
			SrvEvento srv = new SrvEvento();
			srv.cargarCategorias(req, resp,"WEB-INF/views/consultaDeEdicion.jsp");
          
            return;
        }

       
  
       private static LocalDate parse(String iso) {
        if (iso == null || iso.isBlank()) return null;
        try { return LocalDate.parse(iso); } catch (Exception e) { return null; }
    }
    
}





