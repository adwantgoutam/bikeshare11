package com.project11.bikeshare.Service;

import com.project11.bikeshare.DBImpl.BikeConfirmationDAO;

public class BikeConfirmationService {

	public void confirmRent(String bike_id) {
		new BikeConfirmationDAO().confirmRent(bike_id);
	}

}
