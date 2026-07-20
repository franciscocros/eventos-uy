package main.java.logica;
public class Fabrica {
	private  static Fabrica instance;

	private Fabrica() {
	}

	public static Fabrica getInstance() {
		if (instance == null)  {
			instance = new Fabrica();
		}
		return instance;
	}

	public IControladorUsuario getIControladorUsuario() {
		return new ControladorUsuario();
	}

	public IControladorEvento getIControladorEvento() {
		return new ControladorEvento();
	}

}
