package com.booking.ejecutable;

import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;
import java.lang.NumberFormatException;


/*******************************************
 * Clase principal de la aplicación Boo-King.
 * @author Ismael Núñez
 *******************************************/
public class Main {
		
	/* ==============XXX==============|  Principal  |==============XXX==============  */
	
	public static void main(String[] args) throws BookingException {
		
		Utilidades.configurarSesion();
		
		lanzarMenu();

		Utilidades.cerrarSesion();

	}
	
	/* ==============XXX==============|  Métodos  |==============XXX==============  */
	
	private static void lanzarMenu() {
		int opcion;
		try {
			do {
				opcion = Utilidades.mostrarMenu();
				tratarMenu(opcion);
			} while (opcion != Utilidades.TOTAL_OPCIONES);
		}
		catch (NumberFormatException e) {
			System.out.println("Debe introducir un número.");
		}
	}

	private static void tratarMenu(int decision) throws NumberFormatException {
		try {
			switch (decision) {
				case 1:
					Utilidades.insertar();
				break;
				case 2:
					Utilidades.actualizar();
				break;
				case 3:
					Utilidades.borrar();
				break;
				case 4:
					Utilidades.consultarDetalles();
				break;
				case 5:
					Utilidades.consultarArrendadoresPorNombre();
				break;
				case 6:
					Utilidades.consultarPrestamosPorNombre();
				break;
				case 7:
					Utilidades.prestamosPorAno();
				break;
				case 8:
					Utilidades.estadisticasPrestamo();
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
