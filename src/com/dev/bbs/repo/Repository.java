package com.dev.bbs.repo;

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
	public static HashMap<Integer,Availability> availability=new HashMap<>();
	public static HashMap<Integer,Feedback> suggestion=new HashMap<>();
	public static HashMap<Integer,Admin> adminInfo=new HashMap<>();
	
	
	
	

}
