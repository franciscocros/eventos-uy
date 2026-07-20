package main.java.logica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import main.java.excepciones.ExisteInstitucionExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.excepciones.FechaNacimientoPosteriorExcepcion;
import main.java.excepciones.NoExisteEdicionExcepcion;
import main.java.excepciones.NoExisteEvento;
import main.java.excepciones.NoExisteInstitucion;
import main.java.excepciones.NoExisteTipoRegistro;
import main.java.excepciones.NoExisteUsuarioExcepcion;
import main.java.excepciones.SuperaCantidadGratuitos;
import main.java.excepciones.YatienePatrocinioException;

	public class ControladorUsuario implements IControladorUsuario {
		private ManejadorUsuario musu;
		private ManejadorEvento medi;
		public ControladorUsuario() {
			musu = ManejadorUsuario.getInstance();
			medi = ManejadorEvento.getInstance();
		}

		@Override
		public void crearAsistente(String nombre, String nickname, String correo, String apellido, LocalDate fechaNacimiento)	throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
			if (musu.existeUsuario(nickname, correo)){
				throw new ExisteUsuarioExcepcion("El usuario ya existe, volver a intentar ?");
			}else if (fechaNacimiento.isAfter(LocalDate.now())){
				throw new FechaNacimientoPosteriorExcepcion("La fecha de nacimiento es posterior a la de hoy, volver a intentar ?");
			}else {
				Asistente nuevo = new Asistente(nombre, nickname, correo, apellido, fechaNacimiento);
				musu.addUsuario(nuevo);
			}
		}

		@Override
		public void modificarAsistente(String nombre, String nickname, String correo, String apellido, LocalDate fechaNacimiento) throws NoExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
			if (!musu.existeUsuario(nickname, correo)) {
				throw new  NoExisteUsuarioExcepcion("El usuario ingresado no existe en sistema");
			}else if (fechaNacimiento.isAfter(LocalDate.now())){
				throw new FechaNacimientoPosteriorExcepcion("La fecha de nacimiento es posterior a la de hoy");
			}else {
				Asistente modificar = (Asistente) musu.getUsuario(nickname);
				modificar.modificarAsistente(nombre, nickname, correo, apellido, fechaNacimiento);
			}
		}

		@Override
		public void crearOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb)throws ExisteUsuarioExcepcion {
			if (musu.existeUsuario(nickname, correo)){
				throw new ExisteUsuarioExcepcion("El usuario ya existe, volver a intentar ?");
			}else {
				Organizador nuevo = new Organizador(nombre, nickname, correo, descripcion, paginaWeb);
				musu.addUsuario(nuevo);
			}
		}

		@Override
		public void modificarOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) throws NoExisteUsuarioExcepcion {
			if (!musu.existeUsuario(nickname, correo)) {
				throw new  NoExisteUsuarioExcepcion("El usuario ingresado no existe en sistema");
			}else {
				Organizador modificar = (Organizador) musu.getUsuario(nickname);
				modificar.modificarOrganizador(nombre, nickname, correo, descripcion, paginaWeb);
			}
		}

		@Override
		public void crearInstitucion(String nombre, String descripcion, String paginaWeb)throws ExisteInstitucionExcepcion {
			if (musu.existeInstitucion(nombre)) {
				throw new ExisteInstitucionExcepcion("La institucion existe, volver a intentar ?");
			}else {
				Institucion nueva = new Institucion(nombre, descripcion, paginaWeb);
				musu.addInstitucion(nueva);
			}
		}

		@Override
		public Map<String, DTInstitucion> listarInstituciones() {
			Map<String, Institucion> instituciones = musu.getInstituciones();
			Map<String, DTInstitucion> dtResultado = new HashMap<>();
			for (Institucion institucion : instituciones.values()) {
				DTInstitucion nueva = new DTInstitucion(institucion.getNombre(), institucion.getDescripcion(), institucion.getPaginaWeb());
				nueva.setImagen(institucion.getImagen());
				dtResultado.put(nueva.getNombre(), nueva);
			}
			return dtResultado;
		}

		@Override
		public Map<String, DTUsuario> listarUsuarios() {
			Map<String, Usuario> usuarios = musu.getUsuarios();
			Map<String, DTUsuario> dtUsuarios = new HashMap<>();
			for (Usuario usuario : usuarios.values()) {
				if (usuario.getClass() == Asistente.class) {
					DTAsistente nuevo = new DTAsistente(usuario.getNombre(), usuario.getNickName(), usuario.getCorreo(), ((Asistente) usuario).getApellido(), ((Asistente) usuario).getFechaNacimiento(), ((Asistente) usuario).getDTCompras().stream().toList() , ((Asistente) usuario).getInstitucion());
					nuevo.setImagen(usuario.getImagen());
					dtUsuarios.put(usuario.getNickName(), nuevo);
				}else {
					DTOrganizador nuevo = new DTOrganizador(usuario.getNombre(), usuario.getNickName(), usuario.getCorreo(), ((Organizador) usuario).getDescripcion(), ((Organizador) usuario).getPaginaWeb());
					nuevo.setImagen(usuario.getImagen());
					dtUsuarios.put(usuario.getNickName(), nuevo);
				}
			}
			return dtUsuarios;
		}

		@Override
		public Map<String, DTUsuario> listarAsistentes() {
			Map<String, Usuario> usuarios = musu.getUsuarios();
			Map<String, DTUsuario> dtUsuarios = new HashMap<>();
			for (Usuario usuario : usuarios.values()) {
				if (usuario.getClass() == Asistente.class) {
					DTAsistente nuevo = new DTAsistente(usuario.getNombre(), usuario.getNickName(), usuario.getCorreo(), ((Asistente) usuario).getApellido(), ((Asistente) usuario).getFechaNacimiento(), ((Asistente) usuario).getDTCompras().stream().toList(), ((Asistente) usuario).getInstitucion());
					nuevo.setImagen(usuario.getImagen());
					dtUsuarios.put(nuevo.getNombre(), nuevo);
				}
			}
			return dtUsuarios;
		}

		@Override
		public DTUsuario getDTUsuario(String usuario) {
			Usuario usr= musu.getUsuario(usuario);
			if (usr.getClass() == Asistente.class) {
				DTAsistente dtBuscado = new DTAsistente(usr.getNombre(), usr.getNickName(), usr.getCorreo(), ((Asistente) usr).getApellido(), ((Asistente) usr).getFechaNacimiento(), ((Asistente) usr).getDTCompras().stream().toList(), ((Asistente) usr).getInstitucion());
				dtBuscado.setImagen(usr.getImagen());
				return dtBuscado;
			}else {
				DTOrganizador dtBuscado = new DTOrganizador(usr.getNombre(), usr.getNickName(), usr.getCorreo(), ((Organizador) usr).getDescripcion(), ((Organizador) usr).getPaginaWeb());
				Organizador org= (Organizador) usr;
				Set<DTEdicion>  edis = org.lisarDTEdiciones();
				dtBuscado.setEdiciones(edis.stream().toList());
				dtBuscado.setImagen(usr.getImagen());
				return dtBuscado;
			}
		}
		@Override
		public Set<DTEdicion> getSetEdiciones(String usuario) {
			Usuario usr= musu.getUsuario(usuario);
			Set<DTEdicion> ediciones = null;
			if (usr.getClass() == Organizador.class) {
				ediciones = new HashSet<>();
				ediciones = ((Organizador) usr).lisarDTEdiciones();

			}
			return ediciones;

		}
		@Override
		
		public void crearPatrocinio(float aporteEcon, String codigo, LocalDate fecha, int cantGratis, ENivelPatrocinio enu, String tiporeg, String evento, String edicion, String institution) throws Exception {
			if (!musu.existeInstitucion(institution)) {
				throw new NoExisteInstitucion("La institucion no existe");
			}else if (!medi.existeEvento(evento)) {
				throw new NoExisteEvento("El evento no existe");
			}else if (medi.findEvento(evento).findEdicion(edicion) == null) {
				throw new NoExisteEdicionExcepcion("La edicion no existe");
			}else if (medi.findEvento(evento).findEdicion(edicion).getMisTipoRegistro().get(tiporeg) == null){
				throw new NoExisteTipoRegistro("No existe el tipo de registro");
			}else {

				Institucion institucion = musu.getInstitucion(institution);
			    Evento event = medi.findEvento(evento);
				Edicion edic = event.findEdicion(edicion);
				TipoRegistro tipoRegistro = edic.findTipoRegistro(tiporeg);

				if (institucion.tienePatrocinio(tiporeg)) {
					throw new YatienePatrocinioException("Ya tiene este patrocinio, modificar datos? ");
				}else if (cantGratis*tipoRegistro.getCosto() > aporteEcon*0.2 ) {
					throw new SuperaCantidadGratuitos("Los registros gratuitos a otorgar supera el 20 % del aporte economico, modificar? ");
				}else {
					Patrocinio patrocinio = new Patrocinio(aporteEcon, codigo, fecha, cantGratis, enu, tipoRegistro, institucion);
					institucion.addPatrocinio(patrocinio);
					tipoRegistro.addPatrocinio(patrocinio);
				}
			}
		}

		@Override
		public Set<DTPatrocinio> listarPatrocinios(String event, String edi) {
			Set<DTPatrocinio> resultado = new HashSet<>();
			Evento evento = medi.findEvento(event);
			if (evento != null) {
				Edicion edicion = evento.findEdicion(edi);
				if (edicion != null){
					DTEdicion dtedi = edicion.getDT();
					resultado = dtedi.getPatrocinios().stream().collect(Collectors.toSet());
					if (resultado!= null) {
					}
				}
			}
			return resultado;
		}

		@Override
		public Set<DTPatrocinio> listarPatrociniosAlt(String event, String edi) {
			Set<DTPatrocinio> resultado = new HashSet<>();
			Evento evento = medi.findEvento(event);
			if (evento != null) {
				Edicion edicion = evento.findEdicion(edi);
				if (edicion != null){
					Map<String, TipoRegistro> treg = edicion.getMisTipoRegistro();
					if (treg != null) {
						for (TipoRegistro reg:treg.values()) {
							if (reg.getPatrocinios()!= null) {
								Map<String, Patrocinio> patro  = reg.getPatrocinios();
								for (Patrocinio ptrs: patro.values()) {
									resultado.add(ptrs.getDT());
								}
							}
						}
					}
				}
			}
			return resultado;
		}

		@Override
		public void setInstitucionAsistente(String asistente, String institucion) {
			Asistente asist = (Asistente) musu.getUsuario(asistente);
			if (asist != null) {
				Institucion inst = musu.findInstitucion(institucion);
				asist.setInstitucion(inst);
			}
		}

		@Override
		public Map<String, DTCompra> listarComprasDeUsuario(String nickname) throws NoExisteUsuarioExcepcion{
			Map<String, DTCompra> dtResultado = new HashMap<>();
			if (!musu.existeUsuario(nickname, null)){
				throw new  NoExisteUsuarioExcepcion("El usuario ingresado no existe en sistema");
			}else if (esAsistente(nickname)) {
				Asistente asist = (Asistente) musu.getUsuario(nickname);
				Map<String, Compra> compras = asist.getCompras();
				if (compras.size()>0) {
					for (Compra cmp : compras.values()) {
						DTCompra nueva = new DTCompra(cmp.getId(), cmp.getFecha(), cmp.getCosto(), nickname, cmp.getEdicion().getNombre(), cmp.getTipoRegistro().getNombre(), cmp.getEdicion().getEvento().getNombre(), cmp.isAceptada());
						dtResultado.put(nueva.getId(), nueva);
					}
				}
			}
			return dtResultado;
		}

		@Override
		public Set<DTCompraWeb> listarComprasDeUsuarioWeb(String nickname) throws NoExisteUsuarioExcepcion{
			Map<String, DTCompra> compras=listarComprasDeUsuario(nickname);
			Set<DTCompraWeb> comprasWeb = new HashSet<>();
			for (DTCompra c : compras.values()) {
				Map<String, Edicion> edis =  medi.getEdiciones();
				String imagen ="";
				for (Edicion e: edis.values()) {
					if (e.getNombre().equals(c.getEdicion())) {
						imagen = e.getImagen();
					}
				}
				DTCompraWeb nueva = new DTCompraWeb(c.getId(), c.getFecha(), c.getCosto(), c.getAsistente(), c.getEdicion(), c.getTipoRegistro(), c.getEvento(), imagen, c.isAceptada());
				comprasWeb.add(nueva);
			}
			return comprasWeb;
		}

		@Override
		public void liberarManejadorUsuario() {
			musu.liberarManejador();
		}

		@Override
		public DTCompra getDTCompra(String usuario, String idetificador) {
			if (esAsistente(usuario) && idetificador != null) {
				Asistente asist = (Asistente) musu.getUsuario(usuario);
				return asist.getCompras().get(idetificador).getDT();
			} else {
				return null;
			}
		}

		@Override
		public boolean esAsistente(String usuario) {
			if (usuario != null && musu.getUsuario(usuario) != null && musu.getUsuario(usuario).getClass().equals(Asistente.class)) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean tieneInstitucion(String asistente) {
			Asistente asist = (Asistente) musu.getUsuario(asistente);
			return asist.tieneInstitucion();
		}

		@Override
		public String getInstitucionAsistente(String asistente) {
			Asistente asist = (Asistente) musu.getUsuario(asistente);
			if (tieneInstitucion(asistente)) {
				return asist.getInstitucion().getNombre();
			}
			return "";
		}

		 @Override
		public String findEvento(String  nickname, String edicion ) {
			 Usuario usr = musu.getUsuario(nickname);
			 Edicion edi= null;
			 if ( usr instanceof Asistente) {
				edi = ((Asistente) usr).getCompra(edicion).getEdicion();
			 }
			 if ( usr instanceof Organizador) {
				edi = ((Organizador) usr).findEdicion(edicion);
			 }
			 return edi.getEvento().getNombre();
		 }

		 @Override
		public boolean existeUsuario(String dato) {
			 return musu.existeUsuario(dato, dato);
		 }

		 @Override
		public void cargarDatos(String ruta, String origen) throws Exception {
			new CargarDatos(ruta, origen);
		 }

		 @Override
		public void setImagenUsuario(String usu, String img) {
			 Usuario usuario = musu.getUsuario(usu);
			 usuario.setImagen(img);
		 }

		 @Override
		public void setContraseniaUsuario(String usu, String contra) {
			 Usuario usuario = musu.getUsuario(usu);
			 usuario.setContrasenia(contra);
		 }

		 @Override
		public void setImagenInstitucion(String instit, String img) {
			Institucion inst = musu.getInstitucion(instit);
			inst.setImagen(img);
		 }

		 @Override
		public boolean login(String nombre, String contrasenia) {
			Usuario usuario = musu.getUsuarioNickOrMail(nombre);
			return usuario != null && usuario.getContrasenia().equals(contrasenia);
		 }


		 @Override
		public Set<DTPatrocinio> listarPatrociniosregistro(String edicion, String registro) {
			 Edicion edi = medi.findEdicion(edicion);
			 Set<DTPatrocinio> ret = null;
			 Map<String, TipoRegistro> registros = null;
			 if (edi != null) {
				 registros = edi.getMisTipoRegistro();
				 TipoRegistro reg = registros.get(registro);
				 if (reg != null) {
					  ret = reg.dtPatrocinios();
				 }
			 }
			 return ret;
		 }
		 
		 @Override
		 public void aceptarCompraWeb(String nickAsis, String compraID) {     // PRECONDICIÓN: la compra existe en el sistema
			 Asistente asis = (Asistente) musu.getUsuario(nickAsis);
			 Compra comp = asis.getCompras().get(compraID);
			 comp.setAceptada(true);
		 }
		 
		 @Override
		 public boolean loginAsistente(String nombre, String contrasenia) {
			 Usuario usuario = musu.getUsuarioNickOrMail(nombre);
			 return usuario != null && usuario.getContrasenia().equals(contrasenia) && usuario.getClass() == Asistente.class;
		 }
		 
		 @Override
		 public void addSeguido(String seguidonick, String seguidornick){
			 Usuario seguido = musu.getUsuario(seguidonick);
			 Usuario seguidor = musu.getUsuario(seguidornick);
			 seguido.addSeguidor(seguidor);
             seguidor.addSeguido(seguido);
		 }		
		 
		 @Override
		 public void removeSeguido(String seguidonick, String seguidornick){
			 Usuario seguido = musu.getUsuario(seguidonick);
			 Usuario seguidor = musu.getUsuario(seguidornick);
			 seguido.removeSeguidor(seguidor);
             seguidor.removeSeguido(seguido);
		 }		
		 
		 
		 @Override
		 public void removeSeguidor(String seguidonick, String seguidornick){
			 Usuario seguido = musu.getUsuario(seguidonick);
			 Usuario seguidor = musu.getUsuario(seguidornick);
			 seguido.removeSeguidor(seguidor);
			 seguidor.removeSeguido(seguido);
		 }
		 
		 @Override
		 public Set<DTUsuario> listarSeguidos(String nick){
			 Usuario usu = musu.getUsuario(nick);
			 Set<Usuario> setSeguidos = new HashSet<>();
			 setSeguidos = usu.getSeguidos();
			 Set<DTUsuario> setDTSeguidos = new HashSet<>();
			 for (Usuario usuar : setSeguidos) {
				 setDTSeguidos.add(this.getDTUsuario(usuar.getNickName()));
			 }
			 return setDTSeguidos;
					 
		 }
		 
		 @Override
		 public Set<DTUsuario> listarSeguidores(String nick){
			 Usuario usu = musu.getUsuario(nick);
			 Set<Usuario> setSeguidores = new HashSet<>();
			 setSeguidores = usu.getSeguidores();
			 Set<DTUsuario> setDTSeguidores = new HashSet<>();
			 for (Usuario usuar : setSeguidores) {
				 setDTSeguidores.add(this.getDTUsuario(usuar.getNickName()));
			 }
			 return setDTSeguidores;		 
		 }
		 
		 
}

	
