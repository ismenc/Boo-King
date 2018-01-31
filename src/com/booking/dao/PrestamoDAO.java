package com.booking.dao;

import java.sql.Date;
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

	/* -------------------- Consultas estadisticas -------------------- */
	
	public int prestamosEnUnAno(int ano) throws BookingException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(p) FROM Prestamo p WHERE p.fecha LIKE '"+ ano +"%'");
		
		if(query.uniqueResult() == null)
			throw new BookingException("No hubo préstamos en el año " + ano + ".");
		
		return ((Number) query.uniqueResult()).intValue();
	}
	
	public int librosPrestadosEnUnAno(int ano) throws BookingException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
		Query query = session.createQuery("SELECT SUM(s.cantidad) FROM Prestamo p, Stack s WHERE p.fecha LIKE '"+ ano +"%' AND p=s.prestamo");
		
		if(query.uniqueResult() == null)
			throw new BookingException("No hubo libros prestados en el año " + ano + ".");
		
		return ((Number) query.uniqueResult()).intValue();
	}
	
	public int totalPrestamos() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(p) FROM Prestamo p");
		
		return ((Number) query.uniqueResult()).intValue();
	}
	
	public String fechaPrimerPrestamo() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT MIN(p.fecha) FROM Prestamo p");
		
		return ((Date) query.uniqueResult()).toString();
	}
	
	public double mediaLibrosPrestados() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		int sumaLibros, sumaPersonas;
		
		/* 
		 * Lo intenté hacer tal que: SELECT AVG(tabla.suma) 
		 * FROM (SELECT SUM(cantidad) suma FROM arrendador a, prestamo p, stack s 
		 * WHERE a.id=p.idArrendador AND s.idPrestamo=p.id GROUP BY a.id) tabla
		 * Pero hibernate no soporta subqueries en el from
		*/
		Query queryLibros = session.createQuery("SELECT SUM(cantidad) FROM Stack s");
		sumaLibros = ((Number) queryLibros.uniqueResult()).intValue();
		
		Query queryPersonas = session.createQuery("SELECT COUNT(a) FROM Arrendador a");
		sumaPersonas = ((Number) queryPersonas.uniqueResult()).intValue();
				
		return (double)sumaLibros/sumaPersonas;
	}
}
