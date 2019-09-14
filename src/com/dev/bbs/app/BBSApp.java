package com.dev.bbs.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.dev.bbs.beans.Admin;
import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;
import com.dev.bbs.exception.BusNotFoundException;
import com.dev.bbs.exception.DeletionFailedException;
import com.dev.bbs.exception.LoginFailedException;
import com.dev.bbs.exception.RegsitrationFailedException;
import com.dev.bbs.exception.TicketBookingFailedException;
import com.dev.bbs.exception.UpdationFailedException;
import com.dev.bbs.exception.UserNotFoundException;
import com.dev.bbs.services.ServiceDAO;
import com.dev.bbs.services.ServiceDAOImpl;

public class BBSApp {
	static ServiceDAO services = new ServiceDAOImpl();
	static Scanner sc = new Scanner(System.in);
	static int userId;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter a app to use" + "\n1. User App" + "\n2. Admin App" + "\n3. Exit");
		switch (Integer.parseInt(sc.next())) {
		case 1:
			user();
			break;
		case 2:
			admin();
			break;
		case 3:
			sc.close();
			System.exit(0);
			break;
		default:
			System.out.println("Enter the correct option");

		}
	}

	public static void user() {

		boolean loginReg = true;
		while (loginReg) {
			System.out.println("Enter a choice\n1. Login" + "\n2. Register" + "\n3. Exit");
			int choice = Integer.parseInt(sc.next());

			if (choice == 1) {
				Boolean login = false;
				try {
					login = loginUser();
				} catch (LoginFailedException e1) {
					//e1.printStackTrace();
					System.err.println(e1.getMessage());
				}
				if (login) {
					System.out.println("Login Successful");
					Boolean loop = true;
					while (loop) {
						// options to perform different operation
						System.out.println(
								"*******************************************************************************************************************************");
						System.out.println("Enter a choice");
						System.out.println("1.Update the User profile");
						System.out.println("2.Delete the user Profile");
						System.out.println("3.Book Ticket");
						System.out.println("4.Cancel Ticket");
						System.out.println("5.Search Bus Details");
						System.out.println("6.Check Ticket Availability");
						System.out.println("7.Print Booked Ticket");
						System.out.println("8.View Profile");
						System.out.println("9.Write Feedback");
						System.out.println("10.exit");
						int ch = Integer.parseInt(sc.next());
						switch (ch) {
						case 1:
							try {
								updateUser();
							} catch (UpdationFailedException e) {
								System.err.println(e.getMessage());
							}
							break;
						case 2:
							try {
								deleteUser();
							} catch (DeletionFailedException e2) {
								System.err.println(e2.getMessage());
							}
							try {
								loginUser();
							} catch (LoginFailedException e) {
								loop = false;
								System.err.println(e.getMessage());

							}
							break;
						case 3:
							try {
								bookTicket();
							} catch (Exception e1) {
								System.err.println(e1.getMessage());
							}
							break;
						case 4:
							cancelTicket();
							break;
						case 5:
							try {
								searchBus();
							} catch (BusNotFoundException e) {
								System.err.println(e.getMessage());
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
					System.err.println(e.getMessage());
				}

			} else if (choice == 3) {
				loginReg = false;
			} else {
				System.out.println("Plz enter the valid option");
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

			boolean idValidate = true;
			int busId = 0;
			while (idValidate) {
				System.out.println("Enter the Bus id");
				String sbusId = sc.next();
				Integer res = services.validateId(sbusId);
				if (res != null) {
					busId = res;
					idValidate = false;
				} else {
					System.out.println("Id should be Integer");
				}
			}
			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Integer availSeats = services.checkAvailability(busId, date);
			if (availSeats != null) {
				System.out.println("Total available seats are: " + availSeats);
			}

		} else if (chAvail == 2) {
			System.out.println("Enter source point");
			String source = sc.next();
			System.out.println("Enter Destination point");
			String destination = sc.next();

			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				System.err.println(e.getMessage());
			}

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
		boolean idValidate = true;
		int busId = 0;
		while (idValidate) {
			System.out.println("Enter the Bus id");
			String sbusId = sc.next();
			Integer res = services.validateId(sbusId);
			if (res != null) {
				busId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}

		Bus bus = services.searchBus(busId);
		if (bus != null) {
			System.out.println(bus);

		} else {
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
			boolean idValidate = true;
			int bookingId = 0;
			while (idValidate) {
				System.out.println("Enter the bookingId");
				String sbusId = sc.next();
				Integer res = services.validateId(sbusId);
				if (res != null) {
					bookingId = res;
					idValidate = false;
				} else {
					System.out.println("Id should be Integer");
				}
			}

			if (services.cancelTicket(bookingId, userId)) {
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

		System.out.println("Enter the date(yyyy-mm-dd)");
		String tempDate = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(tempDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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

				boolean idValidate = true;
				int busId = 0;
				while (idValidate) {
					System.out.println("Enter the busId");
					String sId = sc.next();
					Integer res = services.validateId(sId);
					if (res != null) {
						busId = res;
						idValidate = false;
					} else {
						System.out.println("Id should be Integer");
					}
				}
				ticket.setBusId(busId);

				// get the global userId
				ticket.setUserId(userId);

				Integer availSeats = services.checkAvailability(busId, date);
				Bus bus = services.searchBus(busId);
				if (availSeats > 0) {
					System.out.println("Total available seats are: " + availSeats);
				}

				System.out.println("Enter number of seats to book");
				int noOfseats = Integer.parseInt(sc.next());
				if (noOfseats > 0) {
					ticket.setTicketPrice(bus.getPrice() * noOfseats);

					ticket.setNumofseats(noOfseats);

				} else {
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
			} else {
				throw new BusNotFoundException("No bus Found");
			}
		} else {
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

		String email = null;
		boolean emailValidate = true;
		while (emailValidate) {
			System.out.println("Enter the email id");
			String sEmail = sc.next();
			String res = services.validateEmail(sEmail);
			if (res != null) {
				email = res;
				emailValidate = false;
			} else {
				System.out.println("Plz enter valid email");
			}
		}

		user.setEmail(email);
		System.out.println("Enter the password");
		user.setPassword(sc.next());
		boolean conValidate = true;
		while (conValidate) {
			System.out.println("Enter the 10 digit Contact no.)");
			String sContact = sc.next();
			Long contact = services.valdateContact(sContact);
			if (contact != null) {
				user.setContact(contact);
				conValidate = false;
			} else {
				System.out.println("Contact ");
			}
		}

		if (services.updateUser(user)) {
			System.out.println("Profile sucessfully Updated");
		} else {
			System.out.println("Profile Updation Failed!");
			throw new UpdationFailedException("User profile Updation Failed");
		}

	}

	public static boolean loginUser() throws LoginFailedException {

		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the userId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				userId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		System.out.println("Enter the password");
		String password = sc.next();
		User user = services.loginUser(userId, password);
		if (user != null) {
			System.out.println("hello " + user.getUsername());
			return true;
		} else {
			throw new LoginFailedException("User Login Failed");

		}

	}

	public static void createUser() throws RegsitrationFailedException {
		User user = new User();
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the userId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				user.setUserId(res);
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}

		System.out.println("Enter the username");
		user.setUsername(sc.next());
		String email = null;
		boolean emailValidate = true;
		while (emailValidate) {
			System.out.println("Enter the email id");
			String sEmail = sc.next();
			String res = services.validateEmail(sEmail);
			if (res != null) {
				email = res;
				emailValidate = false;
			} else {
				System.out.println("Plz enter valid email");
			}
		}
		user.setEmail(email);
		System.out.println("Enter the password");
		user.setPassword(sc.next());
		System.out.println("Enter the contact no.");

		boolean conValidate = true;
		while (conValidate) {
			System.out.println("Enter the 10 digit Contact no.)");
			String sContact = sc.next();
			Long contact = services.valdateContact(sContact);
			if (contact != null) {
				user.setContact(contact);
				conValidate = false;
			} else {
				System.out.println("Contact ");
			}
		}

		if (services.createUser(user)) {
			System.out.println("Profile sucessfully created");
		} else {
			System.out.println("Profile creation Failed!");
			throw new RegsitrationFailedException("User Registration Failed");
		}
	}

	public static void admin() {
		Admin admin = null;
		try {
			admin = adminLogin();
			System.out.println("hello " + admin.getName());
		} catch (LoginFailedException e1) {
			e1.printStackTrace();
		}

		if (admin != null) {
			System.out.println("Login Successful");
			Boolean b = true;
			while (b) {
				// options to perform different operation
				System.out.println(
						"***************************************************************************************************************");

				System.out.println("Enter a choice");
				System.out.println("1.Add Bus");
				System.out.println("2.Update Bus");
				System.out.println("3.Delete Bbs");
				System.out.println("4.Bus availability");
				System.out.println("5.Search User");
				System.out.println("6.Search Bus");
				System.out.println("7.View Feedback");
				System.out.println("8.Set Bus Availability");
				System.out.println("9.Check Booked Ticket");
				System.out.println("10.exit");
				int ch = Integer.parseInt(sc.next());
				switch (ch) {
				case 1:
					try {
						createBus();
					} catch (RegsitrationFailedException e1) {
						e1.printStackTrace();
					}
					break;
				case 2:
					try {
						updateBus();
					} catch (UpdationFailedException e1) {
						e1.printStackTrace();
					}
					break;
				case 3:
					try {
						deletebus();
					} catch (DeletionFailedException e1) {
						e1.printStackTrace();
					}
					break;
				case 4:
					checkAvailabilityA();
					break;
				case 5:
					try {
						searchUser();
					} catch (UserNotFoundException e) {
						e.printStackTrace();
					}
					break;
				case 6:
					try {
						searchBusA();
					} catch (BusNotFoundException e) {
						e.printStackTrace();
					}
					break;
				case 7:
					viewFeddback();
					break;
				case 8:
					try {
						setBusAvailability();
					} catch (BusNotFoundException e) {
						e.printStackTrace();
					}
					break;
				case 9:
					checkBookedTicket();
					break;
				case 10:
					b = false;
					break;
				default:
					System.out.println("Plz enter correct option");
				}
			}
		} else {
			System.out.println("Login failed");
		}

	}

	private static void checkBookedTicket() {
		int busId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the busId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				busId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}

		System.out.println("Enter the date(yyyy-mm-dd)");
		String tempDate = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(tempDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Ticket> ticketAL = services.getTicketByBus(busId, date);
		if (ticketAL.size() > 0) {
			System.out.println("Tickets are:");
			for (Ticket ticket : ticketAL) {
				System.out.println(ticket);
				System.out.println(
						"---------------------------------------------------------------------------------------------------------------------------------------");
			}
		} else {
			System.out.println("No Ticket found");
		}

	}

	private static void setBusAvailability() throws BusNotFoundException {
		Availability availability = new Availability();
		int busId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the busId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				busId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		Bus bus = services.searchBus(busId);
		if (bus != null) {
			System.out.println(bus);
			availability.setBusId(busId);
			System.out.println("Enter the Available seats");
			availability.setAvailSeats(Integer.parseInt(sc.next()));
			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			availability.setAvailDate(date);

			if (services.setBusAvailability(availability)) {
				System.out.println("Successfully Set the availability");
			}
		} else {
			System.out.println("Failed to Set the availability");
			throw new BusNotFoundException("No Bus found");
		}

	}

	private static void viewFeddback() {
		List<Feedback> feedList = services.viewFeedbac();
		if (feedList.size() > 0) {
			System.out.println("Feedbacks are:");
			for (Feedback feed : feedList) {
				System.out.println(feed);
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------");
			}
		} else {
			System.out.println("No feedback found");
		}

	}

	private static void searchBusA() throws BusNotFoundException {
		int busId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the busId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				busId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		Bus bus = services.searchBus(busId);
		if (bus != null) {
			System.out.println(bus);

		} else {
			throw new BusNotFoundException("No Bus found");
		}

	}

	public static void createBus() throws RegsitrationFailedException {
		Bus bus = new Bus();
		int busId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the busId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				busId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		bus.setBusId(busId);
		System.out.println("Enter Bus name");
		bus.setBusname(sc.next());
		System.out.println("Enter source point");
		bus.setSource(sc.next());
		System.out.println("Enter Destination point");
		bus.setDestination(sc.next());
		System.out.println("Enter Bus type");
		bus.setBusType(sc.next());
		System.out.println("Total seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter fare charge");
		bus.setPrice(sc.nextDouble());

		if (services.createBus(bus)) {
			System.out.println("Bus added Successfully");
		} else {
			throw new RegsitrationFailedException("Bus could not be added");
		}
	}

	public static Admin adminLogin() throws LoginFailedException {
		int adminId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the adminId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				adminId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		System.out.println("Enter Admin password");
		String password = sc.next();
		Admin admin = services.adminLogin(adminId, password);
		if (admin != null) {
			return admin;
		} else {
			throw new LoginFailedException("Admin login Failed");
		}

	}

	public static void updateBus() throws UpdationFailedException {
		Bus bus = new Bus();
		int busId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the busId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				busId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		bus.setBusId(busId);
		System.out.println("Enter Bus name");
		bus.setBusname(sc.next());
		System.out.println("Enter source point");
		bus.setSource(sc.next());
		System.out.println("Enter Destination point");
		bus.setDestination(sc.next());
		System.out.println("Enter Bus type");
		bus.setBusType(sc.next());
		System.out.println("Total seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter fare charge");
		bus.setPrice(sc.nextDouble());

		if (services.updateBus(bus)) {
			System.out.println("Bus updated Successfully");
		} else {
			throw new UpdationFailedException("Bus Updation Failed");
		}

	}

	public static void deletebus() throws DeletionFailedException {
		int busId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the busId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				busId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		System.out.println("Are you sure you want to delete\n1. Yes");
		if (Integer.parseInt(sc.next()) == 1) {
			if (services.deletebus(busId)) {
				System.out.println("Sucessfylly Deleted Bus data");
			} else {
				throw new DeletionFailedException("Bus deletion failed");
			}
		}

	}

	public static void checkAvailabilityA() {

		System.out.println("Enter a choice");
		System.out.println("Check Availability by \n1.Bus id\n2.Source and Destination");
		int chAvail = Integer.parseInt(sc.next());
		if (chAvail == 1) {
			System.out.println("Enter the Bus id");
			int busId = Integer.parseInt(sc.next());
			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Integer availSeats = services.checkAvailability(busId, date);
			if (availSeats != null) {
				System.out.println("Total available seats are: " + availSeats);
			}

		} else if (chAvail == 2) {
			System.out.println("Enter source point");
			String source = sc.next();
			System.out.println("Enter Destination point");
			String destination = sc.next();
			System.out.println("Enter the date(yyyy-mm-dd)");
			String tempDate = sc.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(tempDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<Availability> avaiList = services.checkAvailability(source, destination, date);
			for (Availability avail : avaiList) {
				Bus bus = services.searchBus(avail.getBusId());
				System.out.println(bus);
				System.out.println("Available seats : " + avail.getAvailSeats());

			}
		}
	}

	public static void searchUser() throws UserNotFoundException {
		int userId = 0;
		boolean idValidate = true;
		while (idValidate) {
			System.out.println("Enter the userId");
			String sId = sc.next();
			Integer res = services.validateId(sId);
			if (res != null) {
				userId = res;
				idValidate = false;
			} else {
				System.out.println("Id should be Integer");
			}
		}
		User user = services.searchUser(userId);
		if (user != null) {
			System.out.println(user);
		} else {
			throw new UserNotFoundException("No User Found");
		}

	}

}
