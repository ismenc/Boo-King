package com.booking.ejecutable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.booking.dao.*;
import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;
import com.booking.persistencia.*;


/**
 * Clase principal de la aplicación Boo-King.
 * @author Ismael Núñez
 *
 */
public class Main {
	
	private static final int TOTAL_OPCIONES = 9;
	
	private static LibroDAO libroDao = new LibroDAO();
	private static PrestamoDAO prestamoDao = new PrestamoDAO();
	private static ArrendadorDAO arrendadorDao = new ArrendadorDAO();
	
	/* ========================= Principal ========================= */

	public static void main(String[] args) throws BookingException {
		
		int opcion;

		Utilidades.configurarSesion();
		
		do {
			opcion = Utilidades.mostrarMenu();
			tratarMenu(opcion);
		} while (opcion != TOTAL_OPCIONES);

		Utilidades.cerrarSesion();

	}
	
	/* ========================= Métodos ========================= */

	private static void tratarMenu(int decision) {
		try {
			switch (decision) {
				case 1:
					nuevoArrendador();
				break;
				case 2:
					nuevoLibro();
				break;
				case 3:
					nuevoPrestamo();
				break;
				case 4:
					//modificarAlgo();
				break;
				case 5: 
					borrarArrendador();
				break;
				case 6:
					consultarArrendador();
				break;
				case 7: 
					consultarLibro();
				break;
				case 8:
					consultarPrestamo();
				break;
				case TOTAL_OPCIONES:
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
	
	/* ------------------------ Tratamiento de opciones ------------------------ */
	
	/**
	 * Solicita datos del arrendador y lo guarda en la BD
	 */
	private static void nuevoArrendador() {
		System.out.println("Los campos con * son obligatorios:");
		
		Arrendador arrendador = new Arrendador(
				Utilidades.solicitarCadena("Nombre* : "),
				Utilidades.solicitarCadena("Entidad: "),
				Utilidades.solicitarCadena("Dirección* : "),
				Utilidades.solicitarCadena("Código postal* : "),
				Utilidades.solicitarCadena("Teléfono*: "));
		arrendadorDao.guardar(arrendador);
		
		System.out.println("Se ha creado el nuevo arrendador con ID: "+ arrendador.getId() +".");
	}
	
	/**
	 * Solicita datos del libro y lo guarda en la BD
	 */
	private static void nuevoLibro() {
		System.out.println("Los campos con * son obligatorios:");
		
		Libro libro = new Libro(
				Utilidades.solicitarCadena("Título* : "),
				Utilidades.solicitarCadena("Autor* : "),
				Utilidades.solicitarCadena("Editorial* : "),
				Utilidades.solicitarCategoria("Introduce una categoría* : "),
				Utilidades.solicitarCadena("Año de publicación*: "));
		libroDao.guardar(libro);
		
		System.out.println("Se ha creado el nuevo arrendador con ID: "+ libro.getId() +".");
	}
	
	/**
	 * Solicita datos y da de alta un nuevo préstamo
	 */
	private static void nuevoPrestamo() throws BookingException {
		Prestamo prestamo = new Prestamo(
				new Date(),
				Utilidades.solicitarEntero("Duración en días: "),
				Utilidades.solicitarArrendador("Introduce la ID del arrendador: "));
		
		// XXX: Problemas al insertar la lista en cascada
		ArrayList<Stack> stacks = Utilidades.nuevaListaStacks();
		prestamo.setListaStacks(stacks);
		
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = stacks.iterator(); iterator.hasNext();) {
			Stack stack = (Stack) iterator.next();
			stack.setPrestamo(prestamo);
		}
		
		prestamoDao.guardar(prestamo);
	}
	
	/**
	 * Permite borrar un arrendador
	 * @throws BookingException
	 */
	private static void borrarArrendador() throws BookingException {
		Utilidades.mostrarArrendadores();
		
		Arrendador arrendador = Utilidades.solicitarArrendador("Introduzca la ID del arrendador: ");
		arrendadorDao.borrar(arrendador);
		System.out.println("Se ha borrado el con id " + arrendador.getId());
	}
	
	/**
	 * Muestra los detalles del arrendador solicitado
	 * @throws BookingException
	 */
	private static void consultarArrendador() throws BookingException {
		Utilidades.mostrarArrendadores();
		
		int id = Utilidades.solicitarEntero("Introduce la ID del arrendador: ");
		Arrendador arrendador = arrendadorDao.obtener(id);
		
		System.out.println("\n" + arrendador);
	}
	
	/**
	 * Muestra los detalles del libro solicitado
	 * @throws BookingException
	 */
	private static void consultarLibro() throws BookingException {
		Utilidades.mostrarLibros();
		
		int id = Utilidades.solicitarEntero("Introduce la ID del libro: ");
		Libro libro = libroDao.obtener(id);
		
		System.out.println("\n" + libro);
	}
	
	/**
	 * Muestra los detalles del prestamo solicitado
	 * @throws BookingException
	 */
	private static void consultarPrestamo() throws BookingException {
		Utilidades.mostrarPrestamos();
		
		int id = Utilidades.solicitarEntero("Introduce la ID del préstamo: ");
		Prestamo prestamo = prestamoDao.obtener(id);
		
		System.out.println("\n" + prestamo);
	}

}
