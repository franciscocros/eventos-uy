package main.java.excepciones;

import jakarta.xml.ws.WebFault;

@WebFault(name = "ExisteEdicion")
public class ExisteEdicion extends Exception {
	private static final long serialVersionUID = 8756110125082047150L;

	public ExisteEdicion(String mensaje) {
        super(mensaje);
    }
}
