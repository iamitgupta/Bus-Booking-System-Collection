package com.dev.bbs.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dev.bbs.beans.Admin;
import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;
import com.dev.bbs.repo.Repository;
import com.dev.bbs.services.ServiceDAO;
import com.dev.bbs.services.ServiceDAOImpl;

public class BbsDAOImpl implements BbsDAO {

	Repository repo=new Repository();

	private HashMap<Integer,User> usersInfo=repo.usersInfo;
	private HashMap<Integer,Bus> busInfo=repo.busInfo;
	private HashMap<Integer,Ticket> bookingInfo=repo.bookingInfo; 
	private HashMap<Integer,Availability> availabilityInfo=repo.availabilityInfo;
	private HashMap<Integer,Feedback> suggestionInfo=repo.suggestionInfo;
	private HashMap<Integer,Admin> adminInfo=repo.adminInfo;

	@Override
	public Boolean createUser(User user) {
		if(usersInfo.containsKey(user.getUserId())){
			return false;
		}else{
			usersInfo.put(user.getUserId(), user);
			return true;
		}
	}

	@Override
	public Boolean updateUser(User user) {
		if(usersInfo.containsKey(user.getUserId())){
			usersInfo.put(user.getUserId(), user);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean deleteUser(int userId) {
		// delete user if already exists
		if(usersInfo.containsKey(userId)){
			usersInfo.remove(userId);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public User loginUser(int userId, String password) {
		// return user detail
		if(usersInfo.containsKey(userId)){
			User user=usersInfo.get(userId);
			if(user.getPassword().equals(password)){
				return user;
			}
		}
		return null;
	}

	@Override
	public User searchUser(int userId) {
		// return user detail
		if(usersInfo.containsKey(userId)){
			return usersInfo.get(userId);
		}
		return null;
	}

	@Override
	public Boolean createBus(Bus bus) {
		if(busInfo.containsKey(bus.getBusId())){
			return false;
		}else{
			busInfo.put(bus.getBusId(), bus);
			return true;
		}
	}

	@Override
	public Boolean updateBus(Bus bus) {
		if(busInfo.containsKey(bus.getBusId())){
			busInfo.put(bus.getBusId(), bus);
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Bus searchBus(int busId) {
		// return Bus detail
		if(busInfo.containsKey(busId)){
			return busInfo.get(busId);
		}
		else{
			return null;
		}
	}

	@Override
	public Boolean deletebus(int busId) {
		// delete bus if already exists
		if(busInfo.containsKey(busId)){
			busInfo.remove(busId);
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Admin adminLogin(int adminId, String password) {
		// admin login
		Admin tempAdmin=null;
		if(adminInfo.containsKey(adminId)){
			Admin admin=adminInfo.get(adminId);
			if(admin.getPassword().equals(password)) {
				tempAdmin= admin;
			}
		}
		return tempAdmin;
	}

	@Override
	public Ticket bookTicket(Ticket ticket) {
		//getbooking id
		ServiceDAO service=new  ServiceDAOImpl();

		int bookingId=service.getUniqueKey();
		ticket.setBookingId(bookingId);

		//get timestamp
		ticket.setBookingDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));

		Availability availability = null;
		Integer availSeats=checkAvailability(ticket.getBusId(), (Date) ticket.getJourneyDate());
		if (ticket.getNumofseats() <= availSeats) {
			if(bookingInfo.put(ticket.getBookingId(), ticket)!=null){
				//reduce number of booked seats from availabiity
				for(Integer availId:availabilityInfo.keySet() ){
					availability=availabilityInfo.get(availId);
					if(availability.getBusId()==ticket.getBusId() && 
							availability.getAvailDate().equals(ticket.getJourneyDate()))
					{
						availability.setAvailSeats(availability.getAvailSeats() - ticket.getNumofseats());
						availabilityInfo.put(availability.getAvailId(), availability);
					}
				}
			}
			return ticket;
		}else 
			return null;
	}

	@Override
	public Boolean cancelTicket(int bookingId, int userId) {
		// cancel if already booked and add num of seats to availability column
		Boolean res=false;
		if(bookingInfo.containsKey(bookingId)){
			Ticket ticket=bookingInfo.get(bookingId);
			//cancel the ticket
			bookingInfo.remove(bookingId);
			//inc avail seats in availabilityInfo
			Availability availability = null;
			for(Integer availId:availabilityInfo.keySet() ){
				availability=availabilityInfo.get(availId);
				if(availability.getBusId()==ticket.getBusId() && 
						availability.getAvailDate().equals(ticket.getJourneyDate()))
				{
					availability.setAvailSeats(availability.getAvailSeats() + ticket.getNumofseats());
					availabilityInfo.put(availability.getAvailId(), availability);
					res=true;
				}
			}
		}
		return res;
	}

	@Override
	public Ticket getTicket(int bookingId) {
		// return Ticket detail
		Ticket ticket=null;
		if(bookingInfo.containsKey(bookingId)){
			return bookingInfo.get(bookingId);
		}else{
			return ticket;
		}
	}

	@Override
	public List<Availability> checkAvailability(String source, String destination,Date date) {
		List<Availability> availList = new ArrayList<Availability>();
		Availability availability = null;
		Bus bus=null;
		for(Integer availId:availabilityInfo.keySet() ){
			availability=availabilityInfo.get(availId);
			bus=searchBus(availability.getBusId());
			if(bus.getSource().equals(source) &&
					bus.getDestination().equals(destination) && 
					availability.getAvailDate().equals(date)){
				availList.add(availability);
			}

		}
		return availList;
	}

	@Override
	public Integer checkAvailability(int busId, Date date) {
		int seats = 0;
		for(Integer availId: availabilityInfo.keySet()){
			Availability avail=availabilityInfo.get(availId);
			if(busId==avail.getBusId() && date.equals(avail.getAvailDate())){
				seats=avail.getAvailSeats();

			}

		}
		return seats;
	}

	@Override
	public List<Integer> getAllTicket(int userId) {
		List<Integer> ticketAl = new ArrayList<>();
		for(Integer bookingId : bookingInfo.keySet()){
			Ticket ticket=bookingInfo.get(bookingId);
			if(ticket.getUserId()==userId){
				ticketAl.add(bookingId);
			}
		}
		return ticketAl;
	}

	@Override
	public Boolean writeFeedback(Feedback feed) {
		ServiceDAO service=new  ServiceDAOImpl();
		int feedbackId=service.getUniqueKey();
		if(feed!=null){
			suggestionInfo.put(feedbackId, feed);
			return true;
		}else{
			return false;
		}

	}

	@Override
	public List<Feedback> viewFeedbac() {
		List<Feedback> feedList = new ArrayList<>();

		for(Integer feedBackId : suggestionInfo.keySet()){
			feedList.add(suggestionInfo.get(feedBackId));
		}

		return feedList;

	}

	@Override
	public List<Ticket> getTicketByBus(int busId, Date date) {
		List<Ticket> ticketAl = new ArrayList<>();
		Ticket ticket=null;
		for(Integer bookingId : bookingInfo.keySet()){
			ticket=bookingInfo.get(bookingId);
			if(ticket.getBusId()==busId && ticket.getJourneyDate().equals(date)){
				ticketAl.add(ticket);
			}
		}
		return ticketAl;
	}

	@Override
	public Boolean setBusAvailability(Availability avail) {
		//get avail id
		ServiceDAO service=new  ServiceDAOImpl();
		if(avail.getAvailId()==0){
			avail.setAvailId(service.getUniqueKey());
		}
		if(availabilityInfo.put(avail.getAvailId(), avail)!=null){
			return true;
		}
		return false;
	}

}
