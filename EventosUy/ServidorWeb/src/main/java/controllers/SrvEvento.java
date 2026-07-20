package controllers;

import com.sun.xml.fastinfoset.util.StringArray;
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
import webservice.ExisteEventoExcepcion_Exception;
import webservice.StringArrayWrapper;
import webservice.WebServiceEvento;
import webservice.WebServiceEventoService;
import webservice.WebServiceUsuario;
import webservice.WebServiceUsuarioService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

	public boolean CargarSesion(HttpServletRequest request, HttpServletResponse response, WebServiceUsuario ICUw,String ruta) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("EstadoDeSesion") == null) {
			session.setAttribute("EstadoDeSesion", true);
			//ICU.cargarDatos("Servlet",ruta);
			cargarEventos(request,response);
	    	cargarCategorias(request,response,"WEB-INF/index.jsp");
			return true;
		}
		return false;
	}
    
	public void cargarCategorias(HttpServletRequest request, HttpServletResponse response, String ruta) throws ServletException, IOException {
		List<String> categorias = ICEw.listarCategorias().getItem();
		

		//System.out.println(categorias);
		if(categorias != null) {
			//TreeSet<String> categoriasOrdenadas = new TreeSet<String>(categorias);
			request.setAttribute("listaCategorias", categorias);
	        RequestDispatcher dispatcher = request.getRequestDispatcher(ruta);
	        dispatcher.forward(request, response);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(ruta);
        dispatcher.forward(request, response);
	}
	
	public void ingresarEvento(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
		String desc = request.getParameter("desc");
		String sigla = request.getParameter("sigla");
		String[] cats = request.getParameterValues("categorias");
		List<String> categorias = new ArrayList<>();
		Part filePart      = request.getPart("imagen");
		
		//Recordar parametros del formulario en caso de error
		request.setAttribute("old_nombre",nombre);
		request.setAttribute("old_desc",desc);
		request.setAttribute("old_sigla",sigla);
		request.setAttribute("old_categorias", cats);	
		request.setAttribute("old_imagen",filePart);

		if(cats != null) {
		    for(String itecat : cats) {
		        categorias.add(itecat);
		    }
		} else {
		    request.setAttribute("errorCategoria", "Debes seleccionar al menos una categoría.");
		    cargarCategorias(request, response, "WEB-INF/views/altaEvento.jsp");
		    return;
		}
		LocalDate fechaActual = LocalDate.now();
		
		if(!ICEw.existeEvento(nombre)) {
	        boolean imagen     = (!Paths.get(filePart.getSubmittedFileName()).getFileName().toString().isEmpty());
	        String rutaImg	   =  ""; 
			try {
				StringArrayWrapper aaa = new StringArrayWrapper() ;
				for (String aux : cats) {
					aaa.getItem().add(aux);
				}
				ICEw.addEvento(nombre, desc, sigla, fechaActual.toString(),aaa);
			} catch (ExisteEventoExcepcion_Exception e) {
				e.printStackTrace();
			}
			if(imagen) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                File vuelo = new File(System.getProperty("user.dir")+"/git/tpgr32/Laboratorio/tarea2.2/tarea2.2/src/main/webapp/media/eventos");
                File file = new File(vuelo, fileName);

                File imgBorrar = new File(file.toString());
                if(imgBorrar.exists()) {
                    imgBorrar.delete();
                }
                Files.copy(fileContent, file.toPath());
                rutaImg = fileName;
                ICEw.setImagenEvento(nombre,rutaImg);
			}
	            request.setAttribute("mensajeExito", "Evento ingresado con exito.");
				request.getRequestDispatcher("SrvEvento?destino=index").forward(request, response);
			}else {
                request.setAttribute("error", "Ya existe un evento en el sistema con el nombre");
                cargarCategorias(request,response,"WEB-INF/views/altaEvento.jsp");
            }
	}

	private void cargarEventos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<DtEvento> eventosList = ICEw.listarEventos().getItem();
		System.out.println(eventosList);
		//eventosList.sort(Comparator.comparing(DtEvento::getNombre));
		request.setAttribute("eventos", eventosList);

    	cargarCategorias(request,response,"WEB-INF/index.jsp");
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("finalizar",false);
        try {
			CargarSesion(request,response,this.ICUw,"");
		} catch (Exception e) {
		}
    	String destino = request.getParameter("destino");
    	if(destino != null) {
    		if(destino.equals("index")) {
    			
    			cargarEventos(request,response);
    		}else if(destino.equals("detalleEvento")){
    			String ev = request.getParameter("nomEvento");
    			DtEvento dtEv = ICEw.getEvento(ev);
    			request.setAttribute("datosEvento", dtEv);
    			List<DtEdicion> edicionesList = ICEw.listarEdicionesConfirmadas(dtEv.getNombre()).getItem();
    			edicionesList.sort(Comparator.comparing(DtEdicion::getNombre));
    			request.setAttribute("edsConfiEvento", edicionesList);
    			
    			cargarCategorias(request,response,"WEB-INF/views/consultaDeEvento.jsp");
    		}else if(destino.equals("altaEvento")) {
    			cargarCategorias(request,response,"WEB-INF/views/altaEvento.jsp");
    		}else if(destino.equals("listarEventosCategoria")) {
    			String cat = request.getParameter("categoria");
    			List<DtEvento> eventosList =ICEw.listarEventosDeCategoria(cat).getItem();
    			eventosList.sort(Comparator.comparing(DtEvento::getNombre));
    			request.setAttribute("eventos", eventosList);
    	    	cargarCategorias(request,response,"WEB-INF/index.jsp");
    		}else if(destino.equals("finEvento")) {
    			String ev = request.getParameter("nomEvento"); 
    			if (ev != null){
    				ICEw.setFinalizado(ev);
    			}
    			request.setAttribute("finalizar",true);
    			cargarEventos(request,response);
    		}
    		else {
    	    	request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
    		}
    	}else {
	    	request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destino = request.getParameter("destino");
		if(destino != null) {
			if(destino.equals("ingresarEvento")) {
				ingresarEvento(request,response);
		    }else {
				doGet(request, response);
			}
		}else {
			doGet(request, response);
		}
		doGet(request, response);
	}
	
	

}
