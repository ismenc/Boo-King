package com.booking.modelo;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import com.booking.dao.ArrendadorDAO;
import com.booking.dao.LibroDAO;
import com.booking.dao.PrestamoDAO;
import com.booking.dao.StackDAO;
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
	
	public static final int TOTAL_OPCIONES = 13;
	public static final int ANO_ACTUAL = Year.now().getValue();
	public static final int ANO_APERTURA = 2010;

	@SuppressWarnings("unused")
	private static Session session;
	private static Scanner teclado = new Scanner(System.in);
	private static LibroDAO libroDao = new LibroDAO();
	private static StackDAO stackDao = new StackDAO();
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
	
	public static int solicitarEntero(String msg) throws BookingException {
		int entero;
		System.out.print(msg);
		entero = Integer.parseInt(teclado.nextLine());
		
		if(entero < 1)
			throw new BookingException("El número introducido debe ser mayor que 0.");
		
		return entero;
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
	 * @throws BookingException 
	 */
	public static Categoria solicitarCategoria(String msg) throws BookingException {
		int i = 1, opcion;
		
		System.out.print("CATEGORÍAS:");
		do{
			System.out.print(" ["+i+"]-" + Categoria.values()[i-1]);
			i++;
		}while(i <= Categoria.values().length);
		opcion = solicitarEntero("\n" + msg);
		
		if(opcion > Categoria.values().length)
			throw new BookingException("Debe elegir una categoría de la lista.");
		
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
	public static ArrayList<Stack> nuevaListaStacks() {
		String respuesta;
		ArrayList<Stack> stacks = new ArrayList<Stack>();

		Stack stack;
		
		do {
			try {
				stack = new Stack(
						solicitarLibro("Introduce la ID del libro: "),
						solicitarEntero("Introduce la cantidad: "),
						null /* Aquí va id prestamo */);
				stacks.add(stack);
			}catch (BookingException e) {
				System.out.println("Debe introducir un libro que esté en la base de datos.");
			}
			respuesta = solicitarCadena("¿Desea seguir añadiendo libros? (Si/No)");
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
			System.out.println("| [9] Buscar arrendadores nombre|");
			System.out.println("| [10] Buscar prestamos nombre  |");
			System.out.println("| [11] Préstamos por año\t|");
			System.out.println("| [12] Estadísticas globales    |");
			System.out.println("| [13] Salir\t\t\t|");
			System.out.println("+-------------------------------+");
			opcion = Integer.parseInt(teclado.nextLine());
		} while (opcion < 1 || opcion > TOTAL_OPCIONES);

		return opcion;
	}
	
	/* ========================= Gestión de opciones ========================= */
	
	/**
	 * Solicita datos del arrendador y lo guarda en la BD
	 * @throws BookingException 
	 */
	public static void nuevoArrendador() throws BookingException {
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
	 * @throws BookingException 
	 */
	public static void nuevoLibro() throws BookingException {
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
	public static void consultarDetallesArrendador() throws BookingException {
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
	public static void consultarDetallesLibro() throws BookingException {
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
	public static void consultarDetallesPrestamo() throws BookingException {
		List<Prestamo> prestamos = obtenerPrestamos();
		mostrarListaBreve(prestamos);
		
		int id = solicitarEntero("Introduce la ID del préstamo: ");
		Prestamo prestamo = prestamoDao.obtener(id);
		
		System.out.println("\n" + prestamo.informacionDetalle());
	}
	
	/* ========================= Gestión de opciones avanzadas ========================= */

	/**
	 * Consulta los arrendadores por nombre. Puede resultar en varios
	 * @throws BookingException 
	 */
	public static void consultarArrendadoresPorNombre() throws BookingException {
		String nombre = solicitarCadena("Introduce el nombre: ");
		List<Arrendador> resultado = arrendadorDao.obtenerPorNombre(nombre);
		
		if(resultado.isEmpty())
			throw new BookingException("No se ha encontrado ningún arrendador con ese nombre.");
		
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = resultado.iterator(); iterator.hasNext();) {
			Arrendador arrendador = (Arrendador) iterator.next();
			System.out.println(arrendador.informacionDetalle());
		}
	}
	
	/**
	 * Consulta los préstamos de una persona por nombre.
	 * @throws BookingException 
	 */
	public static void consultarPrestamosPorNombre() throws BookingException {
		String nombre = solicitarCadena("Introduce el nombre: ");
		List<Prestamo> resultado = prestamoDao.obtenerPorNombre(nombre);
		
		if(resultado.isEmpty())
			throw new BookingException("No se ha encontrado ningún arrendador con ese nombre.");
			
		System.out.println("==============> Préstamos de "+ nombre+ " <==============");
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = resultado.iterator(); iterator.hasNext();) {
			Prestamo prestamo = (Prestamo) iterator.next();
			System.out.println(prestamo.informacionDetalle());
		}
	}

	/**
	 * @throws BookingException 
	 * 
	 */
	public static void prestamosPorAno() throws BookingException {
		int ano = solicitarEntero("Introduce el año: ");
		
		if(ano > ANO_ACTUAL || ano < ANO_APERTURA)
			throw new BookingException("Debe introducir un año válido ("+ANO_APERTURA+" min, "+ANO_ACTUAL+" máx).");
		
		int prestamos = prestamoDao.prestamosEnUnAno(ano);
		int totalLibros = prestamoDao.librosPrestadosEnUnAno(ano);
		
		System.out.println("En el año "+ano+" hubo "+ prestamos + " préstamos y un total de "+ totalLibros + " libros prestados.");
	}

	/**
	 * Muestra estadísticas globales de nuestra base de datos.
	 */
	public static void estadisticasPrestamo() {
		// TODO: posibles errores (como que no haya arrendadores)
		int numeroArrendadores = arrendadorDao.totalArrendadores();
		String fechaPrimerPrestamo = prestamoDao.fechaPrimerPrestamo();
		int prestamos = prestamoDao.totalPrestamos();
		int librosPrestados = stackDao.totalLibrosPrestados();
		double mediaLibrosPorArrendador = prestamoDao.mediaLibrosPrestados();
		
		System.out.println("Ahí van algunas estadísticas sobre nuestro engocio: \n"
				+ " Total de arrendadores registrados: "+ numeroArrendadores + "\n"
				+ " Fecha del primer préstamo realizado: "+ fechaPrimerPrestamo + "\n"
				+ " Total de préstamos realizados: "+ prestamos + "\n"
				+ " Total de libros que han sido prestados: "+ librosPrestados + "\n"
				+ " Media de libros prestados por persona: " + mediaLibrosPorArrendador);
	}
}
