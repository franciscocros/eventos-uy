package main.java.excepciones;
import jakarta.xml.ws.WebFault;

@WebFault(name = "YaTienePatrocinioException")

public class YatienePatrocinioException extends Exception {
	private static final long serialVersionUID = 1538863054980417495L;

	public YatienePatrocinioException(String info) {
		super(info);
	}

}
