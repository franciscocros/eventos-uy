package excepciones;
public class ExisteUsuarioEnEdicionException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExisteUsuarioEnEdicionException(String info) {
		super(info);
	}
}
