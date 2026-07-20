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
import webservice.DtUsuarioArray;
import webservice.ExisteInstitucionExcepcion_Exception;
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
import java.util.HashMap;
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

import jakarta.xml.ws.BindingProvider;

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

		}
		
		if(!ICUw.existeUsuario(nickname) & !ICUw.existeUsuario(email)) {
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
		boolean datosCorrectos = ICUw.login(usuario, contrasenia);
		if(datosCorrectos) {
			DtUsuario logeado = ICUw.getDTUsuario(usuario);
			HttpSession session = request.getSession(true);
			session.setAttribute("estadoSesion", "logeado");
			session.setAttribute("datosUsuario", logeado);
			RequestDispatcher dispatcher = request.getRequestDispatcher("SrvEvento?destino=index");
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
				DtUsuarioArray usuarios = ICUw.listarUsuarios();
			//	usuarios = ordenarPorNombre(usuarios);
				List<DtUsuario> listausu = usuarios.getItem();
				Map<String, DtUsuario> mapusu = new HashMap<>();
				if (listausu != null) {
					for (DtUsuario dtu : listausu) {
						mapusu.put(dtu.getNickname(), dtu);
					}
				}
				mapusu = ordenarPorNombre(mapusu);
				request.setAttribute("usuarios", mapusu);
				
                File rutaImagen = new File(System.getProperty("user.dir")+"/git/tpgr32/Laboratorio/tarea2.2/tarea2.2/src/main/webapp/media/users");
				request.setAttribute("rutaImagen", rutaImagen.toString());
				
				HttpSession sesion = request.getSession(false);
			    if (sesion != null) {
			    	String estadoSesion = (String) sesion.getAttribute("estadoSesion");
			        if ("logeado".equals(estadoSesion)) {			    	
			    	DtUsuario dtu = (DtUsuario)sesion.getAttribute("datosUsuario");
			        String nicklog = dtu.getNickname(); 		
			        DtUsuarioArray seguidosarray = ICUw.listarSeguidos(nicklog);
			        List<DtUsuario> listaseguidos = seguidosarray.getItem(); 
			        Map<String, DtUsuario> mapseguidos = new HashMap<>();
			        if (listaseguidos != null) {
			        	for (DtUsuario dt : listaseguidos) {
			        		mapseguidos.put(dt.getNickname(), dt);
			        	}
			        }
			    request.setAttribute("seguidos", mapseguidos);    
			    }
			   }
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
    		}else if(destino.equals("comenzarASeguirUsuario")) {
    			HttpSession sesion = request.getSession(false);
			    if (sesion != null) {
			    	String estadoSesion = (String) sesion.getAttribute("estadoSesion");
			        if ("logeado".equals(estadoSesion)) {			    	
			    	DtUsuario dtulog = (DtUsuario)sesion.getAttribute("datosUsuario");
			        String nicklog = dtulog.getNickname();     		     	
    			    String nick = request.getParameter("nick");
    			    String origen = request.getParameter("origen");
    			//Aca va Operacion para comenzar a seguir usuario nick
    			
    			ICUw.addSeguidor(nick, nicklog);
 				    			
			        	
    			if(origen == null) {
    		  		SrvEvento srv = new SrvEvento();
    			    srv.cargarCategorias(request, response,"SrvUsuario?destino=listarUsuarios");
    			}else {
    				String nickUsu = request.getParameter("nickusu");
    		  		SrvEvento srv = new SrvEvento();
    				srv.cargarCategorias(request, response,"SrvUsuario?destino=detalleUsuario&nick="+nickUsu);
    			}
			    }
			    }     
    		}else if(destino.equals("dejarDeSeguirUsuario")) {
    			HttpSession sesion = request.getSession(false);
			    if (sesion != null) {
			    	String estadoSesion = (String) sesion.getAttribute("estadoSesion");
			        if ("logeado".equals(estadoSesion)) {			    	
			    	DtUsuario dtulogueado = (DtUsuario)sesion.getAttribute("datosUsuario");
			        String nicklogueado = dtulogueado.getNickname(); 		
    		     	String origen = request.getParameter("origen");
    			    String nick = request.getParameter("nick");
    		   	
    			    			
    			//Aca va Operacion para comenzar a dejar de seguir usuario nick
    			ICUw.removeSeguido(nick, nicklogueado);
    			
    			
    			if(origen == null) {
    		  		SrvEvento srv = new SrvEvento();
    			    srv.cargarCategorias(request, response,"SrvUsuario?destino=listarUsuarios");
    			}else {
    				String nickUsu = request.getParameter("nickusu");
    		  		SrvEvento srv = new SrvEvento();
    				srv.cargarCategorias(request, response,"SrvUsuario?destino=detalleUsuario&nick="+nickUsu);
    			}

    		}
			    }
    		}  
		}else if(formulario != null) {
			if(formulario.equals("login")) {
				iniciarSesion(request,response);
			}else if(formulario.equals("altaInst")){
				altaDeInstitucion(request,response);
			}
		}
	}
 
	private void altaDeInstitucion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("desc");
		String web = request.getParameter("sitio");
		
		request.setAttribute("old_descripcion", descripcion);
		request.setAttribute("old_web", web);
		
		try {
			ICUw.crearInstitucion(nombre, descripcion, web);
		} catch (ExisteInstitucionExcepcion_Exception e) {
			e.printStackTrace();
		}
        request.getRequestDispatcher("SrvEvento?destino=index").forward(request, response);
	}

	private void mostrarDetalleUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoExisteUsuarioExcepcion {
		String nick = request.getParameter("nick");
		DtUsuario usuario = ICUw.getDTUsuario(nick);
		String nicklog = null;
		boolean losigo = false;
		request.setAttribute("detalleUsuario", usuario);
		HttpSession sesion = request.getSession(false);
	    if (sesion != null) {
	        String estadoSesion = (String) sesion.getAttribute("estadoSesion");
	        if ("logeado".equals(estadoSesion)) {
	            DtUsuario usulog = (DtUsuario)sesion.getAttribute("datosUsuario");
	            nicklog = usulog.getNickname();}
	    }
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
		 
		            // seguidos del detalle
		     
			        DtUsuarioArray seguidosarray = ICUw.listarSeguidos(nick);
			        List<DtUsuario> listaseguidos = seguidosarray.getItem(); 
			        Map<String, DtUsuario> mapseguidos = new HashMap<>();
			        if (listaseguidos != null) {
			        	for (DtUsuario dt : listaseguidos) {
			        		mapseguidos.put(dt.getNickname(), dt);
			        	}
			        }
	        	
	        	 	//seguidores del detalle
			        
			        DtUsuarioArray seguidoresarray = ICUw.listarSeguidores(nick);
			        List<DtUsuario> listaseguidores = seguidoresarray.getItem(); 
			        Map<String, DtUsuario> mapseguidores = new HashMap<>();
			        if (listaseguidores != null) {
			        	for (DtUsuario dt : listaseguidores) {
			        		mapseguidores.put(dt.getNickname(), dt);
			        	}
			        }
			         
			        //seguidos logueado
			        if (nicklog != null)  {
			        DtUsuarioArray seguidoslog = ICUw.listarSeguidos(nicklog);
			        List<DtUsuario> listaseglog = seguidoslog.getItem(); 
			        for (DtUsuario dtusua : listaseglog) {
			        	if (dtusua.getNickname().equals(nick)) {
			        		losigo = true;
			         	}
			    }
			        }
			        
			    request.setAttribute("losigo", losigo);    
	        	request.setAttribute("mapseguidores", mapseguidores);
	        	request.setAttribute("mapseguidos", mapseguidos);
	        	    
		
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
