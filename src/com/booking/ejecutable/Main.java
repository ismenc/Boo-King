package com.booking.ejecutable;

import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;


/**
 * Clase principal de la aplicación Boo-King.
 * @author Ismael Núñez
 *
 */
public class Main {
		
	/* ========================= Principal ========================= */
	
	public static void main(String[] args) throws BookingException {
		
		int opcion;

		Utilidades.configurarSesion();
		
		do {
			opcion = Utilidades.mostrarMenu();
			tratarMenu(opcion);
		} while (opcion != Utilidades.TOTAL_OPCIONES);

		Utilidades.cerrarSesion();

	}
	
	/* ========================= Métodos ========================= */

	private static void tratarMenu(int decision) {
		try {
			switch (decision) {
				case 1:
					Utilidades.nuevoArrendador();
				break;
				case 2:
					Utilidades.nuevoLibro();
				break;
				case 3:
					Utilidades.nuevoPrestamo();
				break;
				case 4:
					Utilidades.modificarLibro();
				break;
				case 5: 
					Utilidades.borrarArrendador();
				break;
				case 6:
					Utilidades.consultarArrendador();
				break;
				case 7: 
					Utilidades.consultarLibro();
				break;
				case 8:
					Utilidades.consultarPrestamo();
				break;
				case 9:
					Utilidades.consultarArrendadoresPorNombre();
				break;
				case 10:
					// FIXME: Aunque no haya arrendadores muestra nombre
					Utilidades.consultarPrestamosPorNombre();
				break;
				case Utilidades.TOTAL_OPCIONES:
					System.out.println("¡Hasta la vista!");
				break;
				default:
					System.out.println("Debe seleccionar una de las opciones mostradas.");
				break;
			}
			
		} catch (BookingException e) {
			System.out.println(e.getMessage());
		}
	}
	
	

}
