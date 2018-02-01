package com.booking.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import com.booking.modelo.BookingException;
import com.booking.persistencia.Arrendador;
import com.booking.persistencia.HibernateUtil;

/**
 * Define las operaciones sobre arrendador en la base de datos.
 * @author Ismael Núñez
 *
 */
public class ArrendadorDAO extends GenericEntity<Arrendador> {

	/**
	 * Obtiene un arrendador de la base de datos.
	 * @param idArrendador ID de la persona que queremos obtener.
	 * @return Objeto arrendador.
	 * @throws BookingException
	 */
	public Arrendador obtener(int idArrendador) throws BookingException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Arrendador arrendador = (Arrendador) session.get(Arrendador.class, idArrendador);
		
		if(arrendador == null)
			throw new BookingException("Error. El arrendador "+ idArrendador + " no existe.");
		
		return arrendador;
	}

	/**
	 * Obtiene una lista con todos los objetos arrendador.
	 * @return Lista de arrendadores.
	 */
	@SuppressWarnings("unchecked")
	public List<Arrendador> obtenerTodos()  {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Criteria criterio = session.createCriteria(Arrendador.class);
		
		return criterio.list();
	}
	
	/**
	 * Obtiene todos los arrendadores cuyo nombre coincida parcialmente con la búsqueda.
	 * @param nombre Cadena de búsqueda en el campo nombre.
	 * @return Lista de arrendadores coincidentes.
	 */
	@SuppressWarnings("unchecked")
	public List<Arrendador> obtenerPorNombre(String nombre) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("FROM Arrendador WHERE nombre LIKE '%" + nombre+"%'");
		
		return query.list();
	}
	
	/**
	 * Obtiene la cantidad total de arrendadores que hay en la base de datos.
	 * @return Número total de arrendadores registrados.
	 */
	public int totalArrendadores() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(a) FROM Arrendador a");
		
		return ((Number) query.uniqueResult()).intValue();
	}
	
}
