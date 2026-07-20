package excepciones;
public class NoEstanLosDatos extends Exception {
	private static final long serialVersionUID = 5530744799390073631L;

	public NoEstanLosDatos(String info) {
		super(info);
	}
}
