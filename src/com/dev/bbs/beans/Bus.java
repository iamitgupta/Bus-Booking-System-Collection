package com.dev.bbs.beans;

public class Bus {

	private int busId;
	private String busname;
	private String source;
	private String destination;
	private String busType;
	private int totalSeats;
	private double price;

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getBusname() {
		return busname;
	}

	public void setBusname(String busname) {
		this.busname = busname;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String bus_type) {
		this.busType = bus_type;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Bus [busId=" + busId + ", busname=" + busname + ", source=" + source + ", destination=" + destination
				+ ", busType=" + busType + ", totalSeats=" + totalSeats + ", price=" + price + "]";
	}

}
