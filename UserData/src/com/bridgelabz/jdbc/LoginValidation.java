package com.bridgelabz.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginValidation {
	
	static Scanner scanner=new Scanner(System.in);

	public static void main(String[] args) {
		int choice=0;
		
		do{
			System.out.println("************Menu************");
			System.out.println("1)SignUp");
			System.out.println("2)SignIn");
			System.out.println("3)Exit");
			System.out.println("****************************");
			System.out.println("Enter Your choice");
			choice=scanner.nextInt();
			switch (choice) {
			
			case 1:
				signup();
				break;
			case 2:
				int result=signIn();
				if(result==0)
				System.out.println("Invalid Credentials....please try Again!!!");
				break;

			default:
				break;
			}
			
		}while(choice != 3);

	}
	
	public static void signup()
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String insertQuery="insert into login(user,password) values(?,?)";
		System.out.println("Enter User Name");
		String userName=scanner.next();
		System.out.println("Enter Password");
		String password=scanner.next();
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			
			preparedStatement=connection.prepareStatement(insertQuery);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	public static int signIn()
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String selectQuery="select * from login where user = ? and passowrd = ?";
		System.out.println("Enter User Name");
		String userName=scanner.next();
		System.out.println("Enter Password");
		String password=scanner.next();
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			
			preparedStatement=connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			ResultSet result=preparedStatement.executeQuery();
			if(result.next())
			{
				/*if(result.getString(2).equals(userName) && result.getString(3).equals(password))
				{*/
					System.out.println("Login Successfull for "+userName.toUpperCase());
					return 1;
				//}
			}
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		return 0;
	}

}
