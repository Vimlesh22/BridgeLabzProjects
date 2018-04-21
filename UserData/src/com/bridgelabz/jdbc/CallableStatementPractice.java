package com.bridgelabz.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CallableStatementPractice {
	
	static Scanner scanner=new Scanner(System.in);

	public static void main(String[] args) {
		int choice=0;
	
		
		do{
			System.out.println("**********************Menu**********************");
			System.out.println("1)Create Table in DataBase");
			System.out.println("2)Insert employee details into table");
			System.out.println("3)Update emplyoee in table");
			System.out.println("4)Delete employee from table");
			System.out.println("5)Display employees Details");
			System.out.println("6)Multiple insert into table");
			System.out.println("9)Exit");
			System.out.println("*************************************************");
			System.out.println("Enter Your choice:");
			choice=scanner.nextInt();
			switch (choice) {
			case 1:
				createTable();
				break;
				
			case 2:
				insertIntoTable();
				break;
				
			case 3:
				update();
				break;
				
			case 4:
				delete();
				break;
				
			case 5:
				display();
				break;

			case 6:
				multipleInsert();
				break;
				
			default:
				break;
			}
		
			
		}while(choice!=9);
	
		scanner.close();
	}
	
	public static void createTable()
	{
		Connection connection=null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			CallableStatement callableStatement=connection.prepareCall("{call createTable()}");
			callableStatement.execute();
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void insertIntoTable()
	{
		Connection connection=null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			CallableStatement callableStatement=connection.prepareCall("{call insertIntoTable(?,?)}");
			System.out.println("Enter Product Name");
			String name=scanner.next();
			System.out.println("Enter Product Price");
			int price=scanner.nextInt();
			callableStatement.setString(1, name);
			callableStatement.setInt(2, price);
			int result=callableStatement.executeUpdate();
			if(result==0)
			{
				System.out.println("No rows inserted!!!");
			}
			else
			{
				System.out.println(result+" rows inserted in table!!!");
			}
		} 
		catch (ClassNotFoundException | SQLException e) 
		{	
			e.printStackTrace();
		}
		
	}
	
	public static void update()
	{
		Connection connection=null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			CallableStatement callableStatement=connection.prepareCall("{call updateTable(?,?)}");
			System.out.println("Enter Product Name");
			String name=scanner.next();
			System.out.println("Enter Product Name should be replaced with");
			String replace=scanner.next();
			callableStatement.setString(1, name);
			callableStatement.setString(2, replace);
			int result=callableStatement.executeUpdate();
			if(result==0)
			{
				System.out.println("No rows updated");
			}
			else
			{
				System.out.println(result+" rows updated in database!!");
			}
		} 
		catch (ClassNotFoundException | SQLException e) 
		{	
			e.printStackTrace();
		}
	}
	
	public static void delete()
	{
		Connection connection=null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			CallableStatement callableStatement=connection.prepareCall("{call deleteFromTable(?)}");
			System.out.println("Enter id of product!!");
			int id=scanner.nextInt();
			callableStatement.setInt(1, id);
			int result=callableStatement.executeUpdate();
			if(result==0)
			{
				System.out.println("No rows deleted");
			}
			else
			{
				System.out.println(result+" rows deleted in database table!!");
			}
		} 
		catch (ClassNotFoundException | SQLException e) 
		{	
			e.printStackTrace();
		}
	}
	
	public static void multipleInsert()
	{
		Connection connection=null;
		int result=0;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			System.out.println("Enter how many rows you want to insert");
			int rows=scanner.nextInt();
			for(int i=0;i<rows;i++)
			{
				CallableStatement callableStatement=connection.prepareCall("{call insertIntoTable(?,?)}");
				System.out.println("Enter Product Name");
				String name=scanner.next();
				System.out.println("Enter Product Price");
				int price=scanner.nextInt();
				callableStatement.setString(1, name);
				callableStatement.setInt(2, price);
				result=callableStatement.executeUpdate();
			}
			if(result==0)
			{
				System.out.println("No rows inserted!!!");
			}
			else
			{
				System.out.println(rows+" rows inserted in table!!!");
			}
		} 
		catch (ClassNotFoundException | SQLException e) 
		{	
			e.printStackTrace();
		}
	}
	
	
	private static void display() {
		Connection connection=null;
		CallableStatement callableStatement=null;
		String selectQuery="select * from products";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			callableStatement=connection.prepareCall(selectQuery);
			ResultSet resultSet=callableStatement.executeQuery();
			while(resultSet.next())
			{
				System.out.println("Id="+resultSet.getInt(1)+
				" ProductName="+resultSet.getString(2)+
				" ProductPrice="+resultSet.getInt(3));
			}
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				try 
				{
					connection.close();
				} 
				catch (SQLException e) 
				{	
					e.printStackTrace();
				}
			}
			if(callableStatement!=null)
			{
				try 
				{
					callableStatement.close();
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		
		
	}

}
