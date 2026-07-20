package main.java.logica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ManejadorEvento {
	private static ManejadorEvento instance = null;

	private Map<String, Evento> mapEvento;
	private Set<Categoria> setCategoria;
	private Map<String, Ciudad> mapCiudad;
	private Map<String, Edicion> mapEdicion;

	 /* Constructor*/
	private ManejadorEvento() {
		mapEvento    = new HashMap<>();
		setCategoria = new HashSet<>();
		mapCiudad    = new HashMap<>();
		mapEdicion	 = new HashMap<>();
	}

	/*
	 * Devuelve instancia Singleton*/
	public static ManejadorEvento getInstance() {
		if (instance == null) {
			instance = new ManejadorEvento();
		}
		return instance;
	}

	public Map<String, Evento> getEventos(){
	
		return  this.mapEvento;
	}
	public void addEvento(Evento newEvento) {
		this.mapEvento.put(newEvento.getNombre().replaceAll("\\s+", "").toLowerCase(), newEvento);
	}
	public Evento findEvento(String nomEvento) {
		return this.mapEvento.get(nomEvento.replaceAll("\\s+", "").toLowerCase());
	}
	public boolean existeEvento(String nomEvento) {
		return this.mapEvento.containsKey(nomEvento.replaceAll("\\s+", "").toLowerCase());
	}

	public Set<Categoria> lisarCategorias(){
		return this.setCategoria;
	}

	public Categoria findCategoria(String cat) {
		for (Categoria iter : setCategoria) {
			if (iter.getCategoria().equals(cat)) {
				return iter;
			}
		}
		return null;
	}

	public void addCategoria(Categoria newCategoria) {
		this.setCategoria.add(newCategoria);
	}
	public Map<String, Ciudad> listarCiudades(){
		return this.mapCiudad;
	}

	public Ciudad findCiudad(String ciudad) {
		return mapCiudad.get(ciudad);
	}

	public void addCiudad(Ciudad ciu){
		if (!this.mapCiudad.containsKey(ciu.getNombre())) {
			this.mapCiudad.put(ciu.getNombre(), ciu);
		}
	}

	public void liberarManejador() {
		mapEvento    = new HashMap<>();
		setCategoria = new HashSet<>();
		mapCiudad    = new HashMap<>();
	}

	public Map<String, Edicion> getEdiciones(){
		return this.mapEdicion;
	}
	public void addEdicion(Edicion newEdicion) {
		this.mapEdicion.put(newEdicion.getNombre().replaceAll("\\s+", "").toLowerCase(), newEdicion);
	}
	public Edicion findEdicion(String nomEdicion) {
		return this.mapEdicion.get(nomEdicion.replaceAll("\\s+", "").toLowerCase());
	}
	public boolean existeEdicion(String nomEdicion) {
		return this.mapEdicion.containsKey(nomEdicion.replaceAll("\\s+", "").toLowerCase());
	}
}
