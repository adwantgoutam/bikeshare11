package com.project11.bikeshare.Service;

import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.DBImpl.BikeConfirmationDAO;
import com.project11.bikeshare.util.TwilioMessage;
import com.twilio.sdk.TwilioRestException;

public class BikeConfirmationService {

	public void confirmRent(String bike_id,String user_id) {
		BikeConfirmationDAO bikeConfirmationDAO=new BikeConfirmationDAO();
		Bikes bike = bikeConfirmationDAO.confirmRent(bike_id);
		User user = bikeConfirmationDAO.findUser(user_id);
		sendUserConfirmationTextMessage(user,bike);
	}
	
	public void sendUserConfirmationTextMessage(User user,Bikes bike){
		StringBuffer b = new StringBuffer("");
		b.append("Access Code :"+bike.getAccessCode());
		b.append("Start Time :"+bike.getStart_time());
		b.append("End Time :"+bike.getEnd_time());
		b.append("Enter Custom Message here");
		
		TwilioMessage twilioMessage = new TwilioMessage();
		
		try {
			twilioMessage.sendMessage(user.getMobile_number(),b.toString());
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
