package main.java.logica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Asistente extends Usuario {
	private String apellido;
	private LocalDate fechaNacimiento;
	private Map<String, Compra> compras;
	private Institucion institucion;


	public Asistente(String nombre, String nickname, String correo, String apellido, LocalDate fechaNacimiento){
		super(nombre, nickname, correo);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.compras = new HashMap<>();
		this.institucion = null;
	}

	public boolean tieneInstitucion() {
	return this.institucion != null;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public String getApellido() {
		return this.apellido;
	}

	public LocalDate getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public Map<String, Compra> getCompras(){
		return this.compras;
	}

	public List<DTCompra> getDTCompras(){
		Set<DTCompra> dtcompras = new HashSet<>();
		for (Compra compra: this.compras.values()) {
			DTCompra actual = new DTCompra(compra.getId(), compra.getFecha(), compra.getCosto(), compra.getAsistente().getNickName(), compra.getEdicion().getNombre(), compra.getTipoRegistro().getNombre(), compra.getEdicion().getEvento().getNombre(), compra.isAceptada());
			dtcompras.add(actual);
		}
		return dtcompras.stream().toList();
	}

	public Compra getCompra(String compra) {
		return this.compras.get(compra);
	}

	public void addCompra(Compra compra) {
		this.compras.put(compra.getEdicion().getNombre(), compra);
	}

	public boolean tieneCompra(String compra) {
		return this.compras.containsKey(compra);
	}

	public void modificarDatos(String nombre , String apellido, LocalDate fechaNac, Institucion institucion) {
		this.apellido = apellido;
		this.fechaNacimiento = fechaNac;
		this.institucion = institucion;
		super.setNombre(nombre);
	}

	public void modificarAsistente(String nombre, String nickname, String correo, String apellido, LocalDate fechaNacimiento) {
		super.setNombre(nombre);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

}
