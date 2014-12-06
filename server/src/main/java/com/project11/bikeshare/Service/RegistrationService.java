package com.project11.bikeshare.Service;

import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;

import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.Beans.UserContext;
import com.project11.bikeshare.Beans.UserFeedback;
import com.project11.bikeshare.DBImpl.RegistrationDAO;
import com.project11.bikeshare.util.BikeShareUtil;


public class RegistrationService {
	final RegistrationDAO registrationDAO = new RegistrationDAO();
	private Random random = new SecureRandom();
	public void registerUser(UserContext userContext){
		//change the business logic of user details
		/*String passwordHash = BikeShareUtil.makePasswordHash(user.getPassword(), Integer.toString(random.nextInt()));
		user.setPassword(passwordHash);*/
		registrationDAO.registerUser(userContext);
	}
	
	public User login(String user) throws UnknownHostException{
		return registrationDAO.login(user);
	}

	public void registerFeedback(UserFeedback uf) {
		// TODO Auto-generated method stub
		 registrationDAO.feedback(uf);
		
	}
	

}
