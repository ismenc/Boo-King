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
		
		mostrarMensajeBienvenida();
		lanzarMenu();

		Utilidades.cerrarSesion();

	}
	
	/* ==============XXX==============|  Métodos  |==============XXX==============  */
	
	private static void lanzarMenu() {
		int opcion;
		
		do {
			try {
				opcion = Utilidades.mostrarMenu();
				tratarMenu(opcion);
			}
			catch (NumberFormatException e) {
				System.out.println("Debe introducir un número.");
				opcion = 0;
			}
		} while (opcion != Utilidades.TOTAL_OPCIONES);
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
	
	private static void mostrarMensajeBienvenida() {
		System.out.println(
				"+-----------------------------------+\n"
				+ "|Bienvenido a nuestro marketplace...|\n"
				+ "|---------> Boo-King.com <----------|\n"
				+ "|                                   |\n"
				+ "| Dale vida a eso libros empolvados |\n"
				+ "|   Solicita libros prestados       |\n"
				+ "+-----------------------------------+");
	}

}
