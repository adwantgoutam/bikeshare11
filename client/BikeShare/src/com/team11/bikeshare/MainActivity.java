package com.team11.bikeshare;

import java.util.List;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.GlobalClass;
import com.team11.beans.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	private Button b1;
	private Button b2;
	EditText username;
	EditText password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1 = (Button ) findViewById(R.id.register);
		b1.setOnClickListener(register);
		b2 = (Button ) findViewById(R.id.login);
		b2.setOnClickListener(login);
		username=(EditText) findViewById(R.id.user_name);
		password=(EditText) findViewById(R.id.password);
		
		
		
	}
	OnClickListener register = new OnClickListener() {
        public void onClick(View v) {

    	    Intent in = new Intent().setClass(getApplicationContext(), Registration.class);
			startActivity(in);
        }
    };
    OnClickListener login = new OnClickListener() {
        public void onClick(View v) {
        	AsyncHttpClient client = new AsyncHttpClient();
        	RequestParams params=new RequestParams();
    		params.put("user_id", username.getText().toString());
    		client.get("http://10.185.237.62:8080/login",params, new AsyncHttpResponseHandler(){
    			
    			public void onSuccess(int statuscode,String response)
    			{
    				Gson gson = new Gson();
    		        User u=gson.fromJson(response, User.class);
    		        System.out.println(username.getText().toString());
    		        System.out.println(password.getText().toString());
    		        
        				if(u.getUser_name().equals(username.getText().toString()) && u.getPassword().equals(password.getText().toString()))
    					{
        					final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        					globalVariable.setUsername(username.getText().toString());
        					Intent in = new Intent().setClass(getApplicationContext(), Home.class);
        					startActivity(in);
       					}
    					else
    					{
    						Toast.makeText(getApplicationContext(), "Invalid Login Credentials", Toast.LENGTH_LONG).show();
    					}
    				
    			}
    			
    		}
    		);
         }
    };


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
