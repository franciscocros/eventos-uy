package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.logica.DTCiudad;

class DTCiudadtest {

	@Test
	void test() {
		DTCiudad ciudad = new DTCiudad("dato1","dato2");
		ciudad.setNombre("nombre");
		ciudad.setPais("pais");
		DTCiudad ciud = new DTCiudad();
		assertEquals("nombre",ciudad.getNombre());
		assertEquals("pais",ciudad.getPais());
		
	}

}
