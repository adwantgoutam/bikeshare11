package com.project11.bikeshare.DBImpl;

import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.User;

public class BikeConfirmationDAO extends BikeShareDB{

	public Bikes confirmRent(String bike_id) {
		bikesCollectionJongo.update("{bikeid: '"+bike_id+"'}").with("{isBikeAvailable: 'no'}");
		Bikes bikes= bikesCollectionJongo.findOne("{bikeid: '"+bike_id+"'}").as(Bikes.class);
	return bikes;
	}

	public User findUser(String user_id) {
		User user = userCollectionJongo.findOne("{user_id: '"+user_id+"'}").as(User.class);
		return user;
	}
	

}
