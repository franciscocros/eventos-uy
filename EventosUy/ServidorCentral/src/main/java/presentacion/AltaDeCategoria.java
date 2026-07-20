package main.java.presentacion;

import java.util.Scanner;

import main.java.excepciones.ExisteCategoriaExcepcion;
//import excepciones.existeCategoriaExcepcion;
import main.java.logica.IControladorEvento;


public class AltaDeCategoria {
	private Scanner sc;
	String input;

	public AltaDeCategoria(Scanner scmain) {
		sc = scmain;
	}

	public void darAltaCategoria(IControladorEvento ice) {
		do {
			System.out.println("Ingrese el el nombre de la categoria o exit para salir ");
			input = sc.next();
			try {
				if (!input.equals("exit")) {
					ice.altaCategoria(input);
				}
			} catch (ExisteCategoriaExcepcion e) {
				System.out.println(e );
			}

		}while(!input.equals("exit"));
		System.out.println("Gracias por ingresar categorias ");
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}


