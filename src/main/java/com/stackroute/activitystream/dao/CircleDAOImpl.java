package com.stackroute.activitystream.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.activitystream.model.Circle;
@Repository(value="circleDAO")
@Transactional
public class CircleDAOImpl implements CircleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean addCircle(Circle circle) {
		try {
			sessionFactory.getCurrentSession().save(circle);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addUserToCircle(String emailId, String circleName) {
		try {
			Circle circle = new Circle();
			circle.setOwnerEmailId(emailId);
			circle.setCircleName(circleName);
			sessionFactory.getCurrentSession().save(circle);
			return true;
		} catch (HibernateException e) {

			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Circle> getAllCircles() {
		String hql="from Circle where status=true";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public List<Circle> getCircleByUser(String userEmail) {
		String sql="from Circle where ownerEmailId='"+userEmail+"'";
		return sessionFactory.getCurrentSession().createQuery(sql).list();
	}

}
