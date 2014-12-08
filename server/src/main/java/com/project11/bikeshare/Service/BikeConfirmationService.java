package com.project11.bikeshare.Service;

import java.net.UnknownHostException;


import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.RentDetails;
import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.DBImpl.BikeConfirmationDAO;
import com.project11.bikeshare.util.TwilioMessage;
import com.twilio.sdk.TwilioRestException;

public class BikeConfirmationService {
	BikeConfirmationDAO bikeConfirmationDAO=new BikeConfirmationDAO();
	TwilioMessage twilioMessage = new TwilioMessage();
	public void confirmRent(String bike_id,String user_id) {
		
		Bikes bike = bikeConfirmationDAO.confirmRent(bike_id);
		User user = bikeConfirmationDAO.findUser(user_id);
		RentDetails rentDetails=bikeConfirmationDAO.updateRentDetails(bike_id,user_id);
		sendUserConfirmationTextMessage(user,bike);
		sendBikeOwnerTextMessage(rentDetails);
	}
	
	public Bikes findBike(String bikeid) throws UnknownHostException
	{
		return new BikeConfirmationDAO().findBike(bikeid);
	}
	private void sendBikeOwnerTextMessage(RentDetails rentDetails) {
		User owner = bikeConfirmationDAO.findUser(rentDetails.getUser_id_owner());
		StringBuilder sb = new StringBuilder("");
		sb.append("Your Bike is in use !");
		sb.append("\n");
		sb.append("Renter id "+rentDetails.getUser_id_renter());
		
		try {
			twilioMessage.sendMessage(owner.getMobile_number(), sb.toString());
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendUserConfirmationTextMessage(User user,Bikes bike){
		StringBuffer b = new StringBuffer("");
		b.append("Access Code :"+bike.getAccessCode());
		b.append("Start Time :"+bike.getStart_time());
		b.append("End Time :"+bike.getEnd_time());
		b.append("Enter Custom Message here");
		
		try {
			twilioMessage.sendMessage(user.getMobile_number(),b.toString());
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
