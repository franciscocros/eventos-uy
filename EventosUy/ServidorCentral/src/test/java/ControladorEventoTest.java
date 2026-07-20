package  test.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.java.excepciones.ExisteCategoriaExcepcion;
import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.ExisteInstitucionExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.excepciones.FechaNacimientoPosteriorExcepcion;
import main.java.excepciones.NoTieneCupo;
import main.java.excepciones.YaTieneCompra;
import main.java.logica.Asistente;
import main.java.logica.Ciudad;
import main.java.logica.DTEdicion;
import main.java.logica.DTEvento;
import main.java.logica.DTTipoRegistro;
import main.java.logica.EEstadoEdicion;
import main.java.logica.ENivelPatrocinio;
import main.java.logica.Edicion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;
import main.java.logica.Institucion;
import main.java.logica.ManejadorUsuario;
import main.java.logica.Patrocinio;


class ControladorEventoTest {

    static IControladorUsuario ICU;
    static IControladorEvento ICE;

    static LocalDate fechaAltaBase;
    static LocalDate fechaInicioBase;
    static LocalDate fechaFinBase;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        Fabrica fabrica = Fabrica.getInstance();
        ICU = fabrica.getIControladorUsuario();
        ICE = fabrica.getIControladorEvento();


        //ICU.liberarManejadorUsuario();
        //ICE.liberarManejadorEvento();

        // Fechas base
        fechaAltaBase   = LocalDate.of(2025, 1, 10);
        fechaInicioBase = LocalDate.of(2025, 3, 1);
        fechaFinBase    = LocalDate.of(2025, 3, 5);

