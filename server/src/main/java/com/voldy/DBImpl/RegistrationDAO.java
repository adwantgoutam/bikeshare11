package com.voldy.DBImpl;

import org.jongo.MongoCollection;

import com.voldy.Beans.PaymentDetail;
import com.voldy.Beans.User;

public class RegistrationDAO extends BikeShareDB{

	public void registerUser(User user,PaymentDetail paymentDetail){
		//save this user in mongo
		System.out.println("Inserting user");
		userCollection.save(user);
		paymentDetailsCollection.save(paymentDetail);
		
	}
}
