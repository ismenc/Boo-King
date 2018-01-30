package com.booking.modelo;

import java.util.ArrayList;
import java.util.Date;
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

/**
 * Métodos para mostrar por pantalla y solicitar datos
 * @author Ismael Núñez
 *
 */
public class Utilidades {
	
	private static final int TOTAL_OPCIONES = 9;

	@SuppressWarnings("unused")
	private static Session session;
	private static Scanner teclado = new Scanner(System.in);
	private static LibroDAO libroDao = new LibroDAO();
	private static PrestamoDAO prestamoDao = new PrestamoDAO();
	private static ArrendadorDAO arrendadorDao = new ArrendadorDAO();
	
	/* ========================= Métodos Básicos ========================= */
	
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
		mostrarListaBreve(obtenerArrendadores());
		
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
		mostrarListaBreve(obtenerLibros());
		
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

		Stack stack;
		
		do {
			stack = new Stack(
					solicitarLibro("Introduce la ID del libro: "),
					solicitarEntero("Introduce la cantidad: "),
					null /* Aquí va id prestamo */);
			stacks.add(stack);
			respuesta = solicitarCadena("¿Desea seguir introduciendo datos? (Si/No)");
		}while(respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("sí"));
		
		return stacks;
	}
	
	/* ========================= Métodos para mostrar ========================= */
	
	/**
	 * Método genérico para mostrar en pantalla la lista de un objeto mediante toString.
	 * @param lista
	 * @throws BookingException 
	 */
	public static <T> void mostrarListaBreve(List<T> lista) throws BookingException {
		if(lista.isEmpty())
			throw new BookingException("Error. No hay datos que mostrar.");
		
		System.out.println("---------------------- Lista ----------------------");
		
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = lista.iterator(); iterator.hasNext();) {
			@SuppressWarnings("unchecked")
			T entidad = (T) iterator.next();
			System.out.println(entidad.toString());
		}
		
		System.out.println("---------------------------------------------------");
	}
	
	/**
	 * Devuelve una lista con todos los arrendadores.
	 */
	public static List<Arrendador> obtenerArrendadores() {
		return arrendadorDao.obtenerLista();
	}
	
	/**
	 * Devuelve una lista con todos los libros.
	 */
	public static List<Libro> obtenerLibros() {
		return libroDao.obtenerLista();
	}
	
	/**
	 * Devuelve una lista con todos los préstamos.
	 */
	public static List<Prestamo> obtenerPrestamos() {
		return prestamoDao.obtenerLista();
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
	
	/* ========================= Gestión de opciones ========================= */
	
	/**
	 * Solicita datos del arrendador y lo guarda en la BD
	 */
	public static void nuevoArrendador() {
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
	public static void nuevoLibro() {
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
	public static void nuevoPrestamo() throws BookingException {
		Prestamo prestamo = new Prestamo(
				new Date(),
				solicitarEntero("Duración en días: "),
				solicitarArrendador("Introduce la ID del arrendador: "));
		
		ArrayList<Stack> stacks = nuevaListaStacks();
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
	public static void borrarArrendador() throws BookingException {
		Arrendador arrendador = solicitarArrendador("Introduzca la ID del arrendador: ");
		arrendadorDao.borrar(arrendador);
		System.out.println("Se ha borrado el arrendador con id " + arrendador.getId() + " y sus préstamos asociados.");
	}
	
	/**
	 * Actualiza los datos de un libro ya existente.
	 * @throws BookingException
	 */
	public static void modificarLibro() throws BookingException {
		Libro libro = solicitarLibro("Introduzca la ID del libro: ");
		System.out.println(libro.informacionDetalle());
		
		libro = new Libro(
				solicitarCadena("Título* : "),
				solicitarCadena("Autor* : "),
				solicitarCadena("Editorial* : "),
				solicitarCategoria("Introduce una categoría* : "),
				solicitarCadena("Año de publicación*: "));
		
		libroDao.actualizar(libro);
	}
		
	/**
	 * Muestra los detalles del arrendador solicitado
	 * @throws BookingException
	 */
	public static void consultarArrendador() throws BookingException {
		List<Arrendador> arrendadores = obtenerArrendadores();
		mostrarListaBreve(arrendadores);
		
		int id = solicitarEntero("Introduce la ID del arrendador: ");
		Arrendador arrendador = arrendadorDao.obtener(id);
		
		System.out.println("\n" + arrendador.informacionDetalle());
	}
	
	/**
	 * Muestra los detalles del libro solicitado
	 * @throws BookingException
	 */
	public static void consultarLibro() throws BookingException {
		List<Libro> libros = obtenerLibros();
		mostrarListaBreve(libros);
		
		int id = solicitarEntero("Introduce la ID del libro: ");
		Libro libro = libroDao.obtener(id);
		
		System.out.println("\n" + libro.informacionDetalle());
	}
	
	/**
	 * Muestra los detalles del prestamo solicitado
	 * @throws BookingException
	 */
	public static void consultarPrestamo() throws BookingException {
		List<Prestamo> prestamos = obtenerPrestamos();
		mostrarListaBreve(prestamos);
		
		int id = solicitarEntero("Introduce la ID del préstamo: ");
		Prestamo prestamo = prestamoDao.obtener(id);
		
		System.out.println("\n" + prestamo.informacionDetalle());
	}
}
