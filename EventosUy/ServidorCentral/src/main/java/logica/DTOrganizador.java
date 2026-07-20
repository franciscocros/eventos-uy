package main.java.logica;

import java.util.ArrayList;
import java.util.HashSet;
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
public class DTOrganizador extends DTUsuario {
	private String descripcion;
	private String paginaWeb;
	private List<DTEdicion> ediciones;
	public DTOrganizador() {
		
	}
	public DTOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) {
		super(nombre, nickname, correo);
		this.descripcion = descripcion;
		this.paginaWeb = paginaWeb;
		this.ediciones = new ArrayList<>();
	}

	public void setEdiciones(List<DTEdicion> edis) {
		this.ediciones = edis;
	}

	public List<DTEdicion> getEdiciones(){
		return this.ediciones;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getPaginaWeb() {
		return this.paginaWeb;
	}
	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	public void setPaginaWeb(String paginaWeb) {
	    this.paginaWeb = paginaWeb;
	}



}
