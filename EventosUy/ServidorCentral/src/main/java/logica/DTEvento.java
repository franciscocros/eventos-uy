package main.java.logica;

import java.time.LocalDate;
import main.java.webservice.LocalDateAdapter;
import java.util.List;
import java.util.Set;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import main.java.webservice.LocalDateAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)
public class DTEvento {
	private String nombre;
	private String descipcion;
	private String sigla;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate fechaAlta;
	private List<String> categorias;
	private List<DTEdicion> ediciones;
	private String imagen;
	private boolean finalizado;

	public DTEvento() {

		this.nombre = "";
		this.descipcion = "";
		this.sigla = "";
		this.fechaAlta = null;
		this.categorias = null;
		this.ediciones = null;
		this.imagen = "";
		this.finalizado= false;
	}	
	public DTEvento(String nombre, String descipcion, String sigla, LocalDate fechaAlta,
		List<String> categorias, List<DTEdicion> ediciones, boolean finalizado) {
		this.nombre = nombre;
		this.descipcion = descipcion;
		this.sigla = sigla;
		this.fechaAlta = fechaAlta;
		this.categorias = categorias;
		this.ediciones = ediciones;
		this.imagen = "";
		this.finalizado = finalizado;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getImagen() {
		return this.imagen;
	}


	// Getters
	public String getNombre() {
		return nombre;
	}

	public String getDescipcion() {
		return descipcion;
	}

	public String getSigla() {
		return sigla;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public List<DTEdicion> getEdiciones() {
		return ediciones;
	}
	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}

	public void setDescipcion(String descipcion) {
	    this.descipcion = descipcion;
	}

	public void setSigla(String sigla) {
	    this.sigla = sigla;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
	    this.fechaAlta = fechaAlta;
	}

	public void setCategorias(List<String> categorias) {
	    this.categorias = categorias;
	}

	public void setEdiciones(List<DTEdicion> ediciones) {
	    this.ediciones = ediciones;
	}
	public boolean getFinalizado() {
		// TODO Auto-generated method stub
		return this.finalizado;
	}
	public void setFinalizado(boolean fin) {
		// TODO Auto-generated method stub
		 this.finalizado = fin;
	}

}
