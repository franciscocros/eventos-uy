package main.java.logica;

import java.util.HashMap;
import java.util.Map;

public class Institucion {
	private String nombre;
	private String descripcion;
	private String paginaWeb;
	private Map<String, Patrocinio> patrocinios;
	private Map<String, Asistente> asistentes;
	private String imagen;

	public Institucion(String nombre, String descripcion, String paginaWeb) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.paginaWeb = paginaWeb;
		patrocinios = new HashMap<>();
		asistentes = new HashMap<>();
		this.imagen = "";
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void addAsistente(Asistente asistente) {
		this.asistentes.put(asistente.getNickName(), asistente);
	}

	public void addPatrocinio(Patrocinio patrocinio) {
		this.patrocinios.put(patrocinio.getTipoRegistro().getEdicion().getNombre(), patrocinio); //Indice por nombre de edicion
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getPaginaWeb() {
		return this.paginaWeb;
	}

	public Map<String, Patrocinio> getPatrocinios() {
		return this.patrocinios;
	}

	public Map<String, Asistente> getAsistentes() {
		return this.asistentes;
	}

	//Verifica si tiene un patrocinio con el tipo de registro
	public boolean tienePatrocinio(String tipoReg) {
		for (Patrocinio patrocinio: this.patrocinios.values()) {
			if (patrocinio.getTipoRegistro().getNombre().equals(tipoReg)) {
				return true;
			}
		}
		return false;
	}

}
