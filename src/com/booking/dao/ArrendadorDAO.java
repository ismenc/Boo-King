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

	public Arrendador obtener(int idArrendador) throws BookingException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Arrendador arrendador = (Arrendador) session.get(Arrendador.class, idArrendador);
		
		if(arrendador == null)
			throw new BookingException("Error. El arrendador "+ idArrendador + " no existe.");
		
		return arrendador;
	}

	@SuppressWarnings("unchecked")
	public List<Arrendador> obtenerLista()  {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Criteria criterio = session.createCriteria(Arrendador.class);
		
		return criterio.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Arrendador> obtenerPorNombre(String nombre) throws BookingException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("FROM Arrendador WHERE nombre LIKE '%" + nombre+"%'");
		
		return query.list();
	}
	
}
