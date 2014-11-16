package com.project11.bikeshare.DBImpl;

import org.jongo.MongoCollection;


import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.Beans.UserContext;

public class RegistrationDAO extends BikeShareDB{

	public void registerUser(UserContext userContext){
		//save this user in mongo
		System.out.println("Inserting user");
		userCollection.save(userContext.getUser());
		bikesCollection.save(userContext.getBike());
		
		
	}
}
