package main.java.logica;

import java.time.LocalDate;

public class Patrocinio {
	private float aporteEconomico;
	private String codigo;
	private LocalDate fecha;
	private int cantGratis;
	private ENivelPatrocinio nivel;
	private TipoRegistro tiporeg;
	private Institucion institucion;
	private int cantGratisEntregadas;

	public Patrocinio(float aporteEconomico, String codigo, LocalDate fecha, int cantGratis, ENivelPatrocinio nivel, TipoRegistro tiporeg, Institucion institucion) {
		this.aporteEconomico = aporteEconomico;
		this.codigo = codigo;
		this.fecha = fecha;
		this.cantGratis = cantGratis;
		this.nivel = nivel;
		this.tiporeg = tiporeg;
		this.institucion = institucion;
		this.cantGratisEntregadas = 0;
	}

	public boolean tieneGratis() {
		return this.cantGratis>this.cantGratisEntregadas;
	}

	public void darEntradaGratis() {
		this.cantGratisEntregadas++;
	}

	public float getAporte() {
		return this.aporteEconomico;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public LocalDate getDTFecha() {
		return this.fecha;
	}

	public int getCantGratis() {
		return this.cantGratis;
	}

	public ENivelPatrocinio getNivelPatrocinio() {
		return this.nivel;
	}

	public TipoRegistro getTipoRegistro() {
		return this.tiporeg;
	}

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public DTPatrocinio getDT() {
		return new DTPatrocinio(aporteEconomico, codigo, fecha, cantGratis, nivel);
	}
}
