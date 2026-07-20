package main.java.logica;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.excepciones.ExisteCategoriaExcepcion;
import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.NoTieneCupo;
import main.java.excepciones.YaTieneCompra;
import main.java.excepciones.existeTipoRegistro;

public class ControladorEvento implements IControladorEvento{

	private ManejadorUsuario musu; // NOPMD by leonardog on 10/10/25, 13:35
	private ManejadorEvento medi;

	public ControladorEvento() {
		musu = ManejadorUsuario.getInstance();
		medi = ManejadorEvento.getInstance();
	}

	@Override
	public void liberarManejadorEvento() {
		medi.liberarManejador();
	}

	@Override
	public void addTipoRegistro(String nombEvento, String nombEdicion, String nombre, String desc, float costo, int cupo) throws existeTipoRegistro {
		Evento eve = medi.findEvento(nombEvento);
		Edicion edi = eve.findEdicion(nombEdicion);
		TipoRegistro nuevoTR = new TipoRegistro(nombre, desc, costo, cupo, edi);
		edi.addTipoRegistro(nuevoTR);
	}

	@Override
	public Set<DTTipoRegistro> listarTipoRegistro(String nombEvento, String nombEdicion){
		Set<DTTipoRegistro> resultado = new HashSet<>();
		Evento eve = medi.findEvento(nombEvento);
		Edicion edi = eve.findEdicion(nombEdicion);

		if (edi  != null) {
			for (TipoRegistro tipo: edi.getMisTipoRegistro().values()) {
				resultado.add(tipo.getDT());
			}
			if (resultado.isEmpty()) {
				resultado = null;
			}
		}else {
			resultado = null;
		}
		return resultado;
	}

	@Override
	public Set<String> listarCategorias(){
		Set<String> cats = new HashSet<>();
		for (Categoria tipo: medi.lisarCategorias()) {
			cats.add(tipo.getCategoria());
		}
		if (cats.isEmpty()) {
			cats = null;
		}
		return cats;
	}
	@Override
	public void altaCiudad(String ciudad, String pais) {
		Ciudad ciu = new Ciudad(ciudad, pais);
		medi.addCiudad(ciu);
	}
	@Override
	public Map<String, Ciudad> listarCiudades(){
		return medi.listarCiudades();
	}

	@Override
	public Set<DTEvento> listarEventos(){
		Set<DTEvento> eventos = new HashSet<>();

		for (String clave : medi.getEventos().keySet()) {
			DTEvento eve = this.getEvento(clave);
			eventos.add(eve);
		}
		return eventos;
	}

	@Override
	public Set<DTEvento> listarEventosDeCategoria(String cat){
		Set<DTEvento> eventosCat = null;
		if (medi.getEventos() != null) {
			eventosCat = new HashSet<>();
			for (Evento eve : medi.getEventos().values()) {
				for (Categoria catEve : eve.getCategorias()) {
					if (catEve.getCategoria().equals(cat)) {
						eventosCat.add(eve.getDT());
						break;
					}
				}
			}
		}
		return eventosCat;
	}

	@Override
	public DTEvento getEvento(String nombreevento) {
		Evento evento = this.medi.findEvento(nombreevento);
		DTEvento dtEvento = evento.getDT();
		return dtEvento;
	}
	@Override
	public void addEvento(String nombre, String descripcion, String sigla, LocalDate fechaAlta, Set<String> categorias) throws ExisteEventoExcepcion {
		if (medi.existeEvento(nombre)) {
			throw new ExisteEventoExcepcion("El nombre del evento ya esta en uso. Desea volver a intentar?");
		}else {
			Set<Categoria> catsObj = new HashSet<>();
			for (String iter : categorias) {
				Categoria cat = this.medi.findCategoria(iter);
				catsObj.add(cat);
			}

			Evento newEvento = new Evento(nombre, descripcion, sigla, fechaAlta, catsObj);
			this.medi.addEvento(newEvento);
		}
	}

	@Override
	public Set<DTEdicion> listarEdiciones(String nombEvento) {
		Evento eve = medi.findEvento(nombEvento);
		Set<DTEdicion> resultado =null;
		if (eve != null) {
			resultado = new HashSet<>();
			for (Edicion edicion: eve.getEdiciones().values()) {
				resultado.add(edicion.getDT());
			}
		}
		return resultado;
	}


