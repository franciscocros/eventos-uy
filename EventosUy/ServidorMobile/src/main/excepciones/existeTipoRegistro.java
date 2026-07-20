package main.java.excepciones;

import jakarta.xml.ws.WebFault;

@WebFault(name = "existeTipoRegistro")

public class existeTipoRegistro extends Exception {
	private static final long serialVersionUID = -8384890246662942718L;

	public existeTipoRegistro(String info) {
		super(info);
	}
}
