package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import main.java.logica.Asistente;
import main.java.logica.Ciudad;
import main.java.logica.Compra;
import main.java.logica.DTCompra;
import main.java.logica.Edicion;
import main.java.logica.Evento;
import main.java.logica.Organizador;
import main.java.logica.TipoRegistro;

class CompraTest {
	
	@Test
	void compratest() {
		LocalDate fecha = LocalDate.of(1, 2, 3);
		Asistente asistente = new Asistente("dato1","dato2","dato3","dato4",fecha);
		TipoRegistro reg = new TipoRegistro(null, null, 0, 0, null);
		float costo = 1500;
		Compra compra = new Compra("compra1",fecha,costo,asistente,reg);
		Edicion edi = new Edicion("nombre","sigla",fecha,fecha,fecha,new Ciudad(null, null),new Evento(null, null, null, fecha, null),new Organizador(null, null, null, null, null));
		compra.setEdicion(edi);
		compra.setId("id");
		DTCompra dtc = compra.getDT(); 
		dtc.getAsistente();
		
		compra.setAceptada(true);
		assertEquals(compra.getId(),"id");
		assertTrue(compra.isAceptada());
		assertEquals(reg,compra.getTipoRegistro());
		assertEquals(asistente,compra.getAsistente());
		assertEquals(compra.getCosto(),costo);
		assertEquals(edi,compra.getEdicion());
		assertEquals(fecha,compra.getFecha());
		
	}
	
}
