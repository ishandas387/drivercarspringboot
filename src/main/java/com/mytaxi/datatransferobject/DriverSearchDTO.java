package com.mytaxi.datatransferobject;

import com.mytaxi.domainvalue.OnlineStatus;

public class DriverSearchDTO {

	private String userName;
	private OnlineStatus onlineStatus;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public OnlineStatus getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(OnlineStatus isOnline) {
		this.onlineStatus = isOnline;
	}
	
}
