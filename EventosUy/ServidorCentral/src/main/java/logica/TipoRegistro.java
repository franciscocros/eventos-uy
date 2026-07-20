package main.java.logica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.excepciones.ExisteUsuarioEnEdicionException;

public class TipoRegistro {
	private String nombre;
	private String descripcion;
	private float costo;
	private int cantCupos;
	private Edicion edicion;

	private Map<String, Patrocinio> patrocinios;
	private Map<String, Compra> compras;

	public TipoRegistro(String nombre, String descripcion, float costo, int cantCupos, Edicion edicion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.cantCupos = cantCupos;
		this.edicion = edicion;
		this.patrocinios = new HashMap<>();
		this.compras = new HashMap<>();
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

	public Edicion getEdicion() {
		return this.edicion;
	}

	public Map<String, Patrocinio> getPatrocinios(){
		return this.patrocinios;
	}

	public Map<String, Compra> getCompras(){
		return this.compras;
	}

	public Set<DTCompra> getDTCompras() {

		Set<DTCompra> dtCompras = new HashSet<>();
		for (  Compra comp:  this.compras.values()){
			DTCompra tmpCompra = comp.getDT();

			if (tmpCompra != null) {
				dtCompras.add(tmpCompra);
			}

			if (dtCompras.isEmpty()) {
				dtCompras = null;
			}

		}
		if (dtCompras.isEmpty()) {
			dtCompras = null;
		}

		return dtCompras;
	}

	public Set<DTPatrocinio> dtPatrocinios() {
		Set<DTPatrocinio> dtPatrocinios = new HashSet<>();
		for (  Patrocinio pat:  this.patrocinios.values()){

			DTPatrocinio tmpPatrocinios = pat.getDT();

			if (tmpPatrocinios !=null) {
				dtPatrocinios.add(tmpPatrocinios);
			}

			if (dtPatrocinios.isEmpty()) {
				dtPatrocinios = null;
			}

		}
		if (dtPatrocinios.isEmpty()) {
			dtPatrocinios = null;
		}

			return dtPatrocinios;

	}

	public DTTipoRegistro getDT() {
		return new DTTipoRegistro(nombre, descripcion, costo, cantCupos, edicion.getNombre(), edicion.getEvento().getNombre());
	}
	public void addCompra(Compra nuevaCompra) throws ExisteUsuarioEnEdicionException {
		String nomtr = nuevaCompra.getAsistente().getNickName();
	       if ( this.compras.containsKey(nomtr)) {
	    	   throw new ExisteUsuarioEnEdicionException("Este usuario" +nomtr+" ya realizo una compra para esta edicion" );
	       }else {
	    	   this.compras.put(nomtr, nuevaCompra);
	       }
	}
	public void addPatrocinio(Patrocinio nuevo) throws Exception {
		/*String nomtr = nueva.getInstitucion().getNombre();
	       if ( this.patrocinios.containsKey(nomtr)) {
	    	   throw new Exception("Este instituto " +nomtr+" ya patrocinia esta edicion" );
	       }else {
	    	   this.patrocinios.put(nomtr, nueva);
	       }*/
		this.patrocinios.put(nuevo.getTipoRegistro().getNombre(), nuevo);
	}
	public boolean tienCupo() {
		return this.cantCupos > this.compras.size();
	}
}
