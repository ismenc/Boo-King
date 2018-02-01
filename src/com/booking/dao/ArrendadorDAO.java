package com.booking.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.booking.persistencia.Arrendador;
import com.booking.persistencia.HibernateUtil;

/**
 * Define las operaciones sobre arrendador en la base de datos.
 * @author Ismael Núñez
 *
 */
public class ArrendadorDAO extends GenericEntity<Arrendador> {
	
	// FIXME: encuentra clase null
	private static Class claseAsociada = Arrendador.class;
	
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
