package excepciones;
public class NoExisteInstitucion extends Exception{
	private static final long serialVersionUID = 7070714021990311017L;

	public NoExisteInstitucion(String info) {
		super(info);
	}
}
