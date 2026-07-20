package main.java.excepciones;
import jakarta.xml.ws.WebFault;

@WebFault(name = "existeTipoRegistroException")
public class ExisteTipoRegistroException extends Exception {

	private static final long serialVersionUID = 1L;
	public ExisteTipoRegistroException(String info) {
		super(info);
	}
}
