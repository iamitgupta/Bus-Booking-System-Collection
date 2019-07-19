package com.dev.bbs.app;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;
import com.dev.bbs.dao.BbsDAO;
import com.dev.bbs.dao.BbsDAOImpl;
import com.dev.bbs.exception.BusNotFoundException;
import com.dev.bbs.exception.DeletionFailedException;
import com.dev.bbs.exception.LoginFailedException;
import com.dev.bbs.exception.RegsitrationFailedException;
import com.dev.bbs.exception.TicketBookingFailedException;
import com.dev.bbs.exception.UpdationFailedException;

public class UserApp {
	static BbsDAO services = new BbsDAOImpl();
	static Scanner sc = new Scanner(System.in);
	static int userId;

	public static void main(String[] args) {

		System.out.println("Enter a choice\n1. Login\n2. Register");
		int choice = Integer.parseInt(sc.next());

		if (choice == 1) {
			Boolean login = false;
			try {
				login = loginUser();
			} catch (LoginFailedException e1) {
				e1.printStackTrace();
			}
			if (login) {
				System.out.println("Login Successful");
				Boolean loop = true;
				while (loop) {
					// options to perform different operation
					System.out.println(
							"*******************************************************************************************************************************");
					System.out.println("Enter a choice");
					System.out.println("1.Update profile");
					System.out.println("2.Delete Profile");
					System.out.println("3.Book Ticket");
					System.out.println("4.Cancel Ticket");
					System.out.println("5.Search Bus");
					System.out.println("6.Check Ticket Availability");
					System.out.println("7.Print Ticket");
					System.out.println("8.View Profile");
					System.out.println("9.Write Feedback");
					System.out.println("10.exit");
					int ch = Integer.parseInt(sc.next());
					switch (ch) {
					case 1:
						try {
							updateUser();
						} catch (UpdationFailedException e) {
							e.printStackTrace();
						}
						break;
					case 2:
						try {
							deleteUser();
						} catch (DeletionFailedException e2) {
							e2.printStackTrace();
						}
						try {
							loginUser();
						} catch (LoginFailedException e) {
							loop = false;
							e.printStackTrace();
							
						}
						break;
					case 3:
						try {
							bookTicket();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						break;
					case 4:
						cancelTicket();
						break;
					case 5:
						try {
							searchBus();
						} catch (BusNotFoundException e) {
							e.printStackTrace();
						}
						break;
					case 6:
						checkAvailability();
						break;
					case 7:
						getTicket();
						break;
					case 8:
						serachUser();
						break;
					case 9:
						writeFeedback();

						break;
					case 10:
						loop = false;
						break;
					default:
						System.out.println("Plz enter correct option");
					}
				}
			} else {
				System.out.println("Login failed");
			}

		} else if (choice == 2) {
			try {
				createUser();
			} catch (RegsitrationFailedException e) {
				e.printStackTrace();
			}

		}

	}

	private static void writeFeedback() {
		Feedback feed = new Feedback();
		feed.setUserId(userId);
		System.out.println("Write the feedback");
		String feedback = sc.next();
		feed.setFeedback(feedback);
		if (!feedback.equals("")) {
			if (services.writeFeedback(feed)) {
				System.out.println("Successfully done");
			} else {
				System.out.println("Failed");
			}
		}
	}

	private static void serachUser() {
		System.out.println(services.searchUser(userId));
	}

	private static void getTicket() {
		Ticket ticket = null;
		List<Integer> ticketList = services.getAllTicket(userId);
		if (ticketList.size() > 0) {
			for (Integer bookingId : ticketList) {
				ticket = services.getTicket(bookingId);
				System.out.println(ticket);
				System.out.println("--------------------------------------------------------------------------------");
			}
		} else {
			System.out.println("No tickets Found");
		}

	}

	private static void checkAvailability() {

		System.out.println("Enter a choice");
		System.out.println("Check Availability by \n1.Bus id\n2.Source and Destination");
		int chAvail = Integer.parseInt(sc.next());
		if (chAvail == 1) {
			System.out.println("Enter the Bus id");
			int busId = Integer.parseInt(sc.next());
			System.out.println("Enter date of journey(yyyy-mm-dd)");
			String tempDate = sc.next();
			Date date = Date.valueOf(tempDate);
			Integer availSeats = services.checkAvailability(busId, date);
			if (availSeats != null) {
				System.out.println("Total available seats are: " + availSeats);
			}

		} else if (chAvail == 2) {
			System.out.println("Enter source point");
			String source = sc.next();
			System.out.println("Enter Destination point");
			String destination = sc.next();

			System.out.println("Enter date of journey(yyyy-mm-dd)");
			String tempDate = sc.next();
			Date date = Date.valueOf(tempDate);

			List<Availability> busList = services.checkAvailability(source, destination, date);
			for (Availability avail : busList) {
				Bus bus = services.searchBus(avail.getBusId());
				int availSeats = avail.getAvailSeats();
				System.out.println(bus);
				System.out.println("Available seats : " + availSeats);
				System.out.println(
						"--------------------------------------------------------------------------------------------------");
			}
		}
	}

	private static void searchBus() throws BusNotFoundException {
		System.out.println("Enter the busId");
		Bus bus = services.searchBus(Integer.parseInt(sc.next()));
		if (bus != null) {
			System.out.println(bus);

		} else {
			System.out.println("No bus found");
			throw new BusNotFoundException("No Bus found");
		}

	}

	private static void cancelTicket() {
		Ticket ticket = null;
		List<Integer> ticketList = services.getAllTicket(userId);

		if (ticketList.size() > 0) {
			for (Integer bookingId : ticketList) {
				ticket = services.getTicket(bookingId);
				System.out.println(ticket);
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------------");
			}
			System.out.println("Enter the bookingId");
			if (services.cancelTicket(Integer.parseInt(sc.next()), userId)) {
				System.out.println("Ticket cancelled");
			} else {
				System.out.println("Ticket cancelation Failed");
			}
		} else {
			System.out.println("No tickets found to cancel");
		}

	}

	private static void bookTicket() throws TicketBookingFailedException, BusNotFoundException {
		Ticket ticket = new Ticket();
		System.out.println("Enter source point");
		String source = sc.next();
		System.out.println("Enter Destination point");
		String destination = sc.next();

		System.out.println("Enter date of journey(yyyy-mm-dd)");
		String tempDate = sc.next();
		Date date = Date.valueOf(tempDate);

		// set Date
		ticket.setJourneyDate(date);

		// get current Date
		long millis = System.currentTimeMillis();
		java.sql.Date currentDate = new java.sql.Date(millis);

		if (date.compareTo(currentDate) > 0) {

			List<Availability> busList = services.checkAvailability(source, destination, date);
			if (busList.size() > 0) {
				for (Availability avail : busList) {
					Bus bus = services.searchBus(avail.getBusId());
					int availSeats = avail.getAvailSeats();
					System.out.println(bus);
					System.out.println("Available seats : " + availSeats);
					System.out.println(
							"--------------------------------------------------------------------------------------------------");
				}

				System.out.println("Enter the busId");
				int busId = Integer.parseInt(sc.next());
				ticket.setBusId(busId);

				// get the global userId
				ticket.setUserId(userId);

				Integer availSeats = services.checkAvailability(busId, date);
				if (availSeats>0) {
					System.out.println("Total available seats are: " + availSeats);
				}

				System.out.println("Enter number of seats to book");
				int noOfseats=Integer.parseInt(sc.next());
				if(noOfseats>0){
					ticket.setNumofseats(noOfseats);
					
				}else{
					throw new TicketBookingFailedException("Ticket Booking Failed");
				}
				Ticket bookedTicket = services.bookTicket(ticket);
				if (bookedTicket != null) {
					System.out.println("Ticket sucessfully Booked");
					System.out.println(bookedTicket);
				} else {
					System.out.println("Ticket Booking Failed");
					throw new TicketBookingFailedException("Ticket Booking Failed");
				}
			}else {
					throw new BusNotFoundException("No bus Found");
				}
			}else {
				System.out.println("Invalid date");
				throw new TicketBookingFailedException("Ticket Booking Failed: Invalid Date");
			}
		 

	}

	private static void deleteUser() throws DeletionFailedException {
		Boolean login = false;
		try {
			login = loginUser();
		} catch (LoginFailedException e1) {
			e1.printStackTrace();
		}
		if (login) {
			if (services.deleteUser(userId)) {
				System.out.println("Profile sucessfully Deleted");
			} else {
				System.out.println("Profile deletion Failed");
				throw new DeletionFailedException("User Profile deletion Failed");
			}
		}

	}

	private static void updateUser() throws UpdationFailedException {
		User user = new User();
		user.setUserId(userId);
		System.out.println("Enter the username");
		user.setUsername(sc.next());
		System.out.println("Enter the email id");
		user.setEmail(sc.next());
		System.out.println("Enter the password");
		user.setPassword(sc.next());
		System.out.println("Enter the contact no.");
		user.setContact(sc.nextLong());
		if (services.updateUser(user)) {
			System.out.println("Profile sucessfully Updated");
		} else {
			System.out.println("Profile Updation Failed!");
			throw new UpdationFailedException("User profile Updation Failed");
		}

	}

	public static boolean loginUser() throws LoginFailedException {
		System.out.println("Enter the userId");
		userId = Integer.parseInt(sc.next());
		System.out.println("Enter the password");
		String password = sc.next();
		User user=services.loginUser(userId, password);
		if (user != null) {
			System.out.println("hello "+user.getUsername());
			return true;
		} else {
			throw new LoginFailedException("User Login Failed");

		}

	}

	public static void createUser() throws RegsitrationFailedException {
		User user = new User();
		System.out.println("Enter the user id:");
		user.setUserId(Integer.parseInt(sc.next()));
		System.out.println("Enter the username");
		user.setUsername(sc.next());
		System.out.println("Enter the email id");
		user.setEmail(sc.next());
		System.out.println("Enter the password");
		user.setPassword(sc.next());
		System.out.println("Enter the contact no.");
		user.setContact(Long.parseLong(sc.next()));
		if (services.createUser(user)) {
			System.out.println("Profile sucessfully created");
		} else {
			System.out.println("Profile creation Failed!");
			throw new RegsitrationFailedException("User Registration Failed");
		}
	}

}
