package com.booking.dao;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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

}
