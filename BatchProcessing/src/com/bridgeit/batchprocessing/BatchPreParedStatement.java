package com.bridgeit.batchprocessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BatchPreParedStatement {
	static Scanner scanner=new Scanner(System.in);


	public static void main(String[] args) 
	{
		int choice;
		do
		{
			System.out.println("*****************Menu*************");
			System.out.println("1)Insert");
			System.out.println("2)update");
			System.out.println("3)delete");
			System.out.println("4)exit");
			System.out.println("***********************************");
			System.out.println("Enter Your choice");
			choice=scanner.nextInt();
			switch (choice) 
			{
			case 1:
				insertBatchProcessing();
				break;
				
			case 2:
				updateBatchprocessing();
				break;
				
			case 3:
				deleteBatchProcessing();
				break;
				

			default:
				break;
			}
		}while(choice !=4);
	
	}
	
	public static void insertBatchProcessing() 
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			System.out.println("------Insert Data into table----------");
			System.out.println("Enter how many data you want to insert");
			int insert=scanner.nextInt();
			String[] name=new String[insert];
			int[] age=new int[insert];
			String[] course=new String[insert];
		
			for(int i=0;i<insert;i++)
			{
				System.out.println("***********************");
				System.out.println("Enter Name:");
				name[i]=scanner.next();
				System.out.println("Enter Age:");
				age[i]=scanner.nextInt();
				System.out.println("Enter Course:");
				course[i]=scanner.next();	
			}
			String sql="insert into students(name,age,course) values(?,?,?)";
			preparedStatement=connection.prepareStatement(sql);
			for(int i=0;i<insert;i++)
			{
				preparedStatement.setString(1, name[i]);
				preparedStatement.setInt(2, age[i]);
				preparedStatement.setString(3, course[i]);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		
		}
		catch (ClassNotFoundException | SQLException e1) 
		{
			e1.printStackTrace();
		}
	}


	public static void updateBatchprocessing()
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			System.out.println("-----Update data-------------");
			String updatequery="update students set name=? where name=?";
			preparedStatement=connection.prepareStatement(updatequery);
			System.out.println("Enter no. of rows to be updated:");
			int rowsUpdate=scanner.nextInt();
			String[] nameToSet=new String[rowsUpdate];
			String[] replaceName=new String[rowsUpdate]; 
			for(int i=0;i<rowsUpdate;i++)
			{
				System.out.println("Enter Name to Set");
				nameToSet[i]=scanner.next();
				System.out.println("Enter Name from where to replace");
				replaceName[i]=scanner.next();
				
				preparedStatement.setString(1, nameToSet[i]);
				preparedStatement.setString(2, replaceName[i]);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (ClassNotFoundException | SQLException e1) 
		{
			e1.printStackTrace();
		}
	}

	public static void deleteBatchProcessing()
	{
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		System.out.println("-------delete data--------");
		System.out.println("Enter No of rows to be deleted");
		int deleteRows=scanner.nextInt();
		String deleteQuery="delete from students where id=?";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			preparedStatement=connection.prepareStatement(deleteQuery);
			int id[]=new int[deleteRows];
			for(int i=0;i<deleteRows;i++)
			{
				System.out.println("Enter id of user to be deleted");
				id[i]=scanner.nextInt();
				preparedStatement.setInt(1, id[i]);
				preparedStatement.addBatch();
			}
				preparedStatement.executeBatch();
		} 
		catch (SQLException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}

}



