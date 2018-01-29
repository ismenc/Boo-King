package com.booking.ejecutable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.hibernate.Session;

import com.booking.dao.*;
import com.booking.modelo.BookingException;
import com.booking.persistencia.*;

import java.util.List;

import com.booking.modelo.Categoria;

/**
 * 
 * @author Ismael Nunez
 *
 */
public class Main {
	
	private static final int TOTAL_OPCIONES = 9;
	
	@SuppressWarnings("unused")
	private static Session session;
	private static Scanner teclado = new Scanner(System.in);
	private static LibroDAO libroDao = new LibroDAO();
	private static PrestamoDAO prestamoDao = new PrestamoDAO();
	private static ArrendadorDAO arrendadorDao = new ArrendadorDAO();

	public static void main(String[] args) throws BookingException {
		
		int opcion;

		configurarSesion();
		
		do {
			opcion = mostrarMenu();
			tratarMenu(opcion);
		} while (opcion != TOTAL_OPCIONES);

		cerrarSesion();

	}
	
	/* ========================= Métodos auxiliares ========================= */
	
	private static void cerrarSesion() {
		HibernateUtil.closeSessionFactory();
	}

	private static void configurarSesion() {
		HibernateUtil.buildSessionFactory();
		HibernateUtil.openSessionAndBindToThread();
		session = HibernateUtil.getSessionFactory().getCurrentSession();

	}
	
	private static int solicitarEntero(String msg) {
		int id;
		System.out.print(msg);
		id = Integer.parseInt(teclado.nextLine());
		return id;
	}
	
	private static String solicitarCadena(String msg) {
		String descripcion;
		System.out.println(msg);
		descripcion = teclado.nextLine();
		return descripcion;
	}
	
	/**
	 * Muestra las categorías y solicita una con mensaje personalizado
	 * @param msg
	 * @return Un objeto categoría
	 */
	private static Categoria solicitarCategoria(String msg) {
		int i = 1, opcion;
		
		System.out.print("CATEGORÍAS:");
		do{
			System.out.print(" ["+i+"]-" + Categoria.values()[i-1]);
			i++;
		}while(i <= Categoria.values().length);
		opcion = solicitarEntero("\n" + msg);
		
		return Categoria.values()[opcion-1];
	}
	
	/**
	 * Muestra los arrendadores y solicita el que se desea
	 * @param msg
	 * @return El arrendador solicitado
	 * @throws BookingException
	 */
	private static Arrendador solicitarArrendador(String msg) throws BookingException{
		mostrarArrendadores();
		
		int idArrendador = solicitarEntero(msg);
		
		return arrendadorDao.obtener(idArrendador);
	}
	
	/**
	 * Muestra los libros y solicita el que se desea
	 * @param msg
	 * @return El libro solicitado
	 * @throws BookingException
	 */
	private static Libro solicitarLibro(String msg) throws BookingException{
		mostrarLibros();
		
		int idLibro = solicitarEntero(msg);
		
		return libroDao.obtener(idLibro);
	}
	
	/**
	 * Menu con el que crearemos una lista de Stacks(pilas) introduciendo datos
	 * @return Lista de Stacks(pilas)
	 * @throws BookingException
	 */
	private static ArrayList<Stack> nuevaListaStacks() throws BookingException{
		String respuesta;
		ArrayList<Stack> stacks = new ArrayList<Stack>();
		// FIXME: Guarda 1 sólo stack del prestamo
		Stack stack;
		
		do {
			stack = new Stack(
					solicitarLibro("Introduce la ID del libro: "),
					solicitarEntero("Introduce la cantidad: "),
					null /* Aquí va id prestamo */);
			stacks.add(stack);
			System.out.println(stack);
			respuesta = solicitarCadena("¿Desea seguir introduciendo datos? (Si/No)");
		}while(respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("sí"));
		
		return stacks;
	}
	
	/* ------------------------ Métodos de Pantalleo ------------------------ */
	
