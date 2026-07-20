package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import webservice.DtAsistente;
import webservice.DtCompraWeb;
import webservice.DtCompraWebArray;
import webservice.DtInstitucion;
import webservice.DtInstitucionArray;
import webservice.DtUsuario;
import webservice.ExisteUsuarioExcepcion_Exception;
import webservice.FechaNacimientoPosteriorExcepcion_Exception;
import webservice.NoExisteUsuarioExcepcion_Exception;
import webservice.WebServiceEvento;
import webservice.WebServiceEventoService;
import webservice.WebServiceUsuario;
import webservice.WebServiceUsuarioService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import excepciones.ExisteInstitucionExcepcion;
import excepciones.ExisteUsuarioExcepcion;
import excepciones.FechaNacimientoPosteriorExcepcion;
import excepciones.NoExisteUsuarioExcepcion;

@WebServlet("/SrvUsuario")
@MultipartConfig
public class SrvUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebServiceUsuarioService servicioUsuario = new WebServiceUsuarioService();
	private WebServiceUsuario ICUw = servicioUsuario.getWebServiceUsuarioPort();
	private WebServiceEventoService servicioEvento = new WebServiceEventoService();
	private WebServiceEvento ICEw = servicioEvento.getWebServiceEventoPort();

	
    public SrvUsuario() {
        super();
    }
    
public void cargarInstituciones(HttpServletRequest request) {
		
		DtInstitucionArray instarray = ICUw.listarInstituciones();
		List<DtInstitucion> listinst = instarray.getItem();
		Set<String> instituciones = new HashSet<>();
		if (listinst != null) {
			for (DtInstitucion ins : listinst) {
				instituciones.add(ins.getNombre());
			}
		}
			request.setAttribute("instituciones", instituciones);
	}
    
private Map<String, DtUsuario> ordenarPorNombre(Map<String, DtUsuario> usuarios){
		Map<String, DtUsuario> ordenado = usuarios.entrySet().stream()
			    .sorted(Map.Entry.comparingByValue(Comparator.comparing(DtUsuario::getNombre, String.CASE_INSENSITIVE_ORDER)))
			    .collect(Collectors.toMap(
			        Map.Entry::getKey,
			        Map.Entry::getValue,
			        (e1, e2) -> e1,
			        LinkedHashMap::new // mantiene el orden
			    ));
		return ordenado;
  }
