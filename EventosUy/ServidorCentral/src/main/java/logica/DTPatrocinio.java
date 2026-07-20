package main.java.logica;

import java.time.LocalDate;
import main.java.webservice.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)
public class DTPatrocinio {
	private float aporteEconomico;
	private String codigo;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate fecha;
	private int cantGratis;
	private ENivelPatrocinio nivel;
	public DTPatrocinio() {
		
	}
	public DTPatrocinio(float aporteEconomico, String codigo, LocalDate fecha, int cantGratis, ENivelPatrocinio nivel) {
		this.aporteEconomico = aporteEconomico;
		this.codigo = codigo;
		this.fecha = fecha;
		this.cantGratis = cantGratis;
		this.nivel = nivel;
	}
	public void setAporteEconomico(float aporteEconomico) {
	    this.aporteEconomico = aporteEconomico;
	}

	public void setCodigo(String codigo) {
	    this.codigo = codigo;
	}

	public void setFecha(LocalDate fecha) {
	    this.fecha = fecha;
	}

	public void setCantGratis(int cantGratis) {
	    this.cantGratis = cantGratis;
	}

	public void setNivel(ENivelPatrocinio nivel) {
	    this.nivel = nivel;
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
}
