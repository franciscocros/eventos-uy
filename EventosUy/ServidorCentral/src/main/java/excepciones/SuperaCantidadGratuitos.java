package main.java.excepciones;

import jakarta.xml.ws.WebFault;

@WebFault(name = "SuperaCantidadGratuitos")

public class SuperaCantidadGratuitos extends Exception {
	private static final long serialVersionUID = 5797135432103061148L;

	public SuperaCantidadGratuitos(String info) {
		super(info);
	}
}
