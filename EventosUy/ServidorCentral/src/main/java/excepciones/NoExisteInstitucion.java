package main.java.excepciones;

import jakarta.xml.ws.WebFault;

@WebFault(name = "NoExisteInstitucion")

public class NoExisteInstitucion extends Exception{
	private static final long serialVersionUID = 7070714021990311017L;

	public NoExisteInstitucion(String info) {
		super(info);
	}
}
