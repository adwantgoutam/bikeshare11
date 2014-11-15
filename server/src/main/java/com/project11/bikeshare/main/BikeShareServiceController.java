package com.project11.bikeshare.main;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project11.bikeshare.Beans.PaymentDetail;
import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.Beans.UserContext;
import com.project11.bikeshare.Service.RegistrationService;

@RestController
public class BikeShareServiceController {
	RegistrationService registrationService = new RegistrationService();
	
	@RequestMapping(value="/register_user",method = RequestMethod.POST)
    public String createUsers(@RequestParam String user) {
		System.out.println("in post");
		registrationService.registerUser(userContext.getUser(),userContext.getPaymentDetail());
    	return user;
    }
	
}
