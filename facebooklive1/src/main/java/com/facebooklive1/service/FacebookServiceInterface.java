package com.facebooklive1.service;

import java.util.List;

import com.facebooklive1.entity.FacebookUser;
import com.facebooklive1.entity.TimeLine;

public interface FacebookServiceInterface {

	int createProfileService(FacebookUser fb);

	int loginProfileService(FacebookUser fb);

	List<TimeLine> timeLineService(FacebookUser fb);

	List<FacebookUser> searchProfileService(FacebookUser fb);

	int editprofileService(FacebookUser fb);

	int deleteProfileService(FacebookUser fb);

	FacebookUser viewProfileService(FacebookUser fb);

}
