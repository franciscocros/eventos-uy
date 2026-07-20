package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.logica.Edicion;
import main.java.logica.TipoRegistro;

class TipoRegistroTest {

	@Test
	void test() {
		Edicion edicion = new Edicion();

		TipoRegistro nuevo = new TipoRegistro("dato1","dato2",99,20,edicion);

		assertEquals(nuevo.getNombre(),"dato1");
		assertEquals(nuevo.getDescripcion(),"dato2");
		assertEquals(nuevo.getCosto(),99);
		assertEquals(nuevo.getCupos(),20);
		assertEquals(nuevo.getEdicion(),edicion);

	}

}
