package com.project11.bikeshare.Service;

import java.net.UnknownHostException;

import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.DBImpl.BikesDAO;

public class BikeDetailsService {
	
	public Bikes getBikeDetails(String bike_id) throws UnknownHostException{
		
		return new BikesDAO().getBikeDetails(bike_id);
}

}
