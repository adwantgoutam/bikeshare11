package com.project11.bikeshare.main;


import java.net.UnknownHostException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.BikesList;
import com.project11.bikeshare.Beans.Coordinates;
import com.project11.bikeshare.Beans.RentDetails;
import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.Beans.UserContext;
import com.project11.bikeshare.DBImpl.BikeConfirmationDAO;
import com.project11.bikeshare.DBImpl.MyAccountDAO;
import com.project11.bikeshare.DBImpl.MyHistoryDAO;
import com.project11.bikeshare.Service.BikeConfirmationService;
import com.project11.bikeshare.Service.BikesService;
import com.project11.bikeshare.Service.MyHistoryService;
import com.project11.bikeshare.Service.OfflineModeService;
import com.project11.bikeshare.Service.RegistrationService;
import com.project11.bikeshare.DBImpl.MyAccountDAO;
import com.project11.bikeshare.Beans.UserFeedback;
import com.twilio.sdk.TwilioRestException;

@RestController
public class BikeShareServiceController {
	RegistrationService registrationService = new RegistrationService();
	
	@RequestMapping(value="/register_user",method = RequestMethod.POST)
    public String createUsers(@RequestParam String userContext) {
		System.out.println("in post");
		Gson gson = new Gson();
        UserContext uc=gson.fromJson(userContext, UserContext.class);
		registrationService.registerUser(uc);
    	return "Successfully registered";
    }
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(@RequestParam String user_id) throws UnknownHostException
	{
		Gson gson = new Gson();
		User u=new RegistrationService().login(user_id);
        GsonBuilder builder = new GsonBuilder();
        Gson gson1 = builder.create();
        String str=gson1.toJson(u);
        return str;
	}
	
	@RequestMapping(value="/locations",method = RequestMethod.GET)
	public String getLocations(@RequestParam String coordinates) throws UnknownHostException
	{
		
		Gson gson = new Gson();
        Coordinates coord=gson.fromJson(coordinates, Coordinates.class);
        List<Bikes> list=new BikesService().getLocations(coord);
        BikesList bl=new BikesList();
        bl.setList(list);
        GsonBuilder builder = new GsonBuilder();
        Gson gson1 = builder.create();
        String str=gson1.toJson(bl);
        System.out.println("---"+str);
		return str;
	}
    @RequestMapping(value="/my_account",method = RequestMethod.GET)
    public String getMyAccount(@RequestParam String username) throws UnknownHostException
    {
        Gson gson = new Gson();
        User u = new MyAccountDAO().getUserFromDB(username);
        GsonBuilder builder = new GsonBuilder();
        Gson gson1 = builder.create();
        String str=gson1.toJson(u);
        return str;
    }


    @RequestMapping(value="/edit_my_account",method = RequestMethod.POST)
    public String editMyAccount(@RequestParam String user) {
        Gson gson = new Gson();
        User u=gson.fromJson(user, User.class);
        new MyAccountDAO().updateAccount(u);
        return "Successfully updated";
    }
	
	@RequestMapping(value="/getBike",method = RequestMethod.GET)
	public String getLendBikes(@RequestParam String bikeid) throws UnknownHostException
	{
	  //  BikeContext bc= new BikeContext();
        BikesService bs = new BikesService();
    	Bikes bb = new Bikes();
       // bc=bs.getBikeDetails(bikeid);
    	 bb=bs.getBikeDetails(bikeid);
        GsonBuilder builder = new GsonBuilder();
        Gson gson1 = builder.create();
        //String str=gson1.toJson(bc);
        String str=gson1.toJson(bb);
		return str;
	}
	
	@RequestMapping(value="/bike",method = RequestMethod.GET)
	 public String findBike(@RequestParam String bikeid) throws UnknownHostException
	{
			Bikes bike=new BikeConfirmationDAO().findBike(bikeid);
			GsonBuilder builder = new GsonBuilder();
	        Gson gson = builder.create();
	        String str=gson.toJson(bike);
			return str;
	}
    
    @RequestMapping(value="/feedback",method = RequestMethod.POST)
    public String getUserFeedback(@RequestParam String userFeedbackStr) {
		System.out.println("in feedback");
		Gson gson = new Gson();
		UserFeedback uf=gson.fromJson(userFeedbackStr, UserFeedback.class);
		registrationService.registerFeedback(uf);
    	return "Successfully submitted feedback";
    }
    
    //Used for my history
    @RequestMapping(value="/get_my_history",method = RequestMethod.GET)
    public String getMyHistory(@RequestParam String user_id) {
        List<RentDetails> rentDetailsList = new MyHistoryService().getMyHistory(user_id);
        Gson gson = new Gson();
        String rentDetailsListJson = gson.toJson(rentDetailsList);
        return rentDetailsListJson;
    }
    
    //used to confirm the rent thus the marker disappears from map
    @RequestMapping(value="/confirm_rent",method = RequestMethod.POST)
    public String confirmRenting(@RequestParam String bike_id,@RequestParam String user_id) {
    	new BikeConfirmationService().confirmRent(bike_id,user_id);
        return "success";
    }
    
    @RequestMapping(value="/twilio_offline",method = RequestMethod.POST)
    public String OfflineMode(@RequestParam String From,@RequestParam String Body) {
    	try {
			new OfflineModeService().offlineMode(From, Body);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "Successfully updated";
    }
  
}
