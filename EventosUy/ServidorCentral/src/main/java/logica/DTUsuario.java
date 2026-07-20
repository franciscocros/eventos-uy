package main.java.logica;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import main.java.webservice.LocalDateAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)
public class DTUsuario {
	private String nombre;
	private String nickname;
	private String correo;
	private String imagen;
	
	public DTUsuario() {
		
	}

	public DTUsuario(String nombre, String nickname, String correo) {
		this.nombre   = nombre;
		this.nickname = nickname;
		this.correo   = correo;
		this.imagen = "";
	}


	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}

	public void setCorreo(String correo) {
	    this.correo = correo;
	}

	public void setImagen(String imagen) {
	    this.imagen = imagen;
	}

	public String getImagen() {
		return this.imagen;
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
}
