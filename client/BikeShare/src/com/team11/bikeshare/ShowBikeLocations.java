package com.team11.bikeshare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team11.beans.Bikes;
import com.team11.beans.BikesList;
import com.team11.beans.Coordinates;
import com.team11.beans.User;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowBikeLocations extends CommonMenu implements OnMarkerClickListener, android.location.LocationListener, OnInfoWindowClickListener {
	LocationManager locationManager;
	LocationListener locationListener;
	Location location;
	GoogleMap googleMap;
	BikesList bikeslist;
	LatLng gpsLocation;
	EditText editText1;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showbikelocations);
		editText1=(EditText)findViewById(R.id.paswd);
		// gps
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gps_enabled)
        {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
        }
        if(gps_enabled){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Coordinates coordinates=new Coordinates();
		coordinates.setLatitude(String.valueOf(location.getLatitude()));
		coordinates.setLongitude(String.valueOf(location.getLongitude()));
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		gpsLocation=new LatLng(location.getLatitude(),location.getLongitude());
		googleMap.addMarker(new MarkerOptions().position(gpsLocation).title("MySelf"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsLocation, 10));
		AsyncHttpClient client = new AsyncHttpClient();
    	RequestParams params=new RequestParams();
    	GsonBuilder builder = new GsonBuilder();
        Gson gson1 = builder.create();
        String str=gson1.toJson(coordinates);
		params.put("coordinates", str);
		client.get("http://10.0.0.9:8080/locations",params, new AsyncHttpResponseHandler(){
			public void onSuccess(int statuscode,String response)
			{
				Gson gson = new Gson();
				bikeslist=gson.fromJson(response, BikesList.class);	
				Toast.makeText(getApplicationContext(),"In Success"+bikeslist.getList().size(), Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(),"--"+response, Toast.LENGTH_LONG).show();
		        if(bikeslist==null)
		        {
		        	Toast.makeText(getApplicationContext(),"No bikes available nearby your location", Toast.LENGTH_LONG)
		    				.show();
		        }
		        else
		        {
		        	List<Bikes> list=bikeslist.getList();
		        	for(int i=0;i<list.size();i++)
		        	{
		        		Bikes b=list.get(i);
		        		//googleMap.clear();
		        		double lat=Double.parseDouble(b.getLocation().getCoordinates().getLatitude());
		        		double lngt=Double.parseDouble(b.getLocation().getCoordinates().getLongitude());
		        		Toast.makeText(getApplicationContext(),"lat"+lat, Toast.LENGTH_LONG).show();
		        		//googleMap.addMarker(new MarkerOptions().position(gpsLocation).title("hello").snippet("helo"));
		        		googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lngt)));
		        	}
		        }
			}
			
		});
		googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

		googleMap.setOnMarkerClickListener(this);
		googleMap.setOnInfoWindowClickListener(this);



        }

			}

	@Override
	public boolean onMarkerClick(Marker marker) {
		
		marker.showInfoWindow();
		//marker.
		return false;
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
	public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);
            TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);
            markerLabel.setText(marker.getTitle());
            return v;
        }
    }
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		  Toast.makeText(getBaseContext(), 
				    "Info Window clicked@" + marker.getTitle(), 
				    Toast.LENGTH_SHORT).show();

		
	}
}