package main.java.excepciones;

import jakarta.xml.ws.WebFault;

@WebFault(name = "NoExisteEdicionExcepcion")

public class NoExisteEdicionExcepcion extends Exception{
	private static final long serialVersionUID = 5583590916963503018L;

	public NoExisteEdicionExcepcion(String info) {
		super(info);
	}
}
