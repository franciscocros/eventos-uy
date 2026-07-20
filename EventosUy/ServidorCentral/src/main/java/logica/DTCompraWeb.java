package main.java.logica;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import main.java.webservice.LocalDateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)
public class DTCompraWeb {

	private String identificador;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate fechaCompra;
	private float costo;
    private String asistente;
    private String edicion;
    private String tipoRegistro;
    private String evento;
    private String imagenEdicion;
    private boolean aceptada;

    public DTCompraWeb() {
    }
    public DTCompraWeb(String identificador, LocalDate fechaCompra, float costo, String asistente, String edicion, String tipoRegistro, String evento, String imagenEdicion, boolean aceptada) {
	   this.identificador = identificador;
	   this.fechaCompra = fechaCompra;
	   this.costo = costo;
	   this.asistente = asistente;
	   this.edicion = edicion;
	   this.tipoRegistro = tipoRegistro;
	   this.evento = evento;
	   this.imagenEdicion = imagenEdicion;
	   this.aceptada = aceptada;
    }
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public void setAsistente(String asistente) {
        this.asistente = asistente;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public void setImagenEdicion(String imagenEdicion) {
        this.imagenEdicion = imagenEdicion;
    }

    public String getId() {
 	   return this.identificador;
    }

    public LocalDate getFecha() {
    	   return this.fechaCompra;
    }

    public float getCosto() {
    	   return this.costo;
    }

    public String getAsistente() {
    	   return this.asistente;
    }

    public String getEdicion() {
    	   return this.edicion;
    }

    public String getTipoRegistro() {
    	   return this.tipoRegistro;
    }

    public String getEvento() {
    	return this.evento;
    }

    public String getImagenEdicion() {
    	return this.imagenEdicion;
    }
    
    public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean acept) {
		this.aceptada = acept;
	}
}
