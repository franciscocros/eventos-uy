package excepciones;
public class NoExisteUsuarioExcepcion extends Exception {
	private static final long serialVersionUID = -6369883974233696915L;

	public NoExisteUsuarioExcepcion(String info) {
		super(info);
	}
}
