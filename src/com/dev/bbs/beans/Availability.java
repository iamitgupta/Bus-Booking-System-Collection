package com.dev.bbs.beans;

import java.util.Date;

public class Availability {
	private int availId;
	private Date availDate;
	private int availSeats;
	private int busId;

	public int getAvailId() {
		return availId;
	}

	public void setAvailId(int availId) {
		this.availId = availId;
	}

	public Date getAvailDate() {
		return availDate;
	}

	public void setAvailDate(Date availDate) {
		this.availDate = availDate;
	}

	public int getAvailSeats() {
		return availSeats;
	}

	public void setAvailSeats(int availSeats) {
		this.availSeats = availSeats;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	@Override
	public String toString() {
		return "Availability [availId=" + availId + ", availDate=" + availDate + ", availSeats=" + availSeats
				+ ", busId=" + busId + "]";
	}

}
