package com.booking.dao;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.booking.modelo.BookingException;
import com.booking.persistencia.HibernateUtil;

/**
 * Clase Abstracta que contiene operaciones básicas con la BD.
 * @author Ismael Núñez
 *
 * @param <T>
 */
public class GenericEntity<T> {
	
	private Session session;
	protected Class<T> claseAsociada;
	
	public GenericEntity(Class clase) {
		claseAsociada = clase;
	}
	
	public Class getClaseAsociada() {
		return claseAsociada;
	}

	public void setClaseAsociada(Class claseAsociada) {
		this.claseAsociada = claseAsociada;
	}


	/**
	 * Método que inserta un objeto en la base de datos
	 * @param entidad Objeto que se isertará.
	 * @throws BookingException
	 */
	public void guardar(T entidad) throws BookingException {
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(entidad);
			session.getTransaction().commit();
		} 
		// Excepciones provocados por Hibernate validator
		catch (ConstraintViolationException cve) {
			session.getTransaction().rollback();
			StringBuilder error = new StringBuilder(300);
			
			error.append("No se ha podido insertar el "+ entidad.getClass().getName() + " debido a los siguientes errores:\n");
			for (ConstraintViolation<?> constraintViolation : cve.getConstraintViolations()) {
				error.append("Para el campo " + constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage() + ".\n");
			}
			throw new BookingException(error.toString());
		}
	}
	
	/**
	 * Método que borra un objeto de la base de datos.
	 * @param entidad Objeto que queremos borrar
	 */
	public  void borrar(T entidad) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(entidad);
		session.getTransaction().commit();
	}
	
	/**
	 * Método que actualiza un objeto de la base de datos.
	 * @param entidad Objeto que queremos actualizar.
	 * @throws BookingException
	 */
	public void actualizar(T entidad) throws BookingException {
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(entidad);
			session.getTransaction().commit();
		}catch (ConstraintViolationException cve) {
			session.getTransaction().rollback();
			StringBuilder error = new StringBuilder(300);
			
			error.append("No se ha podido insertar el "+ entidad.getClass().getName() + " debido a los siguientes errores:\n");
			for (ConstraintViolation<?> constraintViolation : cve.getConstraintViolations()) {
				error.append("Para el campo " + constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage() + ".\n");
			}
			throw new BookingException(error.toString());
		}
	}
	
	/**
	 * Obtiene el objeto préstamo deseado.
	 * @param id ID del préstamo que queremos obtener.
	 * @return Objeto préstamo.
	 * @throws BookingException
	 */
	@SuppressWarnings("unchecked")
	public T obtener(int id) throws BookingException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		T entidad = (T) session.get(claseAsociada, id);
		
		if(entidad == null)
			throw new BookingException("Error. El "+ claseAsociada.getName() + " " + id + " no existe.");
		
		return entidad;
	}
	
	/**
	 * Obtiene una lista con todos los objetos de la clase especificada.
	 * @param clase Clase de la que obtener la lista.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> obtenerTodos()  {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Criteria criterio = session.createCriteria(claseAsociada);
		
		return criterio.list();
	}

}
