package com.team11.bikeshare;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.Bikes;
import com.team11.beans.GlobalClass;
import com.team11.beans.User;
import com.team11.beans.UserContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterBike extends CommonMenu{
	
	private EditText bikeName;
	private EditText bikeModel;
	private String username;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerbike);
		Button b = (Button ) findViewById(R.id.register2);
		b.setOnClickListener(register);
		
		bikeName=(EditText)findViewById(R.id.bikeName1);
		bikeModel=(EditText)findViewById(R.id.bikeModel1);
		
		
		
	}
	
	
	
	OnClickListener register = new OnClickListener() {
        public void onClick(View v) {
        	GlobalClass gv=(GlobalClass)getApplicationContext();
        	username=gv.getUsername();
        	AsyncHttpClient client = new AsyncHttpClient();
        	RequestParams params=new RequestParams();
    		//UserContext uc=new UserContext();
    		//User user=new User();
    		System.out.println("Here 1");
       		Bikes bike = new Bikes();

       		bike.setBike_id(bikeName.getText().toString());
       		bike.setBikeModel(bikeModel.getText().toString());
       		bike.setUser_id(username); 
       		System.out.println("Here 2");
       		//uc.setBikes(bike);
       		//bike.se
    		GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            System.out.println("Here 3");
            //String str=gson.toJson(uc);
            String str=gson.toJson(bike);
            System.out.println("Str is"+str);
            params.put("registerBikeStr", str);
            System.out.println("Here 4");
    		//DataObject obj = gson.fromJson(br, DataObject.class);
            System.out.println("Here 5");
    		client.post("http://10.185.237.62:8080/register_bike",params, new AsyncHttpResponseHandler(){
            //client.post("http://192.168.2.9:8080/register_user",params, new AsyncHttpResponseHandler(){
    			
    			public void onSuccess(int statuscode,String response)
    			{
    				System.out.println("Response: "+response);
    				//Toast.makeText(getApplicationContext(), statuscode+"Success registration", Toast.LENGTH_LONG).show();
    				if(response.contains("Success"))
					{
						//Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
						Intent in = new Intent().setClass(getApplicationContext(), Home.class);
						String userid=username;
						in.putExtra("userid", userid);
						startActivity(in);
					}		
					else
					{
						Toast.makeText(getApplicationContext(), "Bike ID Exist! Please choose another BikeID.", Toast.LENGTH_LONG).show();

					}	

    				
    			}
    		}
    		);
    		/*
    		Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
			Intent in = new Intent().setClass(getApplicationContext(), Home.class);
			String userid=user_name.getText().toString();
			in.putExtra("userid", userid);
			startActivity(in);
			*/




      }
	};


}
