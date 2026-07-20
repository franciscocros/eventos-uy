package main.java.logica;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.excepciones.ExisteUsuarioEnEdicionException;
import main.java.excepciones.existeTipoRegistro;
public class Edicion {
	private String nombre;
	private String sigla;
	private LocalDate alta;
	private LocalDate inicio;
	private LocalDate fin;
	private Ciudad ciudad;
	private Evento evento;
	private Organizador organizador;
	private Map<String, TipoRegistro> misTipoRegistro;
	private Map<String, Compra> compras;
	private EEstadoEdicion estado;
	private String imagen;
	private String video;
	
	public Edicion() {

	}

	public Edicion(String nombre, String sigla, LocalDate alta, LocalDate fin, LocalDate ini, Ciudad ciu, Evento eve, Organizador org) {
		this.nombre=nombre;
		this.sigla=sigla;
		this.alta=alta;
		this.inicio=ini;
		this.fin=fin;
		this.ciudad=ciu;
		this.evento=eve;
		this.organizador=org;
		this.misTipoRegistro = new HashMap<>();
		this.compras= new HashMap<>();
		this.imagen ="";
		this.estado = EEstadoEdicion.INGRESADA;
		this.video = "";
	}
	
	public void setVideo(String video) {
		this.video = video;
	}
	
	public String getVideo() {
		return this.video;
	}
	
	public void setEstado(EEstadoEdicion estado) {
		this.estado = estado;
	}

	public EEstadoEdicion getEstado() {
		return this.estado;
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
    public String getSigla() {
        return sigla;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public LocalDate getAlta() {
        return alta;
    }
    public void setAlta(LocalDate alta) {
        this.alta = alta;
    }
    public LocalDate getInicio() {
        return inicio;
    }
    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }
    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
    public Ciudad getCiudad() {
        return ciudad;
    }
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
    public Evento getEvento() {
        return evento;
    }
    public Organizador getOrganizador() {
        return organizador;
    }
    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }
    public Map<String, TipoRegistro> getMisTipoRegistro() {
        return misTipoRegistro;
    }
    public void setMisTipoRegistro(Map<String, TipoRegistro> misTipoRegistro) {
        this.misTipoRegistro = misTipoRegistro;
    }

    public void addTipoRegistro(TipoRegistro tipo) throws existeTipoRegistro {
    	String nomtr = tipo.getNombre();
       if ( this.misTipoRegistro.containsKey(nomtr)) {
    	   String text = "Ya existe este tipo de registro  para esta edicion";
    	   throw new existeTipoRegistro(text);
       }else {
    	   this.misTipoRegistro.put(nomtr, tipo);
       }
    }

    public TipoRegistro findTipoRegistro(String idtr) {
        return this.misTipoRegistro.get(idtr);
    }

    public void addCompra(Compra nueva) throws ExisteUsuarioEnEdicionException {
    	String asis = nueva.getAsistente().getNickName();
    	if (this.compras.containsKey(asis)) {
    		String text = "Ya existe el nombre " + asis +" para esta edicion";
     	   throw new ExisteUsuarioEnEdicionException(text);
    	}else {
    		this.compras.put(asis, nueva);
    	}
    }

	public DTEdicion getDT() {
		Set<String> treggistros = this.misTipoRegistro.keySet();
		Set<DTCompra> dtcompras = new HashSet<>();
		Set<DTPatrocinio> dtPatrocinios = new HashSet<>();
		for (TipoRegistro tr:  this.misTipoRegistro.values()){
			Set<DTPatrocinio> tmpPatrocinios = tr.dtPatrocinios();

			if (tmpPatrocinios != null) {
				for (DTPatrocinio d: tmpPatrocinios) {
					dtPatrocinios.add(d);
				}
			}


		}

		for (Compra comp : this.compras.values()) {
			dtcompras.add(comp.getDT());
		}

		if (dtcompras.isEmpty()) {
			dtcompras = null;
		}

		String city ="" ;
		String pais = "";
		String org = "";
		if (ciudad != null){
			city = ciudad.getNombre();
			pais = ciudad.getPais();
		}
		if (organizador != null) {
			org = organizador.getNickName();
		}
		DTEdicion res = new DTEdicion(nombre, inicio, fin, alta, city, pais, org , treggistros , dtcompras, dtPatrocinios);
		res.setEvento(evento.getNombre());
		res.setImagen(this.imagen);
		res.setEstado(this.estado);
		res.setVideo(this.video);
		return res;
	}
}
