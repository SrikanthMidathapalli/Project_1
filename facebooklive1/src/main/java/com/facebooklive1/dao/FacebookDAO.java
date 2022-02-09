package com.facebooklive1.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.facebooklive1.entity.FacebookUser;
import com.facebooklive1.entity.TimeLine;

public class FacebookDAO implements FacebookDAOInterface {
	
	private SessionFactory sf;
	 
	public FacebookDAO() {
		sf=new Configuration().configure().buildSessionFactory();
	}

	public int createProfileDAO(FacebookUser fb) {
		int i=0;
		Session s=sf.openSession();
		EntityTransaction et=s.getTransaction();
		et.begin();
		s.save(fb);
		et.commit();
		i=1;
		
		
		return i;
	}

	public int loginProfileDAO(FacebookUser fb) {
		int i = 0;
		Session s = sf.openSession();
		Query q = s.createQuery("from com.facebooklive1.entity.FacebookUser f where f.email=:em and f.password=:pw");
		q.setParameter("em", fb.getEmail());
		q.setParameter("pw", fb.getPassword());
		FacebookUser ff = (FacebookUser) q.getSingleResult();

		if (ff != null) {
			i = 1;
		}

		return i;
	}

	public List<TimeLine> timelineDAO(FacebookUser fb) {
		Session s = sf.openSession();
		Query q = s.createQuery("from com.facebooklive1.entity.TimeLine f");
				
		List<TimeLine> ll=q.getResultList();
		System.out.println(ll.size());
		return ll;
	}

	
	public List<FacebookUser> searchProfileDAO(FacebookUser fb) {
		
		System.out.println("DAOSearch");
		System.out.println(fb.getEmail());
		
		Session s=sf.openSession();
		Query q=s.createQuery("from com.facebooklive1.entity.FacebookUser f where f.email=:e");
		
		q.setParameter("e","%"+fb.getEmail()+"%");
		
		List<FacebookUser> ff=q.getResultList();
		
		System.out.println(ff.size());
		
		return ff;
	}
	
	

	


	
	public int editProfileDAO(FacebookUser fb) {
		int i=0;
		Session s=sf.openSession();
		Query q=s.createQuery("from com.facebooklive1.entity.FacebookUser f where f.email=:email and f.password=:password");
		q.setParameter("name", fb.getName());
		q.setParameter("address", fb.getAddress());
		q.setParameter("email", fb.getEmail());
		q.setParameter("password", fb.getPassword());
		FacebookUser ff=(FacebookUser)q.getSingleResult();
		if(ff!=null) {
			i=1;

		}
		return i;

		
		
		
		
		
	}

	public int deleteProfileDAO(FacebookUser fb) {
		int i=0;
		System.out.println(fb.getEmail());

		Session s=sf.openSession();
		EntityTransaction et=s.getTransaction();
		et.begin();
		Query q=s.createQuery("delete com.facebooklive1.entity.FacebookUser f where f.email=:e");
		q.setParameter("e",fb.getEmail());

		
		i=q.executeUpdate();
		
		et.commit();
		
	
		
		return i;
	}

	public FacebookUser viewProfileDAO(FacebookUser fb) {
		int i=0;
		
		
		Session s=sf.openSession();
		Query q=s.createQuery("from com.facebooklive1.entity.FacebookUser f where  f.email=:email ");
		q.setParameter("email",fb.getEmail());
		
		
		FacebookUser ff=(FacebookUser)q.getSingleResult();
		
		return ff;
	}

}
