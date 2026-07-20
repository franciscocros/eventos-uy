package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.logica.Ciudad;

public class CiudadTesting {
	private Ciudad ciudad;
    private void setUp() {
    	ciudad = new Ciudad("Canelones", "Uruguay");
    }

    @Test
    void testConstructorYGetters() {
    	setUp();
        assertEquals("Canelones", ciudad.getNombre());
        assertEquals("Uruguay", ciudad.getPais());
    }



}
