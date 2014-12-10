package com.team11.bikeshare;

import java.util.List;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
import android.widget.TextView;
import com.team11.beans.GlobalClass;

public class MyAccount extends CommonMenu{
	
	private TextView user_name1;
	private TextView paswd1;
	private TextView mobile1;
	private TextView email1;
	private TextView ssn1;
	private User u;
	String user_name_str;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount);
		
		//receive userid 
		GlobalClass gv=(GlobalClass) getApplicationContext();
		user_name_str=gv.getUsername();
		
		
		//wait for 1 sec until database is updated.
		wait_for_n_sec(1);
		
    	//get user account information from DB
		RequestParams params=new RequestParams();
		params.put("user_name", user_name_str);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://192.168.1.108:8080/my_account",params,new AsyncHttpResponseHandler(){
			public void onSuccess(int statuscode,String response)
			{	
				
				Gson gson = new Gson();
		        u=gson.fromJson(response, User.class);
		        
		        user_name1=(TextView)findViewById(R.id.uname1);
				paswd1=(TextView)findViewById(R.id.paswd1);
				mobile1=(TextView)findViewById(R.id.mobile1);
				email1=(TextView)findViewById(R.id.email1);
				ssn1=(TextView)findViewById(R.id.ssn1);
				
				user_name1.setText(user_name_str);
		        email1.setText(u.getEmail_id());
				paswd1.setText(u.getPassword());
				mobile1.setText(u.getMobile_number());
				ssn1.setText(u.getSsn());
				
				if(response.contains("password"))
				{
					Toast.makeText(getApplicationContext(), "My account", Toast.LENGTH_LONG).show();
    				
    			
				}
				else
				{
				
					Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();

				}	
			}
		});

		
		Button b1 = (Button ) findViewById(R.id.edit);
		b1.setOnClickListener(Edit);

		
	}
		

		OnClickListener Edit = new OnClickListener() {
	        public void onClick(View v) {

	    	    Intent in = new Intent().setClass(getApplicationContext(), UpdateAccount.class);
	
	    	    in.putExtra("username", user_name1.getText().toString());
	    	    in.putExtra("email", email1.getText().toString());
	    	    in.putExtra("mobile", mobile1.getText().toString());
	    	    in.putExtra("password", paswd1.getText().toString());
	    	    in.putExtra("ssn", ssn1.getText().toString());
	    	    
				startActivity(in);
	        }
	    };
	    
	    private static void wait_for_n_sec(int n){
	    	try {
			    Thread.sleep(n*1000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
	    }
	    
	    private String getUserName(Bundle savedInstanceState){
			//get user name from last screen
			String userid;
			if (savedInstanceState == null) {
				Bundle extras = getIntent().getExtras();
			    if (extras != null)
			    {
			    	userid = extras.getString("user_name");
			    }
			    else
			    {
			        userid = null;
			    }
			}
			else{
				userid= (String) savedInstanceState.getSerializable("user_name");
			}
			return userid;
		}
	
	}
