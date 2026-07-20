package excepciones;
public class SuperaCantidadGratuitos extends Exception {
	private static final long serialVersionUID = 5797135432103061148L;

	public SuperaCantidadGratuitos(String info) {
		super(info);
	}
}
