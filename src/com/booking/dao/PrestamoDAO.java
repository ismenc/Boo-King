package com.booking.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import com.booking.modelo.BookingException;
import com.booking.persistencia.HibernateUtil;
import com.booking.persistencia.Prestamo;

/**
 * Define las operaciones sobre prestamos en la base de datos.
 * @author Ismael Núñez
 *
 */
public class PrestamoDAO extends GenericEntity<Prestamo> {

	@SuppressWarnings("unchecked")
	public List<Prestamo> obtenerLista() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Criteria criterio = session.createCriteria(Prestamo.class);
		
		return criterio.list();
	}

	public Prestamo obtener(int id) throws BookingException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Prestamo prestamo = (Prestamo) session.get(Prestamo.class, id);
		
		if(prestamo == null)
			throw new BookingException("Error. El préstamo "+ id + " no existe.");
		
		return prestamo;
	}

	@SuppressWarnings("unchecked")
	public List<Prestamo> obtenerPorNombre(String nombre) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT p FROM Prestamo p, Arrendador a WHERE a.nombre='" + nombre+"' AND p.arrendador=a");
		
		return query.list();
	}


	public int prestamosEnUnAno(int ano) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(p) FROM Prestamo p WHERE p.fecha LIKE '"+ ano +"%'");
		
		return ((Number) query.uniqueResult()).intValue();
	}
	
	public int librosPrestadosEnUnAno(int ano) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT SUM(s.cantidad) FROM Prestamo p, Stack s WHERE p.fecha LIKE '"+ ano +"%' AND p=s.prestamo");
		
		return ((Number) query.uniqueResult()).intValue();
	}
	
}
