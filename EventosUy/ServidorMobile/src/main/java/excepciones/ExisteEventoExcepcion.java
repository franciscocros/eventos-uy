package excepciones;

public class ExisteEventoExcepcion extends Exception {
	private static final long serialVersionUID = 7322919274490237073L;

	public ExisteEventoExcepcion(String info) {
        super(info);
    }
}