package com.hotelmanagement;

import java.sql.*;
import java.util.Scanner;

public class Hotel_Reservation_System 
{
	private static final String url ="jdbc:mysql://localhost:3306/hotel_db";
	private static final String username ="root";
	private static final String password ="W123@jai";
	
	public static void main(String[] args) throws InterruptedException,SQLException 
	{
     try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	     } 
     catch (ClassNotFoundException e) 
         {
		System.out.println("driver class loaded successfully");
		System.out.println(e.getMessage());
	     }
     try {
		Connection con = DriverManager.getConnection(url,username,password);
		System.out.println("connection establish successfully");
		while(true)
		{
			System.out.println();
			System.out.println("hotel management sysytem");
			System.out.println("1, reserve a room");
			System.out.println("2, view reservation");
			System.out.println("3, got room number");
			System.out.println("4,update reservation");
			System.out.println("5,cancil reservation");
			System.out.println("0,exit ");
			Scanner sc = new Scanner(System.in);
			System.out.println("choose an option" );
			int choice = sc.nextInt();
			switch(choice)
			{
			case 1:
				reserveRoom(con,sc);
			break;
			case 2:
				viewReservation(con);
			break;
			case 3:
				getRoomNumber(con,sc);
			break;
			case 4:
				cancilReservation(con,sc);
			break;
			case 5:
				updateReservation(con,sc);
				break;
			case 0:
				exit();
			    sc.close();
			return;
			 default:
				System.out.println("invalid choice,try again");
			}
		}
	     } 
     catch (SQLException e) 
         {
    	 System.out.println("connection establish success");
		e.printStackTrace();
	     }
	}
	private static void reserveRoom(Connection con,Scanner sc)
	{
		String query = "INSERT INTO reserv (guest_name,room_number,contact_number)values('tom',111,99999)";
		   try {
			   System.out.println("enter guest name: ");
			   String guestname = sc.next();
			   sc.nextLine();
			   System.out.println("enter room number: ");
			   int roomNumber = sc.nextInt();
			   int contactNumber = sc.nextInt();
			   Statement stmt = con.createStatement();
			   int affectedRows = stmt.executeUpdate(query);
			   if(affectedRows > 0)
			   {
				System.out.println("Reservation successfull ");   
			   }
			   else
			   {
				   System.out.println("Reservation failed");
			   }
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
	}
	private static void viewReservation(Connection con) throws SQLException
	{
		String query = "SELECT reservation_id,guest_name,room_no,contact_no,reservation_date FROM reserv";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		System.out.println("current reservation:");
		System.out.println("+-------------+-----------------+------------------+");
		System.out.println("| reservation id | guest name | room number | contact number | reservation date|");
		while(rs.next())
		{
			int reservationid = rs.getInt("reservation_id");
			String guestname = rs.getString("guest_name");
			int contactnumber = rs.getInt("contact_no");
			int roomnumber = rs.getInt("room_no");
			String reservationdate = rs.getTimestamp("reservation_date").toString();
			System.out.println(reservationid+ "" +guestname+ "" +contactnumber+ "" +roomnumber+ "" +reservationdate+ "");
		}
		System.out.println("------------------------------------------------------------------");
	}
      private static void getRoomNumber(Connection con, Scanner sc) throws SQLException
      { 
    	  System.out.println("enter reservation id");
    	  int reservation_id = sc.nextInt();
    	  System.out.println("enter guest name");
    	  String guestname = sc.next();
    	  
    	  String query = "SELECT room_no FROM reserv " +"WHERE reservation_id = " +reservation_id+ "AND guestname ='" +guestname+ "'";
    	  Statement stmt = con.createStatement();
    	  ResultSet rs = stmt.executeQuery(query);
    	  if(rs.next())
    	  {
    		  int roomNumber = rs.getInt("room_no");
    		  System.out.println("room number for reservation ID" +reservation_id+ " and guest" +guestname+"is" +roomNumber); 
    	  } 
    	  else
    	  {
    		  System.out.println("Reservation not found for the given guest name");
    	  }
      }
      private static void updateReservation(Connection con, Scanner sc) throws SQLException
      {
    	  System.out.println("enter reservation ID update :");
    	  int reservationid = sc.nextInt();
    	  sc.nextLine();//consume the new line character
    	  
			
			  if(reservationExists(con,reservationid)); {
			  System.out.println("reservation not found for the given id"); 
			  return;}
		  System.out.println("enter the guest name");
    	  String newguestname = sc.nextLine();
    	  System.out.println("enter new room number:");
    	  int newroomnumber = sc.nextInt();
    	  System.out.println("enter new contact number");
    	  int newcontactnumber = sc.nextInt();
    	  String query = "UPDATE reserv SET guest_name = '" + newguestname + ", " +"room_no = " + newroomnumber + "," + "WHERE reservation_id ="+reservationid;
    	  try {
			Statement stmt = con.createStatement();
			int affectedrows = stmt.executeUpdate(query);
			if(affectedrows > 0)
			{
				System.out.println("reservation updated successfully");
			}
		} catch (SQLException e) 
    	  {
			e.printStackTrace();
		  }
      }
      private static void cancilReservation(Connection con,Scanner sc) throws SQLException
      {
    	  System.out.println("enter reservation id to cancil: ");
    	  int reservationid = sc.nextInt();
    	  if(reservationExists(con,reservationid)){
    		  System.out.println("reservation not found for the given ID");
    		  return; }
    	  String query = "DELETE FROM reserv WHERE reservation_id =" +reservationid;
    	  Statement stmt = con.createStatement();
    	  int affectedrows = stmt.executeUpdate(query);
    	  if(affectedrows > 0)
    	  {
    		  System.out.println("reservation canciled successfully");
    	  }
    	  else
    	  {
    		  System.out.println("cancilation failed");
    	  }
      }
      private static boolean reservationExists(Connection con,int reservationid) throws SQLException
      {
    	  String query = "SELECT reservation_id FROM reserv WHERE reservation_id ="+reservationid;
    	  Statement stmt =con.createStatement();
    	  ResultSet rs = stmt.executeQuery(query);
		return rs.next();
      }
      public static void exit()throws InterruptedException
      {
    	  System.out.println("exiting sysytem");
    	  int i = 5;
    	  while(i!=0) 
    	  {
    		  System.out.println(" ");
    		  Thread.sleep(1000);
    		  i--;
    	  }
    	  System.out.println();
    	  System.out.println("Thank you for using Hotel Reservation System!!!!!!!!!11");
      }
}
