package com.team11.bikeshare;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.BikeContext;
import com.team11.beans.Bikes;
import com.team11.beans.BikesList;
import com.team11.beans.Coordinates;
import com.team11.beans.GlobalClass;
import com.team11.beans.RentDetails;
import com.team11.beans.User;
import com.team11.beans.UserContext;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
public class LendBike extends FragmentActivity implements LocationListener {
	LocationManager locationManager;
	LocationListener locationListener;
	Location location;
	Coordinates coordinates;
	
	private EditText accesscode;
	private EditText bikemodel;
	static EditText startTime;
	static EditText endTime;
	Button lend;
	String bikeid;
	private EditText bikeModel;
	String zipcode;
	static Calendar startCal = Calendar.getInstance();
	static Calendar endCal = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lendbike);
		lend=(Button)findViewById(R.id.lend);
		
		accesscode=(EditText)findViewById(R.id.accessCode);
		startTime=(EditText)findViewById(R.id.startTime);
		endTime=(EditText)findViewById(R.id.endTime);
		bikemodel=(EditText)findViewById(R.id.bikeModel);
		locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        /*if(!gps_enabled)
        {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
        }*/
        if(gps_enabled){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		coordinates=new Coordinates();
		coordinates.setLatitude(String.valueOf(location.getLatitude()));
		coordinates.setLongitude(String.valueOf(location.getLongitude()));
			
					AsyncHttpClient client = new AsyncHttpClient();
					RequestParams params=new RequestParams();
					Intent in=getIntent();
					bikeid=in.getStringExtra("bikeid");
					params.put("bikeid",bikeid);
					client.get("http://10.185.237.62:8080/getBike",params, new AsyncHttpResponseHandler(){
							public void onSuccess(int statuscode,String response)
							{
								Gson gson = new Gson();
								BikeContext bc = new BikeContext();
								bc=gson.fromJson(response, BikeContext.class);
				 				bikemodel.setText(bc.getBike().getBikeModel());
				
							}});
							startTime.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								System.out.println("in start");
							showTruitonTimePickerDialog(v);
							showTruitonDatePickerDialog(v);
							}
							});
							endTime.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
							showTruitonTimePickerDialog1(v);
							showTruitonDatePickerDialog1(v);
							}
							});
							
							lend.setOnClickListener(new OnClickListener() {
						        public void onClick(View v) {
						        	GlobalClass globalVariable=(GlobalClass) getApplicationContext();
						        	
						        	AsyncHttpClient client = new AsyncHttpClient();
						        	RequestParams params=new RequestParams();
						        	BikeContext bc=new BikeContext();
						        	Bikes b = new Bikes();
						        	RentDetails rd = new RentDetails();
						        	b.setBike_id(bikeid);
						        	b.setUser_id(globalVariable.getUsername());
						    		b.setBikeModel(bikemodel.getText().toString());
						    		b.setAccessCode(accesscode.getText().toString());
						    		b.setPincode(zipcode);
						    		b.setStart_time(String.valueOf(startCal.getTime().getTime()));
						    		b.setEnd_time(String.valueOf(endCal.getTime().getTime()));
						    		com.team11.beans.Location loc=new com.team11.beans.Location();
						    		loc.setCoordinates(coordinates);
						    		b.setLocation(loc);
						    		
						    		bc.setBike(b);
						    		rd.setEnd_time(String.valueOf(startCal.getTime().getTime()));
						    		rd.setStart_time(String.valueOf(endCal.getTime().getTime()));
						    		rd.setReceived("nyr");
						    		bc.setRentdetails(rd);
						    		GsonBuilder builder = new GsonBuilder();
						            Gson gson = builder.create();
						            String str=gson.toJson(bc);
						            params.put("bikecontext", str);

						    		client.post("http://10.0.0.36:8080/lendBike",params, new AsyncHttpResponseHandler(){
						    			
						    			public void onSuccess(int statuscode,String response)
						    			{
						    				Intent in1 = new Intent().setClass(getApplicationContext(), Home.class);
											startActivity(in1);
						    			}
						    		}
						    		);
						    	    
						        }});
						
							
							
							
        	}
        	else {
        		Toast.makeText(getApplicationContext(),"No GPS", Toast.LENGTH_SHORT).show();
        	}
	
	}
	public String getAddress(double latitude, double longitude) {
		String zipcode="";
			try {
		Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
		List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
		if (addresses.size() > 0) {
		Address address = addresses.get(0);
		zipcode=address.getPostalCode();
		}}
		catch (IOException e) {
		Log.e("tag", e.getMessage());
		}
			return zipcode;
	}
//first	
	public void showTruitonDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			startCal.set(Calendar.YEAR, year);
			startCal.set(Calendar.MONTH, month);
			startCal.set(Calendar.DAY_OF_MONTH, day);
			startTime.setText(day + "/" + (month + 1) + "/" + year);
		}
	}
	public void showTruitonTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		boolean mFirst = true;
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			
			if (mFirst) {
				mFirst = false;
			startCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
			startCal.set(Calendar.MINUTE, minute);
			startTime.setText(startTime.getText() + " -" + hourOfDay + ":"	+ minute);
			}
	}}
	
	
//second
	public void showTruitonDatePickerDialog1(View v) {
		DialogFragment newFragment1 = new DatePickerFragment1();
		newFragment1.show(getSupportFragmentManager(), "datePicker");
	}
	public  class DatePickerFragment1 extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			endCal.set(Calendar.YEAR, year);
			endCal.set(Calendar.MONTH, month);
			endCal.set(Calendar.DAY_OF_MONTH, day);
			endTime.setText(day + "/" + (month + 1) + "/" + year);
		}
	}
	public void showTruitonTimePickerDialog1(View v) {
		DialogFragment newFragment1 = new TimePickerFragment1();
		newFragment1.show(getSupportFragmentManager(), "timePicker");
	}
	public  class TimePickerFragment1 extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		boolean mSecond = true;
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			if (mSecond) {
				mSecond = false;
			endCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
			endCal.set(Calendar.MINUTE, minute);
			endTime.setText(endTime.getText() + " -" + hourOfDay + ":"	+ minute);
			}
	}
		
		
		
	}
	
	
	
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
