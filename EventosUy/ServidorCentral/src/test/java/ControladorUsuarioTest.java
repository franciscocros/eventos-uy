package  test.java;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.java.excepciones.ExisteEdicion;
import main.java.excepciones.ExisteEventoExcepcion;
import main.java.excepciones.ExisteInstitucionExcepcion;
import main.java.excepciones.ExisteUsuarioExcepcion;
import main.java.excepciones.FechaNacimientoPosteriorExcepcion;
import main.java.excepciones.NoExisteUsuarioExcepcion;
import main.java.excepciones.NoTieneCupo;
import main.java.excepciones.YaTieneCompra;
import main.java.logica.Asistente;
import main.java.logica.CargarDatos;
import main.java.logica.Compra;
import main.java.logica.DTAsistente;
import main.java.logica.DTCompra;
import main.java.logica.DTCompraWeb;
import main.java.logica.DTEdicion;
import main.java.logica.DTInstitucion;
import main.java.logica.DTOrganizador;
import main.java.logica.DTPatrocinio;
import main.java.logica.DTUsuario;
import main.java.logica.ENivelPatrocinio;
import main.java.logica.Edicion;
import main.java.logica.Fabrica;
import main.java.logica.IControladorEvento;
import main.java.logica.IControladorUsuario;
import main.java.logica.Institucion;
import main.java.logica.ManejadorEvento;
import main.java.logica.ManejadorUsuario;
import main.java.logica.TipoRegistro;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControladorUsuarioTest {

	static LocalDate fecha1;
	static LocalDate fecha2;
	static boolean datosCargados;
	 Fabrica fabrica = Fabrica.getInstance();
	 IControladorUsuario icu = fabrica.getIControladorUsuario();
	 IControladorEvento ice = fabrica.getIControladorEvento();
	@BeforeAll
	 void setUpBeforeClass() throws Exception {
		 try {
				CargarDatos datos = new CargarDatos(""," test.java");
				datosCargados = true;
			} catch (Exception e) {
				e.printStackTrace();
				}

		//icu.liberarManejadorUsuario();

		fecha1 = LocalDate.of(2024,12,9);
		fecha2 = LocalDate.of(2024,6,15);
		try {
			if (!icu.existeUsuario("nickki")) {
				icu.crearAsistente("nombre","nickki","correo","apellido",fecha1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!icu.existeUsuario("nickname2")) {
			icu.crearOrganizador("nombre2","nickname2","correo2","descripcion2","paginaWeb2");
		}

	}
/*
	@Test //Constructor y Excepcion de existencia
	void usuariosTest() {
		Exception except = assertThrows(Exception.class, () -> {
    		icu.crearOrganizador("nombre2","nickname2","correo23","descripcion2","paginaWeb2");
        });
        assertEquals(except.getMessage(),"El usuario ya existe, volver a intentar ?");
        
        Exception except2 = assertThrows(Exception.class, () -> {
    		icu.crearAsistente("nombre","nickki2","correo","apellido",fecha1);
        });
        assertEquals(except2.getMessage(),"El usuario ya existe, volver a intentar ?");
	}
*/
	@Test //Modificar datos de Usuario
	void modificarUsuariosTest() throws NoExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		try {
			icu.crearAsistente("nom2", "mu1", "mu@.com", "ap2", fecha2);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			icu.crearOrganizador("nombre2","mu2","mu2@.com","descripcion2","paginaWeb2");
		} catch (ExisteUsuarioExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icu.modificarAsistente("nombre3","mu1","mu@.com","apellido3",fecha2);
		icu.modificarOrganizador("nombre44","mu2","mu2@.com","descripcion44","paginaWeb44");

		DTAsistente asist = (DTAsistente) icu.getDTUsuario("mu1");
		DTOrganizador organiz = (DTOrganizador) icu.getDTUsuario("mu2");

		//Asistente
		assertEquals(asist.getNombre(),"nombre3");
		assertEquals(asist.getNickName(),"mu1");
		assertEquals(asist.getCorreo(),"mu@.com");
		assertEquals(asist.getApellido(),"apellido3");
		assertEquals(asist.getFechaNacimiento(),fecha2);

        Exception except = assertThrows(Exception.class, () -> {
    		icu.modificarAsistente("nombre3","nicknameNoExiste","correoNoExiste","apellido3",fecha2);
        });
        assertEquals(except.getMessage(),"El usuario ingresado no existe en sistema");


		//Organizador
		assertEquals(organiz.getNombre(),"nombre44");
		assertEquals(organiz.getNickName(),"mu2");
		assertEquals(organiz.getCorreo(),"mu2@.com");
		assertEquals(organiz.getDescripcion(),"descripcion44");
		assertEquals(organiz.getPaginaWeb(),"paginaWeb44");

        Exception except2 = assertThrows(Exception.class, () -> {
        	icu.modificarOrganizador("nombre44","nicknameNoExiste","correoNoExiste","descripcion44","paginaWeb44");
        });
        assertEquals(except2.getMessage(),"El usuario ingresado no existe en sistema");
	}

	@Test //Constructor Institucion y existencia
	void crearInstitucionTest() throws ExisteInstitucionExcepcion {
		ManejadorUsuario mu = ManejadorUsuario.getInstance();
		icu.crearInstitucion("dato1", "dato2","dato3");
		Institucion inst = mu.getInstitucion("dato1");

        Exception except = assertThrows(Exception.class, () -> {
        	icu.crearInstitucion("dato1", "dato2","dato3");
        });

        assertEquals(except.getMessage(),"La institucion existe, volver a intentar ?");
		assertEquals(inst.getNombre(),"dato1");
		assertEquals(inst.getDescripcion(),"dato2");
		assertEquals(inst.getPaginaWeb(),"dato3");

	}

	@Test //Constructor Institucion y existencia
	void setInstitucionAsistenteTest() throws ExisteInstitucionExcepcion, ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		ManejadorUsuario mu = ManejadorUsuario.getInstance();
		icu.crearAsistente("asistente01","asistente01","asistente01","asistente01",fecha1);
		icu.crearInstitucion("instit01", "dato2","dato3");
		icu.setInstitucionAsistente("asistente01","instit01");

		Asistente asist = (Asistente) mu.getUsuario("asistente01");
		assertEquals(asist.getInstitucion().getNombre(),"instit01");

		icu.setInstitucionAsistente("asistenteNoExiste","instit01");
	}


	@Test //Constructor Institucion
	void listarInstitucionesTest() throws ExisteInstitucionExcepcion {
		ManejadorUsuario mu = ManejadorUsuario.getInstance();
		//Map<String, DTInstitucion> ins= icu.listarInstituciones();

		//Hay 8 Instituciones ingresadas
		//System.out.println(ins.size());
		icu.crearInstitucion("dato4", "dato5","dato6");
		icu.crearInstitucion("dato7", "dato8","dato9");

		Map<String, DTInstitucion> instituciones = icu.listarInstituciones();

		//Hay 3 Instituciones ingresadas
		assertTrue(instituciones.size()==5);

		DTInstitucion inst = instituciones.get("dato4");

		assertEquals(inst.getNombre(),"dato4");
		assertEquals(inst.getDescripcion(),"dato5");
		assertEquals(inst.getWeb(),"dato6");

	}

	@Test //Constructor Institucion
	void listarUsuariosTest() throws ExisteInstitucionExcepcion, ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		//icu.liberarManejadorUsuario();

		icu.crearAsistente("nombre","nick1","c1","apellido",fecha1);
		icu.crearOrganizador("nombre2","nick2","c2","descripcion2","paginaWeb2");

		DTAsistente asist = (DTAsistente) icu.getDTUsuario("nick1");
		DTOrganizador  organiz = (DTOrganizador) icu.getDTUsuario("nick2");

		Map<String, DTUsuario> usuarios = icu.listarUsuarios();

		assertTrue(!usuarios.isEmpty());
		assertEquals(asist.getNombre(),"nombre");
		assertEquals(asist.getNickName(),"nick1");
		assertEquals(asist.getCorreo(),"c1");
		assertEquals(asist.getApellido(),"apellido");
		assertEquals(asist.getFechaNacimiento(),fecha1);

		//Organizador
		assertEquals(organiz.getNombre(),"nombre2");
		assertEquals(organiz.getNickName(),"nick2");
		assertEquals(organiz.getCorreo(),"c2");
		assertEquals(organiz.getDescripcion(),"descripcion2");
		assertEquals(organiz.getPaginaWeb(),"paginaWeb2");

	}

	@Test //Constructor Institucion
	void patrocinioTest() throws Exception {
		icu.crearOrganizador("nombre2","pat2","pat2","descripcion2","paginaWeb2");
		icu.crearInstitucion("institucion", "descripcion","paginaweb");
		Set<String> cats = new HashSet<>();
		cats.add("cate1");
		ice.addEvento("evento", "descripcion","sigla", fecha1, cats);
		ice.altaEdicion("evento","pat2", "edicion", "Ciudad", "sigla", fecha1, fecha2, fecha1);
		ice.addTipoRegistro("evento", "edicion", "tiporeg", "20", 10, 10);

		//No Existe Institucion
        Exception except = assertThrows(Exception.class, () -> {
    		icu.crearPatrocinio(0, "codigo", fecha1, 0, ENivelPatrocinio.BRONCE, "tiporeg", "evento", "edicion", "institucionNoExiste");
        });
        assertEquals(except.getMessage(),"La institucion no existe");

		//No Existe evento
        Exception except1 = assertThrows(Exception.class, () -> {
        	icu.crearPatrocinio(0, "codigo", fecha1, 0, ENivelPatrocinio.BRONCE, "tiporeg", "eventoNoExiste", "edicion", "institucion");
        });
       assertEquals(except1.getMessage(),"El evento no existe");

		//No Existe evento
       Exception except2 = assertThrows(Exception.class, () -> {
       	icu.crearPatrocinio(0, "codigo", fecha1, 0, ENivelPatrocinio.BRONCE, "tiporeg", "evento", "edicionNoExiste", "institucion");
       });
      assertEquals(except2.getMessage(),"La edicion no existe");

		//No Existe el tipo de registro
      Exception except3 = assertThrows(Exception.class, () -> {
      	icu.crearPatrocinio(0, "codigo", fecha1, 0, ENivelPatrocinio.BRONCE, "tiporegNoExiste", "evento", "edicion", "institucion");
      });
     assertEquals(except3.getMessage(),"No existe el tipo de registro");

		//registro gratuitos uperan el 20%
     Exception except5 = assertThrows(Exception.class, () -> {
     	icu.crearPatrocinio(5, "codigo", fecha1, 10, ENivelPatrocinio.BRONCE, "tiporeg", "evento", "edicion", "institucion");
     });
    assertEquals(except5.getMessage(),"Los registros gratuitos a otorgar supera el 20 % del aporte economico, modificar? ");

	icu.crearPatrocinio(0, "codigo", fecha1, 0, ENivelPatrocinio.BRONCE, "tiporeg", "evento", "edicion", "institucion");

		//Ya tiene este patrocinio
     Exception except4 = assertThrows(Exception.class, () -> {
     	icu.crearPatrocinio(0, "codigo", fecha1, 0, ENivelPatrocinio.BRONCE, "tiporeg", "evento", "edicion", "institucion");
     });
    assertEquals(except4.getMessage(),"Ya tiene este patrocinio, modificar datos? ");

	}

	@Test //Constructor Institucion
	void listarComprasDeUsuarioTest() throws ExisteInstitucionExcepcion, NoExisteUsuarioExcepcion {
	     Exception except = assertThrows(NoExisteUsuarioExcepcion.class, () -> {
	    	 Map<String, DTCompra> compUsario = icu.listarComprasDeUsuario("nicknameNoExiste");
	     });
	     assertEquals(except.getMessage(),"El usuario ingresado no existe en sistema");

	}   
/*
	@Test
	 void testCompras() {
		try {
			Map<String, DTCompra>compras = icu.listarComprasDeUsuario("sofirod");
			assertTrue(!compras.isEmpty());

		} catch (NoExisteUsuarioExcepcion e) {
			// TODO Auto-generated catch block
			
		}
		DTCompra dtc = icu.getDTCompra("sofirod", "Maratón de Montevideo 2025");
		assertEquals("sofirod",dtc.getAsistente());
	 }
*/
/*
	@Test
	void testBuscarEventoAsistente() {
			String res = icu.findEvento("sofirod", "Maratón de Montevideo 2025");
			assertEquals("Maratón de Montevideo", res);
	}
*/
	@Test
	void testValidoCupon() throws NoTieneCupo, YaTieneCompra, Exception {
		// String nickAsistente, String nombreEento, String nombreEdicion, String nombreTipoReg, String cupon
		boolean salida = ice.validoCupon("atorres", "Conferencia de tecnología", "Tecnología Punta del Este 2026", "Estudiante", "TECHUDELAR");
		assertTrue(salida);
	}
/*
	@Test
	void testListarAsistentes() {
		Map<String, DTUsuario> asistentes = icu.listarAsistentes();
		assertTrue(asistentes.size() > 0);
	}
*/
	@Test
	void testGetSetEdiciones() {
		try {
			icu.crearOrganizador("nombre2","getset1","getset1","descripcion2","paginaWeb2");
		} catch (ExisteUsuarioExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> cats = new HashSet<>();
		cats.add("cate1");
		try {
			ice.addEvento("eventogetset", "descripcion","sigla", fecha1, cats);
		} catch (ExisteEventoExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ice.altaEdicion("eventogetset","getset1", "ediciongetset", "Ciudad", "sigla", fecha1, fecha2, fecha1);
		} catch (ExisteEdicion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<DTEdicion> edis = icu.getSetEdiciones("getset1");
		assertTrue(edis != null);
	}

	@Test
	void testTieneYGetInstitucionAsistente() throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion, ExisteInstitucionExcepcion {
		icu.crearAsistente("asistenteX","asistenteX","asistenteX@asist.com","asistenteX",fecha1);
		icu.crearInstitucion("institX", "desc","web");
		icu.setInstitucionAsistente("asistenteX","institX");
		assertTrue(icu.tieneInstitucion("asistenteX"));
		assertEquals("institX", icu.getInstitucionAsistente("asistenteX"));
	}

	@Test
	void testExisteUsuario() {
		try {
			icu.crearAsistente("asistenteX","asistentetrucho","asistente234@asist.com","asistenteX",fecha1);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(icu.existeUsuario("asistentetrucho"));
		assertTrue(!icu.existeUsuario("noexiste"));
	}

	@Test
	void testSetImagenYContrasenia() throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion, ExisteInstitucionExcepcion {
		icu.crearAsistente("asistenteImg","asistenteImg","asistenteImg@asist.com","asistenteImg",fecha1);
		icu.crearInstitucion("institImg","desc","web");
		icu.setImagenUsuario("asistenteImg","imagen.png");
		icu.setContraseniaUsuario("asistenteImg","clave123");
		icu.setImagenInstitucion("institImg","foto.jpg");
		assertTrue(true); // no lanza excepciones
	}

	@Test
	void testLoginYLoginAsistente() throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		icu.crearAsistente("loginA","loginA","loginA@asist.com","loginA",fecha1);
		icu.setContraseniaUsuario("loginA","123");
		assertTrue(icu.login("loginA","123"));
		assertTrue(icu.loginAsistente("loginA","123"));
		assertTrue(!icu.login("loginA","mal"));
		assertTrue(!icu.loginAsistente("loginA","mal"));
	}

	@Test
	void testListarPatrociniosVacios() {
		Set<?> set1 = icu.listarPatrocinios("eventoNoExiste","edicionNoExiste");
		Set<?> set2 = icu.listarPatrociniosAlt("eventoNoExiste","edicionNoExiste");
		assertTrue(set1.isEmpty());
		assertTrue(set2.isEmpty());
	}
	
	@Test
	void testListarPatrocinios() {
		Set<?> set1 = icu.listarPatrocinios("Expointer Uruguay","Expointer Uruguay 2025");
		Set<?> set2 = icu.listarPatrociniosAlt("Expointer Uruguay","Expointer Uruguay 2025");
		assertTrue(!set1.isEmpty());
		assertTrue(!set2.isEmpty());
	}

	@Test
	void testListarPatrociniosRegistro() throws Exception {
		Set<DTPatrocinio> set = icu.listarPatrociniosregistro("edicionNoExiste","registroNoExiste");
		//Set<DTPatrocinio> set2 = icu.listarPatrociniosregistro("Tecnologia Punta del Este 2026","ORO");
		assertTrue(set == null || set.isEmpty());
		//assertTrue(!set2.isEmpty());
	}

	@Test
	void testAceptarCompraWeb() throws Exception {
	    icu.crearAsistente("comprador","comprador","comprador@asist.com","comprador",fecha1);
	    Asistente asistente = (Asistente) ManejadorUsuario.getInstance().getUsuario("comprador");
	    ManejadorEvento meve = ManejadorEvento.getInstance();
	    Edicion edic = meve.findEdicion("Montevideo Rock 2025");
	    TipoRegistro tiporeg = edic.getMisTipoRegistro().get("General");
	    Compra compra = new Compra("C001", fecha2, 1, asistente, tiporeg);
	    compra.setAceptada(false);
	    asistente.getCompras().put("C001", compra);
	    icu.aceptarCompraWeb("comprador", "C001");
	    assertTrue(asistente.getCompras().get("C001").isAceptada());
	}


	@Test
	void testLiberarManejadorUsuario() {
		icu.liberarManejadorUsuario();
		assertTrue(true);
	}

	@Test
	void testEsAsistente() throws ExisteUsuarioExcepcion, FechaNacimientoPosteriorExcepcion {
		icu.crearAsistente("asisTest","asisTest","asisTest","asisTest",fecha1);
		assertTrue(icu.esAsistente("asisTest"));
		assertTrue(!icu.esAsistente("noexiste"));
	}
	
	@Test
	void testListarComprasUsuarioWeb() throws NoExisteUsuarioExcepcion {
		Set<DTCompraWeb> compras = icu.listarComprasDeUsuarioWeb("mariR");
		assertTrue(!compras.isEmpty());
	}

	@Test
	void addYRemoveSeguidoTest() {
		ManejadorUsuario musu = ManejadorUsuario.getInstance();
		try {
			icu.crearAsistente("nom1", "nick1234", "corr1", "ap1", fecha1);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			icu.crearAsistente("nom2", "nick222", "corr2", "ap2", fecha2);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icu.addSeguido("nick1234", "nick222");
		assertTrue(!musu.getUsuario("nick222").getSeguidos().isEmpty());
		icu.removeSeguido("nick1234", "nick222");
		assertTrue(musu.getUsuario("nick222").getSeguidos().isEmpty());
	}
	
	@Test
	void listarSeguidosTest(){
		ManejadorUsuario musu = ManejadorUsuario.getInstance();
		try {
			icu.crearAsistente("nom1", "nick12345", "corr12", "ap1", fecha1);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			icu.crearAsistente("nom2", "nick2222", "corr22", "ap2", fecha2);
		} catch (ExisteUsuarioExcepcion | FechaNacimientoPosteriorExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		icu.addSeguido("nick12345", "nick2222");
		Set<DTUsuario> dtusu = icu.listarSeguidos("nick2222");
		assertTrue(!dtusu.isEmpty());
		dtusu = icu.listarSeguidores("nick12345");
		assertTrue(!dtusu.isEmpty());
		icu.removeSeguidor("nick12345", "nick2222");
		assertTrue(musu.findUsuario("nick2222").getSeguidores().isEmpty());
	}
}





























