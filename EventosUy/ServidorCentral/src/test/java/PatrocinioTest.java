package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import main.java.logica.ENivelPatrocinio;
import main.java.logica.Edicion;
import main.java.logica.Institucion;
import main.java.logica.Patrocinio;
import main.java.logica.TipoRegistro;

class PatrocinioTest {

	@Test
	void test() {
		LocalDate fecha = LocalDate.of(1, 2, 3);
		Institucion inst = new Institucion("dato1","dato2","dato3");

		Edicion edicion = new Edicion();
		TipoRegistro tiporeg = new TipoRegistro("dato1","dato2",99,20,edicion);

		Patrocinio patrocinio = new Patrocinio(99,"dato2",fecha,20,ENivelPatrocinio.ORO,tiporeg,inst);

		assertEquals(patrocinio.getAporte(),99);
		assertEquals(patrocinio.getCodigo(),"dato2");
		assertEquals(patrocinio.getDTFecha(),fecha);

		assertEquals(patrocinio.getCantGratis(),20);
		assertEquals(patrocinio.getNivelPatrocinio(),ENivelPatrocinio.ORO);
		assertEquals(patrocinio.getTipoRegistro(),tiporeg);
		assertEquals(patrocinio.getInstitucion(),inst);


	}

}
