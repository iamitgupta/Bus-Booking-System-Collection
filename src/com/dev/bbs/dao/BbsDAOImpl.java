package com.dev.bbs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.dev.bbs.beans.Admin;
import com.dev.bbs.beans.Availability;
import com.dev.bbs.beans.Bus;
import com.dev.bbs.beans.Feedback;
import com.dev.bbs.beans.Ticket;
import com.dev.bbs.beans.User;

public class BbsDAOImpl implements BbsDAO {

	public BbsDAOImpl() {
		try {
			// load the driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// db query
	String url = "jdbc:mysql://127.0.0.1:3306/busbookingsystem_db?user=root&password=root";

	@Override
	public Boolean createUser(User user) {
		// Insert user into userTable if user doesn't exist
		String query = "INSERT INTO users_info VALUES (?,?,?,?,?)";
		User tempuser = searchUser(user.getUserId());
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			if (tempuser == null) {
				pstmt.setInt(1, user.getUserId());
				pstmt.setString(2, user.getUsername());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getPassword());
				pstmt.setLong(5, user.getContact());
				pstmt.executeUpdate();
				return true;
			} else {

				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean updateUser(User user) {
		// Update user's data
		String query = "UPDATE users_info SET username=?,email=?,password=?,contact=? WHERE user_id=?";
		User tempuser = searchUser(user.getUserId());
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			if (tempuser != null) {
				pstmt.setString(1, user.getUsername());
				pstmt.setString(2, user.getEmail());
				pstmt.setString(3, user.getPassword());
				pstmt.setLong(4, user.getContact());
				pstmt.setInt(5, user.getUserId());
				pstmt.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean deleteUser(int userId) {
		// delete user if already exists
		String query = "DELETE FROM users_info WHERE user_id=?";
		User user = searchUser(userId);
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			if (user != null) {
				pstmt.setInt(1, userId);
				pstmt.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User loginUser(int userId, String password) {
		// return user detail
		String query = "SELECT * FROM users_info where user_id='" + userId + "' and password='" + password + "'";

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery(query)) {

			User user = new User();

			if (rs.next()) {
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setContact(rs.getLong("contact"));
				return user;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User searchUser(int userId) {
		// return user detail
		String query = "SELECT  * FROM users_info where user_id=" + userId;
		User user = null;

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setContact(rs.getLong("contact"));
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Boolean createBus(Bus bus) {
		// Insert user into userTable if user doesn't exist
		String query = "INSERT INTO bus_info VALUES (?,?,?,?,?,?,?)";
		Bus tempbus = searchBus(bus.getBusId());
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			if (tempbus != null) {
				return false;
			} else {
				pstmt.setInt(1, bus.getBusId());
				pstmt.setString(2, bus.getBusname());
				pstmt.setString(3, bus.getSource());
				pstmt.setString(4, bus.getDestination());
				pstmt.setString(5, bus.getBusType());
				pstmt.setInt(6, bus.getTotalSeats());
				pstmt.setDouble(7, bus.getPrice());

				pstmt.executeUpdate();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean updateBus(Bus bus) {
		// Insert user into userTable if user doesn't exist
		String query = "UPDATE bus_info SET busname=?,source=?,destination=?,"
				+ "bus_type=?,total_seats=?,price=? WHERE bus_id=?";
		Bus tempbus = searchBus(bus.getBusId());
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			if (tempbus != null) {
				pstmt.setString(1, bus.getBusname());
				pstmt.setString(2, bus.getSource());
				pstmt.setString(3, bus.getDestination());
				pstmt.setString(4, bus.getBusType());
				pstmt.setInt(5, bus.getTotalSeats());
				pstmt.setDouble(6, bus.getPrice());
				pstmt.setInt(7, bus.getBusId());

				pstmt.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Bus searchBus(int busId) {
		// return Bus detail
		String query = "SELECT  * FROM bus_info where bus_id=" + busId;
		Bus bus = null;

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			if (rs.next()) {
				bus = new Bus();
				bus.setBusId(rs.getInt("bus_id"));
				bus.setBusname(rs.getString("busname"));
				bus.setSource(rs.getString("source"));
				bus.setDestination(rs.getString("destination"));
				bus.setBusType(rs.getString("bus_type"));
				bus.setTotalSeats(rs.getInt("total_seats"));
				bus.setPrice(rs.getDouble("price"));
				return bus;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bus;
	}

	@Override
	public Boolean deletebus(int busId) {
		// delete bus if already exists
		String query = "DELETE FROM bus_info WHERE bus_id=?";
		Bus bus = searchBus(busId);
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			if (bus != null) {
				pstmt.setInt(1, busId);
				pstmt.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Admin adminLogin(int adminId, String password) {
		// admin login
		String query = "SELECT * FROM admin_info where admin_id=" + adminId + " and password='" + password + "'";
		Admin admin=null;
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			if (rs.next()) {
				admin=new Admin();
				admin.setAdminId(rs.getInt("admin_id"));
				admin.setPassword(rs.getString("password"));
				admin.setName(rs.getString("name"));
				admin.setEmail(rs.getString("email"));
				admin.setContact(rs.getLong("contact"));
				return admin;
			} else {
				return admin;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}

	@Override
	public Ticket bookTicket(Ticket ticket) {
		String query = "INSERT INTO booking_info (bus_id,user_id,journey_date,numofseats,booking_datetime)"
				+ " VALUES (?,?,?,?,?)";
		String availQuery = "UPDATE  availability SET avail_seats=? WHERE avail_date=? and bus_id=?";
		int totalAvailSeats = checkAvailability(ticket.getBusId(), (java.sql.Date) ticket.getJourneyDate());

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement availpstmt = conn.prepareStatement(availQuery);) {
			if (ticket.getNumofseats() <= totalAvailSeats) {

				//get timestam
				ticket.setBookingDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));
				pstmt.setInt(1, ticket.getBusId());
				pstmt.setInt(2, ticket.getUserId());
				pstmt.setDate(3, (java.sql.Date) ticket.getJourneyDate());
				pstmt.setInt(4, ticket.getNumofseats());
				pstmt.setTimestamp(5, (Timestamp) ticket.getBookingDatetime());
				int tic = pstmt.executeUpdate();
				if (tic > -1) {
					// to decrement number of seats from availability table
					availpstmt.setInt(1, totalAvailSeats - ticket.getNumofseats());
					availpstmt.setDate(2, (java.sql.Date) ticket.getJourneyDate());
					availpstmt.setInt(3, ticket.getBusId());
					availpstmt.executeUpdate();

					ResultSet rs = pstmt.getGeneratedKeys();
					rs.next();
					ticket.setBookingId(rs.getInt(1));
					return ticket;

				}
			} else {
				return null;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean cancelTicket(int bookingId, int userId) {
		// cancel if already booked and add num of seats to availability column
		String query = "DELETE FROM booking_info WHERE booking_id=? and user_id=?";
		String availQuery = "UPDATE  availability SET avail_seats=? WHERE avail_date=? and bus_id=?";
		Ticket ticket = getTicket(bookingId);
		System.out.println(ticket);

		int totalAvailSeats = checkAvailability(ticket.getBusId(), (java.sql.Date) ticket.getJourneyDate());

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);
				PreparedStatement availpstmt = conn.prepareStatement(availQuery);) {
			if (ticket != null) {
				pstmt.setInt(1, bookingId);
				pstmt.setInt(2, userId);
				pstmt.executeUpdate();

				availpstmt.setInt(1, ticket.getNumofseats() + totalAvailSeats);
				availpstmt.setDate(2, (java.sql.Date) ticket.getJourneyDate());
				availpstmt.setInt(3, ticket.getBusId());
				availpstmt.executeUpdate();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Ticket getTicket(int bookingId) {
		// return Ticket detail
		String query = "SELECT  * FROM booking_info where booking_id=" + bookingId;

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			if (rs.next()) {

				Ticket ticket = new Ticket();
				ticket.setBookingId(rs.getInt("booking_id"));
				ticket.setBusId(rs.getInt("bus_id"));
				ticket.setUserId(rs.getInt("user_id"));
				ticket.setJourneyDate(rs.getDate("journey_date"));
				ticket.setNumofseats(rs.getInt("numofseats"));
				ticket.setBookingDatetime(rs.getTimestamp("booking_datetime"));
				return ticket;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Availability> checkAvailability(String source, String destination, java.sql.Date date) {
		String query = "SELECT bus_id from bus_info where source='" + source + "'" + " and destination='" + destination
				+ "'";

		List<Availability> availList = new ArrayList<Availability>();
		Availability availability = null;
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				availability = new Availability();
				availability.setBusId(rs.getInt("bus_id"));
				availability.setAvailSeats(checkAvailability(rs.getInt("bus_id"), date));
				availList.add(availability);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return availList;
	}

	@Override
	public Integer checkAvailability(int busId, java.sql.Date date) {
		String query = "SELECT avail_seats  FROM availability where bus_id=" + busId + " and avail_date='" + date + "'";
		int seats = 0;
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			if (rs.next()) {
				seats = rs.getInt("avail_seats");
				return seats;
			} else {
				return seats;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seats;
	}

	@Override
	public List<Integer> getAllTicket(int userId) {
		String query = "SELECT  * FROM booking_info where user_id=" + userId;
		List<Integer> ticketAl = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				ticketAl.add(rs.getInt("booking_id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketAl;
	}

	@Override
	public Boolean writeFeedback(Feedback feed) {
		String query = "INSERT INTO suggestion (user_id,feedback) VALUES (?,?)";
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, feed.getUserId());
			pstmt.setString(2, feed.getFeedback());
			int res = pstmt.executeUpdate();
			if (res > -1)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Feedback> viewFeedbac() {
		String query = "select * from suggestion";
		List<Feedback> feedList = new ArrayList<>();
		Feedback feed = null;
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				feed = new Feedback();
				feed.setSuggId(rs.getInt("sugg_id"));
				feed.setUserId(rs.getInt("user_id"));
				feed.setFeedback(rs.getString("feedback"));

				feedList.add(feed);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedList;

	}

	@Override
	public List<Ticket> getTicketByBus(int busId, Date date) {
		String query = "SELECT  * FROM booking_info where bus_id="+busId+" and journey_date='"+date+"'";
		List<Ticket> ticketAl = new ArrayList<>();
		Ticket ticket=null;
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				ticket=new Ticket();
				ticket.setBookingId(rs.getInt("booking_id"));
				ticket.setUserId(rs.getInt("user_id"));
				ticket.setBusId(rs.getInt("bus_id"));
				ticket.setJourneyDate(rs.getDate("journey_date"));
				ticket.setNumofseats(rs.getInt("numofseats"));
				ticket.setBookingDatetime(rs.getTimestamp("booking_datetime"));
				ticketAl.add(ticket);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketAl;
	}

	@Override
	public Boolean setBusAvailability(Availability availability) {
		String query = "INSERT INTO availability (avail_date,avail_seats,bus_id) VALUES (?,?,?)";
		try (Connection conn = DriverManager.getConnection(url);
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setDate(1, availability.getAvailDate());
			pstmt.setInt(2, availability.getAvailSeats());
			pstmt.setInt(3, availability.getBusId());
			int res = pstmt.executeUpdate();
			if (res > -1)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
