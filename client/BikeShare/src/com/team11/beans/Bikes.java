package com.team11.beans;

public class Bikes {
	
	private String user_id;
	private String isBikeAvailable;
	private String bike_id;
	private String accessCode;
	private Location location;
	private String pincode;
	private String bikeModel;
	private String start_time;
	private String end_time;
	
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getBikeModel() {
		return bikeModel;
	}
	public void setBikeModel(String bikeModel) {
		this.bikeModel = bikeModel;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getIsBikeAvailable() {
		return isBikeAvailable;
	}
	public void setIsBikeAvailable(String isBikeAvailable) {
		this.isBikeAvailable = isBikeAvailable;
	}
	public String getBike_id() {
		return bike_id;
	}
	public void setBike_id(String bike_id) {
		this.bike_id = bike_id;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getStartTime() {
		return start_time;
	}

	public String getEndTime() {
		return end_time;
	}


}
