package main.java.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.excepciones.ExisteEdicion;

public class Evento {
	 private String nombre;
	 private String descipcion;
	 private String sigla;
	 private LocalDate fechaAlta;
	 private Set<Categoria> categorias;
	 private Map<String, Edicion> ediciones;
	 private String imagen;
	 private boolean finalizado;
	 private int cantVisitas;

	 public Evento(String nombre, String desc, String sigla, LocalDate alta, Set<Categoria> cats) {
		this.nombre = nombre;
		this.descipcion = desc;
		this.sigla = sigla;
		this.fechaAlta = alta;
		this.categorias = cats;
		this.ediciones=new HashMap<>();
		this.imagen = "";
		finalizado = false;
		cantVisitas = 0;
	}
	 
	 public int getVisitas() {
		 return this.cantVisitas;
	 }
	 
	 public void visitar() {
		 cantVisitas++;
	 }
	 
	 public void setFinalizado(boolean finalizado) {
			this.finalizado = finalizado;
		}
	 public boolean getFinalizado() {
			return this.finalizado;
		}
	 public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getImagen() {
		return this.imagen;
	}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Map<String, Edicion> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(Map<String, Edicion> edis) {
        this.ediciones = edis;
    }

    public DTEvento getDT() {
    	List<String> dtcats = new ArrayList<>() ;

    	for (Categoria iter: categorias) {
    		if (iter != null) {
    			dtcats.add(iter.getCategoria());
    		}

    	}
    	List<DTEdicion> dtedis = new ArrayList<>() ;
    	for (Map.Entry<String, Edicion> edi : ediciones.entrySet()) {
    	    DTEdicion dte = edi.getValue().getDT();
    		dtedis.add(dte);
    	}
    	
    	DTEvento res = new DTEvento(this.nombre, this.descipcion, this.sigla, this.fechaAlta, dtcats, dtedis,this.finalizado);
    	res.setImagen(this.imagen);
    	return res;
    }

    public Edicion findEdicion(String idEdi) {
    	Edicion resultado = null;
    	if (this.ediciones.containsKey(idEdi)) {
    		resultado = this.ediciones.get(idEdi);
    	}
    	return resultado;
    }
    public void addEdicion(Edicion newEd) throws ExisteEdicion {

    	String nombre = newEd.getNombre();
    	if (!this.ediciones.containsKey(nombre)) {
    		this.ediciones.put(nombre, newEd);
    	} else {
			throw new ExisteEdicion("Ya existe en nombre de esta edicion para este evento");
		}
    }
}
