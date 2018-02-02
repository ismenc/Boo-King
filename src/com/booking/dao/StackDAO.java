package com.booking.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.booking.persistencia.HibernateUtil;
import com.booking.persistencia.Stack;

/**
 * Define las operaciones sobre stacks en la base de datos
 * @author Ismael Núñez
 *
 */
public class StackDAO extends GenericEntity<Stack> {
	
	public StackDAO() {
		super(Stack.class);
	}
	
	/**
	 * Obtiene la cantidad total de libros prestados.
	 * @return Cantidad de libros prestados.
	 */
	public int totalLibrosPrestados() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT SUM(s.cantidad) FROM Stack s");
		
		return ((Number) query.uniqueResult()).intValue();
	}
}
