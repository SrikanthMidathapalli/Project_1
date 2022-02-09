package com.facebooklive1.service;

import java.util.List;

import com.facebooklive1.dao.FacebookDAOInterface;
import com.facebooklive1.entity.FacebookUser;
import com.facebooklive1.entity.TimeLine;
import com.facebooklive1.utility.DAOFactory;

public class FacebookService implements FacebookServiceInterface {
	private FacebookDAOInterface fd;
	public FacebookService() {
		fd=DAOFactory.createObject();
		
	}

	public int createProfileService(FacebookUser fb) {
		
		return fd.createProfileDAO(fb);
	}

	public int loginProfileService(FacebookUser fb) {
		// TODO Auto-generated method stub
		return fd.loginProfileDAO(fb);
	}

	
	public List<TimeLine> timeLineService(FacebookUser fb) {
		return fd.timelineDAO(fb);
	}

	public List<FacebookUser> searchProfileService(FacebookUser fb) {
		return fd.searchProfileDAO(fb);
	}

	public int editprofileService(FacebookUser fb) {
		return fd.editProfileDAO(fb);
	}

	public int deleteProfileService(FacebookUser fb) {
		return fd.deleteProfileDAO(fb);
		
	}

	public FacebookUser viewProfileService(FacebookUser fb) {
		return fd.viewProfileDAO(fb);
	}

}
