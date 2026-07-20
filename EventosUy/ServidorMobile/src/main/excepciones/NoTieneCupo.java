package main.java.excepciones;

import jakarta.xml.ws.WebFault;

@WebFault(name = "NoTieneCupo")


public class NoTieneCupo extends Exception {
	private static final long serialVersionUID = -8077606013869025267L;

	public NoTieneCupo(String info) {
		super(info);
	}
}
