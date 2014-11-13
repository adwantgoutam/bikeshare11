package com.voldy.Service;

import java.security.SecureRandom;
import java.util.Random;

import com.voldy.Beans.PaymentDetail;
import com.voldy.Beans.User;
import com.voldy.DBImpl.RegistrationDAO;
import com.voldy.util.BikeShareUtil;


public class RegistrationService {
	final RegistrationDAO registrationDAO = new RegistrationDAO();
	private Random random = new SecureRandom();
	public void registerUser(User user, PaymentDetail paymentDetail){
		//change the business logic of user details
		String passwordHash = BikeShareUtil.makePasswordHash(user.getPassword(), Integer.toString(random.nextInt()));
		user.setPassword(passwordHash);
		registrationDAO.registerUser(user,paymentDetail);
	}

}