	@Override
	public Set<DTEdicion> listarEdicionesConfirmadas(String nombEvento){
		Evento eve = medi.findEvento(nombEvento);
		Set<DTEdicion> resultado = null;
		if (eve != null) {
			resultado = new HashSet<>();
			for (Edicion edicion: eve.getEdiciones().values()) {
				if (edicion.getEstado() == EEstadoEdicion.CONFIRMADA) {
					resultado.add(edicion.getDT());
				}
			}
		}
		return resultado;
	}

	@Override
	public Set<DTEdicion> listarEdicionesSinProcesar(String nombEvento) {
		Evento eve = medi.findEvento(nombEvento);
		Set<DTEdicion> resultado =null;
		if (eve != null) {
			resultado = new HashSet<>();
			for (Edicion edicion: eve.getEdiciones().values()) {
				if (edicion.getEstado() == EEstadoEdicion.INGRESADA) {
					resultado.add(edicion.getDT());
				}
			}
		}
		return resultado;
	}

	@Override
	public DTEdicion getEdicion(String nomEvent, String nomEdicion) {
		Evento eve = medi.findEvento(nomEvent);
		if (eve != null && eve.findEdicion(nomEdicion)!= null) {
			Edicion edi = eve.findEdicion(nomEdicion);
			DTEdicion res = edi.getDT();
			res.setEstado(edi.getEstado());
			res.setImagen(edi.getImagen());
			return res;
		}
		return null;
	}

	@Override
	public DTEdicion getEdicionB(String edicion) {
		Edicion edi = medi.findEdicion(edicion);
		if (edi != null) {
			return edi.getDT();
		}
		return null;
	}

	@Override
	public void altaEdicion(String nomEvento, String nickOrganizador, String nombre , String sigla, String  ciudad, LocalDate fechaAlta, LocalDate fechaFin, LocalDate fechaInicion ) throws ExisteEdicion {
		if (medi.existeEdicion(nombre)) {
			throw new ExisteEdicion("Ya existe edición con ese nombre, desea ingresar otro?");
		}else {
			Evento eve = medi.findEvento(nomEvento);
			Ciudad city = medi.findCiudad(ciudad);
			Organizador org = (Organizador) musu.findUsuario(nickOrganizador);
			Edicion edi = new Edicion(nombre, sigla, fechaAlta, fechaFin, fechaInicion, city, eve, org);
			edi.setEstado(EEstadoEdicion.INGRESADA);
			eve.addEdicion(edi);
			org.addEdicion(edi);
			medi.addEdicion(edi);
		}
	}

	@Override
	public void altaCategoria(String cat) throws ExisteCategoriaExcepcion {
		if (medi.findCategoria(cat) != null) {
			throw new ExisteCategoriaExcepcion("La categoria ya existe");
		}else {
			Categoria cate = new Categoria(cat);
			medi.addCategoria(cate);
		}
     }

	@Override
	public DTTipoRegistro getTipoRegistro(String nomEvent, String nomEdicion, String nomTipo) {
		Evento eve = medi.findEvento(nomEvent);
		if (eve != null && eve.findEdicion(nomEdicion)!= null) {
			Edicion edi = eve.findEdicion(nomEdicion);
			TipoRegistro treg = edi.findTipoRegistro(nomTipo);
			if (treg != null) {
				return treg.getDT();
			}
		}
		return null;
	}

	@Override
	public DTTipoRegistro getTipoRegistroEdicion(String nomEdicion, String nomTipo) {
		Edicion edi = medi.findEdicion(nomEdicion);
		if (edi != null) {
			TipoRegistro treg = edi.findTipoRegistro(nomTipo);
			if (treg != null) {
				return treg.getDT();
			}
		}
		return null;
	}

	@Override
	public void altaDeRegistro(String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, LocalDate fechaReg) throws Exception {
		Asistente asistente = (Asistente) musu.getUsuario(nickAsistente);
		Evento evento = medi.findEvento(nombreEento);
		if (evento != null && asistente!= null) {
			Edicion edicion = evento.getEdiciones().get(nombreEdicion);

			TipoRegistro tiporeg = edicion.getMisTipoRegistro().get(nombreTipoReg);
			if (tiporeg != null && !tiporeg.tienCupo()) {
				throw new NoTieneCupo("El tipo de registro no tiene cupo. modificar datos?");
			}else if (asistente.tieneCompra(nombreTipoReg)) {
				throw new YaTieneCompra("El asistente ya esta registrado a la edicion, modificar datos?");
			}else {
				float costo = tiporeg.getCosto();
				if (asistente.getInstitucion() != null) {
					Institucion inst = asistente.getInstitucion();
					Map<String, Patrocinio> patrocinios = inst.getPatrocinios();
					Patrocinio iter = patrocinios.get(nombreEdicion);
						if (iter != null && iter.getTipoRegistro() == tiporeg) {
							if (iter.tieneGratis()) {
								costo = 0;
								iter.darEntradaGratis();
							}
						}

				}
				Compra compra = new Compra(nombreTipoReg, fechaReg, costo, asistente, tiporeg);
				compra.setEdicion(edicion);
				asistente.addCompra(compra);
				edicion.addCompra(compra);
				tiporeg.addCompra(compra);
			}
		}
	}

