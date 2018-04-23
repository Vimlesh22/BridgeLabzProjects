package com.bridgeit.transaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Properties;
import java.util.Scanner;

public class TransactionPractise {
	
	static Scanner scanner=new Scanner(System.in);

	public static void main(String[] args) {
		int choice=0;
		do{
			System.out.println("********Menu**************");
			System.out.println("1)Transaction Start..");
			System.out.println("2)Set Save Point Demo");
			System.out.println("3)Exit");
			System.out.println("***************************");
			System.out.println("Enter Your choice");
			choice=scanner.nextInt();
			switch (choice) 
			{
			case 1:
				transaction();
				break;
				
			case 2:
				savePoint();
				break;
				
			default:
				break;
			}
		}while(choice!=3);
		transaction();
	
	}
	
	public static void savePoint()
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement2=null;
		Savepoint savepoint = null;
		Properties properties=new Properties();
		InputStream inputStream=null;
		try 
		{
			inputStream=new FileInputStream("/home/bridgeit/project/TransactionPractice/lib/DBproperties.properties");
			properties.load(inputStream);
			String driver=properties.getProperty("MYSQLJDBC.driver");
			String url=properties.getProperty("MYSQLJDBC.url");
			String username=properties.getProperty("MYSQLJDBC.username");
			String password=properties.getProperty("MYSQLJDBC.password");
			Class.forName(driver);
			connection=DriverManager.getConnection(url,username,password);
			connection.setAutoCommit(false);
			DatabaseMetaData databaseMetaData=connection.getMetaData();
			if(databaseMetaData.supportsSavepoints())
			{
				System.out.println("----insert data------");
				String insertQuery="insert into students(name,age,course) values(?,?,?)";
				preparedStatement=connection.prepareStatement(insertQuery);
				System.out.println("Enter Name:");
				String name=scanner.next();
				System.out.println("Enter Age");
				int age=scanner.nextInt();
				System.out.println("Enter Course:");
				String course=scanner.next();
				preparedStatement.setString(1, name);
				preparedStatement.setInt(2, age);
				preparedStatement.setString(3, course);
				preparedStatement.execute();
				savepoint=connection.setSavepoint("insertSavePoint");
				
				System.out.println("-------delete data--------");
				System.out.println("Enter No of rows to be deleted");
				int deleteRows=scanner.nextInt();
				String deleteQuery="delete from students where id=?";
				try
				{
					preparedStatement2=connection.prepareStatement(deleteQuery);
					preparedStatement2.setInt(1, deleteRows);
					preparedStatement2.execute();
				}
				catch(SQLException sq)
				{
					connection.rollback(savepoint);
				}
				connection.commit();	
			}
		}
		catch (SQLException | ClassNotFoundException | IOException e) 
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
			if(preparedStatement!=null)
			{
				try 
				{
					preparedStatement.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			if(preparedStatement2!=null)
			{
				try 
				{
					preparedStatement2.close();
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void transaction()
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user?useSSL=false","root","root");
			connection.setAutoCommit(false);
			String insertQuery="insert into students(name,age,course) values(?,?,?)";
			preparedStatement=connection.prepareStatement(insertQuery);
			System.out.println("Enter Name:");
			String name=scanner.next();
			System.out.println("Enter Age");
			int age=scanner.nextInt();
			System.out.println("Enter Course:");
			String course=scanner.next();
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, course);
			preparedStatement.execute();
			System.out.println("1)Do you want to Commit");
			System.out.println("2)Do you want to RollBack");
			System.out.println("3)Do you want to Continue Without commiting");
			int choice=scanner.nextInt();
			switch (choice) 
			{
			case 1:
				connection.commit();
				break;
			case 2:
				connection.rollback();
				break;
			case 3:
				transaction();
				break;
			default:
				break;
			}
		}
		catch (SQLException | ClassNotFoundException e) 
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
			if(preparedStatement!=null)
			{
				try 
				{
					preparedStatement.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
