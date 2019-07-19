package com.dev.bbs.beans;

import java.util.Date;

public class Ticket {

	private int bookingId;
	private int busId;
	private int userId;
	private Date journeyDate;
	private int numofseats;
	private Date bookingDatetime;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}

	public int getNumofseats() {
		return numofseats;
	}

	public void setNumofseats(int numofseats) {
		this.numofseats = numofseats;
	}

	public Date getBookingDatetime() {
		return bookingDatetime;
	}

	public void setBookingDatetime(Date bookingDatetime) {
		this.bookingDatetime = bookingDatetime;
	}

	@Override
	public String toString() {
		return "Ticket [bookingId=" + bookingId + ", busId=" + busId + ", userId=" + userId + ", journeyDate="
				+ journeyDate + ", numofseats=" + numofseats + ", bookingDatetime=" + bookingDatetime + "]";
	}

}
