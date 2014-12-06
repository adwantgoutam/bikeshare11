package com.project11.bikeshare.DBImpl;

public class BikeConfirmationDAO extends BikeShareDB{

	public void confirmRent(String bike_id) {
		bikesCollectionJongo.update("{bikeid: '"+bike_id+"'}").with("{isBikeAvailable: 'no'}");
	}
	

}
