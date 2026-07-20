package main.java.excepciones;

import jakarta.xml.ws.WebFault;

@WebFault(name = "ExisteCategoriaFault")

public class ExisteCategoriaExcepcion extends Exception{
	private static final long serialVersionUID = 1L;

	public ExisteCategoriaExcepcion(String info) {
        super(info);
    }
}
