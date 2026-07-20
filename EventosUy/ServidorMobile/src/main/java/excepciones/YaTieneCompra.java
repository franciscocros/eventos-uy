package excepciones;
public class YaTieneCompra extends Exception {
	private static final long serialVersionUID = 5489499953777521467L;

	public YaTieneCompra(String info) {
		super(info);
	}
}
