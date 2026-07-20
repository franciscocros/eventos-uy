package excepciones;
public class ExisteInstitucionExcepcion extends Exception{
	private static final long serialVersionUID = 8414536908095816328L;

	public ExisteInstitucionExcepcion(String info) {
		super(info);
	}
}
