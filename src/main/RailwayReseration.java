package main;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Passenger;
import service.TrainService;
import service.UserService;
import util.DatabaseConnection;

public class RailwayReseration {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String userName,userPassword,adminName,adminPassword,trainName,source,destination,at,dt,coachid,coachtype,passengerName,contact;
		boolean adminL,userL;
		int ch,choice,trainNo,seats,fare,userch,userChoice,nop,passengerAge,bookingID;
		Timestamp arrivalTime,depatureTime,depatureTime1;
		DatabaseConnection.getConnection();
		Scanner sc = new Scanner(System.in);
		UserService userService = new UserService();
		TrainService trainService = new TrainService();
		boolean bool=false;
		 while (!bool) {
			 System.out.println("\n1.Admin\n2.User\n3.exit ");
			 System.out.println("Enter your choice: ");
			 ch  = sc.nextInt();
			 sc.nextLine();
			 switch(ch) {
			 	case 1:
			 		System.out.println("Enter Admin name: ");
			 		adminName= sc.nextLine();
			 		System.out.println("Enter Admin Password: ");
			 		adminPassword = sc.nextLine();
			 		adminL=userService.adminLogin(adminName,adminPassword);
			 		while(adminL) {
			 			System.out.println("\n1.Add Train\n2.Add coach\n3.Update Coach\n4.Cancel Train\n5.Show Train\n6.Exit ");
			 			System.out.println("Enter your choice: ");
			 			choice=sc.nextInt();
			 			switch(choice) {
			 			case 1:
			 				System.out.println("Enter Train No: ");
			 				trainNo=sc.nextInt();
			 				sc.nextLine();
			 				System.out.println("Enter Train Name: ");
			 				trainName = sc.nextLine();
			 				System.out.println("Enter Source: ");
			 				source = sc.nextLine();
			 				System.out.println("Enter Destination: ");
			 				destination = sc.nextLine();
			 				System.out.println("Enter Depature Time: ");
			 				dt = sc.nextLine();
			 				depatureTime = Timestamp.valueOf(dt);
			 				System.out.println("Enter Arrival Time: ");
			 				at = sc.nextLine();
			 				arrivalTime = Timestamp.valueOf(at);
			 				trainService.addTrain(trainName,trainNo,source,destination,depatureTime,arrivalTime);
					 		break;
			 			case 2:
			 				sc.nextLine();
			 				System.out.println("Enter Train Name: ");
			 				trainName = sc.nextLine();
			 				System.out.println("Enter Coach Id: ");
			 				coachid = sc.nextLine();
			 				System.out.println("Enter Coach Name: ");
			 				coachtype = sc.nextLine();
			 				System.out.println("Enter number of seats: ");
			 				seats=sc.nextInt();
			 				System.out.println("Enter Train No: ");
			 				trainNo=sc.nextInt();
			 				System.out.println("Enter fare amount for this coach in Rupees: ");
			 				fare=sc.nextInt();
			 				sc.nextLine();
			 				System.out.println("Enter Depature Time: ");
			 				dt = sc.nextLine();
			 				depatureTime = Timestamp.valueOf(dt);
			 				trainService.addcoach(coachid,coachtype,seats,trainName,trainNo,fare,depatureTime);
			 				break;
			 			case 3:
			 				System.out.println("Enter Train No: ");
			 				trainNo=sc.nextInt();
			 				sc.nextLine();
			 				System.out.println("Enter Coach Id: ");
			 				coachid = sc.nextLine();
			 				System.out.println("Enter the fare to be updated: ");
			 				fare=sc.nextInt();
			 				trainService.updatecoach(coachid,trainNo,fare);
					 		break;
			 			case 4:
			 				System.out.println("Enter Train No: ");
			 				trainNo=sc.nextInt();
			 				sc.nextLine();
			 				System.out.println("Enter Train Name: ");
			 				trainName = sc.nextLine();
			 				System.out.println("Enter depature time: ");
			 				dt = sc.nextLine();
			 				depatureTime=Timestamp.valueOf(dt);
			 				trainService.cancelTrain(trainNo,trainName,depatureTime);
					 		break;
			 			case 5:
			 				trainService.showtrain();
					 		break;
					 	case 6:
					 		adminL=false;
					 		break;
					 	default:
					 		System.out.println("Enter a valid number");
			 			
			 			}
			 			
			 		}
			 		break;
			 	case 2:
			 		//print ticket
			 		//cancel ticket
			 		System.out.println("\n1.User Registration\n2.User Login\n3.exit ");
					System.out.println("Enter your choice: ");
					userch  = sc.nextInt();
					sc.nextLine();
					switch(userch) {
						case 1:
							System.out.println("Enter User name: ");
					 		userName= sc.nextLine();
					 		System.out.println("Enter User Password: ");
					 		userPassword = sc.nextLine();
							userService.userRegistration(userName,userPassword);
							break;
						case 2:
							System.out.println("Enter User name: ");
					 		userName= sc.nextLine();
					 		System.out.println("Enter User Password: ");
					 		userPassword = sc.nextLine();
							userL = userService.userLogin(userName,userPassword);
							while(userL) {
								System.out.println("\n1.Search train\n2.book ticket\n3.print ticket\n4.Cancel ticket\n5.exit ");
								System.out.println("Enter your choice: ");
								userChoice  = sc.nextInt();
								sc.nextLine();
								switch(userChoice) {
									case 1:
										System.out.println("Enter Source: ");
						 				source = sc.nextLine();
						 				System.out.println("Enter Destination: ");
						 				destination = sc.nextLine();
						 				System.out.println("Enter Depature Time: ");
						 				dt = sc.nextLine();
						 				at = dt +" 23:59:59";
						 				dt = dt +" 00:00:00";
						 				depatureTime = Timestamp.valueOf(dt);
						 				depatureTime1 = Timestamp.valueOf(at);
										userService.searchTicket(source, destination, depatureTime,depatureTime1);
										break;
									case 2:
										System.out.println("Enter User name: ");
								 		userName= sc.nextLine();
										System.out.println("Enter Train No: ");
						 				trainNo=sc.nextInt();
						 				sc.nextLine();
						 				System.out.println("Enter Train Name: ");
						 				trainName = sc.nextLine();
						 				System.out.println("Enter depature time: ");
						 				dt = sc.nextLine();
						 				depatureTime=Timestamp.valueOf(dt);
						 				System.out.println("Enter Number of passengers: ");
						 				nop=sc.nextInt();
						 				sc.nextLine();
						 				List<Passenger> list=new ArrayList<Passenger>();
						 				for (int i=0;i<nop;i++) {
						 					System.out.println("Enter passenger Name:");
						 					passengerName=sc.nextLine();
						 					System.out.println("Enter passenger Age:");
						 					passengerAge=sc.nextInt();
						 					sc.nextLine();
						 					System.out.println("Enter passenger Contact number:");
						 					contact=sc.nextLine();
						 					list.add(new Passenger(passengerName,passengerAge,contact));
						 				}
						 				System.out.println("Enter coach type:");
						 				coachtype=sc.nextLine();
						 				userService.bookticket(userName,trainNo,trainName,depatureTime,nop,list,coachtype);
										break;
									case 3:
										System.out.println("Enter your booking ID:");
										bookingID=sc.nextInt();
										userService.printticket(bookingID);
										break;
									case 4:
										System.out.println("Enter your booking ID:");
										bookingID=sc.nextInt();
										userService.cancelticket(bookingID);
										break;
									case 5:
										userL=false;
										break;
									default:
										System.out.println("Enter a corect choice");
								
								}
							}
							break;
						case 3:
							
							break;
						default:
							System.out.println("Enter a correct choice");
					}
			 		
			 		break;
			 	case 3:
			 		bool=true;
			 		break;
			 	default:
			 		System.out.println("Enter a valid name");
			 		
			 }
		 }
		 sc.close();
	}

}
