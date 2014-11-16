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
import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.Beans.UserContext;
import com.project11.bikeshare.Service.BikesService;
import com.project11.bikeshare.Service.RegistrationService;

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
		return str;
	}
	
}
