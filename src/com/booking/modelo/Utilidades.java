package com.booking.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import com.booking.dao.ArrendadorDAO;
import com.booking.dao.LibroDAO;
import com.booking.dao.PrestamoDAO;
import com.booking.persistencia.Arrendador;
import com.booking.persistencia.HibernateUtil;
import com.booking.persistencia.Libro;
import com.booking.persistencia.Prestamo;
import com.booking.persistencia.Stack;

public class Utilidades {
	
	private static final int TOTAL_OPCIONES = 9;

	@SuppressWarnings("unused")
	private static Session session;
	private static Scanner teclado = new Scanner(System.in);
	private static LibroDAO libroDao = new LibroDAO();
	private static PrestamoDAO prestamoDao = new PrestamoDAO();
	private static ArrendadorDAO arrendadorDao = new ArrendadorDAO();
	
	public static void cerrarSesion() {
		HibernateUtil.closeSessionFactory();
	}

	public static void configurarSesion() {
		HibernateUtil.buildSessionFactory();
		HibernateUtil.openSessionAndBindToThread();
		session = HibernateUtil.getSessionFactory().getCurrentSession();

	}
	
	public static int solicitarEntero(String msg) {
		int id;
		System.out.print(msg);
		id = Integer.parseInt(teclado.nextLine());
		return id;
	}
	
	public static String solicitarCadena(String msg) {
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
	public static Categoria solicitarCategoria(String msg) {
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
	public static Arrendador solicitarArrendador(String msg) throws BookingException{
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
	public static Libro solicitarLibro(String msg) throws BookingException{
		mostrarLibros();
		
		int idLibro = solicitarEntero(msg);
		
		return libroDao.obtener(idLibro);
	}
	
	/**
	 * Menu con el que crearemos una lista de Stacks(pilas) introduciendo datos
	 * @return Lista de Stacks(pilas)
	 * @throws BookingException
	 */
	public static ArrayList<Stack> nuevaListaStacks() throws BookingException{
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
	public static void mostrarArrendadores() {
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
	public static void mostrarLibros() {
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
	public static void mostrarPrestamos() {
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
	
	public static int mostrarMenu() {
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
}