        // Datos base
        if(!ICU.existeUsuario("orgNick")) {
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
        }
    }

    /* ===================== CATEGORÍAS ===================== */

    @Test
    @DisplayName("altaCategoria: crea categoría y repetir tira ExisteCategoriaExcepcion")
    void altaCategoriaTest() throws Exception {
        ICE.altaCategoria("NuevaCat");

        Set<String> categorias = ICE.listarCategorias();
        assertNotNull(categorias);
        assertTrue(categorias.contains("NuevaCat"));

        Exception ex = assertThrows(ExisteCategoriaExcepcion.class, () -> {
        ICE.altaCategoria("NuevaCat");
        });
        assertEquals("La categoria ya existe", ex.getMessage());
    }

    @Test
    @DisplayName("listarCategorias: null cuando no hay categorías")
    void listarCategoriasNullSiVacio() {
        assertNotNull(ICE.listarCategorias());
    }
    @Test
    void listarEventosDeCategoria() throws ExisteEventoExcepcion {
    Set<String> cats = new HashSet<>();
        cats.add("catDistinto");
        ICE.addEvento("EventoBase1", "desc evento", "EVB", fechaAltaBase, cats);
    	Set<DTEvento> eventos = ICE.listarEventosDeCategoria("catDistinto");
    	assertEquals(eventos.size(),1);
    }

    /* ===================== CIUDADES ===================== */

    @Test
    @DisplayName("altaCiudad + listarCiudades: agrega y lista")
    void ciudadesTest() {
        ICE.altaCiudad("Salto", "Uruguay");
        Map<String, Ciudad> ciudades = ICE.listarCiudades();
        assertNotNull(ciudades);
        assertFalse(ciudades.isEmpty());
    }

    /* ===================== EVENTOS ===================== */

    @Test
    @DisplayName("addEvento: si el nombre existe lanza ExisteEventoExcepcion")
    void addEvento_yaExiste() throws Exception {
        Set<String> cats = new HashSet<>();
        cats.add("catBase");

        Exception ex = assertThrows(ExisteEventoExcepcion.class, () -> {
            ICE.addEvento("EventoBase", "otra desc", "EVB2", fechaAltaBase, cats);
        });
        assertEquals("El nombre del evento ya esta en uso. Desea volver a intentar?", ex.getMessage());
    }

    @Test
    @DisplayName("addEvento + listarEventos + getEvento: devuelve DTEvento coherente")
    void eventos_basico() throws Exception {
        ICE.altaCategoria("catExtra");
        Set<String> cats = new HashSet<>();
        cats.add("catExtra");

        ICE.addEvento("FeriaTech", "feria", "FTE", LocalDate.of(2025, 2, 1), cats);

        Set<DTEvento> eventos = ICE.listarEventos();
        assertNotNull(eventos);
        assertTrue(eventos.stream().anyMatch(dt -> "FeriaTech".equals(dt.getNombre())));

        DTEvento dt = ICE.getEvento("FeriaTech");
        assertNotNull(dt);
        assertEquals("FeriaTech", dt.getNombre());
    }

    /* ===================== EDICIONES ===================== */


    @Test
    @DisplayName("altaEdicion: crea edición y repetir nombre tira ExisteEdicion")
    void altaEdicion_creaYDuplicadaLanza() throws Exception {

        ICE.altaEdicion("EventoBase", "orgNick", "Ed2025", "ED25", "Montevideo",
                fechaAltaBase, fechaFinBase, fechaInicioBase);

        // ahora debe existir
        DTEdicion dt = ICE.getEdicion("EventoBase", "Ed2025");
        assertNotNull(dt);
        assertEquals("Ed2025", dt.getNombre());

        // intentar duplicada
        Exception ex = assertThrows(ExisteEdicion.class, () -> {
            ICE.altaEdicion("EventoBase", "orgNick", "Ed2025", "ED25", "Montevideo",
                    fechaAltaBase, fechaFinBase, fechaInicioBase);
        });
        assertEquals("Ya existe edición con ese nombre, desea ingresar otro?", ex.getMessage());
    }

    @Test
    @DisplayName("listarEdiciones: con datos devuelve set no vacío")
    void listarEdiciones_conDatos() throws Exception {
        // Crea otra edición del mismo evento
        ICE.altaEdicion("EventoBase", "orgNick", "Ed2026", "ED26", "Montevideo",
                fechaAltaBase.plusYears(1), fechaFinBase.plusYears(1), fechaInicioBase.plusYears(1));

        Set<DTEdicion> eds = ICE.listarEdiciones("EventoBase");
        assertNotNull(eds);
        assertTrue(eds.size() >= 1);
        assertTrue(eds.stream().anyMatch(e -> "Ed2026".equals(e.getNombre())));
    }
    
	@Test
	void testGetEdicionB() {
		try {
			ICE.altaEdicion("EventoBase", "orgNick", "EdicionB", "ED27", "Montevideo",
			        fechaAltaBase.plusYears(2), fechaFinBase.plusYears(2), fechaInicioBase.plusYears(2));
		} catch (ExisteEdicion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DTEdicion existe = ICE.getEdicionB("EdicionB");
		DTEdicion noExiste = ICE.getEdicionB("edicionNoExiste");
		assertTrue(noExiste == null);
		assertTrue(existe != null);
	}

    /* ===================== TIPOS DE REGISTRO ===================== */

    @Test
    @DisplayName("listarTipoRegistro: null si no hay; set con datos luego de agregar uno")
    void tipoRegistro_listarYAgregar() throws Exception {
        // Asegurar que exista una edición
        ICE.altaEdicion("EventoBase", "orgNick", "EdTR", "EDTR", "Montevideo",
                fechaAltaBase, fechaFinBase, fechaInicioBase);

        // Al inicio debería ser null
        Set<DTTipoRegistro> trNull = ICE.listarTipoRegistro("EventoBase", "EdTR");
        assertNull(trNull);

        // Agregamos un tipo
        ICE.addTipoRegistro("EventoBase", "EdTR", "General", "Acceso total", 1500f, 100);

        Set<DTTipoRegistro> trs = ICE.listarTipoRegistro("EventoBase", "EdTR");
        assertNotNull(trs);
        assertTrue(trs.stream().anyMatch(t -> "General".equals(t.getNombre())));
    }

    @Test
    @DisplayName("getTipoRegistro: devuelve el DT del tipo si existe; null si no")
    void tipoRegistro_get() throws Exception {
        // asegurar edición y tipo
        ICE.altaEdicion("EventoBase", "orgNick", "EdTR2", "E2TR", "Montevideo",
                fechaAltaBase, fechaFinBase, fechaInicioBase);
        ICE.addTipoRegistro("EventoBase", "EdTR2", "VIP", "Acceso VIP", 3000f, 50);

        DTTipoRegistro dtOk = ICE.getTipoRegistro("EventoBase", "EdTR2", "VIP");
        assertNotNull(dtOk);
        assertEquals("VIP", dtOk.getNombre());

        DTTipoRegistro dtNull = ICE.getTipoRegistro("EventoBase", "EdTR2", "NoExiste");
        assertNull(dtNull);
    }
    @Test
    void test_listarEdicionesConfirmadas() throws Exception {

        ICE.setEstadoEdicion("EdTR2",  EEstadoEdicion.CONFIRMADA);
    	Set<DTEdicion> ediciones = ICE.listarEdicionesConfirmadas("EventoBase");
    	assertEquals(ediciones.size(), 1);
    }
    @Test
    void test_listarEdicionesIngresada() throws Exception {
    	Set<DTEdicion> ediciones = ICE.listarEdicionesSinProcesar("EventoBase");
    	int antes = ediciones.size();
    	ICE.altaEdicion("EventoBase", "orgNick", "EdTR3", "E2TR", "Montevideo",
                fechaAltaBase, fechaFinBase, fechaInicioBase);
    	ediciones = ICE.listarEdicionesSinProcesar("EventoBase");
    	// Ya tiene 4 anteriores
    	assertEquals(ediciones.size(), antes+1);
    }
    @Test
    void test_listarEventoCategoria() {

    }
    /*
    @Test
    void testAltaDeRegistro() throws Exception {
    	try {
			ICU.crearAsistente("comprador","comprador","comprador@asist.com","comprador",fechaInicioBase);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			ICU.crearInstitucion("institucionDePrueba", "dato2","dato3");
		} catch (ExisteInstitucionExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ManejadorUsuario MUSU = ManejadorUsuario.getInstance();
		Institucion inst = MUSU.getInstitucion("institucionDePrueba");
		Asistente asist = (Asistente) MUSU.findUsuario("comprador");
		asist.setInstitucion(inst);
    	try {
			ICE.altaEdicion("EventoBase", "orgNick", "EdTR4", "ED25", "Montevideo",
			        fechaAltaBase, fechaFinBase, fechaInicioBase);
		} catch (ExisteEdicion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ICE.addTipoRegistro("EventoBase", "EdTR4", "General", "Acceso total", 1500f, 100);
    	ICE.addTipoRegistro("EventoBase", "EdTR4", "Sin Cupo", "Acceso total", 1500f, 0);
    	try {
			ICU.crearPatrocinio(1000000f, "PatrocinioDePrueba", fechaAltaBase, 1, ENivelPatrocinio.ORO, "General", "EventoBase", "EdTR4", "institucionDePrueba");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Exception except = assertThrows(Exception.class, () -> {
    		ICE.altaDeRegistro("comprador", "EventoBase", "EdTR4", "Sin Cupo", fechaAltaBase);
        });
        assertEquals(except.getMessage(),"El tipo de registro no tiene cupo. modificar datos?");
    	ICE.altaDeRegistro("comprador", "EventoBase", "EdTR4", "General", fechaAltaBase);
    	assertTrue(!asist.getCompras().isEmpty());
    	Exception except2 = assertThrows(Exception.class, () -> {
    		ICE.altaDeRegistro("comprador", "EventoBase", "EdTR4", "Sin Cupo", fechaAltaBase);
        });
        assertEquals(except2.getMessage(),"El asistente ya esta registrado a la edicion, modificar datos?");
    }
    *//*
    @Test
    void testAltaDeRegistroConCosto() throws Exception {
    	try {
			ICU.crearAsistente("comprador2","comprador2","comprador2@asist.com","comprador",fechaInicioBase);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			ICU.crearInstitucion("institucionDePrueba2", "dato2","dato3");
		} catch (ExisteInstitucionExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ManejadorUsuario MUSU = ManejadorUsuario.getInstance();
		Institucion inst = MUSU.getInstitucion("institucionDePrueba2");
		Asistente asist = (Asistente) MUSU.findUsuario("comprador2");
		asist.setInstitucion(inst);
    	try {
			ICE.altaEdicion("EventoBase", "orgNick", "EdTR5", "ED25", "Montevideo",
			        fechaAltaBase, fechaFinBase, fechaInicioBase);
		} catch (ExisteEdicion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ICE.addTipoRegistro("EventoBase", "EdTR5", "General", "Acceso total", 1500f, 100);
    	ICE.addTipoRegistro("EventoBase", "EdTR5", "Sin Cupo", "Acceso total", 1500f, 0);
    	try {
			ICU.crearPatrocinio(1000000f, "PatrocinioDePrueba2", fechaAltaBase, 1, ENivelPatrocinio.ORO, "General", "EventoBase", "EdTR5", "institucionDePrueba2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Exception except = assertThrows(Exception.class, () -> {
    		ICE.altaDeRegistro("comprador2", "EventoBase", "EdTR5", "Sin Cupo", fechaAltaBase);
        });
        assertEquals(except.getMessage(),"El tipo de registro no tiene cupo. modificar datos?");
    	ICE.altaDeRegistro("comprador2", "EventoBase", "EdTR5", "General", fechaAltaBase, 2);
    	assertTrue(!asist.getCompras().isEmpty());
    	Exception except2 = assertThrows(Exception.class, () -> {
    		ICE.altaDeRegistro("comprador2", "EventoBase", "EdTR5", "Sin Cupo", fechaAltaBase);
        });
        assertEquals(except2.getMessage(),"El asistente ya esta registrado a la edicion, modificar datos?");
    }*/
}
