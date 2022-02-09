package com.facebooklive1.utility;

import com.facebooklive1.service.FacebookService;
import com.facebooklive1.service.FacebookServiceInterface;

public class ServiceFactory {

	public static FacebookServiceInterface createObject() {
		// TODO Auto-generated method stub
		return new FacebookService();
	}

}
