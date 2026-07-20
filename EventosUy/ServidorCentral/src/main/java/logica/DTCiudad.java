package main.java.logica;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ciudad", propOrder = {"nombre", "pais"})
public class DTCiudad {
	private String nombre;
	private String pais;
	
	public DTCiudad(){}
	
	public DTCiudad(String newNombre, String newPais) {
		this.nombre = newNombre;
		this.pais = newPais;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getPais() {
		return this.pais;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
}
