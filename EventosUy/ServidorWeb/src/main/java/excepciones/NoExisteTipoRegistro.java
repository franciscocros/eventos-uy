package excepciones;


public class NoExisteTipoRegistro extends Exception {
	private static final long serialVersionUID = -1243491360896798685L;

	public NoExisteTipoRegistro(String info) {
		super(info);
	}
}
