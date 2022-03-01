package service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model.Passenger;
import util.DatabaseConnection;



public class UserService {
	public boolean adminLogin(String userName , String password) throws ClassNotFoundException, SQLException{
		int flag=0;
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from users where userName=? and userPassword=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,userName);
		stmt.setString(2,password);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			if (rs.getString("userName").equals(userName)) {
				if(rs.getString("userPassword").equals(password)) {
					System.out.println("You have successfully logged in as Admin");
					flag=1;
				}else {
					System.out.println("Enter a correct password");	
				}
			}else {
				if(rs.getString("userPassword").equals(password)) {
					System.out.println("Enter a correct admin name");
				}
				else {
					System.out.println("Wrong credentials");
				}
			}
		}
		if (flag==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public void userRegistration(String username,String userpassword) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from Users where userName=? and userPassword=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,username);
		stmt.setString(2, userpassword);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			System.out.println("Username or Userpassword already exists");
		}else {
			String query1 = "Insert into Users(userName,UserPassword) values (?,?) ";
			PreparedStatement stmt1 = conn.prepareStatement(query1);
			stmt1.setString(1,username);
			stmt1.setString(2, userpassword);
			if (stmt1.executeUpdate()>=1) {
				System.out.println("Sucessful registration");
			}else {
				System.out.println("unsucessful registration");
			}
		}
	}
	
	public boolean userLogin(String username,String userpassword) throws ClassNotFoundException, SQLException {
		int flag=0;
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from users where userName=? and userPassword=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, username);
		stmt.setString(2, userpassword);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			if (rs.getString("userName").equals(username)) {
				if(rs.getString("userPassword").equals(userpassword)) {
					System.out.println("You have successfully logged in");
					flag=1;
				}else {
					System.out.println("Enter a correct password");	
				}
			}else {
				if(rs.getString("userPassword").equals(userpassword)) {
					System.out.println("Enter a correct user name");
				}
				else {
					System.out.println("Wrong credentials");
				}
			}
		}
		if (flag==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public void searchTicket(String source,String destination,Timestamp Date,Timestamp Date1) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query="select * from train,coach where train.trainNo=coach.trainNo and train.trainName=coach.trainName and train.depatureDate=coach.depatureDate and train.sourceLoc=? and train.destination=? and train.depatureDate between ? and ? ";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, source);
		stmt.setString(2, destination);
		stmt.setTimestamp(3, Date);
		stmt.setTimestamp(4, Date1);
		ResultSet rs = stmt.executeQuery();
		int flag=0;
		while(rs.next()) {
				System.out.println(rs.getInt("trainNo")+"\t"+rs.getString("trainName")+"\t"+rs.getString("sourceLoc")+"\t"+rs.getString("destination")+"\t"+rs.getTimestamp("depatureDate")+"\t"+rs.getTimestamp("arrivalDate")+"\t"+rs.getString("coachId")+"\t"+rs.getString("coachType")+"\t"+rs.getInt("seats")+"\t"+rs.getInt("fare"));
				flag = 1;
		}
		if(flag==0) {
			System.out.println("No trains Found");
		}
	}
	
	public int getuserId(String userName) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from Users where userName=?";
		int id = 0;
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,userName);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			id = rs.getInt("userId");
		}
		return id;
	}
	
	private int getTotalFare(String coachId, String trainName, int trainNo, Timestamp depatureTime, int nop) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from coach where coachId=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		int fare=0;
		stmt.setString(1,coachId);
		stmt.setInt(2,trainNo);
		stmt.setString(3,trainName);
		stmt.setTimestamp(4,depatureTime);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			fare = rs.getInt("fare");
		}
		return (fare*nop);
	}
	
	
	public String getcoachId(String coachtype,String trainName,int trainNo, Timestamp depatureTime) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String Id ="";
		String query = "Select * from coach where coachType=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,coachtype);
		stmt.setInt(2,trainNo);
		stmt.setString(3,trainName);
		stmt.setTimestamp(4,depatureTime);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			Id = rs.getString("coachId");
		}
		return Id;
	}
	
	private void addPassengers(String passengerName, int passengerAge, String contactnumber,int bookingId,String seatnumber) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "Insert into Passenger(passengerName,passengerAge ,contactnumber,bookingId,seatno ) values (?,?,?,?,?) ";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,passengerName);
		stmt.setInt(2,passengerAge);
		stmt.setString(3,contactnumber);
		stmt.setInt(4,bookingId);
		stmt.setString(5,seatnumber);
		stmt.executeUpdate();
	}
	
	private void modifyseat(String coachId, int trainNo, String trainName, Timestamp depatureTime, int nop) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from coach where coachId=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,coachId);
		stmt.setInt(2,trainNo);
		stmt.setString(3,trainName);
		stmt.setTimestamp(4,depatureTime);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
		int nos = rs.getInt("seats")-nop;
		String sql = "UPDATE coach " +
	            "SET seats = ? WHERE coachId=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt1 = conn.prepareStatement(sql);
		stmt1.setInt(1,nos);
		stmt1.setString(2,coachId);
		stmt1.setInt(3,trainNo);
		stmt1.setString(4,trainName);
		stmt1.setTimestamp(5,depatureTime);
		stmt1.executeUpdate();
		}
	}
	
	
	public void bookticket(String userName, int trainNo, String trainName, Timestamp depatureTime, int nop,List<Passenger> list, String coachtype) throws ClassNotFoundException, SQLException {
		int userId = getuserId(userName);
		String coachId = getcoachId(coachtype,trainName,trainNo,depatureTime);
		int totalFare=getTotalFare(coachId,trainName,trainNo,depatureTime,nop);
		List <String> seatnumber = new ArrayList <String>();
		List <String> reservedseat = new ArrayList <String>();
		seatnumber=getSeatnumber(coachId,trainNo,trainName,depatureTime);
		Connection conn = DatabaseConnection.getConnection();
		int flag = 0;
		String query = "Insert into booking(totalFare,bookingtime,coachId,coachType,userId,trainNo,trainName,depatureDate) values (?,?,?,?,?,?,?,?) ";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1,totalFare);
		stmt.setTimestamp(2,new Timestamp(new Date().getTime()));
		stmt.setString(3,coachId);
		stmt.setString(4,coachtype);
		stmt.setInt(5,userId);
		stmt.setInt(6,trainNo);
		stmt.setString(7,trainName);
		stmt.setTimestamp(8,depatureTime);
		if(stmt.executeUpdate()>=1) {
			String query1 = "select TIMESTAMPDIFF(minute,?,bookingtime) AS MINUTE,bookingId from Booking";
			PreparedStatement stmt1 = conn.prepareStatement(query1);
			stmt1.setTimestamp(1,new Timestamp(new Date().getTime()));
			ResultSet rs = stmt1.executeQuery();
			while (rs.next()) {
			if(rs.isLast()) {
					int bookingId = rs.getInt("bookingId");
					for(int i=0;i<list.size();i++) {
						addPassengers(list.get(i).getPassengerName(),list.get(i).getPassengerAge(),list.get(i).getContactnumber(),bookingId,seatnumber.get(i));
						reservedseat.add(seatnumber.get(i));
					}
					flag=1;
				}
			}
			if(flag == 1) {
				modifyseat(coachId,trainNo,trainName,depatureTime,nop);
				deletereservedseat(reservedseat,seatnumber,coachId,trainNo,trainName,depatureTime);
				clearreservedseat(reservedseat);
				System.out.println("Booked Successfully");
			}
			else {
				System.out.println("Booking failed");
			}
		}
	}
	private void clearreservedseat(List<String> reservedseat) {
		for (int i=0;i<reservedseat.size();i++) {
			reservedseat.remove(i);
		}
		
	}

	private void deletereservedseat(List<String> reservedseat, List<String> seatnumber, String coachId, int trainNo,
			String trainName, Timestamp depatureTime) throws ClassNotFoundException, SQLException {
		seatnumber.removeAll(reservedseat);
		String seat = String.join(", ",seatnumber);
		seat=seat+",";
		Connection conn = DatabaseConnection.getConnection();
		String sql = "UPDATE coach " +
	            "SET seatno = ? WHERE coachId=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt1 = conn.prepareStatement(sql);
		stmt1.setString(1, seat);
		stmt1.setString(2,coachId);
		stmt1.setInt(3,trainNo);
		stmt1.setString(4,trainName);
		stmt1.setTimestamp(5,depatureTime);
		stmt1.executeUpdate();
	}

	private List<String> getSeatnumber(String coachId, int trainNo, String trainName, Timestamp depatureTime) throws ClassNotFoundException, SQLException {
		List <String> seatnum = null;
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from coach where coachId=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,coachId);
		stmt.setInt(2,trainNo);
		stmt.setString(3,trainName);
		stmt.setTimestamp(4,depatureTime);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			String seatno=rs.getString("seatno");
			seatnum = new ArrayList <String>(Arrays.asList(seatno.split(",")));
			
		}
		return seatnum;
	}
	
	public void printticket(int bookingID) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "select * from booking,Passenger,train where Passenger.bookingId = booking.bookingId and booking.bookingId=? and booking.trainNo=train.trainno and booking.trainName=train.trainName and booking.depatureDate=train.depatureDate";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1,bookingID);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			System.out.print("***************************RAILWAYRESERATIONSYSTEM*********************************************************************\n\n");
			System.out.print("BOOKING TIME: "+rs.getString("bookingtime")+"\n\n");
			System.out.print("TRAIN NO: "+rs.getInt("trainNo")+"                                "+"TRAIN NAME: "+rs.getString("trainName")+"\n");
			System.out.print("SOURCE  : "+rs.getString("sourceLoc")+"                                 "+"DESTINATION: "+rs.getString("destination")+"\n");
			System.out.print("DEPATURE: "+rs.getTimestamp("depatureDate")+"                 "+"ARRIVAL   : "+rs.getTimestamp("arrivalDate")+"\n");
			System.out.println("COACH ID  :"+rs.getString("coachId"));
			System.out.println("COACH TYPE:"+rs.getString("coachType"));
			System.out.println("FARE      :"+rs.getInt("totalfare"));
			System.out.println("************************************************************************************************************************");
			System.out.println(rs.getString("PassengerName")+"     "+rs.getInt("PassengerAge")+"           "+rs.getString("contactnumber")+"            "+rs.getString("seatno"));
		}
		while(rs.next()) {
			System.out.println(rs.getString("PassengerName")+"     "+rs.getInt("PassengerAge")+"           "+rs.getString("contactnumber")+"            "+rs.getString("seatno"));
		}
	}
	
	public void cancelticket(int bookingID) throws ClassNotFoundException, SQLException {
		String coachId = null; 
		String seats = null;
		int trainNo=0,seatsize = 0; 
		String trainName = null; 
		Timestamp depatureTime = null;
		int size=0;
		List <String> list = new ArrayList <String>();
		Connection conn = DatabaseConnection.getConnection();
		String query = "select * from Passenger where bookingId=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1,bookingID);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			list.add(rs.getString("seatno"));
		}
		size = list.size();
		String seat = String.join(",",list);
		seat=seat+",";
		String query1 = "delete from Passenger where bookingId=?";
		PreparedStatement stmt1 = conn.prepareStatement(query1);
		stmt1.setInt(1,bookingID);
		stmt1.executeUpdate();
		
		String query3 = "select * from Booking where bookingId=?";
		PreparedStatement stmt3 = conn.prepareStatement(query3);
		stmt3.setInt(1,bookingID);
		ResultSet rs3 = stmt3.executeQuery();
		if(rs3.next()) {
			coachId = rs3.getString("coachId");
			trainNo = rs3.getInt("trainNo");
			trainName = rs3.getString("trainName");
			depatureTime = rs3.getTimestamp("depatureDate");
		}
		
		String query4 = "Select seatno,seats from coach where coachId=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt4 = conn.prepareStatement(query4);
		stmt4.setString(1,coachId);
		stmt4.setInt(2,trainNo);
		stmt4.setString(3,trainName);
		stmt4.setTimestamp(4,depatureTime);
		ResultSet rs4 = stmt4.executeQuery();
		if(rs4.next()) {
			seats = rs4.getString("seatno");
			seatsize = rs4.getInt("seats");
		}
		System.out.println(coachId+"         "+trainNo+"        "+trainName+"    "+depatureTime);
		System.out.println(seats);
		seat=seat+seats;
		size=size+seatsize;
		String sql = "UPDATE coach SET seatno = ?,seats=? WHERE coachId=? and trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt5 = conn.prepareStatement(sql);
		stmt5.setString(1, seat);
		stmt5.setInt(2,size);
		stmt5.setString(3,coachId);
		stmt5.setInt(4,trainNo);
		stmt5.setString(5,trainName);
		stmt5.setTimestamp(6,depatureTime);
		stmt5.executeUpdate();
		System.out.println(seat+ "      "+size);
		
		String query2 = "delete from Booking where bookingId=?";
		PreparedStatement stmt2 = conn.prepareStatement(query2);
		stmt2.setInt(1,bookingID);
		stmt2.executeUpdate();
	}
}