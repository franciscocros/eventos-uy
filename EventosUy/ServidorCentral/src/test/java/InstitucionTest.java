package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import main.java.logica.Asistente;
import main.java.logica.ENivelPatrocinio;
import main.java.logica.Edicion;
import main.java.logica.Institucion;
import main.java.logica.Patrocinio;
import main.java.logica.TipoRegistro;

class InstitucionTest {

	@Test
	void test() {
		Institucion inst = new Institucion("dato1","dato2","dato3");

		LocalDate fecha = LocalDate.of(1, 2, 3);
		Asistente asistente = new Asistente("dato1","dato2","dato3","dato4",fecha);
		inst.addAsistente(asistente);


		Edicion edicion = new Edicion();
		TipoRegistro tiporeg = new TipoRegistro("dato1","dato2",99,20,edicion);

		Patrocinio patrocinio = new Patrocinio(99,"dato2",fecha,20,ENivelPatrocinio.ORO,tiporeg,inst);
		inst.addPatrocinio(patrocinio);

		assertTrue(inst.getPatrocinios().size() > 0 );
		assertEquals(asistente,inst.getAsistentes().get(asistente.getNickName()));
		assertEquals("dato1",inst.getNombre());
		assertEquals("dato2",inst.getDescripcion());
		assertEquals("dato3",inst.getPaginaWeb());
	}

}