private void ingresarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String usuario = request.getParameter("inlineRadioOptions");
		String nombre = request.getParameter("nombre");
		String contrasenia = request.getParameter("contra");
		Part filePart      = request.getPart("imagen");
		
		//Recordar parametros del formulario en caso de error
		request.setAttribute("old_usuario",usuario);
		request.setAttribute("old_nombre",nombre);
		request.setAttribute("old_nickname",nickname);
		request.setAttribute("old_email",email);
		request.setAttribute("old_imagen",filePart);
		
		if(usuario.equals("asistente")) {
			String apellido = request.getParameter("apellido");
			String fechaNacimiento = request.getParameter("fechaNacimiento");
			String institucion = request.getParameter("institucion");
			
			request.setAttribute("old_apellido",apellido);
			request.setAttribute("old_institucion",institucion);
			request.setAttribute("old_fechaNacimiento",fechaNacimiento);
		}else {
			String descripcion = request.getParameter("descripcion");
			String paginaWeb = request.getParameter("paginaWeb");
			
			request.setAttribute("old_descripcion",descripcion);
			request.setAttribute("old_paginaWeb",paginaWeb);

		}if(!ICUw.existeUsuario(nickname) & !ICUw.existeUsuario(email)) {
	        boolean imagen     = (!Paths.get(filePart.getSubmittedFileName()).getFileName().toString().isEmpty());
	        String rutaImg	   =  ""; 
			
			if(usuario.equals("asistente")) {
				String apellido = request.getParameter("apellido");
				String fechaNacimiento = request.getParameter("fechaNacimiento");
				String institucion = request.getParameter("institucion");
				
				request.setAttribute("old_apellido",apellido);
				request.setAttribute("old_institucion",institucion);
				request.setAttribute("old_fechaNacimiento",fechaNacimiento);
				try {
					ICUw.crearAsistente(nombre, nickname, email, apellido, fechaNacimiento);
				} catch (ExisteUsuarioExcepcion_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FechaNacimientoPosteriorExcepcion_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ICUw.setInstitucionAsistente(nickname , institucion);
			}else {
				String descripcion = request.getParameter("descripcion");
				String paginaWeb = request.getParameter("paginaWeb");
				
				request.setAttribute("old_descripcion",descripcion);
				request.setAttribute("old_paginaWeb",paginaWeb);
				try {
					ICUw.crearOrganizador(nombre,nickname, email, descripcion, paginaWeb);
				} catch (ExisteUsuarioExcepcion_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ICUw.setContraseniaUsuario(nickname, contrasenia);
			if(imagen) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                File vuelo = new File(System.getProperty("user.dir")+"/git/tpgr32/Laboratorio/tarea2.2/tarea2.2/src/main/webapp/media/users");
                File file = new File(vuelo, fileName);

                File imgBorrar = new File(file.toString());
                if(imgBorrar.exists()) {
                    imgBorrar.delete();
                }
                System.out.print(file.toPath());
                Files.copy(fileContent, file.toPath());
                rutaImg = fileName;
                ICUw.setImagenUsuario(nickname,rutaImg);
			}
            
			request.getRequestDispatcher("SrvEvento?destino=index").forward(request, response);
		
			}else if(ICUw.existeUsuario(nickname)) {
                request.setAttribute("error", "Ya existe un usuario registrado con el nickname");
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/altaDeUsuario.jsp");
				cargarInstituciones(request);
                dispatcher.forward(request, response);
			}else if(ICUw.existeUsuario(email)) {
                request.setAttribute("error", "Ya existe un usuario registrado con el email");
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/altaDeUsuario.jsp");
				cargarInstituciones(request);
                dispatcher.forward(request, response);
			}
	}

	private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", "false");
		
		String usuario = request.getParameter("usuario");
		String contrasenia = request.getParameter("contrasenia");
		boolean datosCorrectos = ICUw.loginAsistente(usuario, contrasenia);
		if(datosCorrectos) {
			DtUsuario logeado = ICUw.getDTUsuario(usuario);
			HttpSession session = request.getSession(true);
			session.setAttribute("estadoSesion", "logeado");
			session.setAttribute("datosUsuario", logeado);
			RequestDispatcher dispatcher = request.getRequestDispatcher("SrvEvento?destino=indexLogeado");
			dispatcher.forward(request, response);
		}else {
			request.setAttribute("error", "datos incorrectos");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
		}

	}
	
	public void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if (session == null || !(session.getAttribute("estadoSesion").equals("logeado"))) {
	    	request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
	    	return;
        }
		DtUsuario datosUsuario = (DtUsuario)session.getAttribute("datosUsuario");
		request.setAttribute("datosUsuario", datosUsuario);
		if (ICUw.esAsistente(datosUsuario.getNickname())) {
			request.setAttribute("tipoUsuario", "Asistente");
			cargarInstituciones(request);
		}
		else {
			request.setAttribute("tipoUsuario", "Organizador");
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/editarUsuario.jsp");
		dispatcher.forward(request, response);	
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destino = request.getParameter("destino");
		String formulario= request.getParameter("formulario");
		if(destino != null) {
			if(destino.equals("listarUsuarios")) {
				List<DtUsuario> usuarios = ICUw.listarUsuarios().getItem();

				
				request.setAttribute("usuarios", usuarios);
				
                File rutaImagen = new File(System.getProperty("user.dir")+"/git/tpgr32/Laboratorio/tarea2.2/tarea2.2/src/main/webapp/media/users"); ////-----CAMBIAR --
				request.setAttribute("rutaImagen", rutaImagen.toString());
                
				SrvEvento srv = new SrvEvento();
				srv.cargarCategorias(request, response,"WEB-INF/views/listaUsuarios.jsp");
			}else if(destino.equals("editarPerfil")) {
				editarUsuario(request, response);
			}else if (destino.equals("cerrarSesion")) {
		        HttpSession session = request.getSession(false);
		        if (session != null) {
		            session.invalidate();
		        }
		        response.sendRedirect("SrvEvento?destino=index");
			}if(destino.equals("inicioSesion")) {
    			request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
    		}else if(destino.equals("registroUsuario")) {
    			cargarInstituciones(request);
    			request.getRequestDispatcher("WEB-INF/views/altaDeUsuario.jsp").forward(request, response);
    		}else if(destino.equals("detalleUsuario")) {
    			try {
					mostrarDetalleUsuario(request,response);
				} catch (ServletException | IOException | NoExisteUsuarioExcepcion e) {
				}
    		}else if(destino.equals("altaInstitucion")) {
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/altaInstitucion.jsp");
				dispatcher.forward(request, response);   			
    		}
		}else if(formulario != null) {
			if(formulario.equals("login")) {
				iniciarSesion(request,response);
			}else if(formulario.equals("altaInst")){
				//altaDeInstitucion(request,response);
			}
		}
	}



	private void mostrarDetalleUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoExisteUsuarioExcepcion {
		String nick = request.getParameter("nick");
		DtUsuario usuario = ICUw.getDTUsuario(nick);
		request.setAttribute("detalleUsuario", usuario);
		
		if(usuario instanceof DtAsistente) {
			HttpSession session = request.getSession();
			DtUsuario usu = (DtUsuario) session.getAttribute("datosUsuario");			
			boolean esAsistente = (usu != null) && (usu instanceof DtAsistente);
			if(esAsistente && usu.getNickname().equals(nick)) {
				DtCompraWebArray comprarray = null;
				try {
					comprarray = ICUw.listarComprasDeUsuarioWeb(nick);
				} catch (NoExisteUsuarioExcepcion_Exception e) {
					e.printStackTrace();
				}
				List<DtCompraWeb> compras = comprarray.getItem(); 
				
				request.setAttribute("comprasUsuario", compras);
			} 
			request.setAttribute("tipo", "asistente");
		}else {
			request.setAttribute("tipo", "organizador");
		}
		
		

		SrvEvento srv = new SrvEvento();
		srv.cargarCategorias(request, response,"WEB-INF/views/detalleUsuario.jsp");
	}
	
	public void modificarUsuario(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException, NoExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
	    String nickname = request.getParameter("nickname");
	    String correo = request.getParameter("email");
	    String nombre = request.getParameter("nombre");
	    String contra = request.getParameter("contra");
	    String contrabis = request.getParameter("contrabis");
	    Part filePart      = request.getPart("imagen");
        boolean imagen     = (!Paths.get(filePart.getSubmittedFileName()).getFileName().toString().isEmpty());
        String rutaImg	   =  ""; 

	    if (!contra.equals(contrabis)) {
	        request.setAttribute("error", "Las contraseñas no coinciden.");
	        editarUsuario(request,response);
	        return;
	    }

	    if (ICUw.esAsistente(nickname)) {
	        String apellido = request.getParameter("apellido");
			String fechaNac = request.getParameter("fechaNacimiento");
	        String institucion = request.getParameter("institucion");
	        
	        try {
				ICUw.modificarAsistente(nombre, nickname, correo, apellido, fechaNac);
			} catch (FechaNacimientoPosteriorExcepcion_Exception e) {
				e.printStackTrace();
			} catch (NoExisteUsuarioExcepcion_Exception e) {
				e.printStackTrace();
			}
	        ICUw.setInstitucionAsistente(nickname, institucion);
	        
	    } else{
	        String descripcion = request.getParameter("descripcion");
	        String paginaWeb = request.getParameter("paginaWeb");

	        try {
				ICUw.modificarOrganizador(nombre, nickname, correo, descripcion, paginaWeb);
			} catch (NoExisteUsuarioExcepcion_Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    if(imagen) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            File vuelo = new File(System.getProperty("user.dir")+"/git/tpgr32/Laboratorio/tarea2.2/tarea2.2/src/main/webapp/media/users");
            File file = new File(vuelo, fileName);

            File imgBorrar = new File(file.toString());
            if(imgBorrar.exists()) {
                imgBorrar.delete();
            }
            Files.copy(fileContent, file.toPath());
            rutaImg = fileName;
            ICUw.setImagenUsuario(nickname,rutaImg);
		}
	    
	    ICUw.setContraseniaUsuario(nickname, contra);
	    
	    DtUsuario logeado = ICUw.getDTUsuario(nickname);
		HttpSession session = request.getSession(false);
		session.setAttribute("estadoSesion", "logeado");
		session.setAttribute("datosUsuario", logeado);

	    request.setAttribute("mensajeExito", "Usuario modificado con exito.");
		request.getRequestDispatcher("SrvEvento?destino=index").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destino = request.getParameter("destino");
		if(destino != null) {
			if(destino.equals("ingresarUsuario")) {
				ingresarUsuario(request,response);
		    }
			else if(destino.equals("modificarUsuario")) {
				try {
					modificarUsuario(request,response);
				} catch (IOException | ServletException | NoExisteUsuarioExcepcion
						| FechaNacimientoPosteriorExcepcion e) {
					e.printStackTrace();
				}
			}
			else {
				doGet(request, response);
			}		
		}
		else {
			doGet(request, response);
		}
	}
	
}
