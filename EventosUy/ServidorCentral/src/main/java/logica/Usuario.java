package main.java.logica;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
	private String nombre;
	private String nickname;
	private String correo;
	private String contrasenia;
	private String imagen;
	private Set<Usuario> seguidos;
	private Set<Usuario> seguidores;

	public Usuario(String nombre, String nickname, String correo) {
		this.nombre   = nombre;
		this.nickname = nickname;
		this.correo   = correo;
		this.imagen = "";
		this.seguidos = new HashSet<>();
		this.seguidores = new HashSet<>();
		
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getNickName() {
		return this.nickname;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public String getImagen() {
		return this.imagen;
	}
	
	public void addSeguido(Usuario usu) {
		this.seguidos.add(usu);
	}
  
	public void addSeguidor(Usuario usu) {
		this.seguidores.add(usu);
	}
	
	public void removeSeguidor(Usuario usu) {
		this.seguidores.remove(usu);
	}
	
	public void removeSeguido(Usuario usu) {
		this.seguidos.remove(usu);
	}
	
	public Set<Usuario> getSeguidos(){
	    return this.seguidos;
	}
	
	public Set<Usuario> getSeguidores(){
		return this.seguidores;
	}
}
