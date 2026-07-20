package excepciones;
public class ExisteUsuarioExcepcion extends Exception {
	private static final long serialVersionUID = 837741399379679488L;

	public ExisteUsuarioExcepcion(String info) {
		super(info);
	}
}