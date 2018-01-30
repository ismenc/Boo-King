package com.booking.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.booking.persistencia.HibernateUtil;
import com.booking.persistencia.Stack;

/**
 * Define las operaciones sobre stacks en la base de datos
 * @author Ismael Núñez
 *
 */
public class StackDAO extends GenericEntity<Stack> {

	
	@SuppressWarnings("unchecked")
	public List<Stack> obtenerLista()  {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Criteria criterio = session.createCriteria(Stack.class);
		
		return criterio.list();
	}
}