	public void altaDeRegistro(String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, LocalDate fechaReg , float costo  ) throws Exception {
		Asistente asistente = (Asistente) musu.getUsuario(nickAsistente);
		Evento evento = medi.findEvento(nombreEento);
		if (evento != null && asistente!= null) {
			Edicion edicion = evento.getEdiciones().get(nombreEdicion);

			TipoRegistro tiporeg = edicion.getMisTipoRegistro().get(nombreTipoReg);
			if (tiporeg != null && !tiporeg.tienCupo()) {
				throw new NoTieneCupo("El tipo de registro no tiene cupo. modificar datos?");
			}else if (asistente.tieneCompra(nombreEdicion)){
				throw new YaTieneCompra("El asistente ya esta registrado a la edicion, modificar datos?");
			}else {
				Compra compra = new Compra(nombreTipoReg, fechaReg, costo, asistente, tiporeg);
				compra.setEdicion(edicion);
				asistente.addCompra(compra);
				edicion.addCompra(compra);
				tiporeg.addCompra(compra);
			}
		}
	}

	@Override
	public boolean validoCupon( String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, String cupon) {
		Asistente asistente = (Asistente) musu.getUsuario(nickAsistente);
		boolean resultado = false;
		Evento evento = medi.findEvento(nombreEento);
		if (evento != null && asistente!= null) {
			Edicion edicion = evento.getEdiciones().get(nombreEdicion);
			TipoRegistro tiporeg = edicion.getMisTipoRegistro().get(nombreTipoReg);
			if (asistente.getInstitucion() != null) {
				Institucion inst = asistente.getInstitucion();
				Map<String, Patrocinio> patrocinios = inst.getPatrocinios();
				Patrocinio iter = patrocinios.get(nombreEdicion);
					if (iter != null && iter.getTipoRegistro() == tiporeg) {
						if (iter.tieneGratis() && iter.getCodigo().equals(cupon)) {
							resultado = true;
						}
					}

			}
		}
		return resultado;
	}
	@Override
	public void setImagenEvento(String evento, String img) {
		Evento evt = medi.findEvento(evento);
		evt.setImagen(img);
	}

	@Override
	public void setImagenEdicion(String edicion, String img) {
		Edicion edi = medi.findEdicion(edicion);
		edi.setImagen(img);
	}

	@Override
	public void setEstadoEdicion(String edicion, EEstadoEdicion est) {
		Edicion edi = medi.findEdicion(edicion);
		edi.setEstado(est);
	}

	@Override
	public  boolean existeEvento(String nombre) {
		return medi.existeEvento(nombre);
	}

	@Override
	public Set<DTEdicion> listarEdicionesOrganizadorEvento(String organizador, String evento){
		Set<DTEdicion> edicionesRes = null;
		Organizador organ = (Organizador) musu.getUsuario(organizador);
		Set<DTEdicion> edicionesEv = listarEdiciones(evento);
		if (organ != null && edicionesEv != null) {
			edicionesRes = new HashSet<>();
			Set<DTEdicion> edicionesOrg = organ.lisarDTEdiciones();
			for (DTEdicion eOrg : edicionesOrg) {
				for (DTEdicion eEv : edicionesEv) {
					if (eOrg.getNombre().equals(eEv.getNombre())) {
						edicionesRes.add(eOrg);
						break;
					}
				}
			}
		}
		return edicionesRes;
	}

	
	public void setFinalidado(String edicion){
		Evento edi = medi.findEvento(edicion);
		edi.setFinalizado(true);
	}

	public void visitar(String evento) {
		Evento eve = medi.findEvento(evento);
		eve.visitar();
	}
	
	public int getVisitas(String evento) {
		Evento eve = medi.findEvento(evento);
		return eve.getVisitas();
	}


	public void setVideo(String edi, String video) {
		Edicion edicion = medi.findEdicion(edi);
		edicion.setVideo(video);
	}

	public String getVideoEdicion(String edi) {
		Edicion edicion = medi.findEdicion(edi);
		return edicion.getVideo();
	}
}



