package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import main.java.logica.Asistente;
import main.java.logica.Institucion;
import main.java.logica.Organizador;

class UsuarioTest {

	@Test //Constructor Asistente, getters
	void asistenteTest() {
		LocalDate fecha = LocalDate.of(1, 2, 3);
		Asistente asistente = new Asistente("dato1","dato2","dato3","dato4",fecha);

		Institucion inst = new Institucion("dato1","dato2","dato3");
		asistente.setInstitucion(inst);

		assertEquals(inst,asistente.getInstitucion());
		assertEquals("dato1",asistente.getNombre());
		assertEquals("dato2",asistente.getNickName());
		assertEquals("dato3",asistente.getCorreo());
		assertEquals("dato4",asistente.getApellido());
		assertEquals(asistente.getFechaNacimiento(),fecha);


		//Setter de nombre
		asistente.setNombre("dato05");
		assertEquals("dato05",asistente.getNombre());


		//ModificarDatos
		Institucion inst2 = new Institucion("dato6","dato7","dato8");
		asistente.setInstitucion(inst);
		LocalDate fecha2 = LocalDate.of(1, 2, 3);
		asistente.modificarDatos("dato5","dato6", fecha2, inst2);

		assertEquals(inst2,asistente.getInstitucion());
		assertEquals("dato5",asistente.getNombre());
		assertEquals("dato6",asistente.getApellido());

		asistente.modificarAsistente("dato1","dato2","dato3","dato4", fecha);
		assertEquals("dato1",asistente.getNombre());
		assertEquals("dato4",asistente.getApellido());

		assertEquals(true,asistente.tieneInstitucion());


	}

	@Test //Constructor Asistente, getters
	void organizadorTest() {
		Organizador organizador = new Organizador("dato1","dato2","dato3","dato4","dato5");

		assertEquals("dato1",organizador.getNombre());
		assertEquals("dato2",organizador.getNickName());
		assertEquals("dato3",organizador.getCorreo());
		assertEquals("dato4",organizador.getDescripcion());
		assertEquals("dato5",organizador.getPaginaWeb());

	}



}
