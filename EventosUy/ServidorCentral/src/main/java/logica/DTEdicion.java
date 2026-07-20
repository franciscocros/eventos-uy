package main.java.logica;

import java.time.LocalDate;
import main.java.webservice.LocalDateAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)
public class DTEdicion {

	private String nombre;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate fechaInicio;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate fechaFin;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate fechaAlta;
	private String ciudad;
	private String pais;
	private String organizador;
	private List<String> tiposRegistros;
	private List<DTCompra> datosCompras;
    private List<DTPatrocinio> patrocinios;
    private String imagen;
    private String video;
    private EEstadoEdicion estado;
    private String evento;

	public DTEdicion() {
	
	}
    public DTEdicion(String nombre, LocalDate fini, LocalDate ffin, LocalDate falta, String ciudad, String pais, String org, List<String> treg, List<DTCompra> dtcomp, List<DTPatrocinio> pat, String video) {
    	    this.nombre = nombre;
    	    this.fechaInicio = fini;
    	    this.fechaFin = ffin;
    	    this.fechaAlta = falta;
    	    this.ciudad = ciudad;
    	    this.pais = pais;
    	    this.organizador = org;
    	    this.tiposRegistros = treg;
    	    this.datosCompras = dtcomp;
    	    this.patrocinios = pat;
    	    this.imagen = "";
    	    this.video = "" ;
    	    this.estado = EEstadoEdicion.INGRESADA;
     }
    public DTEdicion(String nombre2, LocalDate inicio, LocalDate fin, LocalDate alta, String city, String pais2,
			String org, Set<String> treggistros, Set<DTCompra> dtcompras, Set<DTPatrocinio> dtPatrocinios) {
    	this.nombre = nombre2;
	    this.fechaInicio = inicio;
	    this.fechaFin = fin;
	    this.fechaAlta = alta;
	    this.ciudad = city;
	    this.pais = pais2;
	    this.organizador = org;
	    if (treggistros != null) {
	    	this.tiposRegistros = treggistros.stream().toList();
	    }else {
	    	this.tiposRegistros = new ArrayList<>();
	    }
	    if (dtcompras != null) {
	    	this.datosCompras = dtcompras.stream().toList();
	    }else {
	    	this.datosCompras = new ArrayList<>();
	    }
	    if(dtPatrocinios != null) {
	    	this.patrocinios = dtPatrocinios.stream().toList();
	    }else {
	    	this.patrocinios = new ArrayList<>();
	    }
	    this.imagen = "";
	    this.video = "";
	    this.estado = EEstadoEdicion.INGRESADA;
	}
    public void setVideo(String video) {
    	this.video = video;
    }
    public String getVideo() {
    	return this.video;
    }
	public void setEvento(String evento) {
        this.evento = evento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public void setTiposRegistros(List<String> tiposRegistros) {
        this.tiposRegistros = tiposRegistros;
    }

    public void setDatosCompras(List<DTCompra> datosCompras) {
        this.datosCompras = datosCompras;
    }

    public void setPatrocinios(List<DTPatrocinio> patrocinios) {
        this.patrocinios = patrocinios;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setEstado(EEstadoEdicion estado) {
        this.estado = estado;
    }

	public String getEvento() {
		return  this.evento;
	}


	public String getImagen() {
		return this.imagen;
	}


	public EEstadoEdicion getEstado() {
		return this.estado;
	}

	public String getNombre() {
    	   return this.nombre;
    }

    public LocalDate getFechaini() {
    	   return this.fechaInicio;
    }

    public LocalDate getFechaFin() {
    		return this.fechaFin;
    }

    public LocalDate getFechaAlta() {
    		return this.fechaAlta;
    }

    public String getCiudad() {
    		return this.ciudad;
    }

    public String getPais() {
    		return this.pais;
    }

    public String getOrganizador() {
    		return this.organizador;
    }

    public List<String> getTiposRegistros(){
    		return this.tiposRegistros;
    }

    public List<DTCompra> getDatosCompras(){
    		return this.datosCompras;
    }

    public List<DTPatrocinio> getPatrocinios(){
    		return this.patrocinios;
    }
}



