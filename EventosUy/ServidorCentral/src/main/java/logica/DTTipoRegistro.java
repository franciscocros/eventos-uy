package main.java.logica;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import main.java.webservice.LocalDateAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)
public class DTTipoRegistro {

	private String nombre;
	private String descripcion;
	private String nombreEvento;
	private String nombreEdicion;
	private float costo;
	private int cantCupos;
	public DTTipoRegistro() {
		
	}
	public DTTipoRegistro(String nom, String desc, float costo, int cupos, String nombreEdicion, String nombreEvent) {
		this.nombre = nom;
		this.descripcion = desc;
		this.costo = costo;
		this.cantCupos = cupos;
		this.nombreEvento = nombreEvent;
		this.nombreEdicion = nombreEdicion;
	}
	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	public void setNombreEvento(String nombreEvento) {
	    this.nombreEvento = nombreEvento;
	}

	public void setNombreEdicion(String nombreEdicion) {
	    this.nombreEdicion = nombreEdicion;
	}

	public void setCosto(float costo) {
	    this.costo = costo;
	}

	public void setCantCupos(int cantCupos) {
	    this.cantCupos = cantCupos;
	}

    public String getNombre() {
    		return this.nombre;
    }

    public String getDescripcion() {
    		return this.descripcion;
    }

    public float getCosto() {
    		return this.costo;
    }

    public int getCupos() {
    		return this.cantCupos;
    }
    public String getEdicion() {
    	return this.nombreEdicion;
    }
    public String getEvento() {
    	return this.nombreEvento;
    }
}