	/**
	 * Muestra informacion breve de todos los arrendadores
	 */
	private static void mostrarArrendadores() {
		List<Arrendador> arrendadores = arrendadorDao.obtenerLista();
		
		System.out.println("-------------- Lista de Arrendadores --------------");
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = arrendadores.iterator(); iterator.hasNext();) {
			Arrendador arrendador = (Arrendador) iterator.next();
			System.out.println(arrendador.getId() + " - " + arrendador.getNombre());
		}
		System.out.println("---------------------------------------------------");
	}
	
	/**
	 * Muestra informacion breve de todos los libros
	 */
	private static void mostrarLibros() {
		List<Libro> libros = libroDao.obtenerLista();
		
		System.out.println("-------------- Lista de Libros --------------");
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = libros.iterator(); iterator.hasNext();) {
			Libro libro = (Libro) iterator.next();
			System.out.println(libro.getId() + " - " + libro.getTitulo());
		}
		System.out.println("---------------------------------------------");
	}
	
	/**
	 * Muestra informacion breve de todos los prestamos
	 */
	private static void mostrarPrestamos() {
		List<Prestamo> prestamos = prestamoDao.obtenerLista();
		
		System.out.println("------------- Lista de préstamos -------------");
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = prestamos.iterator(); iterator.hasNext();) {
			Prestamo prestamo = (Prestamo) iterator.next();
			System.out.println(prestamo.getIdPrestamo() + " - " + prestamo.getArrendador().getNombre());
		}
		System.out.println("---------------------------------------------");
	}
	
	private static int mostrarMenu() {
		int opcion = 0;
		do {
			System.out.println("+------------ MENÚ -------------+");
			System.out.println("| [1] Añadir arrendador\t\t|");
			System.out.println("| [2] Añadir libro\t\t|");
			System.out.println("| [3] Nuevo préstamo\t\t|");
			System.out.println("| [4] Modificar libro\t\t|");
			System.out.println("| [5] Borrar arrendador\t\t|");
			System.out.println("| [6] Consultar arrendador\t|");
			System.out.println("| [7] Consultar libro\t\t|");
			System.out.println("| [8] Consultar préstamo\t|");
			System.out.println("| [9] Salir\t\t\t|");
			System.out.println("+-------------------------------+");
			opcion = Integer.parseInt(teclado.nextLine());
		} while (opcion < 1 || opcion > TOTAL_OPCIONES);

		return opcion;
	}

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
				solicitarCadena("Nombre* : "),
				solicitarCadena("Entidad: "),
				solicitarCadena("Dirección* : "),
				solicitarCadena("Código postal* : "),
				solicitarCadena("Teléfono*: "));
		arrendadorDao.guardar(arrendador);
		
		System.out.println("Se ha creado el nuevo arrendador con ID: "+ arrendador.getId() +".");
	}
	
	/**
	 * Solicita datos del libro y lo guarda en la BD
	 */
	private static void nuevoLibro() {
		System.out.println("Los campos con * son obligatorios:");
		
		Libro libro = new Libro(
				solicitarCadena("Título* : "),
				solicitarCadena("Autor* : "),
				solicitarCadena("Editorial* : "),
				solicitarCategoria("Introduce una categoría* : "),
				solicitarCadena("Año de publicación*: "));
		libroDao.guardar(libro);
		
		System.out.println("Se ha creado el nuevo arrendador con ID: "+ libro.getId() +".");
	}
	
	/**
	 * Solicita datos y da de alta un nuevo préstamo
	 */
	private static void nuevoPrestamo() throws BookingException {
		Prestamo prestamo = new Prestamo(
				new Date(),
				solicitarEntero("Duración en días: "),
				solicitarArrendador("Introduce la ID del arrendador: "));
		ArrayList<Stack> stacks = nuevaListaStacks();
		// XXX: Aqui se le llama
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
		mostrarArrendadores();
		
		Arrendador arrendador = solicitarArrendador("Introduzca la ID del arrendador: ");
		arrendadorDao.borrar(arrendador);
		System.out.println("Se ha borrado el con id " + arrendador.getId());
	}
	
	/**
	 * Muestra los detalles del arrendador solicitado
	 * @throws BookingException
	 */
	private static void consultarArrendador() throws BookingException {
		mostrarArrendadores();
		
		int id = solicitarEntero("Introduce la ID del arrendador: ");
		Arrendador arrendador = arrendadorDao.obtener(id);
		
		System.out.println("\n" + arrendador);
	}
	
	/**
	 * Muestra los detalles del libro solicitado
	 * @throws BookingException
	 */
	private static void consultarLibro() throws BookingException {
		mostrarLibros();
		
		int id = solicitarEntero("Introduce la ID del libro: ");
		Libro libro = libroDao.obtener(id);
		
		System.out.println("\n" + libro);
	}
	
	/**
	 * Muestra los detalles del prestamo solicitado
	 * @throws BookingException
	 */
	private static void consultarPrestamo() throws BookingException {
		mostrarPrestamos();
		
		int id = solicitarEntero("Introduce la ID del préstamo: ");
		Prestamo prestamo = prestamoDao.obtener(id);
		
		System.out.println("\n" + prestamo);
	}

}
