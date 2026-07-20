package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.java.logica.DTPatrocinio;
import main.java.logica.ENivelPatrocinio;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DTPatrocinioTest {
	DTPatrocinio drp;
	@BeforeAll
	void setUp() {
		LocalDate fecha = LocalDate.of(1, 2, 3);
		float aux = 11.22f;
		this.drp = new DTPatrocinio(aux, "aaa", fecha, 2, ENivelPatrocinio.PLATINO);
	}

	@Test
	void test() {
		LocalDate fecha = LocalDate.of(1, 2, 3);
		assertEquals("aaa",drp.getCodigo());
		assertEquals(11.22f,drp.getAporte());
		assertEquals(fecha,drp.getDTFecha());
		assertEquals(2,drp.getCantGratis());
		assertEquals(ENivelPatrocinio.PLATINO,drp.getNivelPatrocinio());
	}

}
