package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.print.attribute.DateTimeSyntax;

import util.DatabaseConnection;
//int TrainNo,DateTimeSyntax arrivalTime,DateTimeSyntax depatureTime
public class TrainService {
	public void addTrain(String trainName,int trainNo,String source,String Destination,Timestamp depatureTime,Timestamp arrivalTime) throws SQLException, ClassNotFoundException {
		Connection conn = DatabaseConnection.getConnection();
		 String Query="select * from Train where trainNo=? and arrivalDate between  ? and  ?";
		 PreparedStatement stmt = conn.prepareStatement(Query);
		 stmt.setInt(1,trainNo);
		 stmt.setTimestamp(2, depatureTime);
		 stmt.setTimestamp(3, arrivalTime);
		 ResultSet rs = stmt.executeQuery();
		 if (rs.next()) {
			 System.out.println("Train will arrive "+source+" at "+rs.getTimestamp("arrivalDate"));
		 }else {
			 String sql = "INSERT INTO Train (trainName, trainNo,sourceLoc,destination,depatureDate,arrivalDate) VALUES (?,?,?,?,?,?) ";
			 PreparedStatement stmt1 = conn.prepareStatement(sql);
			 stmt1.setString(1, trainName);
			 stmt1.setInt(2, trainNo);
			 stmt1.setString(3, source);
			 stmt1.setString(4, Destination);
			 stmt1.setTimestamp(5, depatureTime);
			 stmt1.setTimestamp(6, arrivalTime);
			 if(stmt1.executeUpdate()>=1) {
					System.out.println("Record added successfully");
			}else {
					System.out.println("unsuccessfully insert operation");
			}
		 }
	}
	public void addcoach(String coachid,String coachtype,int seats,String trainName,int trainNo,int fare,Timestamp depatureTime) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "select * from coach where coachId=? and trainName = ? and trainNo = ? and depatureDate = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1,coachid);
		stmt.setString(2,trainName);
		stmt.setInt(3,trainNo);
		stmt.setTimestamp(4, depatureTime);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			 System.out.println("Coach already exixts");
		 }else {
			 String sql = "INSERT INTO coach (coachId, coachType,seats,trainName,trainNo,fare,depatureDate) VALUES (?,?,?,?,?,?,?) ";
			 PreparedStatement stmt1 = conn.prepareStatement(sql);
			 stmt1.setString(1, coachid);
			 stmt1.setString(2, coachtype);
			 stmt1.setInt(3, seats);
			 stmt1.setString(4,trainName);
			 stmt1.setInt(5, trainNo);
			 stmt1.setInt(6, fare);
			 stmt1.setTimestamp(7, depatureTime);
			 if(stmt1.executeUpdate()>=1) {
					System.out.println("Record added successfully");
			}else {
					System.out.println("unsuccessfully insert operation");
			}
		 }
	}
	public void updatecoach(String coachid,int trainno,int Fare) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query = "Select * from coach";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			if (rs.getString("coachId").equals(coachid)) {
				if(rs.getInt("trainNo") == trainno) {
					System.out.println("You can make change");
					String sql = "UPDATE coach " +
				            "SET fare = ? WHERE trainNo=? and coachId=?";
					PreparedStatement stmt1 = conn.prepareStatement(sql);
					stmt1.setInt(1,Fare);
					stmt1.setString(3, coachid);
					stmt1.setInt(2,trainno);
					if(stmt1.executeUpdate()>=1) {
						System.out.println("Record added successfully");
					}else {
						System.out.println("unsuccessfully insert operation");
				}
				        
				}else {
					System.out.println("Enter a train no");	
				}
			}else {
				if(rs.getInt("trainNo") == trainno) {
					System.out.println("Enter correct coach id");
				}
				else {
					System.out.println("Wrong credentials");
				}
			}
		}
		
	}
	public void cancelTrain(int trainNo,String trainName,Timestamp depatureTime) throws ClassNotFoundException, SQLException {
		Connection conn = DatabaseConnection.getConnection();
		String query1 = "delete from coach where trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt1 = conn.prepareStatement(query1);
		stmt1.setInt(1,trainNo);
		stmt1.setString(2, trainName);
		stmt1.setTimestamp(3, depatureTime);
		String query = "delete from train where trainNo=? and trainName=? and depatureDate=?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1,trainNo);
		stmt.setString(2, trainName);
		stmt.setTimestamp(3, depatureTime);
		
		if (stmt1.executeUpdate()>=1) {
			if (stmt.executeUpdate()>=1) {
				System.out.println("Cancelled train successfully");
			}
		}else {
			System.out.println("Cant delete the train");
		}
		
	}
	
	public void showtrain() throws ClassNotFoundException, SQLException{
		Connection conn = DatabaseConnection.getConnection();
		String query="select * from train,coach where train.trainNo=coach.trainNo and train.trainName=coach.trainName and train.depatureDate=coach.depatureDate ";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			System.out.println(rs.getInt("trainNo")+"\t"+rs.getString("trainName")+"\t"+rs.getString("sourceLoc")+"\t"+rs.getString("destination")+"\t"+rs.getTimestamp("depatureDate")+"\t"+rs.getTimestamp("arrivalDate")+"\t"+rs.getString("coachId")+"\t"+rs.getString("coachType")+"\t"+rs.getInt("seats")+"\t"+rs.getInt("fare"));
		}
	}
}
