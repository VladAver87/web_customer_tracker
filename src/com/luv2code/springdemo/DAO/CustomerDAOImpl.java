package com.luv2code.springdemo.DAO;


import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
		
		List<Customer> customers = query.getResultList();
		
				
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(customer);

	}

	@Override
	public Customer getCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Customer customer = session.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> query = session.createQuery("DELETE FROM Customer WHERE id=:customerId");
		
		query.setParameter("customerId", id);
		
		query.executeUpdate();

	}

	@Override
	public List<Customer> searchCustomer(String theSearchName) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> query = null;
		
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			
			query = session.createQuery("FROM Customer WHERE lower(firstName) LIKE :Name OR lower(lastName) LIKE :Name", Customer.class);
			query.setParameter("Name", "%" + theSearchName.toLowerCase() + "%");
		} else {
			
			query = session.createQuery("from Customer", Customer.class);
		}
		
		List<Customer> result = query.getResultList();
		
		
		return result;
	}

}
