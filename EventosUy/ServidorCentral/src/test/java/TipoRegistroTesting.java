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

import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.logica.Categoria;
import main.java.logica.Ciudad;
import main.java.logica.DTEdicion;
import main.java.logica.DTTipoRegistro;
import main.java.logica.Edicion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;
import main.java.logica.ManejadorEvento;
import main.java.logica.TipoRegistro;

public class TipoRegistroTesting  {

	static Edicion edicion;
	static TipoRegistro tipo1;
	static TipoRegistro tipo2;
    @BeforeAll
    static void setUp() throws ExisteEdicion {
    	Fabrica f = Fabrica.getInstance();
    	IControladorEvento ICE = f.getIControladorEvento();
    	IControladorUsuario ICU = f.getIControladorUsuario();
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
		ICE.altaCiudad("Montevideo", "Uruguay");
		ICE.altaCiudad("Canelones", "Uruguay");

		try {
			ICE.addEvento("EventoTest", "descTest", "siglaTest", fecha, cats);
		} catch (ExisteEventoExcepcion e) {
			e.printStackTrace();
		}

		//Evento event = ME.findEvento("EventoTest");
		//Organizador organizador = new Organizador("dato3","dato1","dato2","dato4","dato5");


		try {
			ICU.crearOrganizador("dato3", "dato1", "dato2", "dato4", "dato5");
		} catch (ExisteUsuarioExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ICE.altaEdicion("EventoTest", "dato1", "NombreTest",  "SIG","Montevideo",LocalDate.now(),LocalDate.now(),LocalDate.now() );
		edicion = ME.findEvento("EventoTest").findEdicion("NombreTest");
		try {
			ICE.addTipoRegistro("EventoTest", "NombreTest", "t1", "t1", 10, 10);
			ICE.addTipoRegistro("EventoTest", "NombreTest", "t2", "t2", 99, 99);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 tipo1 = edicion.findTipoRegistro("t1");
		 tipo2 = edicion.findTipoRegistro("t2");
    }

    @Test
    void testConstructorYGetters() {
        assertEquals("NombreTest", edicion.getNombre());
        assertEquals("SIG", edicion.getSigla());
        assertEquals(LocalDate.now(), edicion.getInicio());
        assertEquals(LocalDate.now(), edicion.getAlta());
        assertEquals(LocalDate.now(), edicion.getFin());
        assertEquals("Montevideo", edicion.getCiudad().getNombre());
        assertEquals("EventoTest", edicion.getEvento().getNombre());
        assertEquals("dato3", edicion.getOrganizador().getNombre());
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
    	Fabrica f = Fabrica.getInstance();
    	IControladorEvento ICE = f.getIControladorEvento();
    	assertTrue(edicion.getMisTipoRegistro().containsKey("t1"));
        Exception ex = assertThrows(Exception.class, () -> {
        	ICE.addTipoRegistro("EventoTest", "NombreTest", "t1", "t1", 10, 10);
        });

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
    	Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();
		String nom = ICE.getEdicion("EventoTest", "NombreTest").getNombre();
		assertEquals("NombreTest",nom);
    }
    @Test
    void TestListarEdiciones() {
    	Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();
		Set<DTEdicion> dtedis = ICE.listarEdiciones("EventoTest");
		DTEdicion dte = edicion.getDT();
		boolean res = false;
		for (DTEdicion aux : dtedis) {
			res = res | aux.getNombre() == dte.getNombre() ;
		}
		assertTrue(res);
    }
    @Test
    void TestListaTipoRegistro() {
    	Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();
		Set<DTTipoRegistro> dtedis = ICE.listarTipoRegistro("EventoTest",edicion.getNombre());
		boolean a2 = false;
		boolean a1 = false;

		for (DTTipoRegistro aux : dtedis) {
			a1 = a1 | aux.getNombre() == tipo1.getNombre();
			a2 = a2 | aux.getNombre() == tipo2.getNombre();

		}
		assertTrue(a1&a2);
    }
    @Test
    void TestFindYGetDT() {
    	Fabrica f = Fabrica.getInstance();
		IControladorEvento ICE = f.getIControladorEvento();
    	DTTipoRegistro salida = ICE.getTipoRegistro("EventoTest", "NombreTest","t1" );
    	DTTipoRegistro esperado = tipo1.getDT();
    	assertEquals(esperado.getNombre(),salida.getNombre());
    }
}
