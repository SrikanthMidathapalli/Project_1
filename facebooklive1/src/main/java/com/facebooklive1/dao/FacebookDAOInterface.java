package com.facebooklive1.dao;

import java.util.List;

import com.facebooklive1.entity.FacebookUser;
import com.facebooklive1.entity.TimeLine;

public interface FacebookDAOInterface {

	int createProfileDAO(FacebookUser fb);

	int loginProfileDAO(FacebookUser fb);

	List<TimeLine> timelineDAO(FacebookUser fb);

	List<FacebookUser> searchProfileDAO(FacebookUser fb);

	int editProfileDAO(FacebookUser fb);

	int deleteProfileDAO(FacebookUser fb);

	FacebookUser viewProfileDAO(FacebookUser fb);

}
