package main.java.logica;

import java.time.LocalDate;

public class Compra {

	private String identificador;
	private LocalDate fechaReg;
	private float costo;
	private Asistente asistente;
	private TipoRegistro tipoRegistro;
	private Edicion edicion;
	private boolean aceptada;

	public Compra(String identificador, LocalDate fechaReg, float costo, Asistente asistente, TipoRegistro tipoRegistro) {
		this.identificador = identificador;
		this.fechaReg = fechaReg;
		this.costo = costo;
		this.asistente = asistente;
		this.tipoRegistro = tipoRegistro;
		this.edicion = null;
		this.aceptada = false;
	}

	public void setId(String identificador) {
		this.identificador = identificador;
	}

	public String getId() {
		return this.identificador;
	}

	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}

	public LocalDate getFecha() {
		return this.fechaReg;
	}

	public Edicion getEdicion() {
		return this.edicion;
	}

	public float getCosto() {
		return this.costo;
	}

	public Asistente getAsistente() {
		return this.asistente;
	}

	public TipoRegistro getTipoRegistro() {
		return this.tipoRegistro;
	}

	public DTCompra getDT() {
		DTCompra nuevo = new DTCompra(identificador, fechaReg, costo, asistente.getNickName(), edicion.getNombre(), tipoRegistro.getNombre(), edicion.getEvento().getNombre(), this.isAceptada());
		return nuevo;
	}
	
	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

}


