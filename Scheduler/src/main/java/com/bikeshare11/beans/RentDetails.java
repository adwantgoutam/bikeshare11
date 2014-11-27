package com.bikeshare11.beans;

public class RentDetails {

	private String user_id_owner;
	private String user_id_renter;
	private String bike_id;
	private long start_time;
	private long end_time;
	private String remarks;
	private String received;
	private String isNotificationReceived;
	
	public String getIsNotificationReceived() {
		return isNotificationReceived;
	}
	public void setIsNotificationReceived(String isNotificationReceived) {
		this.isNotificationReceived = isNotificationReceived;
	}
	public String getUser_id_owner() {
		return user_id_owner;
	}
	public void setUser_id_owner(String user_id_owner) {
		this.user_id_owner = user_id_owner;
	}
	public String getUser_id_renter() {
		return user_id_renter;
	}
	public void setUser_id_renter(String user_id_renter) {
		this.user_id_renter = user_id_renter;
	}
	public String getBike_id() {
		return bike_id;
	}
	public void setBike_id(String bike_id) {
		this.bike_id = bike_id;
	}
	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	public long getEnd_time() {
		return end_time;
	}
	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReceived() {
		return received;
	}
	public void setReceived(String received) {
		this.received = received;
	}
	
	
	
}
