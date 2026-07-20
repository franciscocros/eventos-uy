package excepciones;
public class NoExisteEvento extends Exception{
	private static final long serialVersionUID = 2184082684030653783L;

	public NoExisteEvento(String info) {
		super(info);
	}
}
