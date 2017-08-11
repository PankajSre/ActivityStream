package com.stackroute.activitystream.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.activitystream.model.Circle;

@Repository(value = "circleDAO")
@Transactional
public class CircleDAOImpl implements CircleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private Circle circle;

	@Override
	public boolean addCircle(Circle circle) {
		try {
			sessionFactory.getCurrentSession().save(circle);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean addUserToCircle(String emailId, String circleName) {
		try {

			circle.setOwnerEmailId(emailId);
			circle.setCircleName(circleName);
			sessionFactory.getCurrentSession().save(circle);
			return true;
		} catch (HibernateException e) {

			e.printStackTrace();
			return false;
		}

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Circle> getAllCircles() {
		String hql = "from Circle where status=true";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public Circle getCircleByName(String circleName) {

		return sessionFactory.getCurrentSession().get(Circle.class, circleName);
	}

	@Override
	public boolean deleteCircle(Circle circle) {
		try {
			sessionFactory.getCurrentSession().delete(circle);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCircle(Circle circle) {

		try {
			sessionFactory.getCurrentSession().update(circle);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

}
