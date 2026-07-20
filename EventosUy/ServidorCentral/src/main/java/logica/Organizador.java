package main.java.logica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.excepciones.ExisteEdicion;

public class Organizador extends Usuario{
	private String descripcion;
	private String paginaWeb;
	private Map<String, Edicion> ediciones;

	public Organizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) {
		super(nombre, nickname, correo);
		this.descripcion = descripcion;
		this.paginaWeb = paginaWeb;
		this.ediciones = new HashMap<>();
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getPaginaWeb() {
		return this.paginaWeb;
	}

	public void modificarDatos(String nombre, String descripcion, String paginaWeb) {
		this.descripcion = descripcion;
		this.paginaWeb = paginaWeb;
		super.setNombre(nombre);
	}

	public void modificarOrganizador(String nombre, String nickname, String correo, String descripcion, String paginaWeb) {
		super.setNombre(nombre);
		this.descripcion = descripcion;
		this.paginaWeb = paginaWeb;
	}

	public void addEdicion(Edicion edi) throws ExisteEdicion {
		if (ediciones.containsKey(edi.getNombre())){
			throw new ExisteEdicion("Ya existe edición para este organizador con ese nombre, desea ingresar otro?");
		}else {
			ediciones.put(edi.getNombre(), edi);
		}

	}
	public Set<DTEdicion> lisarDTEdiciones(){
		Set<DTEdicion> dtedis = new HashSet<>() ;
    	for (Map.Entry<String, Edicion> edi : ediciones.entrySet()) {
    	    DTEdicion dte = edi.getValue().getDT();
    		dtedis.add(dte);
    	}
		return dtedis;
	}
	public Edicion findEdicion(String idEdi) {
    	Edicion resultado = null;
    	if (this.ediciones.containsKey(idEdi)) {
    		resultado = this.ediciones.get(idEdi);
    	}
    	return resultado;
    }

}
