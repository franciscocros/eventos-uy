package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import main.java.excepciones.ExisteEventoExcepcion;
import main.java.logica.Categoria;
import main.java.logica.DTEvento;
import main.java.logica.Evento;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.ManejadorEvento;



 class EventoTest {

	@Test
	void TestAltaEvento() throws ExisteEventoExcepcion {
		ManejadorEvento ME = ManejadorEvento.getInstance();
		Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();

		LocalDate fecha = LocalDate.of(2025, 8, 24);
		Set<String> cats = new HashSet<>();
		Set<Categoria> catsObj = new HashSet<>();
		Categoria cat1 = new Categoria("cat1");
		Categoria cat2 = new Categoria("cat2");
		catsObj.add(cat1);
		catsObj.add(cat2);
		ME.addCategoria(cat1);
		ME.addCategoria(cat2);
		cats.add("cat2");
		cats.add("cat1");

		ICE.addEvento("nombreTest", "descTest", "siglaTest", fecha, cats);

		Evento event = ME.findEvento("nombreTest");

		assertEquals("nombreTest", event.getNombre());
		assertEquals("descTest", event.getDescipcion());
		assertEquals("siglaTest", event.getSigla());
		assertEquals(fecha, event.getFechaAlta());

		event.setNombre("nombre2");
		event.setDescipcion("desc2");
		event.setSigla("sigla2");
		LocalDate fecha2 = LocalDate.of(2024, 10, 10);
		event.setFechaAlta(fecha2);

		assertEquals("nombre2", event.getNombre());
		assertEquals("desc2", event.getDescipcion());
		assertEquals("sigla2", event.getSigla());
		assertEquals(fecha2, event.getFechaAlta());

	}

	@Test
	void TestDTEventos() throws ExisteEventoExcepcion {
		ManejadorEvento ME = ManejadorEvento.getInstance();
		Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();

		 //Evento 1:
		LocalDate fecha = LocalDate.of(2025, 8, 24);
		Set<String> cats = new HashSet<>();
		Set<Categoria> catsObj = new HashSet<>();
		Categoria cat1 = new Categoria("cat1");
		Categoria cat2 = new Categoria("cat2");
		catsObj.add(cat1);
		catsObj.add(cat2);
		ME.addCategoria(cat1);
		ME.addCategoria(cat2);
		cats.add("cat2");
		cats.add("cat1");

		ICE.addEvento("nombreEv1", "descEv1", "siglaEv1", fecha, cats);


		//Creo los DTEvento de Evento1
		DTEvento dtEvento = ICE.getEvento("nombreEv1");
		assertEquals("nombreEv1", dtEvento.getNombre());
		assertEquals("descEv1", dtEvento.getDescipcion());
		assertEquals("siglaEv1", dtEvento.getSigla());


	}
	@Test
	void TestListarEventos() {
		 //Evento 1:
		ManejadorEvento ME = ManejadorEvento.getInstance();
		Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();


		LocalDate fecha = LocalDate.of(2025, 8, 24);
		Set<String> cats = new HashSet<>();
		Set<Categoria> catsObj = new HashSet<>();
		Categoria cat1 = new Categoria("cat1");
		Categoria cat2 = new Categoria("cat2");
		catsObj.add(cat1);
		catsObj.add(cat2);
		ME.addCategoria(cat1);
		ME.addCategoria(cat2);
		cats.add("cat2");
		cats.add("cat1");
		Set<DTEvento> eventosSet = ICE.listarEventos();
		int size = eventosSet.size();
		try {
			ICE.addEvento("nombreEv1000", "descEv1", "siglaEv1", fecha, cats);
			ICE.addEvento("nombreEv2000", "descEv2", "siglaEv2", fecha, cats);
		} catch (ExisteEventoExcepcion e) {

		}

		eventosSet = ICE.listarEventos();
		assertTrue(size+2==eventosSet.size());
	}




}
