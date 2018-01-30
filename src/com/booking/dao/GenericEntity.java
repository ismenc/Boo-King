package com.booking.dao;

import org.hibernate.Session;

import com.booking.persistencia.HibernateUtil;

/**
 * Clase Abstracta que contiene operaciones básicas con la BD.
 * @author usuario
 *
 * @param <T>
 */
public class GenericEntity<T> {
	
	public void guardar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(entidad);
		session.getTransaction().commit();
	}
	
	public  void borrar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(entidad);
		session.getTransaction().commit();
	}
	
	public void actualizar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(entidad);
		session.getTransaction().commit();
	}

}
