package com.stackroute.activitystream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.model.UserCircle;

//move unwanted methods.

@Repository(value = "userCircleDAO")
@Transactional
public class UserCircleDAOImpl implements UserCircleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserCircle userCircle;

	@Override
	public boolean addCircle(UserCircle circle) {

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
			userCircle = new UserCircle();
			userCircle.setOwnerEmailId(emailId);
			userCircle.setCircleName(circleName);
			sessionFactory.getCurrentSession().save(userCircle);
			return true;
		} catch (HibernateException e) {

			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean deleteUserFromCircle(String emailId, String circleName) {
		try {
			userCircle = new UserCircle();
			userCircle.setOwnerEmailId(emailId);
			userCircle.setCircleName(circleName);
			sessionFactory.getCurrentSession().delete(userCircle);
			return true;
		} catch (HibernateException e) {

			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean deleteCircle(UserCircle circle) {

		try {

			circle.setStatus(false);
			sessionFactory.getCurrentSession().update(circle);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<UserCircle> getCircleByUser(String ownerId) {
		String sql = "from UserCircle where ownerEmailId='" + ownerId + "'";
		return sessionFactory.getCurrentSession().createQuery(sql).list();
	}

	@Override
	public List<String> getUserByCircle(String circleName) {
		String sql = "from UserCircle where circleName=circleName";
		@SuppressWarnings("unchecked")
		List<UserCircle> userCircle = sessionFactory.getCurrentSession().createQuery(sql).list();
		List<String> users = new ArrayList<>();
		for (UserCircle s : userCircle)
			users.add(s.getOwnerEmailId());
		return users;
	}

	@Override
	public UserCircle getCircleByName(String circleName) {

		return sessionFactory.getCurrentSession().get(UserCircle.class, circleName);
	}

}
