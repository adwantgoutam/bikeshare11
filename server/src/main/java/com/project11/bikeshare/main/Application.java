package com.project11.bikeshare.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.google.gson.Gson;
import com.project11.bikeshare.Beans.Bikes;
import com.project11.bikeshare.Beans.User;
import com.project11.bikeshare.Beans.UserContext;
import org.springframework.context.annotation.Import;



@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import(ScheduledTasks.class)
public class Application {

	public static void main(String[] args) {
		 SpringApplication.run(Application.class, args);
	}
}
