package main.java.logica;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import main.java.webservice.LocalDateAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)

public class DTInstitucion {
	private String nombre;
	private String descripcion;
	private String web;
	private String imagen;

	public  DTInstitucion() {
		
	}	
	DTInstitucion(String nombre, String descripcion, String web){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.web = web;
		this.imagen = "";
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getImagen() {
		return this.imagen;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getWeb() {
		return this.web;
	}
	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	public void setWeb(String web) {
	    this.web = web;
	}

	

}
