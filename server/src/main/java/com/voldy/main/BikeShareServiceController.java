package com.voldy.main;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.voldy.Beans.PaymentDetail;
import com.voldy.Beans.User;
import com.voldy.Beans.UserContext;
import com.voldy.Service.RegistrationService;

@RestController
public class BikeShareServiceController {
	RegistrationService registrationService = new RegistrationService();
	
	@RequestMapping(value="/register_user",method = RequestMethod.POST)
    public String createUsers(@RequestBody UserContext userContext) {
		registrationService.registerUser(userContext.getUser(),userContext.getPaymentDetail());
    	return "User Registered Successfully ";
    }
	

}
