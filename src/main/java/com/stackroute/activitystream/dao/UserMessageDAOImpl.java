package com.stackroute.activitystream.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.activitystream.model.UserMessage;


@Repository(value="userMessageDAO")
@Transactional
public class UserMessageDAOImpl implements UserMessageDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean sendMessage(UserMessage userMessage) {
		
		try {
			Session session=sessionFactory.openSession();
			session.save(userMessage);
			session.flush();
			session.close();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteMessage(int messageId, String emailId) {
		try {
			//String sql="delete from UserMessage where messageId=messageId and emailId='"+emailId+"' and circleId=circleId";
			if(checkforMessageAuthenticity(messageId,emailId))
			{
			sessionFactory.getCurrentSession().delete(getMessageByMessageId(messageId));
			return true;
			}
		} catch (HibernateException e) {
			
			e.printStackTrace();
		}
		return false;
	}

	private boolean checkforMessageAuthenticity(int messageId, String emailId) {
	    String hql="from UserMessage where messageId=messageId and receiverEmailId='"+emailId+"'";
	    Query query=sessionFactory.getCurrentSession().createQuery(hql);
	    List<UserMessage> userMessage=(List<UserMessage>) query.list();
	    if(userMessage !=null)
	    {
	    	return true;
	    }
		return false;
	}

	@Override
	public List<UserMessage> getMyMessages(String emailId) {
		
		String hql="from UserMessage where receiverEmailId='"+emailId+"'";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<UserMessage> userMessages=query.list();
		return userMessages;
	}

	@Override
	public UserMessage getMessageByMessageId(int messageId) {
		
		return sessionFactory.getCurrentSession().get(UserMessage.class, messageId);
	}

	@Override
	public List<UserMessage> getAllMessageByCircleName(String circleName) {
		String hql="from UserMessage where circleName='"+circleName+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		List<UserMessage> messageList= query.list();
		return messageList;
	}

}
