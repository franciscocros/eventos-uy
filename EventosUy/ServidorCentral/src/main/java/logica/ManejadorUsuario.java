package main.java.logica;

import java.util.HashMap;
import java.util.Map;

public class ManejadorUsuario {
	private static ManejadorUsuario instance = null;

	private Map<String, Usuario> mapUsuarios;
	private Map<String, Institucion> mapInstituciones;

	/*
	 * Constructor*/
	private ManejadorUsuario() {
		mapUsuarios = new HashMap<>(); //Indizado por nickname
		mapInstituciones = new HashMap<>();
	}

	/*
	 * Devuelve instancia Singleton*/
	public static ManejadorUsuario getInstance() {
		if (instance == null) {
			instance = new ManejadorUsuario();
		}
		return instance;
	}

	public Map<String, Usuario> getUsuarios(){
		return this.mapUsuarios;
	}

	/*
	 * retorna al Usuario usuario (si existe), busca por ambas claves, nickname y correo
	 * */
	public Usuario findUsuario(String usuario) {
		Usuario buscado = this.mapUsuarios.get(usuario.replaceAll("\\s+", "").toLowerCase());
		if (buscado == null) {//Si no encuentra al Usuario por el nickname, lo busca por email
			for (Usuario iterador : this.mapUsuarios.values()) {
				if (iterador.getCorreo().equals(usuario)) {
					buscado = iterador;
					break;
				}
			}
		}
		return buscado;
	}

	public boolean existeUsuario(String nickname, String correo) {
		boolean existe = this.mapUsuarios.containsKey(nickname.replaceAll("\\s+", "").toLowerCase());
		if (!existe) {
			for (Usuario iterador : this.mapUsuarios.values()) {
				if (iterador.getCorreo().equals(correo)) {
					existe = true;
					break;
				}
			}
		}
		return existe;
	}

	public void addUsuario(Usuario usuario) {
		this.mapUsuarios.put(usuario.getNickName().replaceAll("\\s+", "").toLowerCase(), usuario);
	}

	public void addInstitucion(Institucion institucion) {
		this.mapInstituciones.put(institucion.getNombre().replaceAll("\\s+", "").toLowerCase(), institucion);
	}

	public Institucion findInstitucion(String institucion) {
		return this.mapInstituciones.get(institucion.replaceAll("\\s+", "").toLowerCase());
	}

	public Map<String, Institucion> getInstituciones(){
		return this.mapInstituciones;
	}

	public Usuario getUsuario(String usu) {
		Usuario usuario = null;
		if (existeUsuario(usu, usu)) {
			usuario = this.mapUsuarios.get(usu.replaceAll("\\s+", "").toLowerCase());
			if (usuario == null) {
				for (Usuario iterador : this.mapUsuarios.values()) {
					if (iterador.getCorreo().equals(usu)) {
						usuario = iterador;
						break;
					}
				}
			}
		}
		return usuario;
	}

	public Usuario getUsuarioNickOrMail(String usu) {
		Usuario usuario = null;
		if (existeUsuario(usu, usu)) {
			usuario = this.mapUsuarios.get(usu.replaceAll("\\s+", "").toLowerCase());
			if (usuario == null) {
				for (Usuario iterador : this.mapUsuarios.values()) {
					if (iterador.getCorreo().equals(usu)) {
						usuario = iterador;
						break;
					}
				}
			}
		}
		return usuario;
	}

	public Institucion getInstitucion(String institucion) {
		return this.mapInstituciones.get(institucion.replaceAll("\\s+", "").toLowerCase());
	}

	public boolean existeInstitucion(String inst) {
		return this.mapInstituciones.containsKey(inst.replaceAll("\\s+", "").toLowerCase());
	}

	public void liberarManejador() {
		mapUsuarios = new HashMap<>(); //Indizado por nickname
		mapInstituciones = new HashMap<>();
	}

}
