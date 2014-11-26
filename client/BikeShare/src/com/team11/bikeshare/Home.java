package com.team11.bikeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Home extends CommonMenu{
	
	private Button b1;
	private Button b2;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		b1 = (Button ) findViewById(R.id.search);
		b1.setOnClickListener(search);
		b2 = (Button ) findViewById(R.id.bikereg);
		b2.setOnClickListener(registerBike);
		
	}
	OnClickListener search = new OnClickListener() {
        public void onClick(View v) {

    	    Intent in = new Intent().setClass(getApplicationContext(), ShowBikeLocations.class);
			startActivity(in);
        }
    };
    OnClickListener registerBike = new OnClickListener() {
        public void onClick(View v) {

    	    Intent in = new Intent().setClass(getApplicationContext(), Registration.class);
			startActivity(in);
        }
    };
	


}
