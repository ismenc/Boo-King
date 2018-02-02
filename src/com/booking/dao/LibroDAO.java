package com.booking.dao;


import com.booking.persistencia.Libro;

/**
 * Define las operaciones sobre libros en la base de datos.
 * @author Ismael Núñez
 *
 */
public class LibroDAO extends GenericEntity<Libro> {

	public LibroDAO() {
		super(Libro.class);
		}
	
}