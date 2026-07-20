package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.java.logica.DTUsuario;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DTUsuarioTest {
	DTUsuario dtu;
	@BeforeAll
	void setUp() {

	}

	@Test
	void test() {
		dtu = new DTUsuario("nom", "nick", "correo");
		assertEquals("nom",dtu.getNombre());
		assertEquals("nick",dtu.getNickName());
		assertEquals("correo",dtu.getCorreo());
	}
}