package main.java.logica;
import main.java.webservice.LocalDateAdapter;
import java.time.LocalDate;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(LocalDateAdapter.class)
public class DTAsistente extends DTUsuario {
	private String apellido;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate fechaNacimiento;
	private List<DTCompra> compras;
	private String institucion;

	public DTAsistente() {
		
	}

	public DTAsistente(String nombre, String nickname, String correo, String apellido, LocalDate fechaNacimiento, List<DTCompra> compras, Institucion institucion) {
		super(nombre, nickname, correo);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.compras = compras;
		if (institucion==null) {
			this.institucion = "";
		}else {
			this.institucion = institucion.getNombre();
		}
	}
	// temporal
	/*public DTAsistente(String nombre, String nickname, String correo,String apellido,DTFecha fechaNacimiento,Institucion institucion) {
		super(nombre, nickname, correo);
		this.apellido = apellido;
		this.fechaNacimiento = LocalDate.of(fechaNacimiento.getAnio(),fechaNacimiento.getMes(),fechaNacimiento.getdia() );
		if (institucion==null) {
			this.institucion = "";
		}else {
			this.institucion = institucion.getNombre();
		}
	}*/

	public void setApellido(String apellido){
		this.apellido= apellido;	
		}
	public void setFechaNacimiento(LocalDate fnac){
		this.fechaNacimiento= fnac;	
		}
	public void setInstitucion(String inst){
		this.institucion= inst;	
		}
	public String getApellido() {
		return this.apellido;
	}

	public LocalDate getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	public String getInstitucion() {
		if (this.institucion == null) {
			return "";
		}
		return this.institucion;
	}
	public List<DTCompra> getCompra(){
		return compras;
	}

}
