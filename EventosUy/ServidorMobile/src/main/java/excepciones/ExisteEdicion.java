package excepciones;

public class ExisteEdicion extends Exception {
	private static final long serialVersionUID = 8756110125082047150L;

	public ExisteEdicion(String mensaje) {
        super(mensaje);
    }
}
