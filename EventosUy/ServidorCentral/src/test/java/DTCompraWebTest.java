package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.java.logica.DTCompraWeb;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DTCompraWebTest {
	DTCompraWeb dtCompraWeb;
	@BeforeAll
	void setUp() {
		LocalDate fechaCompra = LocalDate.of(1, 2, 3);
		this.dtCompraWeb = new DTCompraWeb("idCompWeb", fechaCompra, 100, "asistenteComp", "edicionComp", "tipoRegComp", "eventoComp", "imgComp", false);
	}

	@Test
	void test() {
		LocalDate fechaCompra = LocalDate.of(1, 2, 3);
		assertEquals("idCompWeb",dtCompraWeb.getId());
		assertEquals(fechaCompra,dtCompraWeb.getFecha());
		assertEquals(100,dtCompraWeb.getCosto());
		assertEquals("asistenteComp",dtCompraWeb.getAsistente());
		assertEquals("edicionComp", dtCompraWeb.getEdicion());
		assertEquals("tipoRegComp",dtCompraWeb.getTipoRegistro());
		assertEquals("eventoComp",dtCompraWeb.getEvento());
		assertEquals("imgComp",dtCompraWeb.getImagenEdicion());
		assertEquals(false,dtCompraWeb.isAceptada());
	}

}
