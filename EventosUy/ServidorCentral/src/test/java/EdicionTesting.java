package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.logica.Categoria;
import main.java.logica.Ciudad;
import main.java.logica.DTEdicion;
import main.java.logica.Edicion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;
import main.java.logica.ManejadorEvento;
import main.java.logica.TipoRegistro;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EdicionTesting  {
	 Edicion edicion;
	 TipoRegistro tipo1;
	 TipoRegistro tipo2;
	 Fabrica f = Fabrica.getInstance();
	IControladorEvento ICE = f.getIControladorEvento();
    @BeforeAll
     void setUp() throws ExisteEdicion {
    	ManejadorEvento ME = ManejadorEvento.getInstance();
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
		Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();
		IControladorUsuario ICU = f.getIControladorUsuario();
		ICE.altaCiudad("Montevideo", "Uruguay");
		ICE.altaCiudad("Canelones", "Uruguay");

		try {
			ICE.addEvento("EventoTest1", "descTest", "siglaTest", fecha, cats);
		} catch (ExisteEventoExcepcion e) {
		}

		//Evento event = ME.findEvento("EventoTest");
		//Organizador organizador = new Organizador("dato3","dato1","dato2","dato4","dato5");


		try {
			ICU.crearOrganizador("dato3", "dato1", "dato2", "dato4", "dato5");
		} catch (ExisteUsuarioExcepcion e) {
			// TODO Auto-generated catch block
		}
		ICE.altaEdicion("EventoTest1", "dato1", "NombreTest1","SIG" , "Montevideo",LocalDate.now(),LocalDate.now(),LocalDate.now() );


        edicion = ME.findEvento("EventoTest1").findEdicion("NombreTest1");

        tipo1 = new TipoRegistro("t1", "tp1", 10, 10, edicion);
        tipo2 = new TipoRegistro("t2", "tp2", 99, 99, edicion);
    }

    @Test
    void testConstructorYGetters() {
        assertEquals("NombreTest1", this.edicion.getNombre());
        assertEquals("SIG", this.edicion.getSigla());
        assertEquals(LocalDate.now(), this.edicion.getInicio());
        assertEquals(LocalDate.now(), this.edicion.getAlta());
        assertEquals(LocalDate.now(), this.edicion.getFin());
        assertEquals("Montevideo", this.edicion.getCiudad().getNombre());
        assertEquals("EventoTest1", this.edicion.getEvento().getNombre());
        assertEquals("dato3", this.edicion.getOrganizador().getNombre());
    }

    @Test
    void testSetters() {
        edicion.setNombre("NuevoNombre");
        edicion.setSigla("NUEVA");
        edicion.setCiudad(new Ciudad("Canelones", "Uruguay"));
        edicion.setAlta(LocalDate.now().plusDays(1));
        edicion.setInicio(LocalDate.now().plusDays(1));
        edicion.setFin(LocalDate.now().plusDays(1));
        assertEquals("NuevoNombre", edicion.getNombre());
        assertEquals("NUEVA", edicion.getSigla());
        assertEquals("Canelones", edicion.getCiudad().getNombre());
        assertEquals(LocalDate.now().plusDays(1), edicion.getInicio());
        assertEquals(LocalDate.now().plusDays(1), edicion.getAlta());
        assertEquals(LocalDate.now().plusDays(1), edicion.getFin());
    }

    @Test
    void testAddTipoRegistroDuplicado() throws Exception {
    	edicion.addTipoRegistro(tipo1);
        assertTrue(edicion.getMisTipoRegistro().containsKey("t1"));
        assertEquals(tipo1, edicion.findTipoRegistro("t1"));
        Exception ex = assertThrows(Exception.class, () -> {
            edicion.addTipoRegistro(new TipoRegistro("t1", "tp1", 10, 10, edicion));
        });
        System.out.println(ex);
        assertTrue(ex.getMessage().contains("Ya existe este tipo de registro  para esta edicion"));
    }

    @Test
    void testFindTipoRegistroNoExiste() {
        assertNull(edicion.findTipoRegistro("Inexistente"));
    }

    @Test
    void testSetMisTipoRegistro() {
        HashMap<String, TipoRegistro> nuevoMapa = new HashMap<>();
        nuevoMapa.put(tipo2.getNombre(), tipo2);
        edicion.setMisTipoRegistro(nuevoMapa);

        assertEquals(1, edicion.getMisTipoRegistro().size());
        assertEquals(tipo2, edicion.findTipoRegistro("t2"));
    }

    @Test
    void testGetEdicion() {

		String nom = ICE.getEdicion("EventoTest1", "NombreTest1").getNombre();
		assertEquals("NombreTest1",nom);
    }
    @Test
    void TestListarEdiciones() {

		Set<DTEdicion> dtedis = ICE.listarEdiciones("EventoTest1");
		DTEdicion dte = edicion.getDT();
		boolean res = false;
		for (DTEdicion aux : dtedis) {
			res = res | aux.getNombre() == dte.getNombre() ;
		}
		assertTrue(res);
    }
}
