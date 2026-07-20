package controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
import webservice.Ciudad;
import webservice.DtAsistente;
import webservice.DtCompra;
import webservice.DtEdicion;
import webservice.DtEvento;
import webservice.DtOrganizador;
import webservice.DtPatrocinio;
import webservice.DtUsuario;
import webservice.ExisteEdicion_Exception;
import webservice.WebServiceEvento;
import webservice.WebServiceEventoService;
import webservice.WebServiceUsuario;
import webservice.WebServiceUsuarioService;


@WebServlet("/SrvEdicion")
@MultipartConfig
public class SrvEdicion extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private WebServiceUsuarioService servicioUsuario = new WebServiceUsuarioService();
	private WebServiceUsuario ICUw = servicioUsuario.getWebServiceUsuarioPort();
	private WebServiceEventoService servicioEvento = new WebServiceEventoService();
	private WebServiceEvento ICEw = servicioEvento.getWebServiceEventoPort();



    private void cargarEventos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	List<DtEvento> set = ICEw.listarEventos().getItem();
        List<DtEvento> eventos = new ArrayList<>();
        if (set != null) eventos.addAll(set);
        eventos.sort(Comparator.comparing(DtEvento::getNombre));
        req.setAttribute("eventos", eventos);
        List< Ciudad> ciudades = ICEw.listarCiudades().getItem();
        req.setAttribute("ciudades", ciudades);
        req.getRequestDispatcher("WEB-INF/views/altaEdicion.jsp").forward(req, resp);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String destino = req.getParameter("destino");
        List<String> categorias = ICEw.listarCategorias().getItem();
		if(categorias != null) {
		TreeSet<String> categoriasOrdenadas = new TreeSet<String>(categorias);
		req.setAttribute("listaCategorias", categoriasOrdenadas);
		}
        if ("altaEdicion".equals(destino)) {
        	cargarEventos(req,resp);

        } else if ("detalleEdiciono".equals(destino)) {

            String edicion = req.getParameter("edicion");
            String evento = req.getParameter("evento"); //esto al final no es necesario, no se usa para nada.
            
            DtEdicion dte = ICEw.getEdicionB(edicion);
            List<String> dtregi = dte.getTiposRegistros();
            String org = dte.getOrganizador();
            DtUsuario dtu = ICUw.getDTUsuario(org);            
            List<DtCompra> dtcompra = dte.getDatosCompras();
            List<DtPatrocinio> dtpat = dte.getPatrocinios();
            
    		//Set<DTPatrocinio> patrocinios = ICU.listarPatrociniosregistro(edicion, tipo);
    		//request.setAttribute("patrocinios", patrocinios);
            
			boolean soyOrganizador= false;
            boolean tengoCompra   = false;
            String  logueado     = null;
			
			HttpSession sesion = req.getSession(false);
            if (sesion != null) {
                 DtUsuario dtulogin = (DtUsuario) sesion.getAttribute("datosUsuario"); 
                if (dtulogin != null) {
				   logueado = dtulogin.getNickname(); 		
                   soyOrganizador = (org != null && org.equals(logueado));
             
                    if (dtulogin instanceof DtAsistente && dtcompra != null){
                   		for (DtCompra comp : dtcompra){
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

            req.getRequestDispatcher("WEB-INF/views/consultaDeEdicion.jsp").forward(req, resp);
       	
        }else if(destino.equals("misEdiciones")) {
			String nickOrg = req.getParameter("nickOrg");
			DtOrganizador dtOrg = (DtOrganizador) ICUw.getDTUsuario(nickOrg);
			List<DtEdicion> ediList = new ArrayList<>(dtOrg.getEdiciones());
			ediList.sort(Comparator.comparing(DtEdicion::getNombre));
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
				ICEw.altaEdicion(evento, nickusu, edicion, sigla, ciudad, fechaAlta.toString(), fechaFin.toString(), fechaIni.toString());
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
               
                try { ICEw.setImagenEdicion(edicion, rutaImg ); } catch (Exception ignore) {}
            }

            
            DtEdicion dte = ICEw.getEdicion(evento, edicion);
            String org = dte.getOrganizador();
            DtUsuario dtu = ICUw.getDTUsuario(org);
       
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


