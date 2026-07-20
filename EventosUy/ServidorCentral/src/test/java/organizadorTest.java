package  test.java;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.java.excepciones.ExisteCategoriaExcepcion;
import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.logica.DTEdicion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class organizadorTest {
	Fabrica f = Fabrica.getInstance();
	IControladorEvento ICE = f.getIControladorEvento();
	IControladorUsuario ICU = f.getIControladorUsuario();
	 boolean datosCargados;
	 @BeforeAll
     void setUp() throws ExisteUsuarioExcepcion, ExisteCategoriaExcepcion, ExisteEventoExcepcion, ExisteEdicion {
	        Fabrica fabrica = Fabrica.getInstance();
	        ICU = fabrica.getIControladorUsuario();
	        ICE = fabrica.getIControladorEvento();


	        ICU.liberarManejadorUsuario();
	        ICE.liberarManejadorEvento();

	        // Fechas base
	        LocalDate fechaAltaBase = LocalDate.of(2025, 1, 10);
	        LocalDate fechaInicioBase = LocalDate.of(2025, 3, 1);
	        LocalDate fechaFinBase = LocalDate.of(2025, 3, 5);

	        // Datos base

	        // 1) Un organizador
	        ICU.crearOrganizador("Org Nombre", "orgNick", "org@correo", "desc org", "web org");

	        // 2) Una ciudad
	        ICE.altaCiudad("Montevideo", "Uruguay");

	        // 3) Una categoría
	        ICE.altaCategoria("catBase");
	        ICE.altaCategoria("catDistinto");


	        // 4) Un evento con la categoría
	        Set<String> cats = new HashSet<>();
	        cats.add("catBase");
	        ICE.addEvento("EventoBase", "desc evento", "EVB", fechaAltaBase, cats);

	        ICE.altaEdicion("EventoBase", "orgNick", "EdT123", "E2TR", "Montevideo",
	                fechaAltaBase, fechaFinBase, fechaInicioBase);
	 }
	 @Test
	 void listarEdiciones() {
		 Set<DTEdicion> edis = ICU.getSetEdiciones("orgNick");
		 Set<String> nomEdiciones= new HashSet<>();
		 for (DTEdicion edi : edis) {
			 nomEdiciones.add(edi.getNombre());
		 }
		 assertTrue(nomEdiciones.contains("EdT123"));
	 }
	 @Test
	void findOrganizadorTOEvento() {
		 String eve = ICU.findEvento("orgNick", "EdT123");
		 assertEquals("EventoBase",eve);
	}
}
