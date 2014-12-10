package com.team11.beans;

public class FeedbackBean {

	private String bikeDamage;
	private String returnBike;
	private String ratings;
	private String user_id_renter;
	
	public String getUser_id_renter() {
		return user_id_renter;
	}
	public void setUser_id_renter(String user_id_renter) {
		this.user_id_renter = user_id_renter;
	}
	public String getBikeDamage() {
		return bikeDamage;
	}
	public void setBikeDamage(String bikeDamage) {
		this.bikeDamage = bikeDamage;
	}
	public String getReturnBike() {
		return returnBike;
	}
	public void setReturnBike(String returnBike) {
		this.returnBike = returnBike;
	}
	public String getRatings() {
		return ratings;
	}
	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

}
