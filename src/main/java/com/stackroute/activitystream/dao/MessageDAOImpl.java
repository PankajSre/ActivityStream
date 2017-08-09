package com.stackroute.activitystream.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.activitystream.model.Message;


@Repository(value="messageDAO")
@Transactional
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean sendMessage(Message message) {
		try {
			sessionFactory.getCurrentSession().save(message);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getAllMessages() {
		
		return sessionFactory.getCurrentSession().createQuery("from Message").list();
	}

}
