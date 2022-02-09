package com.facebooklive1.utility;

import com.facebooklive1.dao.FacebookDAO;
import com.facebooklive1.dao.FacebookDAOInterface;

public class DAOFactory {

	public static FacebookDAOInterface createObject() {
		// TODO Auto-generated method stub
		return new FacebookDAO();
	}

}
