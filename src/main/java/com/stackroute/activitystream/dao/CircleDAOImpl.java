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
		//in case of exception, you can return false.  So return false should be in catch block
		return false;
	}

	@Override
	public boolean addUserToCircle(String emailId, String circleName) {
		try {
			//Try to avoid creating object by using 'new' operator
			Circle circle = new Circle();
			//what is this owner emailID.  Method name says addUserToCircle.
			//You already defined addCircle in this DAO.What this method doing?
			circle.setOwnerEmailId(emailId);
			circle.setCircleName(circleName);
			sessionFactory.getCurrentSession().save(circle);
			return true;
		} catch (HibernateException e) {

			e.printStackTrace();
		}
		//this statement should be in catch statement.
		return false;
	}

	@Override
	public List<Circle> getAllCircles() {
		//Better to use Criteria if where condition present.
		String hql="from Circle where status=true";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	//method name should change.
	@Override
	public List<Circle> getCircleByUser(String userEmail) {
		//Better to use Criteria if where condition present.
		//Do you want to retrieve what are the circles created by a particular person?
		String sql="from Circle where ownerEmailId='"+userEmail+"'";
		return sessionFactory.getCurrentSession().createQuery(sql).list();
	}

}
