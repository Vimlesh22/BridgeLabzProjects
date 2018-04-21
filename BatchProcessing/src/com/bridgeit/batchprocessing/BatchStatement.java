package com.bridgeit.batchprocessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BatchStatement {
	
	static Scanner scanner=new Scanner(System.in);
	public static void main(String[] args) {
		batchProcessing();
		
	}
	
	public static void batchProcessing()
	{
		Connection connection=null;
		Statement statement=null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Enter Name:");
			String name=scanner.next();
			System.out.println("Enter Salary:");
			int salary=scanner.nextInt();
			System.out.println("Enter Department Id:");
			int department_id=scanner.nextInt();
			String insert="insert into employees(name,salary,department_id) values"+"('"+name+"',"+salary+","+department_id+")";
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","root");
			statement=connection.createStatement();
			statement.addBatch(insert);
			
			
			System.out.println("Enter Name to be replaced");
			String replaceName=scanner.next();
			System.out.println("Enter Name from where to replace");
			String name1=scanner.next();
			String updatequery="update employees set name='"+replaceName+"' where name='"+name1+"'";
			statement=connection.createStatement();
			statement.addBatch(updatequery);
			
			System.out.println("Enter id of user to be deleted");
			int id=scanner.nextInt();
			String deleteQuery="delete from employees where id="+id;
			statement.addBatch(deleteQuery);
			statement.executeBatch();
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
			if(statement!=null)
			{
				try 
				{
					statement.close();
				} 
				catch (SQLException e) 
				{		
					e.printStackTrace();
				}
			}
		}
	}
}
