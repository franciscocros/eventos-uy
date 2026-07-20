package excepciones;
public class FechaNacimientoPosteriorExcepcion extends Exception{
	private static final long serialVersionUID = 8498709300508756796L;

	public FechaNacimientoPosteriorExcepcion(String info) {
        super(info);
    }
}

