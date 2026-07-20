package main.java.logica;

public class Ciudad {
	private String nombre;
	private String pais;
	public Ciudad(String newNombre, String newPais) {
		this.nombre = newNombre;
		this.pais = newPais;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getPais() {
		return this.pais;
	}
}
