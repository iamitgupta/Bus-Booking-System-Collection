package com.dev.bbs.repo;

import java.sql.Date;
import java.util.HashMap;

import com.dev.bbs.beans.Admin;
import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;

public class Repository {
	public static HashMap<Integer,User> usersInfo=new HashMap<>();
	public static HashMap<Integer,Bus> busInfo=new HashMap<>();
	public static HashMap<Integer,Ticket> bookingInfo=new HashMap<>();
	public static HashMap<Integer,Availability> availabilityInfo=new HashMap<>();
	public static HashMap<Integer,Feedback> suggestionInfo=new HashMap<>();
	public static HashMap<Integer,Admin> adminInfo=new HashMap<>();
	
	public Repository(){
		//user
		User user=new User();
		user.setUserId(10);
		user.setPassword("123");
		user.setContact(955555444);
		user.setUsername("Amit");
		user.setEmail("amit@gmail.com");
		
		usersInfo.put(user.getUserId(), user);
		
		User user1=new User();
		user1.setUserId(20);
		user1.setPassword("234");
		user1.setContact(95555444);
		user1.setUsername("Manoj");
		user1.setEmail("manoj@gmail.com");
		
		usersInfo.put(user1.getUserId(), user1);
		
		//admin
		
		Admin admin=new Admin();
		admin.setAdminId(123);
		admin.setPassword("admin");
		admin.setName("Admin");
		admin.setContact(564654646);
		admin.setEmail("admin@");
		
		adminInfo.put(admin.getAdminId(), admin);
		
		
		//bus
		Bus bus=new Bus();
		bus.setBusId(100);
		bus.setBusname("BangloreExpress");
		bus.setSource("BAN");
		bus.setDestination("MUM");
		bus.setTotalSeats(30);
		bus.setBusType("AC");
		bus.setPrice(5000);
		
		busInfo.put(bus.getBusId(), bus);
		
		Bus bus2=new Bus();
		bus2.setBusId(200);
		bus2.setBusname("MumbaiExpress");
		bus2.setSource("MUM");
		bus2.setDestination("PUN");
		bus2.setTotalSeats(30);
		bus2.setBusType("AC");
		bus2.setPrice(2000);
		
		busInfo.put(bus2.getBusId(), bus2);
		
		//availability
		Availability avail=new Availability();
		avail.setAvailId(55);
		avail.setBusId(100);
		String tempDate="2019-12-13";
		Date date = Date.valueOf(tempDate);
		avail.setAvailDate(date);
		avail.setAvailSeats(30);
		
		availabilityInfo.put(avail.getAvailId(), avail);
		
		Availability avail1=new Availability();
		avail1.setAvailId(45);
		avail1.setBusId(100);
		String tempDate1="2019-12-11";
		Date date1 = Date.valueOf(tempDate1);
		avail1.setAvailDate(date1);
		avail1.setAvailSeats(30);
		
		availabilityInfo.put(avail1.getAvailId(), avail1);
		
	}
}
